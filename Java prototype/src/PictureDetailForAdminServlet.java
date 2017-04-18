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

@WebServlet(name = "PictureDetailForAdminServlet")
// Servlet for handling Picture Detail page for admin.
public class PictureDetailForAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
        String addToTrainingSetButtonClicked = request.getParameter("AddToTrainingSet");
        String removeFromTrainingSetButtonClicked = request.getParameter("RemoveFromTrainingSet");
        String deleteButtonClicked = request.getParameter("Delete");
        String changeStyleButtonClicked = request.getParameter("ChangeStyle");
        String reclassifyButtonClicked = request.getParameter("Reclassify");
        String currentStyle = request.getParameter("style");

        String pathToImage = DBHelper.PathToCurrentImage;
        String styleOfPainting = "";
        String detailedInfo = "";
        String userAgreed = "";
        String inTrainingSet = "";
        String userOfPainting = "";

        if (addToTrainingSetButtonClicked!=null)
        {
            try
            {
                DBHelper.AddPaintingToTrainingSet(pathToImage);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            request.setAttribute("message", "Painting added to Trainings Set.");
        }
        else if (removeFromTrainingSetButtonClicked!=null)
        {
            try
            {
                DBHelper.RemovePaintingFromTrainingSet(pathToImage);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            request.setAttribute("message", "Painting removed from Trainings Set.");
        }
        else if (deleteButtonClicked!=null)
        {
            try
            {
                DBHelper.DeletePainting(pathToImage);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }

            request.setAttribute("usernameDisplay", DBHelper.user);
            RequestDispatcher view = request.getRequestDispatcher("HistoryForAdminServlet");
            view.forward(request, response);
            return;
        }
        else if (changeStyleButtonClicked!=null)
        {
            try
            {
                DBHelper.SetPaintingStyleAdmin(pathToImage, currentStyle);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            request.setAttribute("message", "Painting style set.");
        }
        else if (reclassifyButtonClicked!=null)
        {
            try
            {
                BayesClassifier.Classify(DBHelper.PathToCurrentImage);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            request.setAttribute("message", "Painting reclassified.");
        }

        try
        {
            styleOfPainting = DBHelper.GetPaintingStyleAdmin(pathToImage);
            detailedInfo = DBHelper.GetDetailedInfoAdmin(pathToImage);
            if (detailedInfo == null || detailedInfo.isEmpty())
            {
                detailedInfo = "Style was defined manually. No detailed info available.";
            }
            userAgreed = DBHelper.GetUserAttitudeAdmin(pathToImage);
            inTrainingSet = DBHelper.GetTrainingSetAdmin(pathToImage);
            userOfPainting = DBHelper.GetUserOfPaintingAdmin(pathToImage);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        DBHelper.PathToCurrentImage = pathToImage;

        request.setAttribute("usernameDisplay", DBHelper.user);
        request.setAttribute("resultStyle", styleOfPainting);
        request.setAttribute("DetailedInfo", detailedInfo);
        request.setAttribute("UserAttitude", userAgreed);
        request.setAttribute("InTrainingSet", inTrainingSet);
        request.setAttribute("User", userOfPainting);
        request.setAttribute("pathToImage", pathToImage);
        RequestDispatcher view = request.getRequestDispatcher("PictureDetailForAdminPage.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
        String path = request.getParameter("path");
        String styleOfPainting = "";
        String detailedInfo = "";
        String userAgreed = "";
        String inTrainingSet = "";
        String userOfPainting = "";

        File selectedFile = new File(path);
        String pathToImage = DBHelper.SAVE_DIR+File.separator+selectedFile.getName();

        try
        {
            styleOfPainting = DBHelper.GetPaintingStyleAdmin(pathToImage);
            detailedInfo = DBHelper.GetDetailedInfoAdmin(pathToImage);
            if (detailedInfo == null || detailedInfo.isEmpty())
            {
                detailedInfo = "Style was defined manually. No detailed info available.";
            }
            userAgreed = DBHelper.GetUserAttitudeAdmin(pathToImage);
            inTrainingSet = DBHelper.GetTrainingSetAdmin(pathToImage);
            userOfPainting = DBHelper.GetUserOfPaintingAdmin(pathToImage);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        DBHelper.PathToCurrentImage = pathToImage;

        request.setAttribute("usernameDisplay", DBHelper.user);
        request.setAttribute("resultStyle", styleOfPainting);
        request.setAttribute("DetailedInfo", detailedInfo);
        request.setAttribute("UserAttitude", userAgreed);
        request.setAttribute("InTrainingSet", inTrainingSet);
        request.setAttribute("User", userOfPainting);
        request.setAttribute("pathToImage", pathToImage);
        RequestDispatcher view = request.getRequestDispatcher("PictureDetailForAdminPage.jsp");
        view.forward(request, response);
    }
}
