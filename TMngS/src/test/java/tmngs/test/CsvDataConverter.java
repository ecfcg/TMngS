package tmngs.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CsvDataConverter {
  private CsvDataConverter() {}

  public static <T> List<T> convertFrom(Path csvPath, Function<String[], T> parser) {
    try {
      return Files.readAllLines(csvPath).stream().map(l -> l.split(",")).map(parser)
          .collect(Collectors.toUnmodifiableList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<LocalDate> getLocalDate(Path csvPath) {
    return convertFrom(csvPath, line -> LocalDate.parse(line[0]));
  }
}
