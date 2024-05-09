import javax.swing.*;

public class LogInGUI extends JFrame{
    JFrame frame;
    private JLabel userLabel;
    private JLabel passwordLabel;

    private JTextField userText;
    private JPasswordField passwordText;
    JButton logIn;

     public LogInGUI(){
        frame = new JFrame("Login");
        frame.setSize(280, 180);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 10, 80, 25);
        frame.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        frame.add(userText);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 40, 80, 25);
        frame.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        frame.add(passwordText);



        logIn = new JButton("Log In");
        logIn.setBounds(90, 100, 100, 25);
        logIn.addActionListener(e -> {
            String username = userText.getText().trim();
            boolean b=false;
                for(User user:UserAndBookingManager.getManager().getAllUsers()){
                    System.out.println(user.getUsername()+" ! "+user.getPassword());

                    if (user.getUsername().equals(username)&&user.getPassword().equals(passwordText.getText().trim())) {  // Check if the line is not empty
                        if(user instanceof Member){
                            new MemberHomeGUI((Member)user);
                        }else{
                            new CoachHomeGUI((Coach)user);
                        }
                        b=true;
                        break;
                    }
                }
                if(!b){
                    JOptionPane.showMessageDialog(frame, "Incorrect username or password!");
                }

            
        });
        frame.add(logIn);



     }

     
}
