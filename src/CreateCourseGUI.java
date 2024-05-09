import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CreateCourseGUI extends JFrame {
    private User user;
    private CoachHomeGUI parentGUI1;
    private MemberHomeGUI parentGUI2;
    
    private JComboBox<String> timeComboBox;
    private JComboBox<String> dateComboBox;
    private JTextField maxStudentsField;
    private JButton searchButton;

    private JPanel courseContainer;

    public CreateCourseGUI(CoachHomeGUI parentGUI, User u){
        this.parentGUI1=parentGUI;
        this.user=u;
        setTitle("Create New Course");
        setSize(800, 700);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        // Setup court selection
        // courtComboBox = new JComboBox<>(courts);
        // add(new JLabel("Select Court:"));
        // add(courtComboBox);

        // Setup time selection
        String[] times = {"9:00-12:00", "12:00-15:00", "15:00-18:00", "18:00-21:00"};
        timeComboBox = new JComboBox<>(times);
        add(new JLabel("Select Time Slot:"));
        add(timeComboBox);

        // Setup date selection
        dateComboBox = new JComboBox<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            Date date = calendar.getTime();
            dateComboBox.addItem(new SimpleDateFormat("yyyy-MM-dd").format(date));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        add(new JLabel("Select Date:"));
        add(dateComboBox);

            maxStudentsField = new JTextField(5);
            add(new JLabel("Max Students:"));
            add(maxStudentsField);
        
        searchButton = new JButton("Search Available Courts");
        searchButton.addActionListener(e -> {
            setupCourseContainer();

        });
        add(searchButton);

        setVisible(true);
    }




    
    public CreateCourseGUI(MemberHomeGUI parentGUI, User u){
        this.parentGUI2=parentGUI;
        this.user=u;
        setTitle("Create New Course");
        setSize(800, 700);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Setup court selection
        // courtComboBox = new JComboBox<>(courts);
        // add(new JLabel("Select Court:"));
        // add(courtComboBox);

        // Setup time selection
        String[] times = {"9:00-12:00", "12:00-15:00", "15:00-18:00", "18:00-21:00"};
        timeComboBox = new JComboBox<>(times);
        add(new JLabel("Select Time Slot:"));
        add(timeComboBox);

        // Setup date selection
        dateComboBox = new JComboBox<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            Date date = calendar.getTime();
            dateComboBox.addItem(new SimpleDateFormat("yyyy-MM-dd").format(date));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        add(new JLabel("Select Date:"));
        add(dateComboBox);

        
        searchButton = new JButton("Search Available Courts");
        searchButton.addActionListener(e -> {
            setupCourseContainer();
        });
        add(searchButton);

        setVisible(true);
    }





    private void setupCourseContainer() {
        courseContainer = new JPanel();
        courseContainer.setLayout(new BoxLayout(courseContainer, BoxLayout.Y_AXIS));
        displayCourses();
        JScrollPane scrollPane = new JScrollPane(courseContainer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        add(courseContainer);
        setVisible(true);
    }
    
public void displayCourses() {
    courseContainer.removeAll(); // Clear the existing courses
    int remain=6;
    for(int i=1;i<=6;i++){
        boolean b=false;
                if(!(boolean)isCourtAvailable(String.valueOf(i)).get(0)){
                    b=true;
                    remain-=1;
                    if(user instanceof Member && (boolean)isCourtAvailable(String.valueOf(i)).get(1)&& !((Course)isCourtAvailable(String.valueOf(i)).get(2)).getRegisteredStudentIDs().contains((Member)user)){
                        JPanel courseWithButtonPanel = new JPanel();
                        courseWithButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // small gap between components
                        Course course =(Course)isCourtAvailable(String.valueOf(i)).get(2);
                        courseWithButtonPanel.add(new CoursePanel(course));
                        JButton chooseCourseButton = new JButton("Choose Course");
                        courseWithButtonPanel.add(chooseCourseButton);
                        chooseCourseButton.addActionListener(e -> {
                            if(!course.getRegisteredStudentIDs().contains((Member)user)){
                                course.registerMember((Member)user);
                                ((Member)user).addCourseToList(course);
                                UserAndBookingManager.getManager().saveUsersToFile();
                                parentGUI2.displayCourses();
                                JOptionPane.showMessageDialog(this, "Enroll course successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                            }else{
                                JOptionPane.showMessageDialog(this, "Enroll course successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                            
                        UserAndBookingManager.getManager().saveUsersToFile();
                    });
                    courseContainer.add(courseWithButtonPanel);
                }
                }
        if(!b){
            JPanel courseWithButtonPanel = new JPanel();
            courseWithButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // small gap between components

            CoursePanel singleCoursePanel;
            if(user instanceof Coach){
                singleCoursePanel = new CoursePanel(String.valueOf(i),(String)dateComboBox.getSelectedItem(),(String)timeComboBox.getSelectedItem(),(String)maxStudentsField.getText());
            }else{
                singleCoursePanel = new CoursePanel(String.valueOf(i),(String)dateComboBox.getSelectedItem(),(String)timeComboBox.getSelectedItem(),"1");
            }
            courseWithButtonPanel.add(singleCoursePanel);

            JButton createButton = new JButton("Create This Course!");
            courseWithButtonPanel.add(createButton);
            final int j=i;
            createButton.addActionListener(e -> {
                if(user instanceof Coach){
                    user.createCourse(String.valueOf(j),(String)dateComboBox.getSelectedItem(),(String)timeComboBox.getSelectedItem(),Integer.valueOf(maxStudentsField.getText()));
                    parentGUI1.displayCourses();
                    JOptionPane.showMessageDialog(this, "Course created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                }else{
                    user.createCourse(String.valueOf(j),(String)dateComboBox.getSelectedItem(),(String)timeComboBox.getSelectedItem(),1);
                    parentGUI2.displayCourses();
                    JOptionPane.showMessageDialog(this, "Booking created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                UserAndBookingManager.getManager().saveUsersToFile();
                dispose();
            });
            courseContainer.add(courseWithButtonPanel);
        }
    }
    if(remain==0){
        JOptionPane.showMessageDialog(this, "No available court at this time...", "Full", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
    







    // public CreateCourseGUI(MemberHomeGUI parentGUI, Member u){
    //     this.parentGUI2=parentGUI;
    //     this.user=u;
    //     setTitle("Create New Course");
    //     setSize(400, 300);
    //     setLayout(new FlowLayout());
    //     setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    //     setLocationRelativeTo(null);

    //     // Setup time selection
    //     String[] times = {"9:00-12:00", "12:00-15:00", "15:00-18:00", "18:00-21:00"};
    //     timeComboBox = new JComboBox<>(times);
    //     add(new JLabel("Select Time Slot:"));
    //     add(timeComboBox);

    //     // Setup date selection
    //     dateComboBox = new JComboBox<>();
    //     Calendar calendar = Calendar.getInstance();
    //     for (int i = 0; i < 7; i++) {
    //         Date date = calendar.getTime();
    //         dateComboBox.addItem(new SimpleDateFormat("yyyy-MM-dd").format(date));
    //         calendar.add(Calendar.DAY_OF_MONTH, 1);
    //     }
    //     add(new JLabel("Select Date:"));
    //     add(dateComboBox);


    //     // Create button to submit new course
    //     createButton = new JButton("Create Course");
    //     createButton.addActionListener(e -> {
    //         if(isCourtAvailable(court)){
    //             user.createCourse((String)courtComboBox.getSelectedItem(),(String)dateComboBox.getSelectedItem(),(String)timeComboBox.getSelectedItem(),1);
                
    //     UserAndBookingManager.getManager().saveUsersToFile();
    //             parentGUI2.displayCourses();
    //             JOptionPane.showMessageDialog(this, "Course created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    //             dispose();
    //         }else{
    //             JOptionPane.showMessageDialog(this, "Selected court and time are already booked.", "Booking Error", JOptionPane.ERROR_MESSAGE);   
    //         }
    //     });
    //     add(createButton);

    //     setVisible(true);
    // }

    public ArrayList<Object> isCourtAvailable(String court){
        ArrayList<Object> l=new ArrayList<Object>();
        for(User user:
        UserAndBookingManager.getManager().getAllUsers()){
            for(Course course:user.getCourses()){
                if(course.getCourtId().equals(court) && course.getD().equals((String)dateComboBox.getSelectedItem()) && course.getT().equals((String)timeComboBox.getSelectedItem())){
                    System.out.println("Court "+court+" Not Available");
                    l.add(false);
                    if(!course.isFull()){
                        l.add(true);
                        l.add(course);
                    }else{
                        l.add(false);
                    }
                    return l;
                }
            }
        }
        l.add(true);
        System.out.println("Court "+court+" Available");
        return l;
    }

}
