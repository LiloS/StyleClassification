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

@WebServlet(name = "HistoryForAdminServlet")
// Servlet for handling History page for admin.
public class HistoryForAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
        ArrayList<String> paintings = new ArrayList<>();

        try
        {
            paintings = DBHelper.GetAllPaintings();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        ArrayList<String> paintingsForUserDisplayPaths = new ArrayList<>();
        for (int i=0; i<paintings.size();i++)
        {
            File selectedFile = new File(paintings.get(i));
            System.out.println(DBHelper.SAVE_DIR+File.separator+selectedFile.getName());
            paintingsForUserDisplayPaths.add(DBHelper.SAVE_DIR+File.separator+selectedFile.getName());
        }

        request.setAttribute("usernameDisplay", DBHelper.user);
        request.setAttribute("PathToAllImages", paintingsForUserDisplayPaths);
        RequestDispatcher view = request.getRequestDispatcher("HistoryForAdmin.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("IsAdmin", CommonMethods.ConnectToDBAndCheckUser());
        ArrayList<String> paintings = new ArrayList<>();

        try
        {
            paintings = DBHelper.GetAllPaintings();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        ArrayList<String> paintingsForUserDisplayPaths = new ArrayList<>();
        for (int i=0; i<paintings.size();i++)
        {
            File selectedFile = new File(paintings.get(i));
            System.out.println(DBHelper.SAVE_DIR+File.separator+selectedFile.getName());
            paintingsForUserDisplayPaths.add(DBHelper.SAVE_DIR+File.separator+selectedFile.getName());
        }

        request.setAttribute("usernameDisplay", DBHelper.user);
        request.setAttribute("PathToAllImages", paintingsForUserDisplayPaths);
        RequestDispatcher view = request.getRequestDispatcher("HistoryForAdmin.jsp");
        view.forward(request, response);
    }
}
