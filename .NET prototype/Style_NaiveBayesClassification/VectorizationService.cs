using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Style_NaiveBayesClassification
{
  class VectorizationService
  {
    public static void ExecuteCommand(string[] commands)
    {
      Process p = new Process();
      ProcessStartInfo info = new ProcessStartInfo();
      info.CreateNoWindow = true;
      info.FileName = "cmd.exe";
      info.RedirectStandardInput = true;
      info.UseShellExecute = false;

      p.StartInfo = info;
      p.Start();
      using (StreamWriter sw = p.StandardInput)
      {
        if (sw.BaseStream.CanWrite)
        {
          foreach (var command in commands)
          {
            sw.WriteLine(command);
          }
        }
      }

      p.WaitForExit();
      p.Close();
    }
  }
}
