import BayesClassifier.DBHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "HomePage")
// Servlet for handling Home page request.
public class HomePage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
        request.setAttribute("usernameDisplay", DBHelper.user);
        RequestDispatcher view = request.getRequestDispatcher("HomePage.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
        request.setAttribute("usernameDisplay", DBHelper.user);
        RequestDispatcher view = request.getRequestDispatcher("HomePage.jsp");
        view.forward(request, response);
    }
}
