import java.util.ArrayList;

public abstract class User implements java.io.Serializable{
    protected String userId;
    protected String pw;
    protected ArrayList<Course> courses;
    

    public User(String userId, String pw) {
        this.userId = userId;
        this.pw = pw;
        this.courses=new ArrayList<Course>();
    }

    public String getUsername(){
        return userId;
    }
    public String getPassword(){
        return pw;
    }
    public ArrayList<Course> getCourses(){
        return courses;
    }

    public abstract void createCourse(String courtId, String date, String time, int maxStudents);

    public void cancelCourse(Course course) {
        courses.remove(course);
    }

}
