using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Style_NaiveBayesClassification
{
  interface IFeatureExtractor
  {
    /// <summary>
    /// Extracts features for all files in the folder.
    /// </summary>
    void Extract(string pathToDirectory, string styleTitle);

    ///// <summary>
    ///// Extracts features for single file.
    ///// </summary>
    ///// <param name="filePath"></param>
    ///// <returns></returns>
    //double[] ExtractSingle(string filePath);
  }
}
