import java.util.ArrayList;

public class Member extends User{

    private static final long serialVersionUID = 7189297919495310538L;
    
    public Member(String userId, String pw){
        super(userId ,pw);
    }

    public ArrayList<Course> getCourses(){
        return courses;
    }
    
    public void createCourse(String courtId, String date,String time, int maxStudent){
        Course c=new Course(userId, courtId,date,time,1);
        c.setIsSelfPracticeCourse(true);
        c.registerMember(this);
        addCourseToList(c);
        System.out.println("Created Self Practice course: Member " + c.getCoachId() + " at " + date+" "+time+" " + " on court " + courtId);
    }

    public void addCourseToList(Course course){
        courses.add(course);
    }


    public void quitCourse(Course course){
        if(courses.contains(course)){
            courses.remove(course);
        }
    }





    
}
