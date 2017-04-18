import BayesClassifier.BayesClassifier;
import BayesClassifier.DBHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "PictureDetailForUserServlet")
// Servlet for handling Picture Detail page for user.
public class PictureDetailForUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
        String agreeButtonClicked = request.getParameter("Agree");
        String disagreeButtonClicked = request.getParameter("Disagree");
        String inDoubtButtonClicked = request.getParameter("InDoubt");
        String reclassifyButtonClicked = request.getParameter("Reclassify");
        String deleteButtonClicked = request.getParameter("Delete");

        String styleOfPainting = "";
        String detailedInfo = "";

        String pathToImage = DBHelper.PathToCurrentImage;

        if (agreeButtonClicked!=null)
        {
            // User agreed with classification.
            try
            {
                DBHelper.InsertUserAttitudeToTable("1", DBHelper.PathToCurrentImage );
                request.setAttribute("message", "Thank you for response.");
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if (disagreeButtonClicked!=null)
        {
            // User disagreed with classification.
            try
            {
                DBHelper.InsertUserAttitudeToTable("0", DBHelper.PathToCurrentImage );
                request.setAttribute("message", "Thank you for response.");
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if (inDoubtButtonClicked!=null)
        {
            request.setAttribute("message", "Thank you for response.");
        }
        else if (reclassifyButtonClicked!=null)
        {
            // Classify image
            try
            {
                BayesClassifier.Classify(DBHelper.PathToCurrentImage);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if (deleteButtonClicked!=null)
        {
            // Delete painting.
            try {
                DBHelper.RemovePaintingForUser(DBHelper.PathToCurrentImage);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Trying to navigate to HistoryForUserServlet");
            RequestDispatcher view = request.getRequestDispatcher("HistoryForUserServlet");
            view.forward(request, response);
            return;
        }
        try
        {
            styleOfPainting = DBHelper.GetPaintingStyle(pathToImage);
            detailedInfo = DBHelper.GetDetailedInfo(pathToImage);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        request.setAttribute("usernameDisplay", DBHelper.user);
        request.setAttribute("resultStyle", styleOfPainting);
        request.setAttribute("DetailedInfo", detailedInfo);
        request.setAttribute("pathToImage", pathToImage);
        try {
            if (DBHelper.GetPaintingNeedReclassifyForUser(DBHelper.PathToCurrentImage))
            {
                request.setAttribute("messageNeedReclassify", "Classificator was retrained");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher view = request.getRequestDispatcher("PictureDetailForUserPage.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
        String path = request.getParameter("path");
        String styleOfPainting = "";
        String detailedInfo = "";

        File selectedFile = new File(path);
        String pathToImage = DBHelper.SAVE_DIR+File.separator+selectedFile.getName();

        try
        {
            styleOfPainting = DBHelper.GetPaintingStyle(pathToImage);
            detailedInfo = DBHelper.GetDetailedInfo(pathToImage);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        DBHelper.PathToCurrentImage = pathToImage;

        request.setAttribute("usernameDisplay", DBHelper.user);
        request.setAttribute("resultStyle", styleOfPainting);
        request.setAttribute("DetailedInfo", detailedInfo);
        request.setAttribute("pathToImage", pathToImage);
        try {
            if (DBHelper.GetPaintingNeedReclassifyForUser(DBHelper.PathToCurrentImage))
            {
                request.setAttribute("messageNeedReclassify", "Classifier was retrained");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher view = request.getRequestDispatcher("PictureDetailForUserPage.jsp");
        view.forward(request, response);
    }
}
