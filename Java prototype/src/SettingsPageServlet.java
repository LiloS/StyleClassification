import BayesClassifier.BayesClassifier;
import BayesClassifier.DBHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(name = "SettingsPageServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)   // 50MB
// Servlet for handling Settings page.
public class SettingsPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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

        // Get file.
        Part part = (Part) request.getParts().toArray()[0];
        String fileName = CommonMethods.ExtractFileName(part);
        if (fileName == null || fileName.isEmpty()) {
            request.setAttribute("usernameDisplay", DBHelper.user);
            request.setAttribute("message", "Please select the painting.");
            RequestDispatcher view = request.getRequestDispatcher("SettingsPage.jsp");
            view.forward(request, response);
            return;
        }

        // Create unique file name.
        String onlyFileName = fileName.split("\\.")[0];
        String onlyFileExtension = fileName.split("\\.")[1];
        fileName = onlyFileName + "_" + UUID.randomUUID() + "." + onlyFileExtension;

        part.write(savePath + File.separator + fileName);
        pathToFileForClassification = savePath + File.separator + fileName;

        // Get style.
        String currentStyle = request.getParameter("style");
        System.out.println(currentStyle);

        DBHelper.PathToCurrentImage = pathToFileForClassification;

        if (currentStyle == null || currentStyle.isEmpty())
            {
                request.setAttribute("usernameDisplay", DBHelper.user);
                request.setAttribute("message", "Style should not be empty.");
                RequestDispatcher view = request.getRequestDispatcher("SettingsPage.jsp");
                view.forward(request, response);
                return;
            }
            try
            {
                DBHelper.InsertPaintingToTable(pathToFileForClassification, true, currentStyle);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }


        request.setAttribute("usernameDisplay", DBHelper.user);
        request.setAttribute("message", "Picture added to Training Set.");
        RequestDispatcher view = request.getRequestDispatcher("SettingsPage.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
