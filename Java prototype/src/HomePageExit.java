import BayesClassifier.DBHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Lilos on 5/28/2016.
 */
@WebServlet("/HomePageExit")
public class HomePageExit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBHelper.user = "guest";
        DBHelper.thisUserID = 1;
        try
        {
            if (DBHelper.connection == null)
            {
                DBHelper.ConnectToDB();
            }
                // Logged out.
                request.setAttribute("message", "User logged out.");
                if (DBHelper.CheckUserIsAdmin(DBHelper.user))
                {
                    request.setAttribute("IsAdmin", "1");
                }
                else
                {
                    request.setAttribute("IsAdmin", "0");
                }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        request.setAttribute("usernameDisplay", DBHelper.user);
        RequestDispatcher view = request.getRequestDispatcher("HomePage.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
