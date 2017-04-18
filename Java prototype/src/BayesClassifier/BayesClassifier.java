package BayesClassifier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by Lilos on 4/9/2016.
 */
public class BayesClassifier
{
    public static String Style;
    public static String DetailedInformation = "";
    //region Private methods
    // Normal Gaussian distribution
    private  static double NormalDistribution(double x, double mean, double standard_dev)
    {
        double fact = standard_dev * Math.sqrt(2.0 * Math.PI);
        double expo = (x - mean) * (x - mean) / (2.0 * standard_dev * standard_dev);
        return Math.exp(-expo) / fact;
    }
    //endregion

    // Create Classification table with Mean and Variance for Bayes Classifier.
    public static void CreateClassificationTable() throws SQLException
    {
        Statement statement = DBHelper.connection.createStatement();
        String createClassificationTable = "use FineArtPaintings;\n" +
                "CREATE TABLE Classification\n" +
                "(\n" +
                "ID int IDENTITY(1,1) NOT NULL PRIMARY KEY,\n" +
                "Style varchar(256),\n";
        for (int i=1;i<=DBHelper.maxFeatures;i++)
        {
            createClassificationTable+="Feature_"+Integer.toString(i)+"_Mean float NULL,\n";
            createClassificationTable+="Feature_"+Integer.toString(i)+"_Variance float NULL,\n";
        }
        createClassificationTable+=")";
        statement.executeUpdate(createClassificationTable);
    }

    // Count Mean and Variance for training dataset.
    public static void TrainBayesClassifier() throws SQLException
    {
        Statement statement = DBHelper.connection.createStatement();
        Statement statement2 = DBHelper.connection.createStatement();

        String clearClassificationTable = "use FineArtPaintings;\n" +"DELETE FROM Classification";
        statement2.executeUpdate(clearClassificationTable);
        String getMeanAndVariance = "use FineArtPaintings;\n" +
                "SELECT Style, ";


        for (int i=1;i<=DBHelper.maxFeatures;i++)
        {
            if (i == DBHelper.maxFeatures)
            {
                getMeanAndVariance+="AVG(Feature_"+Integer.toString(i)+") as Feature_"+Integer.toString(i)+"_Mean, ";
                getMeanAndVariance+="VAR(Feature_"+Integer.toString(i)+") as Feature_"+Integer.toString(i)+"_Variance ";
            }
            else
            {
                getMeanAndVariance+="AVG(Feature_"+Integer.toString(i)+") as Feature_"+Integer.toString(i)+"_Mean, ";
                getMeanAndVariance+="VAR(Feature_"+Integer.toString(i)+") as Feature_"+Integer.toString(i)+"_Variance, ";
            }
        }

        getMeanAndVariance+="FROM Paintings\n" +
                "where TrainingSet=1 "+
                "group by Style";
        System.out.println(getMeanAndVariance);
        ResultSet resultSet = statement.executeQuery(getMeanAndVariance);

        while (resultSet.next())
        {
            System.out.println(resultSet.getString("Style"));
            String insertClassification = "use FineArtPaintings;\n" +
                    "INSERT INTO Classification (Style, ";
            for (int i=1;i<=DBHelper.maxFeatures;i++)
            {
                if (i == DBHelper.maxFeatures)
                {
                    insertClassification+="Feature_"+Integer.toString(i)+"_Mean, ";
                    insertClassification+="Feature_"+Integer.toString(i)+"_Variance ";
                }
                else
                {
                    insertClassification+="Feature_"+Integer.toString(i)+"_Mean, ";
                    insertClassification+="Feature_"+Integer.toString(i)+"_Variance, ";
                }
            }

            insertClassification+=")\n" +
                    "VALUES (" +
                    "\'"+resultSet.getString("Style")+"\', " ;

            for (int i=1;i<=DBHelper.maxFeatures;i++)
            {
                if (i == DBHelper.maxFeatures)
                {
                    insertClassification+=resultSet.getString("Feature_"+Integer.toString(i)+"_Mean")+", ";
                    insertClassification+=resultSet.getString("Feature_"+Integer.toString(i)+"_Variance");
                }
                else
                {
                    insertClassification+=resultSet.getString("Feature_"+Integer.toString(i)+"_Mean")+", ";
                    insertClassification+=resultSet.getString("Feature_"+Integer.toString(i)+"_Variance")+", ";
                }
            }
            insertClassification+=");";
            System.out.println(insertClassification);
            statement2.executeUpdate(insertClassification);
        }
        DBHelper.SetNeedReclassifyAdmin();
    }

