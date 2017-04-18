import BayesClassifier.BayesClassifier;
import BayesClassifier.DBHelper;

import java.io.IOException;
import java.sql.SQLException;

public class RunnableExample {

    public static void StartPoint()throws Exception
    {
        DBHelper.ConnectToDB();
        DBHelper.CreateUsersTable();
        DBHelper.InsertUserToTable("guest", null, false);
        DBHelper.InsertUserToTable("admin", "admin", true);
        DBHelper.CreatePainitngsTable();

        DBHelper.LoginAsUser("admin", "admin");
        DBHelper.InsertPaintingToTable("D:\\Java\\Classification\\out\\artifacts\\Classification_war_exploded\\uploadFiles\\adoration-of-the-shepherds.jpg", true, "Classicism");
        DBHelper.InsertPaintingToTable("D:\\Java\\Classification\\out\\artifacts\\Classification_war_exploded\\uploadFiles\\apollo-and-the-muses.jpg", true, "Classicism");
        DBHelper.InsertPaintingToTable("D:\\Java\\Classification\\out\\artifacts\\Classification_war_exploded\\uploadFiles\\chaos-nr-2-1906.jpg", true, "Abstract art");
        DBHelper.InsertPaintingToTable("D:\\Java\\Classification\\out\\artifacts\\Classification_war_exploded\\uploadFiles\\variation-field-of-tulips-1916.jpg", true, "Abstract art");
        BayesClassifier.CreateClassificationTable();
        BayesClassifier.TrainBayesClassifier();
    }

    public static void ClassifyPainting() throws SQLException, ClassNotFoundException, IOException
    {
        DBHelper.ConnectToDB();
        DBHelper.InsertPaintingToTable("D:\\Sharp\\Images\\AbstractArt\\they-tens-mainstay-iv-1907.jpg", false, null);
        BayesClassifier.Classify("D:\\Sharp\\Images\\AbstractArt\\they-tens-mainstay-iv-1907.jpg");
    }

    public static void AddUser() throws SQLException, ClassNotFoundException
    {
        DBHelper.ConnectToDB();
        DBHelper.InsertUserToTable("test", "test", true);
        if (DBHelper.CheckUsernameIsNotUsed("test"))
        {
            System.out.println("WROOONG");
        }
    }

    public static void main(String[] args) throws Exception
    {
        StartPoint();
        //StartPoint();
//        DBHelper.ConnectToDB();
//        DBHelper.InsertPaintingToTable("D:\\Sharp\\Images\\AbstractArt\\variation-field-of-tulips-1916.jpg", true, "Abstract art");
//        BayesClassifier.TrainBayesClassifier();
//        if (DBHelper.LoginAsUser("admin", "admin"))
//        {
//            System.out.println("Login as admin.");
//        }
//        DBHelper.InsertPaintingToTable("D:\\Sharp\\Images\\Classicism\\adoration-of-the-shepherds.jpg", true, "Classicism");
        //StartPoint();
        //ClassifyPainting();
//        DBHelper.ConnectToDB();
//        DBHelper.InsertUserToTable("test", "test", true);
//        if (DBHelper.CheckUsernameIsNotUsed("test"))
//        {
//            System.out.println("WROOONG");
//        }
        //BayesClassifier.Classify("D:\\Sharp\\Images\\AbstractArt\\they-tens-mainstay-iv-1907.jpg");
    }

}
