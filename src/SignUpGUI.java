import javax.swing.*;

public class SignUpGUI extends JFrame{
    JFrame frame;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JLabel roleLabel;

    private JTextField userText;
    private JPasswordField passwordText;
    private JButton saveButton;
    JComboBox<String> roleBox;
    public SignUpGUI(){
        frame = new JFrame("Signup");
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

        roleLabel = new JLabel("Role:");
        roleLabel.setBounds(10, 70, 80, 25);
        frame.add(roleLabel);

        String[] roles = {"Coach", "Member"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setBounds(100, 70, 160, 25);
        frame.add(roleBox);
        

        saveButton = new JButton("Save");
        saveButton.setBounds(10, 110, 100, 25);
        saveButton.addActionListener(e -> {
            boolean b=true;
            String username = userText.getText().trim();
            for(User user:UserAndBookingManager.getManager().getAllUsers()){

                if (user.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(frame, "Username already exists!");
                b=false;
                UserAndBookingManager.getManager().saveUsersToFile();
                    
                break;
                } 
                
            }
            if(b){
                UserAndBookingManager.getManager().saveNewUser(username, new String(passwordText.getPassword()).trim(),roleBox.getSelectedItem().toString());
                UserAndBookingManager.getManager().saveUsersToFile();dispose();
                JOptionPane.showMessageDialog(frame, "User saved successfully!");
                
            }

        });
        
        frame.add(saveButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 110, 100, 25);
        backButton.addActionListener(e -> 
        frame.dispose());
        frame.add(backButton);

        frame.repaint();

    }

      
}
