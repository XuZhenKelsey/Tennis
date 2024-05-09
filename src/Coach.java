import java.util.ArrayList;


public class Coach extends User{

    public Coach(String userId, String pw){
        super(userId ,pw);
    }

    public void createCourse(String courtId, String d, String t, int maxStudents) {
        Course newCourse = new Course(userId, courtId, d, t, maxStudents);
        newCourse.setIsSelfPracticeCourse(false);
        courses.add(newCourse);
        System.out.println("Created course: Coach " + newCourse.getCoachId() + " at " + d+" "+t+" " + " on court " + courtId);
    }



    public ArrayList<Course> getCourses(){
        return courses;
    }

    // public void deleteAllCoachCourses(){
    //     courses=new ArrayList<Course>();
    // }

    
}
