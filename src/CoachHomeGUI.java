import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CoachHomeGUI extends JFrame {
    private Coach coach;
    private JPanel courseContainer;

    public CoachHomeGUI(Coach coach) {
        super("Coach Homepage");
        this.coach = coach;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 800);
        setLocationRelativeTo(null);

        addHeader(); // Method to add header panel
        setupCourseContainer();

        setVisible(true);
    }

    private void addHeader() {
        JPanel headerPanel = new JPanel(new FlowLayout()); // Create the header panel
        headerPanel.add(new JLabel("Welcome, Coach " + coach.getUsername()));

        JButton addCourseButton = new JButton("Create a New Course");
        headerPanel.add(addCourseButton);
        addCourseButton.addActionListener(e -> {
            new CreateCourseGUI(this,coach);
            displayCourses();
        });
        

        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(e -> dispose());
        headerPanel.add(logoutButton);

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(e -> {UserAndBookingManager.getManager().deleteCoachAccount(coach);dispose();});
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

    for(User user: UserAndBookingManager.getManager().getAllUsers()){
        if (user.getUsername().equals(coach.getUsername())) {
            for(Course course: user.getCourses()){
                JPanel courseWithButtonPanel = new JPanel();
                courseWithButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // small gap between components

                CoursePanel singleCoursePanel = new CoursePanel(course);
                courseWithButtonPanel.add(singleCoursePanel);

                JButton cancelButton = new JButton("Cancel Course");
                courseWithButtonPanel.add(cancelButton);
                cancelButton.addActionListener(e -> {
                    coach.cancelCourse(course);
                    displayCourses();
                });
                courseContainer.add(courseWithButtonPanel);
            }
                
            }
        
        }
    
    courseContainer.revalidate();
    courseContainer.repaint();
}
}