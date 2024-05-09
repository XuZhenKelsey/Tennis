import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
public class CoursePanel extends JPanel {
    private Course course;
    public CoursePanel(Course c) {
        this.course=c;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        addComponents();
                    
        
    }
    public CoursePanel(String courtId, String d, String t, String max){
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
            this.add(new JLabel("Court ID: " + courtId));
            this.add(new JLabel("Date: " + d));
            this.add(new JLabel("Time: " + t));
            this.add(new JLabel("Max Students: " + max));
    }

    private void addComponents() {
        this.add(new JLabel("Coach ID: " + course.getCoachId()));
        this.add(new JLabel("Court ID: " + course.getCourtId()));
        this.add(new JLabel("Date: " + course.getD()));
        this.add(new JLabel("Time: " + course.getT()));
        this.add(new JLabel("Max Students: " + course.getMaxStudents()));
        this.add(new JLabel("Student number: " + course.getNumStudents()));
        this.add(new JLabel("Students: " + course.getStudentsNames()));
    }
}
