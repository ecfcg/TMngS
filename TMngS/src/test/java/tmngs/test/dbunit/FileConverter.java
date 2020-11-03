package tmngs.test.dbunit;

import java.nio.file.Path;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.LowerCaseDataSet;
import org.dbunit.dataset.csv.CsvDataSet;

public interface FileConverter {
  /**
   * csvファイルからIDataSetを生成する
   * 
   * @param csvDirPath csvファイル格納先ディレクトリ
   * @return csvファイルから生成したIDataSet
   */
  static IDataSet dataSetFromCsv(Path csvDirPath) {
    try {
      return new LowerCaseDataSet(new CsvDataSet(csvDirPath.toFile()));
    } catch (DataSetException e) {
      // テスト時の例外は無視する.
      throw new RuntimeException(e);
    }
  }
}
