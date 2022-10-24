package main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SalesReport extends JInternalFrame implements ActionListener{
	
	private JPanel mainPanel, topPanel, botPanel, titlePanel, btnPanel;
	
	private JLabel lblDate, lblTotalTransaction, lblTotalRevenue, lblTitle, lblTemp;
	private JTextField txtDate, txtTotalTransaction, txtTotalRevenue;
	
	private JButton btnApprove;
	private Employee loggedInEmployee;
	
	private void initTopPanel () {
		topPanel = new JPanel(new BorderLayout());
		titlePanel = new JPanel();
		lblTitle = new JLabel(loggedInEmployee.getEmployeeId() + " - " + loggedInEmployee.getEmployeeName() + " - " +  "Sales Report");
		
		Font font = lblTitle.getFont();
		
		lblTitle.setFont(font.deriveFont(Font.PLAIN, 24));
		
		titlePanel.add(lblTitle);
		
		topPanel.add(titlePanel);
		mainPanel.add(topPanel, BorderLayout.NORTH);
	}
	
	
	private void initBotPanel () {
		botPanel = new JPanel(new GridLayout(6, 2, 35, 10));
		btnPanel = new JPanel(new BorderLayout());
		
		lblDate = new JLabel("Date");
		lblTotalTransaction = new JLabel("Total Transaction");
		lblTotalRevenue = new JLabel("Total Revenue");
		lblTemp = new JLabel("");
		
		txtDate = new JTextField();
		txtTotalRevenue = new JTextField();
		txtTotalTransaction = new JTextField();
		
		Transaction transactionSummary = Transaction.getUnapprovedTransactionSummary(loggedInEmployee.getEmployeeId());
		
		 Date date = new Date();
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String time = dateFormat.format(date).toString();
		
		txtDate.setEnabled(false);
		txtTotalRevenue.setEnabled(false);
		txtTotalTransaction.setEnabled(false);
		
		txtDate.setText(time);
		txtTotalTransaction.setText(transactionSummary.getTotalTransactions() + "");
		txtTotalRevenue.setText(transactionSummary.getTotalRevenue() + "");
		
		btnApprove = new JButton("Approve Report");
		
		btnApprove.addActionListener(this);
		
		btnPanel.add(btnApprove);
		
		botPanel.add(lblDate);
		botPanel.add(txtDate);
		botPanel.add(lblTotalTransaction);
		botPanel.add(txtTotalTransaction);
		botPanel.add(lblTotalRevenue);
		botPanel.add(txtTotalRevenue);
		botPanel.add(lblTemp);
		botPanel.add(btnPanel);
		
		mainPanel.add(botPanel, BorderLayout.SOUTH);
	}
	
	public SalesReport (Employee employee) {
		loggedInEmployee = employee;
		
		initUI();
		setTitle("Sales Report");
		setVisible(true);
		setSize(550, 400);
		setMaximizable(true);
		setIconifiable(false);
		setResizable(false);
		setClosable(true);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnApprove) {
			if (txtTotalTransaction.getText().toString().equals(0)) {
				JOptionPane.showMessageDialog(null, "No transaction needs to be approved!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}else {			
				String spvUname = JOptionPane.showInputDialog(null, "Input Supervisor Username");
				String spvPass = Employee.getSupervisor(spvUname);
				
				if (spvPass.equals("")) {
					JOptionPane.showMessageDialog(null, "Invalid username!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String responsePass = JOptionPane.showInputDialog(null, "Input Supervisor Password");
				if (responsePass.equals(spvPass)) {
					boolean isSuccess = false;
					isSuccess = Transaction.approveDailyTransaction(loggedInEmployee.getEmployeeId());
					
					if (isSuccess) {
						JOptionPane.showMessageDialog(null, "Sales Report Approved!");
						refresh();
						return;
					}else {
						JOptionPane.showMessageDialog(null, "Oops! Something went wrong, please try again later", "Error", JOptionPane.ERROR_MESSAGE);
						return;					
					}
				}else {
					JOptionPane.showMessageDialog(null, "Credentials not found!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
	}
}