    // Classify painting.
    public static void Classify(String path) throws SQLException
    {
        DetailedInformation = "";
        Statement statement = DBHelper.connection.createStatement();
        Statement statement2 = DBHelper.connection.createStatement();
        Statement statement3 = DBHelper.connection.createStatement();
        Statement statement4 = DBHelper.connection.createStatement();

        ArrayList subScores = new ArrayList();
        HashMap score = new HashMap();
        String getFeatures = "use FineArtPaintings;\n" +
                "SELECT * from Paintings " +
                "where Path LIKE \'%"+path+"\'";

        ResultSet resultSetFeatures = statement2.executeQuery(getFeatures);

        System.out.println(getFeatures);

        String getMeanAndVariance = "use FineArtPaintings;\n" +
                "SELECT * from Classification ";
        ResultSet resultSet = statement.executeQuery(getMeanAndVariance);
        while (resultSetFeatures.next())
        {
            while (resultSet.next())
            {
                System.out.println(resultSet.getString("Style"));

                for (int i = 1; i <= DBHelper.maxFeatures; i++)
                {

                    double mean = Double.parseDouble(resultSet.getString("Feature_" + Integer.toString(i) + "_Mean"));
                    double variance;
                    if (resultSet.getString("Feature_" + Integer.toString(i) + "_Variance")!=null)
                    {
                        variance = Double.parseDouble(resultSet.getString("Feature_" + Integer.toString(i) + "_Variance"));
                    }
                    else
                    {
                        variance=0;
                    }
                    double result = NormalDistribution(Double.parseDouble(resultSetFeatures.getString("Feature_" + Integer.toString(i))), mean, Math.sqrt(variance));
                    subScores.add(result);
                }

                double finalScore = 0;
                for (int z = 0; z < subScores.size(); z++)
                {
                    if (finalScore == 0)
                    {
                        finalScore = (double) subScores.get(z);
                        continue;
                    }
                    finalScore = finalScore * (double)subScores.get(z);
                }

                score.put(resultSet.getString("Style"), finalScore * 0.5);
            }
        }
        System.out.println(score);

        // Get max probability.
        Iterator it = score.entrySet().iterator();
        double max=-1;
        String styleCategory="";
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            DetailedInformation+="Style: "+(String)pair.getKey()+" Probability: "+Double.toString((double)pair.getValue())+"<br>";
            if ((double)pair.getValue() > max)
            {
                max = (double)pair.getValue();
                styleCategory = (String)pair.getKey();
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        System.out.println(styleCategory);
        Style = styleCategory;
        String insertPredictedStyle = "use FineArtPaintings;\n" +
                "UPDATE Paintings " +
                "SET Style=\'"+styleCategory +"\' " +
                "where Path LIKE \'%"+path+"\'";
        statement3.executeUpdate(insertPredictedStyle);
        String insertDetailedInfo = "use FineArtPaintings;\n" +
                "UPDATE Paintings " +
                "SET DetailedInfo=\'"+DetailedInformation +"\' "+
                "where Path LIKE \'%"+path+"\'";
        statement4.executeUpdate(insertDetailedInfo);
        DBHelper.SetPaintingNeedReclassifyForUser(path);
    }
}
