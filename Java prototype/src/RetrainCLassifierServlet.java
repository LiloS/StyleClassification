import BayesClassifier.BayesClassifier;
import BayesClassifier.DBHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(name = "RetrainCLassifierServlet")
// Servlet for handling Retrai Classifier request.
public class RetrainCLassifierServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());

        try
        {
            BayesClassifier.TrainBayesClassifier();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        request.setAttribute("usernameDisplay", DBHelper.user);
        request.setAttribute("message2", "Classifier retrained.");
        RequestDispatcher view = request.getRequestDispatcher("SettingsPage.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
