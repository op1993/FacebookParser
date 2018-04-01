import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    //https://zerohacks.com/find-facebook-id/
  private static   String DIR_LOCATION = "C:\\Users\\Oleh\\Desktop\\facebook";
  private static String FILES_WITH_NAME = "new";

    public static void main(String[] args) {
        List<User> usersWhichIsDone = new ArrayList<>();
        List<User> usersWhichNeedToBeProcessed = new ArrayList<>();
        List<String> rawData = new FileUtil().collectAllDataToList(DIR_LOCATION, FILES_WITH_NAME);
//        Map<String, List<User>> users = creatingUsersObjects(rawData);
//
//        for (Map.Entry<String, List <User>> entry : users.entrySet()){
//            String key = entry.getKey();
//            List <User> user = entry.getValue();
//            if (key.equals("withId")){
//                usersWhichIsDone = user = entry.getValue();
//            }
//            System.out.println(key);
//            user = entry.getValue();
//            System.out.println(user.size());
//            for (User u : user){
//                System.out.println(u.getName());
//            }
//        }

        UserService userService =new UserService(rawData);


        System.out.println("DONEEEEE " + usersWhichIsDone.size());


        List<User> usersWhichWasNotFound = new ArrayList<>();

        System.out.println("******************************");
        System.out.println("START SENDING REQUESTS");
        System.out.println("******************************");
/*

        for (int a = 0; a < usersWhichNeedToBeProcessed.size(); a++) {
//            MultipartBody jsonResponse = Unirest.post("https://findmyfbid.com/?__amp_source_origin=https%3A%2F%2Ffindmyfbid.com")
//                    .field("url", usersWhichNeedToBeProcessed.get(a).getId());
             GetRequest jsonResponse = Unirest.get("https://zerohacks.com/ads/findmyid/index.php").field
            ("username", usersWhichNeedToBeProcessed.get(a).getId());

            try {
                System.out.println(jsonResponse.asString().getBody());
                String id = jsonResponse.asString().getBody();
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
*/
    }
}

