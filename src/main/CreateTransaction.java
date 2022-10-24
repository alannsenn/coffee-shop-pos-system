package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CreateTransaction extends JInternalFrame implements ActionListener, KeyListener{
	
	private JPanel mainPanel, rightPanel, leftPanel, leftTopPanel, leftMidPanel, leftBotPanel, btnPanel, customerFormPanel, transactionFormPanel, radioButtonContainer;
	private JLabel welcomeLbl, leftTitleLbl, custTypeLbl, memberIdLbl, nameLbl, phoneLbl, emailLbl, dobLbl, genderLbl, pointLbl, totalItmLbl, totalPriceLbl, paymentLbl, changeLbl;
	private JTextField txtMemberId, txtName, txtPhone, txtEmail, txtDob, txtGender, txtPoint, txtTotalItm = new JTextField(), txtTotalPrice = new JTextField(), txtPayment, txtChange;
	private JRadioButton member, nonMember;
	private ButtonGroup customerType;
	private JButton btnCheckout, btnHelp;
	private JTable menuTable;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel;
	private Vector<Menu> selectedMenus = new Vector<>();
	private static int selectedMenu;
	private Employee loggedInEmployee;
	
	public void initLeftTopPanel () {
		leftPanel = new JPanel(new BorderLayout());
		leftTopPanel = new JPanel(new BorderLayout());
		
		welcomeLbl = new JLabel("Welcome " + loggedInEmployee.getEmployeeName());
		leftTitleLbl = new JLabel("Create New Customer Transaction");
		
		leftTopPanel.add(welcomeLbl, BorderLayout.NORTH);
		leftTopPanel.add(leftTitleLbl, BorderLayout.SOUTH);
		leftPanel.add(leftTopPanel, BorderLayout.NORTH);
	}
	
	public void initLeftMidPanel () {
		
		leftMidPanel = new JPanel(new GridLayout(2, 1));
		customerFormPanel = new JPanel(new GridLayout(8, 2, 3, 2));
		transactionFormPanel = new JPanel(new GridLayout(6, 2, 3, 5));
		radioButtonContainer = new JPanel(new FlowLayout());
		
		custTypeLbl = new JLabel("Customer Type");
		memberIdLbl = new JLabel("Member ID");
		nameLbl = new JLabel("Customer Name");
		genderLbl = new JLabel("Gender");
		emailLbl = new JLabel("Email");
		phoneLbl = new JLabel("Phone Number");
		dobLbl = new JLabel("Date of Birth");
		pointLbl = new JLabel("Total Loyalty Points");
		
		totalItmLbl = new JLabel("Total Item(s)");
		totalPriceLbl = new JLabel("Total Price");
		paymentLbl = new JLabel("Payment");
		changeLbl = new JLabel("Change");

		txtMemberId = new JTextField();
		txtName = new JTextField();
		txtGender = new JTextField();
		txtEmail = new JTextField();
		txtPhone = new JTextField();
		txtDob = new JTextField();
		txtPoint = new JTextField();
		
		txtMemberId.addKeyListener(this);
		
		txtTotalItm.setText("0");
		txtTotalPrice.setText("0");
		txtPayment = new JTextField();
		txtChange = new JTextField();
		
		txtPayment.addKeyListener(this);
		
		member = new JRadioButton("Member");
		nonMember = new JRadioButton("Non - Member");
		
		member.addActionListener(this);
		nonMember.addActionListener(this);
		
		txtMemberId.setEnabled(false);
		txtName.setEnabled(false);
		txtGender.setEnabled(false);
		txtEmail.setEnabled(false);
		txtPhone.setEnabled(false);
		txtDob.setEnabled(false);
		txtPoint.setEnabled(false);
		
		txtTotalItm.setEnabled(false);
		txtTotalPrice.setEnabled(false);
		txtPayment.setEnabled(false);
		txtChange.setEnabled(false);
		
		customerType = new ButtonGroup();
		
		customerType.add(member);
		customerType.add(nonMember);
		radioButtonContainer.add(member);
		radioButtonContainer.add(nonMember);
		
		customerFormPanel.add(custTypeLbl);
		customerFormPanel.add(radioButtonContainer);
		
		customerFormPanel.add(memberIdLbl);
		customerFormPanel.add(txtMemberId);
		
		customerFormPanel.add(nameLbl);
		customerFormPanel.add(txtName);
		
		customerFormPanel.add(genderLbl);
		customerFormPanel.add(txtGender);
		
		customerFormPanel.add(emailLbl);
		customerFormPanel.add(txtEmail);
		
		customerFormPanel.add(phoneLbl);
		customerFormPanel.add(txtPhone);
		
		customerFormPanel.add(dobLbl);
		customerFormPanel.add(txtDob);
		
		customerFormPanel.add(pointLbl);
		customerFormPanel.add(txtPoint);
		
		customerFormPanel.setBorder(new EmptyBorder(10,10,10,10));
		
		transactionFormPanel.add(totalItmLbl);
		transactionFormPanel.add(txtTotalItm);
		
		transactionFormPanel.add(totalPriceLbl);
		transactionFormPanel.add(txtTotalPrice);
		
		transactionFormPanel.add(paymentLbl);
		transactionFormPanel.add(txtPayment);
		
		transactionFormPanel.add(changeLbl);
		transactionFormPanel.add(txtChange);
		
		transactionFormPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		leftMidPanel.add(customerFormPanel);
		leftMidPanel.add(transactionFormPanel);
		
		leftPanel.add(leftMidPanel);
	}

	public void initLeftBotPanel () {
		
		leftBotPanel = new JPanel(new GridLayout(2, 1));
		btnPanel = new JPanel(new BorderLayout());
		
		btnCheckout = new JButton("Checkout");
		btnHelp = new JButton("Ask for Help");
		
		btnCheckout.addActionListener(this);
		btnHelp.addActionListener(this);
		
		btnPanel.add(btnCheckout, BorderLayout.NORTH);
		btnPanel.add(btnHelp, BorderLayout.SOUTH);
		
		leftBotPanel.add(btnPanel);
		leftPanel.add(leftBotPanel, BorderLayout.SOUTH);
	}
	
	public void initRightPanel () {
		
		rightPanel = new JPanel();
		
		Vector<Object> tableHeader, tableData;
		tableHeader = new Vector<>();
		tableHeader.add(new String ("Item ID"));
		tableHeader.add(new String ("Item Name"));
		tableHeader.add(new String ("Item Price"));
		tableHeader.add(new String ("Stock"));
		
		tableModel = new DefaultTableModel(tableHeader, 0);
		tableModel.setRowCount(0);
		
		Vector<Menu> menus  = Menu.getAllMenus();
		
		for (Menu m : menus) {
			tableData = new Vector<>();
			tableData.add(m.getItemId());
			tableData.add(m.getItemName());
			tableData.add(m.getItemPrice());
			tableData.add(m.getStock());
			tableModel.addRow(tableData);
		}
		
		menuTable = new JTable(tableModel);
		menuTable.setModel(tableModel);
		
		scrollPane = new JScrollPane(menuTable);
		scrollPane.setPreferredSize(new Dimension(350, 550));
		
		menuTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked (MouseEvent e) {
				selectedMenu = menuTable.getSelectedRow();
				int currTotalItem = Integer.parseInt(txtTotalItm.getText());
				txtTotalItm.setText((currTotalItem += 1) + "");
				int currTotalPrice = Integer.parseInt(txtTotalPrice.getText());
				int selectedPrice = Integer.parseInt(menuTable.getValueAt(selectedMenu, 2).toString());
				txtTotalPrice.setText((currTotalPrice + selectedPrice) + "");
				
				int selectedMenuId = Integer.parseInt(menuTable.getValueAt(selectedMenu, 0).toString());
				String itemName = menuTable.getValueAt(selectedMenu, 1).toString();
				
				Menu selectedMenu = new Menu(selectedMenuId, itemName, 1);
				
				addItem(selectedMenu);
			}
		});
		
		rightPanel.add(scrollPane);
		
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);
	}
	
	private void addItem (Menu menu) {
		
		for (int i = 0; i < selectedMenus.size(); i++) {
			if (selectedMenus.get(i).getItemId() == menu.getItemId()) {
				
				int qty = selectedMenus.get(i).getQuantity();
				selectedMenus.get(i).setQuantity(qty += menu.getQuantity());
				return;
			}
		}
		selectedMenus.add(menu);
	}
	
	
	public CreateTransaction(Employee employee) {
		loggedInEmployee = employee;
		initUI();
		
		setTitle("Sales Entry");
		setVisible(true);
		setSize(800, 650);
		setMaximizable(true);
		setIconifiable(false);
		setResizable(false);
		setClosable(true);
	}

	private void initUI () {
		mainPanel = new JPanel(new GridLayout(1, 2));
		
		initLeftTopPanel();
		initLeftMidPanel();
		initLeftBotPanel();
		initRightPanel();
		
		add (mainPanel);
	}
	
	private void refresh () {
		mainPanel.removeAll();
		initUI();
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == member) {
			txtMemberId.setEnabled(true);
			
			txtName.setEnabled(false);
			txtGender.setEnabled(false);
			txtEmail.setEnabled(false);
			txtPhone.setEnabled(false);
			txtDob.setEnabled(false);
			txtPoint.setEnabled(false);
			
			txtPayment.setEnabled(true);
			
		}else if (e.getSource() == nonMember) {
			txtMemberId.setEnabled(false);
			
			txtMemberId.setText("");
			txtName.setText("");
			txtGender.setText("");
			txtEmail.setText("");
			txtPhone.setText("");
			txtDob.setText("");
			txtPoint.setText("");
			
			txtName.setEnabled(true);
			txtGender.setEnabled(true);
			txtEmail.setEnabled(true);
			txtPhone.setEnabled(true);
			txtDob.setEnabled(true);
			txtPoint.setEnabled(false);
			
			txtPayment.setEnabled(true);
		}else if (e.getSource() == btnCheckout) {
			
			boolean isFilled = false;
			
			if (selectedMenus.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please select item first!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String name, gender, email, phone;
			name = txtName.getText().trim().toString();
			gender = txtGender.getText().trim().toString();
			email = txtEmail.getText().trim().toString();
			phone = txtPhone.getText().trim().toString();
			boolean hasLetters = false;
			
			if (member.isSelected() || nonMember.isSelected()) {
				  Date date = new Date();
				  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				  String transactionDate = dateFormat.format(date).toString();
				  
				  String memberId = null, transactionType = null;
				  int employeeId = loggedInEmployee.getEmployeeId();
				  boolean isMember = false;
				  
				if (member.isSelected()) {
					if (txtMemberId.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Member ID can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}else {
						memberId = txtMemberId.getText().toString();
						isMember = true;
					}
				}else if (nonMember.isSelected()) {
					if (!txtName.getText().trim().equals("") && !txtGender.getText().trim().equals("") && !txtEmail.getText().trim().equals("") && !txtPhone.getText().trim().equals("") && !txtDob.getText().trim().equals("")) {
						
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
						
						isFilled = true;
					}else {
						isMember = false;
					}
				}
				
				if (txtTotalItm.getText().trim().equals("0")) {
					JOptionPane.showMessageDialog(null, "Please select item first!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (txtPayment.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill payment amount!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					if (txtChange.getText().equals("0")) {
						JOptionPane.showMessageDialog(null, "Payment amount must be bigger than Total Price!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (isFilled) {

						int response = JOptionPane.showConfirmDialog(null, "Are you sure to continue?", "Create new Member",JOptionPane.YES_NO_OPTION);
						
						if (response == JOptionPane.YES_OPTION) {
							memberId = createMemberId();
							Member newMember = new Member(memberId, name, gender, email, phone, txtDob.getText().trim());
							Member.createNewMember(newMember);
							isMember = true;
							JOptionPane.showMessageDialog(null, "Member created successfully!");
						}
					}
					
					if (isMember) {
						transactionType = "MBR";
					}else {
						memberId = "NONMBR";
						transactionType = "NONMBR";
					}
					
					boolean isSuccess = false;
					
					Transaction newTransaction = new Transaction(memberId, employeeId, transactionDate, transactionType, getLoyaltyPoints(Integer.parseInt(txtTotalPrice.getText().trim())));
					isSuccess = Transaction.createNewTransactionDetail(newTransaction, selectedMenus, isMember);
					
					if (isSuccess && isMember) {
						Menu.updateItemStock(selectedMenus);
						JOptionPane.showMessageDialog(null, "Transaction successful! Loyalty points added!");
						refresh();
					}else if (isSuccess){
						Menu.updateItemStock(selectedMenus);
						JOptionPane.showMessageDialog(null, "Transaction successful! No Loyalty points added!");
						refresh();
					}else {
						JOptionPane.showMessageDialog(null, "Oops! Something went wrong! Please try again later", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}else {
				txtPayment.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Please select Customer Type first!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}else if (e.getSource() == btnHelp) {	
			
			if (!member.isSelected() && !nonMember.isSelected()) {
				JOptionPane.showMessageDialog(null, "Please select Customer Type first!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (txtTotalItm.getText().toString().equals("0")) {
				JOptionPane.showMessageDialog(null, "Pleade add item to cart first!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String spvUname = JOptionPane.showInputDialog(null, "Input Supervisor Username");
			String spvPass = Employee.getSupervisor(spvUname);
			
			if (spvPass.equals("")) {
				JOptionPane.showMessageDialog(null, "Invalid username!");
				return;
			}
			
			String responsePass = JOptionPane.showInputDialog(null, "Input Supervisor Password");
			if (responsePass.equals(spvPass)) {
				MainMenu.openCart(selectedMenus);
			}else {
				JOptionPane.showMessageDialog(null, "Credentials not found!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
	
	private String createMemberId () {
		String newMemberId = "";
		String latestMemberId = Member.getLatestMemberId();
		
		int tempId = Integer.parseInt(latestMemberId) + 1;
		String formattedId = String.format("%04d", tempId);
		newMemberId = "MBR" + formattedId;
		
		return newMemberId;
	}
	
	private int getPaymentChange (int totalPrice, int paymentAmount) {
		if (paymentAmount < totalPrice) {
			return -1;
		}
		return paymentAmount - totalPrice;
	}
	
	private int getLoyaltyPoints (int totalPrice) {
		int loyaltyPoint = (int) ((int) totalPrice * 0.01);
		return loyaltyPoint;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == txtMemberId) {
			String searchMemberId = txtMemberId.getText().trim();
			
			if (searchMemberId.length() == 7) {
				Member memberResult = Member.getMemberDetail(searchMemberId);
				
				if (memberResult == null) {
					JOptionPane.showMessageDialog(null, "Invalid Member ID!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					txtName.setText(memberResult.getMemberName());
					txtGender.setText(memberResult.getMemberGender());
					txtEmail.setText(memberResult.getMemberEmail());
					txtPhone.setText(memberResult.getMemberPhone());
					txtDob.setText(memberResult.getMemberDob());
					txtPoint.setText("" + memberResult.getLoyaltyPoint());
				}
			}else if (searchMemberId.length() > 7) {
				JOptionPane.showMessageDialog(null, "Invalid Member ID Length!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}			
		}else if (e.getSource() == txtPayment){
			int paymentAmount = 0, totalPrice = 0;
			if (txtPayment.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "Payment can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				paymentAmount = Integer.parseInt(txtPayment.getText().trim());
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "Invalid payment amount!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			totalPrice = Integer.parseInt(txtTotalPrice.getText().trim());
			int paymentChange = getPaymentChange(totalPrice, paymentAmount);

			if (paymentChange == -1) {
				txtChange.setText("0");
			}else {				
				txtChange.setText(paymentChange + "");
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	} 
}
