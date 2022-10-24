package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class Cart extends JInternalFrame implements ActionListener{
	
	
	private JPanel mainPanel, topPanel, midPanel, botPanel, titlePanel, btnPanel;
	
	private JLabel lblId, lblName, lblQuantity, lblTitle, lblTemp;
	private JTextField txtId, txtName;
	
	private JButton btnUpdate, btnDelete;
	
	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPane;
	
	private JSpinner spinner;
	private SpinnerModel spinnerVal;
	
	private static int selected, selectedId;
	
	private Vector<Menu> selectedMenus;
	
	private void initTopPanel () {
		topPanel = new JPanel(new BorderLayout());
		titlePanel = new JPanel();
		lblTitle = new JLabel("Update Cart");
		
		Font font = lblTitle.getFont();
		
		lblTitle.setFont(font.deriveFont(Font.PLAIN, 24));
		
		titlePanel.add(lblTitle);
		
		topPanel.add(titlePanel);
		mainPanel.add(topPanel, BorderLayout.NORTH);
	}
	
	private void initMidPanel () {
		midPanel = new JPanel(new BorderLayout());
		
		Vector<Object> tableHeader, tableData;
		tableHeader = new Vector<>();
		tableHeader.add(new String ("Item ID"));
		tableHeader.add(new String ("Item Name"));
		tableHeader.add(new String ("Quantity"));
		
		tableModel = new DefaultTableModel(tableHeader, 0);
		tableModel.setRowCount(0);
		
		for (Menu m : selectedMenus) {
			tableData = new Vector<>();
			tableData.add(m.getItemId());
			tableData.add(m.getItemName());
			tableData.add(m.getQuantity());
			tableModel.addRow(tableData);
		}
		
		table = new JTable(tableModel);
		table.setModel(tableModel);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selected = table.getSelectedRow();
				txtId.setText(table.getValueAt(selected, 0).toString());
				txtName.setText(table.getValueAt(selected, 1).toString());
				spinnerVal = new SpinnerNumberModel(Integer.parseInt(table.getValueAt(selected, 2).toString()), 0, 100, 1);
				
				selectedId = Integer.parseInt(table.getValueAt(selected, 0).toString());
			}
		});
		
		midPanel.add(scrollPane);
		mainPanel.add(midPanel, BorderLayout.CENTER);
	}
	
	private void initBotPanel () {
		botPanel = new JPanel(new GridLayout(6, 2, 35, 10));
		btnPanel = new JPanel(new FlowLayout());
		
		lblId = new JLabel("Item ID");
		lblName = new JLabel("Item Name");
		lblQuantity = new JLabel("Quantity");
		lblTemp = new JLabel("");
		
		txtId = new JTextField();
		txtName = new JTextField();
		
		txtId.setEnabled(false);
		txtName.setEnabled(false);
		
		spinner = new JSpinner();
		
		btnUpdate = new JButton("Update Cart");
		btnDelete = new JButton("Delete Item");
		
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		
		btnPanel.add(btnDelete);
		btnPanel.add(btnUpdate);
		
		botPanel.add(lblId);
		botPanel.add(txtId);
		botPanel.add(lblName);
		botPanel.add(txtName);
		botPanel.add(lblQuantity);
		botPanel.add(spinner);
		botPanel.add(lblTemp);
		botPanel.add(btnPanel);
		
		mainPanel.add(botPanel, BorderLayout.SOUTH);
	}
	
	private void initUI () {
		mainPanel = new JPanel(new BorderLayout());
		
		initTopPanel();
		initMidPanel();
		initBotPanel();

		add(mainPanel);
	}
	
	private void refresh () {
		mainPanel.removeAll();
		initUI();
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	public Cart (Vector<Menu> selectedMenus) {
		super("Cart", true, true, true, true);
		this.selectedMenus = selectedMenus;
		
		initUI();
		setTitle("Update Cart");
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
			for (int i = 0; i < selectedMenus.size(); i++) {
				if (selectedMenus.get(i).getItemId() == selectedId) {
					selectedMenus.get(i).setQuantity((Integer) spinner.getValue());
					JOptionPane.showMessageDialog(null, "Cart updated!");
					refresh();
					return;
				}
			}
		}else if (e.getSource() == btnDelete) {
			int res = JOptionPane.showConfirmDialog(null, "Delete item from cart?", "Delete item", JOptionPane.YES_NO_OPTION);
			
			if (res == JOptionPane.YES_OPTION) {
				for (int i = 0; i < selectedMenus.size(); i++) {
					if (selectedMenus.get(i).getItemId() == selectedId) {
						selectedMenus.remove(selectedMenus.get(i));
						JOptionPane.showMessageDialog(null, "Item deleted!");
						refresh();
						return;
					}
				}
			}
		}
	}
}
