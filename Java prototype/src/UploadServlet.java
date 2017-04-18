import BayesClassifier.BayesClassifier;
import BayesClassifier.DBHelper;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)   // 50MB
// Servlet for handling Home page uploading image.
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());

        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + DBHelper.SAVE_DIR;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists())
        {
            fileSaveDir.mkdir();
        }

        BayesClassifier.DetailedInformation="";
        String pathToFileForClassification="";
        for (Part part : request.getParts())
        {
            String fileName = CommonMethods.ExtractFileName(part);

            if (fileName.isEmpty())
            {
                request.setAttribute("usernameDisplay", DBHelper.user);
                request.setAttribute("message2", "Please select the painting.");
                RequestDispatcher view = request.getRequestDispatcher("HomePage");
                view.forward(request, response);
                return;
            }

            // Create unique file name.
            String onlyFileName = fileName.split("\\.")[0];
            String onlyFileExtension = fileName.split("\\.")[1];
            fileName=onlyFileName+"_"+ UUID.randomUUID()+"."+onlyFileExtension;

            part.write(savePath + File.separator + fileName);
            pathToFileForClassification = savePath + File.separator + fileName;
        }
        File selectedFile = new File(pathToFileForClassification);
        DBHelper.PathToCurrentImage = pathToFileForClassification;


            try
            {
                DBHelper.InsertPaintingToTable(pathToFileForClassification, false, null);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            try
            {
                BayesClassifier.Classify(pathToFileForClassification);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }


            request.setAttribute("usernameDisplay", DBHelper.user);
            request.setAttribute("resultStyle", BayesClassifier.Style);
            request.setAttribute("DetailedInfo", BayesClassifier.DetailedInformation);
            request.setAttribute("pathToImage", DBHelper.SAVE_DIR+File.separator+selectedFile.getName());
            RequestDispatcher view = request.getRequestDispatcher("ResultPage.jsp");
            view.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

    }
}