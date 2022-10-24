package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainMenu extends JFrame implements ActionListener, MenuListener{
	
	private static Employee loggedInEmployee;
	private static JDesktopPane desktopPane = new JDesktopPane();
	
	private JMenuBar menuBar;
	private JMenu salesEntry, salesReport, memberAccount, logout;
	
	public MainMenu (Employee employee) {
		loggedInEmployee = employee;
		
		menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		
		salesEntry = new JMenu("Sales Entry");
		salesReport = new JMenu("Sales Report");
		memberAccount = new JMenu("Member Account");
		logout = new JMenu("Logout");
		
		salesEntry.addMenuListener(this);
		salesReport.addMenuListener(this);
		memberAccount.addMenuListener(this);
		logout.addMenuListener(this);
		
		menuBar.add(salesEntry);
		menuBar.add(salesReport);
		menuBar.add(memberAccount);
		menuBar.add(logout);
		
		setJMenuBar(menuBar);
		
		add(desktopPane);

		setTitle("Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(900, 800));
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void openCart (Vector<Menu> selectedMenus) {
		Cart cart = new Cart(selectedMenus);
		desktopPane.add(cart);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public void menuSelected(MenuEvent e) {
		if (e.getSource() == logout) {
			this.dispose();
			new Login();
		}else if (e.getSource() == salesEntry) {
			desktopPane.add(new CreateTransaction(loggedInEmployee));
		}else if (e.getSource() == salesReport) {
			desktopPane.add(new SalesReport(loggedInEmployee));			
		}else if (e.getSource() == memberAccount) {
			desktopPane.add(new EditMember());
		}
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}
}
