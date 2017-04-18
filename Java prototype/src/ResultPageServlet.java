import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import BayesClassifier.*;

@WebServlet("/ResultPageServlet")
// Servlet for handling Result page.
public class ResultPageServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
        String agreeButtonClicked = request.getParameter("Agree");
        String disagreeButtonClicked = request.getParameter("Disagree");
        String inDoubtButtonClicked = request.getParameter("InDoubt");

        if (agreeButtonClicked!=null)
        {
            // User agreed with classification.
            try
            {
                DBHelper.InsertUserAttitudeToTable("1", DBHelper.PathToCurrentImage );
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if (disagreeButtonClicked!=null)
        {
            // User agreed with classification.
            try
            {
                DBHelper.InsertUserAttitudeToTable("0", DBHelper.PathToCurrentImage );
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        File selectedFile = new File(DBHelper.PathToCurrentImage);

        request.setAttribute("usernameDisplay", DBHelper.user);
        request.setAttribute("resultStyle", BayesClassifier.Style);
        request.setAttribute("DetailedInfo", BayesClassifier.DetailedInformation);
        request.setAttribute("pathToImage", DBHelper.SAVE_DIR+ File.separator+selectedFile.getName());
        request.setAttribute("message", "Thank you for response.");
        RequestDispatcher view = request.getRequestDispatcher("ResultPage.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
