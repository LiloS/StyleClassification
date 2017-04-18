import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import BayesClassifier.*;

@WebServlet("/HomePageLogin")
// Servlet for handling Home page login.
public class HomePageLogin extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.isEmpty() ||  password.isEmpty())
        {
            request.setAttribute("message", "Username and password should not be empty.");
            request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
            request.setAttribute("usernameDisplay", DBHelper.user);
            RequestDispatcher view = request.getRequestDispatcher("HomePage.jsp");
            view.forward(request, response);
            return;
        }

        try
        {
            if (DBHelper.connection == null)
            {
                DBHelper.ConnectToDB();
            }
            if (DBHelper.LoginAsUser(username, password))
            {
                // Login was successfull.
                request.setAttribute("message", "User logged in.");
                if (DBHelper.CheckUserIsAdmin(username))
                {
                    request.setAttribute("IsAdmin", "1");
                }
                else
                {
                    request.setAttribute("IsAdmin", "0");
                }
            }
            else
            {
                request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
                // Username or password is not correct.
                request.setAttribute("message", "Username or password is not correct.");
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
