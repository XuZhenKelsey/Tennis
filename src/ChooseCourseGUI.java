import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;


public class ChooseCourseGUI extends JFrame {
    private JComboBox<String> coachComboBox;
    private JPanel courseContainer;
    private MemberHomeGUI parentGUI;
    private Member member;

    public ChooseCourseGUI(MemberHomeGUI p,Member m) {
        setTitle("Choose Course by Coach");
        setSize(1000, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        this.member=m;
        this.parentGUI=p;

        
        coachComboBox = new JComboBox<>();
        JButton displayButton = new JButton("Display Courses");
        courseContainer = new JPanel();
        courseContainer.setLayout(new BoxLayout(courseContainer, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(courseContainer,
                                                 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                 JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        populateCoaches();
        displayButton.addActionListener(e -> displayCourses((String) coachComboBox.getSelectedItem()));

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(coachComboBox);
        topPanel.add(displayButton);
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void populateCoaches() {
        for(User coach: UserAndBookingManager.getManager().getAllUsers()){
            if(coach instanceof Coach){
                coachComboBox.addItem(coach.getUsername());
            }
        }
    }

private void displayCourses(String coachId) {
    courseContainer.removeAll(); // Clear the existing courses
    for(User coach: UserAndBookingManager.getManager().getAllUsers()){
        if(coachId.equals(coach.getUsername())){
            for(Course course:coach.getCourses()){
                JPanel courseWithButtonPanel = new JPanel();
                courseWithButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // small gap between components

                CoursePanel singleCoursePanel = new CoursePanel(course);
                courseWithButtonPanel.add(singleCoursePanel);
                if(course.getMaxStudents()>=course.getNumStudents() && !member.getCourses().contains(course)){
                        JButton chooseCourseButton = new JButton("Choose Course");
                        courseWithButtonPanel.add(chooseCourseButton);
                        chooseCourseButton.addActionListener(e -> {
                            course.registerMember(member);
                            member.addCourseToList(course);
                            UserAndBookingManager.getManager().saveUsersToFile();
                            parentGUI.displayCourses();
                        JOptionPane.showMessageDialog(this, "Enroll course successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    });
                    
                }
                
            courseContainer.add(courseWithButtonPanel);
            }
            
        }
    }
        
    courseContainer.revalidate();
    courseContainer.repaint();
}




}


