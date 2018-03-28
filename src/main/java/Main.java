import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.MultipartBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Oleh on 28-Mar-18.
 */
public class Main {

    // TODO: 29-Mar-18 add several threads;
    // TODO: 29-Mar-18 add several servers for checking ID. Investigate  how to sent request in https://findmyfbid.in/
    // TODO: 29-Mar-18  improve performance
    // TODO: 29-Mar-18  merge 2 files in one with several tabs


    public static void main(String[] args) {
        String filesLocation = "C:\\Users\\Oleh\\Desktop\\facebook";
        String containName = "new";
        List<User> usersWhichIsDone = new ArrayList<>();
        List<User> usersWhichNeedToBeProcessed = new ArrayList<>();

        List<String> rawData = new FileUtil().collectAllDataToList(filesLocation, containName);
        System.out.println(rawData.size());
        String usersWithId = ".*id=([0-9]*)";
        Pattern validId = Pattern.compile(usersWithId);

        System.out.println("******************************");
        System.out.println("COLLECTING THE DATA");
        System.out.println("******************************");
        for (int a = 0; a < rawData.size(); a++) {
            String splitterRow[];
            String name = null;
            String surname = null;
            String facebookId = null;
            splitterRow = rawData.get(a).split(",");
            facebookId = splitterRow[1];
            String fullname[] = splitterRow[0].split(" ");

            switch (fullname.length) {
                case 1:
                    name = fullname[0];
                    surname = "";
                    break;
                case 2:
                    name = fullname[0];
                    surname = fullname[1];
                    break;
                case 3:
                    name = fullname[0] + " " + fullname[1];
                    surname = fullname[2];
                    break;
                default:
                    name = "INVALID";
                    surname = "INVALID";
                    facebookId = "INVALID";
                    break;
            }
            Matcher matcher = validId.matcher(rawData.get(a));
            if (matcher.find()) {
                usersWhichIsDone.add(new User(name, surname, matcher.group(1)));
            } else
                usersWhichNeedToBeProcessed.add(new User(name, surname, facebookId));
        }

        List<User> usersWhichWasNotFound = new ArrayList<>();

        System.out.println("******************************");
        System.out.println("START SENDING REQUESTS");
        System.out.println("******************************");

        for (int a = 0; a < usersWhichNeedToBeProcessed.size(); a++) {
            MultipartBody jsonResponse = Unirest.post("https://findmyfbid.com/?__amp_source_origin=https%3A%2F%2Ffindmyfbid.com")
                    .field("url", usersWhichNeedToBeProcessed.get(a).getId());
            try {
                System.out.println(jsonResponse.asString().getBody());
                String id = jsonResponse.asString().getBody().replaceAll("[^0-9]", "");
//                System.out.println(id);
                if (id.isEmpty()) {
                    usersWhichWasNotFound.add(new User(usersWhichNeedToBeProcessed.get(a).getName(), usersWhichNeedToBeProcessed.get(a).getLastName(), usersWhichNeedToBeProcessed.get(a).getId()));
                } else {
                    usersWhichIsDone.add(new User(
                            usersWhichNeedToBeProcessed.get(a).getName(),
                            usersWhichNeedToBeProcessed.get(a).getLastName(),
                            id));
                }
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }

        System.out.println("******************************");
        System.out.println("CREATING THE FILES");
        System.out.println("******************************");
        try {
            new FileUtil().writeUsersToFile(usersWhichWasNotFound, "Facebook id which was not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            new FileUtil().writeUsersToFile(usersWhichIsDone, "Facebook id which was found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

