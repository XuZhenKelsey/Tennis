import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class UserAndBookingManager {
    private static UserAndBookingManager single_instance = null;
    private ArrayList<User> users;

    private UserAndBookingManager(){
        users=new ArrayList<User>();
        loadUsersFromFile();
        System.out.println("UserAndBookingManagerCreated!");
    }

    public static synchronized UserAndBookingManager getManager()
    {
        if (single_instance == null)
            single_instance = new UserAndBookingManager();
 
        return single_instance;
    }



    public ArrayList<User> getAllUsers(){
        return users;
    }
    
    public void saveNewUser(String uid, String pw, String uType){
        if(uType.equals("Coach")){
            User c=new Coach(uid,pw);
            users.add(c);
        }else{
            User m=new Member(uid,pw);
            users.add(m);
        }
        saveUsersToFile();
    }

    
   
    public void deleteCoachAccount(Coach c){
        for(Course course:c.getCourses()){
            for(Member member:course.getRegisteredStudentIDs()){
                if(member instanceof Member){
                    course.deregisterMember(member);
                }
            }
        }
        users.remove(c);
        saveUsersToFile();
        
    }
    public void deleteMemberAccount(Member u) {
    List<Course> coursesToRemove = new ArrayList<>();

    for (Course course : u.getCourses()) {
        coursesToRemove.add(course);
    }

    for (Course course : coursesToRemove) {
        course.deregisterMember(u);
    }

    users.remove(u);
    saveUsersToFile();
}

    public void loadUsersFromFile() {
    File file = new File("data.ser");
    try {
        if (!file.exists()) {
            System.out.println("data.ser not found. Creating a new file.");
            boolean created = file.createNewFile();
            if (created) {
                System.out.println("The file has been created successfully.");
            } else {
                System.out.println("The file already exists or couldn't be created.");
            }
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (ArrayList<User>) ois.readObject();
                // Restore the static count for ID continuity
                //UserAndBookingManager.setCount(ois.readInt());
            }
            System.out.println("Users loaded successfully from data.ser.");
        }
    } catch (IOException e) {
        System.err.println("Error accessing file: " + e.getMessage());
        // Handle whether to continue running or exit
        // For now, let's just return and the users list will remain empty
    } catch (ClassNotFoundException e) {
        System.err.println("Class not found during deserialization: " + e.getMessage());
        // Handle whether to continue running or exit
        // For now, let's just return and the users list will remain empty
    }
}


    public void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
            oos.writeObject(users);
            System.out.println("SaveUserSuccessful");
            //oos.writeInt(Users.getCount()); // Save the static count to maintain ID continuity
        } catch (IOException e) {
            System.err.println("Error saving contacts to file: " + e.getMessage());
        }
    }

}
