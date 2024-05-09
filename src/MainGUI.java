import javax.swing.*;

public class MainGUI extends JFrame{
    private static JFrame frame;
    private JButton signupButton;
    private JButton loginButton;
    public MainGUI() {
        UserAndBookingManager.getManager();
        frame = new JFrame("Login or Signup");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        
        loginButton = new JButton("Login");
        loginButton.setBounds(50, 30, 200, 30);
        loginButton.addActionListener(e -> new LogInGUI());
        
        signupButton = new JButton("Signup");
        signupButton.setBounds(50, 80, 200, 30);
        signupButton.addActionListener(e -> new SignUpGUI());

		frame.setLayout(null);
        frame.add(loginButton);
        frame.add(signupButton);
    }
    public static void main(String[] args) {
		new MainGUI();
	}

    
}
