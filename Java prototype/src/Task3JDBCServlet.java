import BayesClassifier.DBHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Created by Lilos on 4/14/2016.
 */
@WebServlet("/Task3JDBCServlet")
public class Task3JDBCServlet extends HttpServlet {

    private static String dbURL = "jdbc:sqlserver://LILO\\SQLEXPRESS;integratedSecurity=true;";
    private static Connection connection;

    // Establish connection to db.
    private static void ConnectToDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(dbURL);
        if (connection != null)
        {
            System.out.println("Connection established.");
        };
    }

    // Create table FineArtPaintings in BayesClassifier.
    private static void CreatePhonesTable() throws SQLException
    {
        Statement statement = connection.createStatement();
        String createDBTable = "use Task3JDBC;\n" +
                "CREATE TABLE Phones\n" +
                "(\n" +
                "ID int IDENTITY(1,1) NOT NULL PRIMARY KEY,\n" +
                "LastName varchar(256),\n" +
                "Address varchar(256),\n" +
                "PhoneNumber varchar(256))\n";

        System.out.println(createDBTable);
        statement.executeUpdate(createDBTable);
    }

    // Add new user.
    private static void InsertPhoneTable(String lastName, String address, String phoneNumber) throws SQLException
    {
        Statement statement = connection.createStatement();

        String insertUser = "use Task3JDBC;\n" +
                "INSERT INTO Phones (LastName, Address, PhoneNumber) \n" +
                "VALUES ('" +
                lastName +
                "', '" +
                address +
                "', '" +
                phoneNumber +
                "')";
        statement.executeUpdate(insertUser);
    }

    // Search by last name.
    private static String SearchByLastName(String lastName) throws SQLException
    {
        java.util.Date date= new java.util.Date();
        String result = new Timestamp(date.getTime())+"<br>";
        Statement statement = connection.createStatement();
        String searchByName = "use Task3JDBC;\n" +
                "select * from Phones\n" +
                "where LastName LIKE  \'%" +
                lastName +
                "%\'";
        ResultSet resultUsers = statement.executeQuery(searchByName);
        while (resultUsers.next())
        {
            result+=resultUsers.getString("LastName")+" ";
            result+=resultUsers.getString("Address")+" ";
            result+=resultUsers.getString("PhoneNumber")+"<br>";
        }
        return result;
    }

    // Search by phone.
    private static String SearchByPhone(String phone) throws SQLException
    {
        java.util.Date date= new java.util.Date();
        String result = new Timestamp(date.getTime())+"<br>";
        Statement statement = connection.createStatement();
        String searchByPhone = "use Task3JDBC;\n" +
                "select * from Phones\n" +
                "where PhoneNumber LIKE  \'%" +
                phone +
                "%\'";
        System.out.println(searchByPhone);
        ResultSet resultUsers = statement.executeQuery(searchByPhone);
        while (resultUsers.next())
        {
            result+=resultUsers.getString("LastName")+" ";
            result+=resultUsers.getString("Address")+" ";
            result+=resultUsers.getString("PhoneNumber")+"<br>";
        }
        return result;
    }

    private static void StartPoint()
    {
        try {
            ConnectToDB();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            CreatePhonesTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            InsertPhoneTable("Ivanov", "Perm", "89213413465");
            InsertPhoneTable("Petrov", "Moscow", "2680979");
            InsertPhoneTable("Sidorov", "SPB", "152726");
            InsertPhoneTable("Smeshnov", "SPB", "8922476313413465");
            InsertPhoneTable("Grustnov", "SPB", "236236357");
            InsertPhoneTable("Bolshova", "SPB", "65857346");
            InsertPhoneTable("Malova", "SPB", "375473");
            InsertPhoneTable("Minionovna", "SPB", "3264547");
            InsertPhoneTable("Kolova", "SPB", "27468");
            InsertPhoneTable("Dvorov", "USA", "3685457");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (connection == null)
        {
            // Connect to DB.
            try {
                ConnectToDB();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String searchByLastNameClicked = request.getParameter("SearchByLastName");
        String searchByPhoneButtonClicked = request.getParameter("SearchByPhone");

        if (searchByLastNameClicked!=null)
        {
            if (lastName.isEmpty())
            {
                request.setAttribute("message", "Last name field should not be empty.");
                RequestDispatcher view = request.getRequestDispatcher("Task3JDBCPlusJSP.jsp");
                view.forward(request, response);
                return;
            }
            try {
                request.setAttribute("message", SearchByLastName(lastName));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (searchByPhoneButtonClicked!=null)
        {
            if (phone.isEmpty())
            {
                request.setAttribute("message", "Phone field should not be empty.");
                RequestDispatcher view = request.getRequestDispatcher("Task3JDBCPlusJSP.jsp");
                view.forward(request, response);
                return;
            }
            try {
                request.setAttribute("message", SearchByPhone(phone));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        RequestDispatcher view = request.getRequestDispatcher("Task3JDBCPlusJSP.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
