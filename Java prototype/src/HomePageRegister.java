import BayesClassifier.DBHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/HomePageRegister")
// Servlet for handling Home page register.
public class HomePageRegister extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
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

        System.out.println("REGISTER");

        try
        {
            if (DBHelper.CheckUsernameIsNotUsed(username))
            {
                // Register only not admin.
                DBHelper.InsertUserToTable(username, password, false);
                request.setAttribute("message", "User registered successfully!");
            }
            else
            {
                request.setAttribute("message", "Username already in use. Please select another.");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
        request.setAttribute("usernameDisplay", DBHelper.user);
        RequestDispatcher view = request.getRequestDispatcher("HomePage.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
