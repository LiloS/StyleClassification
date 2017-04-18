using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Style_NaiveBayesClassification
{
  public static class Helper
  {
    public static double Variance(this IEnumerable<double> source)
    {
      double avg = source.Average();
      double d = source.Aggregate(0.0, (total, next) => total += Math.Pow(next - avg, 2));
      return d / (source.Count() - 1);
    }

    public static double Mean(this IEnumerable<double> source)
    {
      if (source.Count() < 1)
        return 0.0;

      double length = source.Count();
      double sum = source.Sum();
      return sum / length;
    }

    public static double NormalDist(double x, double mean, double standard_dev)
    {
      double fact = standard_dev * Math.Sqrt(2.0 * Math.PI);
      double expo = (x - mean) * (x - mean) / (2.0 * standard_dev * standard_dev);
      return Math.Exp(-expo) / fact;
    }

    public static double SquareRoot(double source)
    {
      return Math.Sqrt(source);
    }

    public static void DeleteDirectory(string target_dir)
    {
      string[] files = Directory.GetFiles(target_dir);
      string[] dirs = Directory.GetDirectories(target_dir);

      foreach (string file in files)
      {
        File.SetAttributes(file, FileAttributes.Normal);
        File.Delete(file);
      }

      foreach (string dir in dirs)
      {
        DeleteDirectory(dir);
      }

      Directory.Delete(target_dir, false);
    }
  }
}
