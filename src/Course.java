import java.util.ArrayList;

public class Course implements java.io.Serializable{
    private static final long serialVersionUID = 6945417843390666493L;

    private String coachId;
    private String courtId;
    private String d;
    private String t;
    private int maxStudents;
    private int numStudents;
    private boolean isSelfPracticeCourse;
    private ArrayList<Member> regStudents; // List to store student IDs
    
    public Course(String coachId, String courtId, String t1, String t2, int maxStudents) {

        this.coachId = coachId;
        this.courtId = courtId;
        this.d = t1;
        this.t = t2;
        this.maxStudents = maxStudents;  
        this.numStudents=0;
        this.regStudents = new ArrayList<Member>();
    }

    public boolean isFull(){
        return maxStudents==regStudents.size();
    }
    
    public void setIsSelfPracticeCourse(boolean b){
        isSelfPracticeCourse=b;
    }

    public boolean getIsSelfPracticeCourse(){
        return isSelfPracticeCourse;
    }

    public int getNumStudents(){
        return regStudents.size();
    }

    
    public ArrayList<String> getStudentsNames(){
        ArrayList<String> l=new ArrayList<String>();
        for(User user: regStudents){
            l.add(user.getUsername());
        }
        return l;
    }

    public void registerMember(Member member) {
                regStudents.add(member);
                numStudents+=1;
    }

    public void deregisterMember(Member member) {
        if(regStudents.contains(member)){
            regStudents.remove(member);
            member.quitCourse(this);
            numStudents -= 1;
            UserAndBookingManager.getManager().saveUsersToFile();
        }else{
            System.out.println("X contain this member");
        }
    }




    public String getCoachId() {
        return coachId;
    }

    public String getCourtId() {
        return courtId;
    }

    public String getD() {
        return d;
    }
    
    public String getT() {
        return t;
    }


    public int getMaxStudents() {
        return maxStudents;
    }

    public ArrayList<Member> getRegisteredStudentIDs() {
        return regStudents;
    }
}
