import BayesClassifier.DBHelper;

import javax.servlet.http.Part;
import java.sql.SQLException;

// Common methods.
public class CommonMethods
{
    // Establish connection to DB if it is not already established and check if user is admin or not.
    public static String ConnectToDBAndCheckUser()
    {
        try
        {
            if (DBHelper.connection == null)
            {
                // Establish connection to DB.
                DBHelper.ConnectToDB();
            }
            if (DBHelper.CheckUserIsAdmin(DBHelper.user))
            {
                // User is admin.
                return "1";
            }
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        // User is not admin.
        return "0";
    }

    // Extract file name.
    public static String ExtractFileName(Part part)
    {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items)
        {
            if (s.trim().startsWith("filename"))
            {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
