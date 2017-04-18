package BayesClassifier;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBHelper
{
    //region Protected fields
    protected static String dbURL = "jdbc:sqlserver://LILO\\SQLEXPRESS;integratedSecurity=true;";
    protected static int maxFeatures = 280;
    public static Connection connection;
    protected static String guest = "guest";
    public static String user = "guest";
    public static String PathToCurrentImage;
    public static final String SAVE_DIR = "uploadFiles";
    public static int thisUserID = 1;
    protected static int guestID = 1;

    protected static ArrayList<String> allPaintings = new ArrayList<>();
    //endregion

    //region Private methods
    // Extract features for painting.
    private static void ExtractClassemes(String pathToFileWithFileName, String pathToDirectory, String pathToFeaturesDirectory)
            throws IOException, InterruptedException
    {
        String[] command =
                {
                        "cmd",
                };
        Process p = Runtime.getRuntime().exec(command);
        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
        PrintWriter stdin = new PrintWriter(p.getOutputStream());
        stdin.println("D:");
        stdin.println("cd D:\\Sharp\\vlg_extractor_1.1.3");
        stdin.println("vlg_extractor.bat --extract_classemes=ASCII "+pathToFileWithFileName+" "+pathToDirectory+" "+pathToFeaturesDirectory);
        stdin.close();
        int returnCode = p.waitFor();
        System.out.println("Return code = " + returnCode);
    }
    //endregion

    //region Public methods
    // Establish connection to db.
    public static void ConnectToDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(dbURL);
        if (connection != null)
        {
            System.out.println("Connection established.");
        };
    }

    // Create table FineArtPaintings in BayesClassifier.
    public static void CreatePainitngsTable() throws SQLException
    {
        Statement statement = connection.createStatement();
        String createDBTable = "use FineArtPaintings;\n" +
                "CREATE TABLE Paintings\n" +
                "(\n" +
                "ID int IDENTITY(1,1) NOT NULL PRIMARY KEY,\n" +
                "Path varchar(256),\n" +
                "PathToFeatures varchar(256),\n" +
                "PathToFileWithFileName varchar(256),\n" +
                "TrainingSet bit,\n" +
                "Style varchar(256),\n";

        for (int i=1;i<=maxFeatures;i++)
        {
            createDBTable += "Feature_" + Integer.toString(i) + " float, \n";
        }
        createDBTable+="UserID int FOREIGN KEY REFERENCES Users(ID), " +
                "UserAttitude bit, \n" +
                "Reclassify bit, \n" +
                "DetailedInfo Varchar(1024))";
        System.out.println(createDBTable);
        statement.executeUpdate(createDBTable);
    }

    // Insert new painting with features to BayesClassifier.
    public static void InsertPaintingToTable(String path, boolean trainingSet, String style) throws SQLException, IOException {
        Statement statement = connection.createStatement();
        Statement statement2 = connection.createStatement();

        String userID = "use FineArtPaintings;\n" +
                "Select ID from Users where Username = \'" +
                user +
                "\'";
        System.out.println(userID);
        ResultSet resultUserID = statement2.executeQuery(userID);
        if (resultUserID.next())
        {
            thisUserID = Integer.parseInt(resultUserID.getString("ID"));
        }

        File paintingFile = new File(path);
        String pathToDirectory = paintingFile.getParent();
        String pathToFileWithFileName = pathToDirectory+"\\"+Arrays.asList(paintingFile.getName().split("\\.")).get(0)+".txt";
        String pathToFeaturesDirectory = pathToDirectory+"\\Features";
        String pathToFeatures = pathToFeaturesDirectory+"\\"+Arrays.asList(paintingFile.getName().split("\\.")).get(0)+"_classemes.ascii";

        // Create file with file name.
        List<String> lines = Arrays.asList(paintingFile.getName());
        Path fileWithFileName = Paths.get(pathToFileWithFileName);
        Files.write(fileWithFileName, lines, Charset.forName("UTF-8"));

        try
        {
            ExtractClassemes(pathToFileWithFileName, pathToDirectory, pathToFeaturesDirectory);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        File featureFile = new File(pathToFeatures);
        String insertPicture = "use FineArtPaintings;\n" +
                "INSERT INTO Paintings (Path, PathToFeatures, PathToFileWithFileName ,TrainingSet, Style, ";
        for (int i=1;i<=maxFeatures;i++)
        {
            if (i == maxFeatures)
            {
                insertPicture+="Feature_"+Integer.toString(i)+ ", ";
            }
            else
            {
                insertPicture += "Feature_" + Integer.toString(i) + ", ";
            }
        }
        String training;
        if (trainingSet)
        {
            training="1";
        }
        else
        {
            training = "0";
        }
        insertPicture+=" UserID, Reclassify )\n" +
                "VALUES (" +
                "\'"+path+"\'," +
                "\'"+pathToFeatures+"\'," +
                "\'"+pathToFileWithFileName+"\'," +
                training+", "+
                "\'"+style+"\', ";

        // Read features from ascii file.
        FileInputStream fileInputStream = new FileInputStream(featureFile);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        String line = null;
        int lineCount= 1;
        while ((line = bufferedReader.readLine()) != null && (lineCount<=maxFeatures))
        {
            if (lineCount == maxFeatures)
            {
                insertPicture+=line+ ", ";
            }
            else
            {
                insertPicture += line+ ", ";
            }
            lineCount++;
        }

        bufferedReader.close();
        insertPicture+="\'"+thisUserID+"\',0);";
        System.out.println(insertPicture);
        statement.executeUpdate(insertPicture);
    }

    // Create Users table.
    public static void CreateUsersTable() throws SQLException
    {
        Statement statement = connection.createStatement();
        String createDBTable = "use FineArtPaintings;\n" +
                "CREATE TABLE Users\n" +
                "(\n" +
                "ID int IDENTITY(1,1) NOT NULL PRIMARY KEY,\n" +
                "Username varchar(256),\n" +
                "Password varchar(256),\n" +
                "IsAdmin bit\n" +
                ")";
        System.out.println(createDBTable);
        statement.executeUpdate(createDBTable);
    }

    // Detect if username is not used.
    public static boolean CheckUsernameIsNotUsed(String username) throws SQLException
    {
        Statement statement = connection.createStatement();
        String checkUser = "use FineArtPaintings;\n" +
                "Select * from Users where Username = '" +
                username +
                "'";
        ResultSet resultUsers = statement.executeQuery(checkUser);
        if (resultUsers.next())
        {
            return false;
        }
        return true;
    }

    // Add new user.
    public static void InsertUserToTable(String username, String password, boolean isAdmin) throws SQLException
    {
        Statement statement = connection.createStatement();
        String admin="";
        if (isAdmin)
        {
            admin = "1";
        }
        else
        {
            admin = "0";
        }
        String insertUser = "use FineArtPaintings;\n" +
                "INSERT INTO Users (Username, Password, IsAdmin) \n" +
                "VALUES ('" +
                username +
                "', '" +
                password +
                "', '" +
                admin +
                "')";
        statement.executeUpdate(insertUser);
    }

    // Check username belongs to admin user.
    public static boolean CheckUserIsAdmin(String username) throws SQLException
    {
        Statement statement = connection.createStatement();
        String checkUserIsAdmin = "use FineArtPaintings;\n" +
                "SELECT IsAdmin from  Users  \n" +
                "where Username = '" +
                username +
                "'";
        ResultSet resultSet = statement.executeQuery(checkUserIsAdmin);
        while (resultSet.next())
        {
            if (resultSet.getString("IsAdmin").equals("1"))
            {
                return true;
            }
        }
        return false;
    }

    // Check credentials in DB.
    public static boolean LoginAsUser(String username, String password) throws SQLException
    {
        Statement statement = connection.createStatement();

        String checkUser = "use FineArtPaintings;\n" +
                "Select * from Users where Username = \'" +
                username +
                "\' and Password = \'"+
                password+
                "\'";
        System.out.println(checkUser);
        ResultSet resultUsers = statement.executeQuery(checkUser);
        if (resultUsers.next())
        {
            user = username;
            thisUserID = Integer.parseInt(resultUsers.getString("ID"));
            return true;
        }
        return false;
    }

    // Get all classified paintings for current user.
    public static ArrayList<String> GetPaintingsForUser() throws SQLException
    {
        ArrayList<String> paintingsForUser = new ArrayList<>();
        Statement statement = connection.createStatement();

        if (thisUserID == guestID)
        {
            paintingsForUser = null;
            return null;
        }

        String getPaintings = "use FineArtPaintings;\n" +
                "Select Path from Paintings where UserID = \'" +
                thisUserID +
                "\'";

        System.out.println(getPaintings);
        ResultSet resultSet = statement.executeQuery(getPaintings);
        while (resultSet.next())
        {
            paintingsForUser.add(resultSet.getString("Path"));
        }
        return paintingsForUser;
    }

    // Set Reclassify after classifying of painting.
    public static void SetPaintingNeedReclassifyForUser(String pathToPainting) throws SQLException
    {
        Statement statement = connection.createStatement();

        String setReclassify = "use FineArtPaintings;\n" +
                "UPDATE Paintings " +
                "SET Reclassify=0 where UserID = '" +
                thisUserID +
                "\'" +
                "and Path LIKE \'%" +
                pathToPainting +
                "\'";

        System.out.println(setReclassify);
        statement.executeUpdate(setReclassify);
    }

    public static boolean GetPaintingNeedReclassifyForUser(String pathToPainting) throws SQLException
    {
        String reclassifyNeeded="";
        Statement statement = connection.createStatement();

        if (thisUserID == guestID)
        {
            return false;
        }

        String getPaintingReclassify = "use FineArtPaintings;\n" +
                "Select Reclassify from Paintings where UserID = \'" +
                thisUserID +
                "\'" +
                "and Path LIKE \'%" +
                pathToPainting +
                "\'";

        System.out.println(getPaintingReclassify);
        ResultSet resultSet = statement.executeQuery(getPaintingReclassify);
        while (resultSet.next())
        {
            reclassifyNeeded = resultSet.getString("Reclassify");
        }

        System.out.println(reclassifyNeeded);
        if (reclassifyNeeded.equals("0"))
            return false;

        return true;
    }

    // Get style for specified painting.
    public static String GetPaintingStyle(String pathToPainting) throws SQLException
    {
        String style = "";
        Statement statement = connection.createStatement();

        if (thisUserID == guestID)
        {
            return style;
        }

        String getPaintingStyle = "use FineArtPaintings;\n" +
                "Select Style from Paintings where UserID = \'" +
                thisUserID +
                "\'" +
                "and Path LIKE \'%" +
                pathToPainting +
                "\'";

        System.out.println(getPaintingStyle);
        ResultSet resultSet = statement.executeQuery(getPaintingStyle);
        while (resultSet.next())
        {
            style = resultSet.getString("Style");
        }
        return style;
    }

    // Set style for specified painting for admin.
    public static void SetPaintingStyleAdmin(String pathToPainting, String style) throws SQLException
    {
        Statement statement = connection.createStatement();

        String setPaintingStyle = "use FineArtPaintings;\n" +
                "UPDATE Paintings " +
                "SET Style=\'"+style+"\' "+
                "where Path LIKE \'%"+pathToPainting+"\'";

        System.out.println(setPaintingStyle);
        statement.executeUpdate(setPaintingStyle);
    }

    // Set Reclassify after retrain classificator.
    public static void SetNeedReclassifyAdmin() throws SQLException
    {
        Statement statement = connection.createStatement();

        String setReclassify = "use FineArtPaintings;\n" +
                "UPDATE Paintings " +
                "SET Reclassify=1 ";

        System.out.println(setReclassify);
        statement.executeUpdate(setReclassify);
    }

    public static boolean GetPaintingNeedReclassifyForAdmin(String pathToPainting) throws SQLException
    {
        String reclassifyNeeded="";
        Statement statement = connection.createStatement();

        if (thisUserID == guestID)
        {
            return false;
        }

        String getPaintingReclassify = "use FineArtPaintings;\n" +
                "Select Reclassify from Paintings "+
                "where Path LIKE \'%" +
                pathToPainting +
                "\'";

        System.out.println(getPaintingReclassify);
        ResultSet resultSet = statement.executeQuery(getPaintingReclassify);
        while (resultSet.next())
        {
            reclassifyNeeded = resultSet.getString("Reclassify");
        }

        if (reclassifyNeeded.equals("0"))
            return false;

        return true;
    }

    // Get style for specified painting for admin.
    public static String GetPaintingStyleAdmin(String pathToPainting) throws SQLException
    {
        String style = "";
        Statement statement = connection.createStatement();

        String getPaintingStyle = "use FineArtPaintings;\n" +
                "Select Style from Paintings "+
                "where Path LIKE \'%" +
                pathToPainting +
                "\'";

        System.out.println(getPaintingStyle);
        ResultSet resultSet = statement.executeQuery(getPaintingStyle);
        while (resultSet.next())
        {
            style = resultSet.getString("Style");
        }
        return style;
    }

    // Get detailed info for specified painting.
    public static String GetDetailedInfo(String pathToPainting) throws SQLException
    {
        String detailedInfo = "";
        Statement statement = connection.createStatement();

        if (thisUserID == guestID)
        {
            return detailedInfo;
        }

        String getDetailedInfo = "use FineArtPaintings;\n" +
                "Select DetailedInfo from Paintings where UserID = \'" +
                thisUserID +
                "\'" +
                "and Path LIKE \'%" +
                pathToPainting +
                "\'";

        ResultSet resultSet = statement.executeQuery(getDetailedInfo);
        while (resultSet.next())
        {
            detailedInfo = resultSet.getString("DetailedInfo");
        }
        return detailedInfo;
    }

    // Get detailed info for specified painting for admin.
    public static String GetDetailedInfoAdmin(String pathToPainting) throws SQLException
    {
        Statement statement = connection.createStatement();

        String getDetailedInfo = "use FineArtPaintings;\n" +
                "Select DetailedInfo from Paintings "+
                "where Path LIKE \'%" +
                pathToPainting +
                "\'";
        System.out.println(getDetailedInfo);

        ResultSet resultSet = statement.executeQuery(getDetailedInfo);
        if (resultSet.next())
        {
            return resultSet.getString("DetailedInfo");
        }
        return null;
    }

    // Get all paintings for admin.
    public static ArrayList<String> GetAllPaintings() throws SQLException
    {
        Statement statement = connection.createStatement();
        ArrayList<String> paintingsForAdmin = new ArrayList<>();

        String getPaintings = "use FineArtPaintings;\n" +
                "Select Path from Paintings";

        System.out.println(getPaintings);
        ResultSet resultSet = statement.executeQuery(getPaintings);
        while (resultSet.next())
        {
            paintingsForAdmin.add(resultSet.getString("Path"));
        }
        return paintingsForAdmin;
    }

    // Remove painting from training set.
    public static void RemovePaintingFromTrainingSet(String pathToImage) throws SQLException
    {
        Statement statement = connection.createStatement();

        String removePictureFromTrainigSet = "use FineArtPaintings;\n" +
                "UPDATE Paintings " +
                "SET TrainingSet=\'0\' "+
                "where Path LIKE \'%"+pathToImage+"\'";

        System.out.println(removePictureFromTrainigSet);
        statement.executeUpdate(removePictureFromTrainigSet);
    }

    // Add painting to training set.
    public static void AddPaintingToTrainingSet(String pathToImage) throws SQLException
    {
        Statement statement = connection.createStatement();

        String addPictureToTrainingSet = "use FineArtPaintings;\n" +
                "UPDATE Paintings " +
                "SET TrainingSet=\'1\' "+
                "where Path LIKE \'%"+pathToImage+"\'";

        System.out.println(addPictureToTrainingSet);
        statement.executeUpdate(addPictureToTrainingSet);
    }

    // Get info if painting is in Trainings set for admin.
    public static String GetTrainingSetAdmin(String pathToImage) throws SQLException
    {
        Statement statement = connection.createStatement();

        String getPictureTrainingSet = "use FineArtPaintings;\n" +
                "select TrainingSet from Paintings " +
                "where Path LIKE \'%"+pathToImage+"\'";

        System.out.println(getPictureTrainingSet);
        ResultSet resultSet = statement.executeQuery(getPictureTrainingSet);
        while (resultSet.next())
        {
            if(resultSet.getString("TrainingSet").equals("1"))
            {
                return "Yes";
            }
        }
        return "No";
    }

    // Update Paintings table with user attitude to classification.
    public static void InsertUserAttitudeToTable(String attitude, String pathToImage) throws SQLException
    {
        Statement statement = connection.createStatement();
        String insertUserAttitude = "use FineArtPaintings;\n" +
                "UPDATE Paintings " +
                "SET UserAttitude= \'" +
                attitude+"\' "+
                "where Path LIKE \'%"+pathToImage+"\' and userID = \'" +
                +thisUserID+"\'";

        System.out.println(insertUserAttitude);
        statement.executeUpdate(insertUserAttitude);
    }

    // Get user attitude to classification.
    public static String GetUserAttitudeAdmin(String pathToImage) throws SQLException
    {
        Statement statement = connection.createStatement();
        String getUserAttitude = "use FineArtPaintings;\n" +
                "select UserAttitude from  Paintings " +
                "where Path LIKE \'%"+pathToImage+"\'";

        System.out.println(getUserAttitude);

        ResultSet resultSet = statement.executeQuery(getUserAttitude);
        while (resultSet.next())
        {
            if (resultSet.getString("UserAttitude") == null)
            {
                return "In doubt";
            }
            if(resultSet.getString("UserAttitude").equals("0"))
            {
                return "Disagreed";
            }
            else if (resultSet.getString("UserAttitude").equals("1"))
            {
                return "Agreed";
            }
        }
        return "In doubt";
    }

    // Removes painting for current user.
    public static void RemovePaintingForUser(String pathToImage) throws SQLException
    {
        Statement statement = connection.createStatement();

        String removePaintingForUser = "use FineArtPaintings;\n" +
                "UPDATE Paintings " +
                "SET UserID=2 "+
                "where Path LIKE \'%"+pathToImage+"\' and userID = \'" +
                +thisUserID+"\'";

        System.out.println(removePaintingForUser);
        statement.executeUpdate(removePaintingForUser);
    }

    // Delete painting from DB and file from server.
    public static void DeletePainting(String pathToImage) throws SQLException
    {
        Statement statement = connection.createStatement();
        File picture = new File("D:\\Java\\Classification\\out\\artifacts\\Classification_war_exploded\\" +pathToImage);
        picture.delete();

        String removePaintingForUser = "use FineArtPaintings;\n" +
                "DELETE from Paintings " +
                "where Path LIKE \'%"+pathToImage+"\'";

        System.out.println(removePaintingForUser);
        statement.executeUpdate(removePaintingForUser);
    }

    public static String GetUserOfPaintingAdmin(String pathToImage) throws SQLException
    {
        Statement statement = connection.createStatement();
        Statement statement2 = connection.createStatement();
        String currentUserID = "";

        String getUserID = "use FineArtPaintings;\n" +
                "select UserID from Paintings " +
                "where Path LIKE \'%"+pathToImage+"\'";

        ResultSet resultUserID = statement.executeQuery(getUserID);

        if (resultUserID.next())
        {
            currentUserID =resultUserID.getString("UserID");
        }

        String username = "use FineArtPaintings;\n" +
                "Select Username from Users where ID = \'" +
                currentUserID +
                "\'";
        System.out.println(username);
        ResultSet resultUsername= statement2.executeQuery(username);
        if (resultUsername.next())
        {
            return resultUsername.getString("Username");
        }
        return null;
    }
    //endregion
}
