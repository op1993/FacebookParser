import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Oleh on 25-Mar-18.
 */
public class FileReader {


    public static void main(String[] args) throws FileNotFoundException {
        List<String> listOfValidFiles = getListOfFilesInDirectory("C:\\Users\\Oleh\\Desktop\\facebook","new");
        List<String> fileLines = collectAllDataToList(listOfValidFiles);
        System.out.println(fileLines.size());
    }


    private static List<String> getListOfFilesInDirectory(String pathToFolder, String name) {
        File folder = new File(pathToFolder);
        List<String> validFiles = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains(name) && file.getName().endsWith(".csv")) {
                validFiles.add(file.getAbsolutePath());
            }
        }

        return validFiles;
    }

    private static List<String> collectAllDataToList( List<String> validFiles) {
        List <String> temp = new ArrayList<>();
        for (int a = 0; a < validFiles.size(); a++) {
            File file = new File(validFiles.get(a));
            Scanner sc = null;
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (sc.hasNextLine()) {
                temp.add(sc.nextLine());
            }
        }

        return temp;
    }
}
