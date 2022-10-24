package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame implements ActionListener{
	
	private JPanel mainPanel, pnlTop, pnlMid, pnlBot;
	private JLabel lblTitle, lblUsername, lblPassword;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JButton btnLogin;

	public Login() {
		mainPanel = new JPanel(new BorderLayout());
		
		pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		lblTitle = new JLabel("LanCoffee Shop");
		lblTitle.setFont(new Font(lblTitle.getFont().getFontName(), Font.BOLD, 24));
		
		pnlMid = new JPanel(new GridLayout(2, 2, 5, 5));
		
		lblUsername = new JLabel("Username: ");
		lblPassword = new JLabel("Password: ");
		
		txtUsername = new JTextField();
		passwordField = new JPasswordField();
		
		pnlTop.add(lblTitle);
		
		pnlMid.add(lblUsername);
		pnlMid.add(txtUsername);
		pnlMid.add(lblPassword);
		pnlMid.add(passwordField);
		
		pnlBot = new JPanel();
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(this);
		pnlBot.add(btnLogin);
		pnlBot.setBorder(new EmptyBorder(8, 0, 0, 0));
		
		mainPanel.add(pnlTop, BorderLayout.NORTH);
		mainPanel.add(pnlMid, BorderLayout.CENTER);
		mainPanel.add(pnlBot, BorderLayout.SOUTH);
		
		mainPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
		
		add(mainPanel);
		
		setTitle("LanCoffee Shop - Login");
		setSize(new Dimension(400, 230)); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); 
		setResizable(false); 
		setVisible(true); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			String uName = txtUsername.getText().trim();
			String pass = passwordField.getText();
			
			if (uName.equals("")) {
				JOptionPane.showMessageDialog(null, "Username can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}else if (pass.equals("")) {
				JOptionPane.showMessageDialog(null, "Password can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
			}else {
				Employee emp = Employee.login(uName, pass);
				
				if (emp ==  null) {
					JOptionPane.showMessageDialog(null, "Credentials not found!", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Welcome, " + emp.getEmployeeName() + "!");
					this.dispose();
					new MainMenu(emp);
				}
			}
			
		}
	}

}
