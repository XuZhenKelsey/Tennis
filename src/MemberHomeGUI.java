import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MemberHomeGUI extends JFrame {
    private Member member;
    private JPanel courseContainer;

    public MemberHomeGUI(Member member) {
        super("Member Homepage");
        this.member = member;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 800);
        setLocationRelativeTo(null);

        addHeader(); // Method to add header panel
        setupCourseContainer();
        displayCourses();

        setVisible(true);
    }

    private void addHeader() {
        JPanel headerPanel = new JPanel(new FlowLayout()); // Create the header panel
        headerPanel.add(new JLabel("Welcome, Member " + member.getUsername()));
        
        JButton addCourseButton = new JButton("Attend a Course");
        headerPanel.add(addCourseButton);
        addCourseButton.addActionListener(e -> {
            new ChooseCourseGUI(this,member);
        });

        JButton addReservationButton = new JButton("Reserve Court for Self Practice");
        headerPanel.add(addReservationButton);
        addReservationButton.addActionListener(e -> {
            new CreateCourseGUI(this,member);
        });
        

        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(e -> dispose());
        headerPanel.add(logoutButton);

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(e -> {UserAndBookingManager.getManager().deleteMemberAccount(member);dispose();});
        headerPanel.add(deleteAccountButton);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void setupCourseContainer() {
        courseContainer = new JPanel();
        courseContainer.setLayout(new BoxLayout(courseContainer, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(courseContainer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        displayCourses();
    }

    
public void displayCourses() {
    courseContainer.removeAll(); // Clear the existing courses
    
    courseContainer.add(new JLabel("Enrolled Courses"));
    for(Course course:member.getCourses()){
        System.out.println(member.getCourses().size());
        if(!course.getIsSelfPracticeCourse()){
            JPanel courseWithButtonPanel1 = new JPanel();
            courseWithButtonPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // small gap between components

            CoursePanel singleCoursePanel = new CoursePanel(course);
            courseWithButtonPanel1.add(singleCoursePanel);

            JButton cancelButton = new JButton("Quit Course");
            courseWithButtonPanel1.add(cancelButton);
            cancelButton.addActionListener(e -> {
                course.deregisterMember(member);
                UserAndBookingManager.getManager().saveUsersToFile();
                displayCourses();
            });
            courseContainer.add(courseWithButtonPanel1);
        }
    }
    courseContainer.add(new JLabel("Reservation for Self Practice"));
    System.out.println("AND!");
    for(Course course: member.getCourses()){
        System.out.println(course.getD());
        if(course.getIsSelfPracticeCourse()){
            System.out.println("res!");
            JPanel courseWithButtonPanel = new JPanel();
            courseWithButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // small gap between components
    
            CoursePanel singleCoursePanel = new CoursePanel(course);
            courseWithButtonPanel.add(singleCoursePanel);
    
            JButton cancelButton = new JButton("Cancel Reservation");
            courseWithButtonPanel.add(cancelButton);
            cancelButton.addActionListener(e -> {
                member.cancelCourse(course);
                UserAndBookingManager.getManager().saveUsersToFile();
                displayCourses();
            });
            courseContainer.add(courseWithButtonPanel);
        }
        
    }
        
    
    
    courseContainer.revalidate();
    courseContainer.repaint();
}
}