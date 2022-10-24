package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditMember extends JInternalFrame implements ActionListener, KeyListener{
	
	private JPanel mainPanel, topPanel, botPanel, titlePanel, btnPanel;
	
	private JLabel lblId, lblName, lblGender, lblEmail, lblPhone, lblDob, lblTitle, lblTemp;
	private JTextField txtId, txtName, txtGender, txtEmail, txtPhone, txtDob;
	
	private JButton btnUpdate;
	
	private void initTopPanel () {
		topPanel = new JPanel(new BorderLayout());
		titlePanel = new JPanel();
		lblTitle = new JLabel("Update Member Account");
		
		Font font = lblTitle.getFont();
		
		lblTitle.setFont(font.deriveFont(Font.PLAIN, 24));
		
		titlePanel.add(lblTitle);
		
		topPanel.add(titlePanel);
		mainPanel.add(topPanel, BorderLayout.NORTH);
	}
	
	private void initBotPanel () {
		botPanel = new JPanel(new GridLayout(7, 2, 35, 10));
		btnPanel = new JPanel(new FlowLayout());
		
		lblId = new JLabel("Member ID");
		lblName = new JLabel("Member Name");
		lblGender = new JLabel("Gender");
		lblEmail = new JLabel("Email");
		lblPhone = new JLabel("Phone number");
		lblDob = new JLabel("Date of Birth");
		lblTemp = new JLabel("");
		
		txtId = new JTextField();
		txtName = new JTextField();
		txtGender = new JTextField();
		txtEmail = new JTextField();
		txtPhone = new JTextField();
		txtDob = new JTextField();
		
		btnUpdate = new JButton("Update Account");
		
		txtId.addKeyListener(this);
		btnUpdate.addActionListener(this);
		
		txtName.setEnabled(false);
		txtGender.setEnabled(false);
		txtEmail.setEnabled(false);
		txtPhone.setEnabled(false);
		txtDob.setEnabled(false);
		
		btnPanel.add(btnUpdate);
		
		botPanel.add(lblId);
		botPanel.add(txtId);
		botPanel.add(lblName);
		botPanel.add(txtName);
		botPanel.add(lblGender);
		botPanel.add(txtGender);
		botPanel.add(lblEmail);
		botPanel.add(txtEmail);
		botPanel.add(lblPhone);
		botPanel.add(txtPhone);
		botPanel.add(lblDob);
		botPanel.add(txtDob);
		botPanel.add(lblTemp);
		botPanel.add(btnPanel);
		
		mainPanel.add(botPanel, BorderLayout.SOUTH);
	}
	
	private void initUI () {
		mainPanel = new JPanel(new BorderLayout());
		
		initTopPanel();
		initBotPanel();

		add(mainPanel);
	}
	
	private void refresh () {
		mainPanel.removeAll();
		initUI();
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	public EditMember () {
		initUI();
		setTitle("Update Member Account");
		setVisible(true);
		setSize(550, 400);
		setMaximizable(true);
		setIconifiable(false);
		setResizable(false);
		setClosable(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnUpdate) {
			
			String name, gender, email, phone, dob;
			name = txtName.getText().trim().toString();
			gender = txtGender.getText().trim().toString();
			email = txtEmail.getText().trim().toString();
			phone = txtPhone.getText().trim().toString();
			dob = txtDob.getText().trim().toString();
			boolean hasLetters = false;
			
			if (name.length() < 2) { 
				JOptionPane.showMessageDialog(null, "Invalid name!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!gender.equals("M") && !gender.equals("F")) {
				JOptionPane.showMessageDialog(null, "Gender must be M or F!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!email.contains("@") && !email.endsWith(".com")) {
				JOptionPane.showMessageDialog(null, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!phone.startsWith("0")) {
				JOptionPane.showMessageDialog(null, "Invalid phone number format!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				for (int i = 0; i < phone.length(); i++) {
					if (phone.toLowerCase().charAt(i) >= 'a' && phone.toLowerCase().charAt(i) <= 'z') {
						hasLetters = true;
					}
				}
				if(hasLetters) {
					JOptionPane.showMessageDialog(null, "Invalid phone number format!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			boolean isSuccess = false;
			Member updatedMember = new Member(txtId.getText().trim().toString(), name, gender, email, phone, dob);
			isSuccess = Member.updateMemberAccount(updatedMember);
			
			if (isSuccess) {
				JOptionPane.showMessageDialog(null, "Member Account Updated!");
				refresh();
			}else {
				JOptionPane.showMessageDialog(null, "Oops! Something went wrong, please try again later", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == txtId) {
				String searchMemberId = txtId.getText().trim();
			
			if (searchMemberId.length() == 7) {
				Member memberResult = Member.getMemberDetail(searchMemberId);
				
				if (memberResult == null) {
					txtName.setText("");
					txtGender.setText("");
					txtEmail.setText("");
					txtPhone.setText("");
					txtDob.setText("");
					
					txtName.setEnabled(false);
					txtGender.setEnabled(false);
					txtEmail.setEnabled(false);
					txtPhone.setEnabled(false);
					txtDob.setEnabled(false);
					
					JOptionPane.showMessageDialog(null, "Invalid Member ID!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					
					txtName.setText(memberResult.getMemberName());
					txtGender.setText(memberResult.getMemberGender());
					txtEmail.setText(memberResult.getMemberEmail());
					txtPhone.setText(memberResult.getMemberPhone());
					txtDob.setText(memberResult.getMemberDob());
					
					txtName.setEnabled(true);
					txtGender.setEnabled(true);
					txtEmail.setEnabled(true);
					txtPhone.setEnabled(true);
					txtDob.setEnabled(true);
				}
			}else if (searchMemberId.length() > 7) {
				txtName.setText("");
				txtGender.setText("");
				txtEmail.setText("");
				txtPhone.setText("");
				txtDob.setText("");
				
				txtName.setEnabled(false);
				txtGender.setEnabled(false);
				txtEmail.setEnabled(false);
				txtPhone.setEnabled(false);
				txtDob.setEnabled(false);
				
				JOptionPane.showMessageDialog(null, "Invalid Member ID Length!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}	
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
