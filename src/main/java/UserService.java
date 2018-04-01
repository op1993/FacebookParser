import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Oleh on 01-Apr-18.
 */
public class UserService {

    private List<String> rawData;
    public List<User> users;


    public UserService(List<String> rawData) {
        this.rawData = rawData;
    }

    Map<String , List<User>> usersMap = creatingUsersObjects(rawData);

    private Map<String, List<User>> creatingUsersObjects(List<String> rawData){
        Map<String, List<User>> users = new HashMap<>();
        String usersWithIdPattern = ".*id=([0-9]*)";
        Pattern validId = Pattern.compile(usersWithIdPattern);
        List<User> usersWithId = new ArrayList<>();
        List<User> usersWithoutID = new ArrayList<>();
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
                usersWithId.add(new User(name, surname, matcher.group(1)));
            } else
                usersWithoutID.add(new User(name, surname, facebookId));
        }
        users.put("withId", usersWithId);
        users.put("withoutId", usersWithoutID);
        return users;
    }

    public List<User> getUsers( Map<String , List<User>> users1, String key){
       for (Map.Entry<String, List <User>> entry : users1.entrySet()){
        String entryKey = entry.getKey();
        List <User> user = entry.getValue();
        if (entryKey.equals(key))
        {
        users = entry.getValue();
        }
    }
    return users;
}
}
