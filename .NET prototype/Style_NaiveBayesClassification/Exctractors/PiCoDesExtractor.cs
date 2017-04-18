using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Style_NaiveBayesClassification
{
  class PiCoDesExtractor: IFeatureExtractor
  {
    public void Extract(string pathToDirectory, string styleTitle)
    {
      string disk = Directory.GetDirectoryRoot(pathToDirectory).Substring(0, 2);

      string folderNavigation = "cd " + pathToDirectory;

      string fileNameWithFileNames = styleTitle.Replace(" ", "_") + "Files.txt";

      string allFilesDocumentCreation = "dir /b > " + fileNameWithFileNames;

      string extractClassemessCD = "cd D:\\Sharp\\vlg_extractor_1.1.3";

      string extractClassemes = "vlg_extractor.bat --extract_picodes128=ASCII " + pathToDirectory + "\\"
        + fileNameWithFileNames + " " + pathToDirectory + " " + pathToDirectory + "\\FeaturesPiCoDes";

      string pathToVectors = pathToDirectory + "\\FeaturesPiCoDes";

      VectorizationService.ExecuteCommand(new[] { disk, folderNavigation, allFilesDocumentCreation, extractClassemessCD, extractClassemes });

      // Vector of features is extracted.

      foreach (string fileName in Directory.GetFiles(pathToVectors))
      {
        // Put info about extracted features in db.
        //IEnumerable<double> features = File.ReadLines(fileName).Select(Double.Parse);
        //int featureNumber = 0;
        //DataRow dr = table.NewRow();
        //dr["Style"] = comboBox1.SelectedItem;
        //foreach (var feature in features)
        //{
        //  dr["Feature " + featureNumber] = feature;
        //  featureNumber++;
        //}
        //table.Rows.Add(dr);
      }

      Console.WriteLine("Vector of PiCoDes features is extracted."); 
    }
  }
}
