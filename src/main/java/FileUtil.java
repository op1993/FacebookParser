import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Oleh on 25-Mar-18.
 */
public class FileUtil {

    private List<String> getListOfFilesInDirectory(String pathToFolder, String name) {
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

    public List<String> collectAllDataToList(String pathToFolder, String name) {
        List<String> validFiles = getListOfFilesInDirectory(pathToFolder, name);
        List<String> temp = new ArrayList<>();
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

    public void writeUsersToFile(List<User> users, String filename) throws IOException {
        try {
            LocalDateTime localDate = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy hh_mm_ss");
            String formattedString = localDate.format(formatter);
            System.out.println(formattedString);
            FileOutputStream fileOutputStream = new FileOutputStream(new java.io.File(filename + " " + formattedString + ".xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Done");

            int rownum = 1;
            int cellnumHeader = 0;
            Row header = sheet.createRow(0);
            header.createCell(cellnumHeader++).setCellValue("Name");
            header.createCell(cellnumHeader++).setCellValue("Surname");
            header.createCell(cellnumHeader++).setCellValue("Facebook Id");


            for (User u : users) {
                Row row = sheet.createRow(rownum++);
                int cellnum = 0;
                row.createCell(cellnum++).setCellValue(u.getName());
                row.createCell(cellnum++).setCellValue(u.getLastName());
                row.createCell(cellnum++).setCellValue(u.getId());
            }
            workbook.write(fileOutputStream);
            workbook.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}