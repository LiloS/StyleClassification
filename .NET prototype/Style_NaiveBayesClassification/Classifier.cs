using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;

namespace Style_NaiveBayesClassification
{
  public class Classifier
  {
    #region constants

    private const string normalDistributionTableName = "Gaussian";
    private const string meanColumnTitle = "Mean";
    private const string varianceColumnTitle = "Variance";

    #endregion

    private DataSet dataSet = new DataSet();

    public DataSet DataSet
    {
      get { return dataSet; }
      set { dataSet = value; }
    }

    public void TrainClassifier(DataTable table)
    {
      dataSet.Tables.Add(table);

      DataTable GaussianDistribution = dataSet.Tables.Add(normalDistributionTableName);
      GaussianDistribution.Columns.Add(table.Columns[0].ColumnName);

      for (int i = 1; i < table.Columns.Count; i++)
      {
        GaussianDistribution.Columns.Add(table.Columns[i].ColumnName + meanColumnTitle);
        GaussianDistribution.Columns.Add(table.Columns[i].ColumnName + varianceColumnTitle);
      }

      var results = (table.AsEnumerable()
        .GroupBy(myRow => myRow.Field<string>(table.Columns[0].ColumnName))
        .Select(item => new { Name = item.Key, Count = item.Count() })).ToList();

      for (int j = 0; j < results.Count; j++)
      {
        DataRow row = GaussianDistribution.Rows.Add();
        row[0] = results[j].Name;

        int a = 1;
        for (int i = 1; i < table.Columns.Count; i++)
        {
          row[a] =
            Helper.Mean(SelectRows(table, i, String.Format("{0} = '{1}'", table.Columns[0].ColumnName, results[j].Name)));
          row[++a] =
            Helper.Variance(SelectRows(table, i,
              String.Format("{0} = '{1}'", table.Columns[0].ColumnName, results[j].Name)));
          a++;
        }
      }
    }

    public string Classify(double[] obj)
    {
      var score = new Dictionary<string, double>();

      var results = (dataSet.Tables[0].AsEnumerable()
        .GroupBy(myRow => myRow.Field<string>(dataSet.Tables[0].Columns[0].ColumnName))
        .Select(g => new {Name = g.Key, Count = g.Count()})).ToList();

      for (int i = 0; i < results.Count; i++)
      {
        var subScoreList = new List<double>();
        int a = 1, b = 1;
        for (int k = 1; k < dataSet.Tables[normalDistributionTableName].Columns.Count; k = k + 2)
        {
          var mean = Convert.ToDouble(dataSet.Tables[normalDistributionTableName].Rows[i][a]);
          var variance = Convert.ToDouble(dataSet.Tables[normalDistributionTableName].Rows[i][++a]);
          var result = Helper.NormalDist(obj[b - 1], mean, Helper.SquareRoot(variance));
          subScoreList.Add(result);
          a++;
          b++;
        }

        double finalScore = 0;
        for (int z = 0; z < subScoreList.Count; z++)
        {
          if (finalScore == 0)
          {
            finalScore = subScoreList[z];
            continue;
          }

          finalScore = finalScore * subScoreList[z];
        }

        score.Add(results[i].Name, finalScore * 0.5);
      }

      double maxOne = score.Max(c => c.Value);
      var name = (score.Where(c => c.Value == maxOne).Select(c => c.Key)).First();

      return name;
    }

    public IEnumerable<double> SelectRows(DataTable table, int column, string filter)
    {
      return table.Select(filter).Select(t => (double) t[column]).ToList();
    }

    public void Clear()
    {
      dataSet = new DataSet();
    }

  }
}
