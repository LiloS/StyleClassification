using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Style_NaiveBayesClassification
{
  public partial class Form1 : Form
  {
    private ProgressDialog alert;
    private DataTable table = new DataTable();
    public static string[] allCommands;
    private Classifier classifier = new Classifier();

    public Form1()
    {
      InitializeComponent();

      comboBox1.SelectedIndex = 0;
      comboBox1.SelectedIndexChanged += comboBox1_SelectedIndexChanged;

      table.Columns.Add("Style");
      for (int featureNumber = 0; featureNumber < 2659; featureNumber++)
      {
        table.Columns.Add("Feature " + featureNumber, typeof(double));
      }
    }

    private void folderBrowserDialog1_HelpRequest(object sender, EventArgs e)
    {
    }

    private void button1_Click(object sender, EventArgs e)
    {
      int size = -1;
      folderBrowserDialog1.ShowNewFolderButton = false;
      folderBrowserDialog1.Description = comboBox1.SelectedItem + " training set";
      folderBrowserDialog1.RootFolder = Environment.SpecialFolder.MyComputer;
      DialogResult result = folderBrowserDialog1.ShowDialog(); // Show the dialog.
     
      if (result == DialogResult.OK) // Test result.
      {
        ClassemesExtractor classemesExtractor = new ClassemesExtractor();
        classemesExtractor.Extract(folderBrowserDialog1.SelectedPath, comboBox1.SelectedItem.ToString());
        //string pathToDirectory = folderBrowserDialog1.SelectedPath;
        //string disk = Directory.GetDirectoryRoot(pathToDirectory).Substring(0,2);

        //string folderNavigation = "cd " + pathToDirectory;

        //string fileNameWithFileNames = comboBox1.SelectedItem.ToString().Replace(" ", "_") +
        //                               "Files.txt";

        //string allFilesDocumentCreation = "dir /b > " + fileNameWithFileNames;

        //string extractClassemessCD = "cd D:\\Sharp\\vlg_extractor_1.1.3";

        //string extractClassemes = "vlg_extractor.bat --extract_classemes=ASCII " + pathToDirectory+"\\"
        //  + fileNameWithFileNames + " " + pathToDirectory + " " + pathToDirectory+"\\Features";

        //string pathToVectors = pathToDirectory + "\\Features";
        //alert = new ProgressDialog();
        //alert.Show();

        //VectorizationService.ExecuteCommand(new[] { disk, folderNavigation, allFilesDocumentCreation, extractClassemessCD, extractClassemes });

        //alert.Close();

        // Vector of features is extracted.

        //foreach (string fileName in Directory.GetFiles(pathToVectors))
        //{
        //  IEnumerable<double> features = File.ReadLines(fileName).Select(Double.Parse);
        //  int featureNumber = 0;
        //  DataRow dr = table.NewRow();
        //  dr["Style"] = comboBox1.SelectedItem;
        //  foreach (var feature in features)
        //  {
        //    dr["Feature " + featureNumber] = feature;
        //    featureNumber++;
        //  }
        //  table.Rows.Add(dr);
        //}
      }

      checkedListBox1.SetItemChecked(checkedListBox1.Items.IndexOf(comboBox1.SelectedItem), true);

      Console.WriteLine(result); 
    }

    private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
    {

    }

    private void button2_Click(object sender, EventArgs e)
    {
      DialogResult result = openFileDialog1.ShowDialog(); // Show the dialog.
      if (result == DialogResult.OK) // Test result.
      {
        string filePath = openFileDialog1.FileName;

        string pathToDirectory = Path.GetDirectoryName(filePath);
        string disk = Directory.GetDirectoryRoot(pathToDirectory).Substring(0, 2);

        if (Directory.Exists(pathToDirectory + "\\FeaturesSingleFile"))
        {
          Helper.DeleteDirectory(pathToDirectory + "\\FeaturesSingleFile");
        }

        string folderNavigation = "cd " + pathToDirectory;

        string fileNameWithFileNames = Path.GetFileNameWithoutExtension(filePath).ToString().Replace(" ", "_") +
                                       "Files.txt";

        string allFilesDocumentCreation = "echo " + Path.GetFileName(filePath) + " > " + fileNameWithFileNames;

        string extractClassemessCD = "cd D:\\Sharp\\vlg_extractor_1.1.3";

        string extractClassemes = "vlg_extractor.bat --extract_classemes=ASCII " + pathToDirectory + "\\"
          + fileNameWithFileNames + " " + pathToDirectory + " " + pathToDirectory + "\\FeaturesSingleFile";

        string pathToVectors = pathToDirectory + "\\FeaturesSingleFile";
        alert = new ProgressDialog();
        alert.Show();

        Image imgtemp = Image.FromFile(filePath);

        pictureBox1.SizeMode = PictureBoxSizeMode.StretchImage;
        pictureBox1.Image = imgtemp;

        VectorizationService.ExecuteCommand(new[]
        {disk, folderNavigation, allFilesDocumentCreation, extractClassemessCD, extractClassemes});

        string fileName = Directory.GetFiles(pathToVectors).First();

        double[] features = File.ReadLines(fileName).Select(Double.Parse).ToArray();
        textBox1.Text = classifier.Classify(features);


        string pathToClassifiedDirectory = pathToDirectory + "\\" + textBox1.Text.Replace(" ", "_")+"_Classified";
        if (!Directory.Exists(pathToClassifiedDirectory))
        {
          Directory.CreateDirectory(pathToClassifiedDirectory);
        }
        File.Copy(filePath, pathToClassifiedDirectory + "\\" + Path.GetFileName(filePath), true);

        alert.Close();
        
      }
    }

    private void button3_Click(object sender, EventArgs e)
    {
      alert = new ProgressDialog();
      alert.Show();
      classifier.TrainClassifier(table);
      alert.Close();
    }

    private void checkedListBox1_SelectedIndexChanged(object sender, EventArgs e)
    {

    }

    private void checkBox1_CheckedChanged(object sender, EventArgs e)
    {
      if (!checkBox1.Checked)
      {
        checkBox1.Select();
      }
    }

    private void checkedListBox1_SelectedIndexChanged_1(object sender, EventArgs e)
    {

    }

    private void textBox1_TextChanged(object sender, EventArgs e)
    {

    }

    private void pictureBox1_Click(object sender, EventArgs e)
    {

    }

    private void Form1_Load(object sender, EventArgs e)
    {

    }
  }
}
