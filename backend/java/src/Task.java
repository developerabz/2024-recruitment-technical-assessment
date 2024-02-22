package src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Task {
  public record File(
      int id,
      String name,
      List<String> categories,
      int parent,
      int size) {
  }

  /**
   * Task 1
   */
  public static List<String> leafFiles(List<File> files) {
    List<Integer> parentIds = files.stream().map(f -> f.parent()).collect(Collectors.toList());

    return files
        .stream()
        .filter(f -> !parentIds.stream().anyMatch(id -> id == f.id()))
        .map(f -> f.name())
        .collect(Collectors.toList());
  }

  /**
   * Task 2
   */
  public static List<String> kLargestCategories(List<File> files, int k) {
    List<String> allCategories = files
        .stream()
        .map(f -> f.categories())
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
    Map<String, Integer> categoriesMap = new HashMap<>();
    for (String category : allCategories) {
      if (categoriesMap.containsKey(category)) {
        Integer newAmount = categoriesMap.get(category) + 1;
        categoriesMap.put(category, newAmount);
      } else {
        categoriesMap.put(category, 1);
      }
    }

    List<String> sortedCategories = categoriesMap
        .entrySet()
        .stream()
        .sorted((a, b) -> {
          if (a.getValue() == b.getValue()) {
            return a.getKey().compareTo(b.getKey());
          }
          return b.getValue() - a.getValue();
        })
        .map(e -> e.getKey())
        .limit(k)
        .collect(Collectors.toList());

    return sortedCategories;
  }

  public static List<Integer> leafFileIds(List<File> files) {
    List<Integer> parentIds = files.stream().map(f -> f.parent()).collect(Collectors.toList());

    return files
        .stream()
        .filter(f -> !parentIds.stream().anyMatch(id -> id == f.id()))
        .map(f -> f.id())
        .collect(Collectors.toList());
  }

  public static int getFileSize(List<File> files, File file, List<Integer> leafFileList) {
    if (leafFileList.stream().anyMatch(f -> f == file.id())) {
      return file.size();
    } else {
      int childFiles = files
          .stream()
          .filter(f -> f.parent() == file.id())
          .map(f -> getFileSize(files, f, leafFileList))
          .reduce(0, (accu, fsize) -> accu + fsize);
      // System.out.println(childFiles);

      return file.size() + childFiles;
    }
  }

  /**
   * Task 3
   */
  public static int largestFileSize(List<File> files) {
    return files
        .stream()
        .map(f -> getFileSize(files, f, leafFileIds(files)))
        .sorted((a, b) -> b - a)
        .findFirst()
        .get();
  }

  public static void main(String[] args) {
    List<File> testFiles = List.of(
        new File(1, "Document.txt", List.of("Documents"), 3, 1024),
        new File(2, "Image.jpg", List.of("Media", "Photos"), 34, 2048),
        new File(3, "Folder", List.of("Folder"), -1, 0),
        new File(5, "Spreadsheet.xlsx", List.of("Documents", "Excel"), 3, 4096),
        new File(8, "Backup.zip", List.of("Backup"), 233, 8192),
        new File(13, "Presentation.pptx", List.of("Documents", "Presentation"), 3, 3072),
        new File(21, "Video.mp4", List.of("Media", "Videos"), 34, 6144),
        new File(34, "Folder2", List.of("Folder"), 3, 0),
        new File(55, "Code.py", List.of("Programming"), -1, 1536),
        new File(89, "Audio.mp3", List.of("Media", "Audio"), 34, 2560),
        new File(144, "Spreadsheet2.xlsx", List.of("Documents", "Excel"), 3, 2048),
        new File(233, "Folder3", List.of("Folder"), -1, 4096));

    List<String> leafFiles = leafFiles(testFiles);
    leafFiles.sort(null);
    assert leafFiles.equals(List.of(
        "Audio.mp3",
        "Backup.zip",
        "Code.py",
        "Document.txt",
        "Image.jpg",
        "Presentation.pptx",
        "Spreadsheet.xlsx",
        "Spreadsheet2.xlsx",
        "Video.mp4"));

    assert kLargestCategories(testFiles, 3).equals(List.of(
        "Documents", "Folder", "Media"));

    assert kLargestCategories(testFiles, 6).equals(List.of(
        "Documents", "Folder", "Media", "Excel", "Audio", "Backup"));
    assert largestFileSize(testFiles) == 20992;
  }
}
