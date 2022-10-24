package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import utils.Connect;

public class Transaction {
	
	private int transactionId, employeeId, loyaltyPoints, totalTransactions, totalRevenue;
	
	public int getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(int totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public int getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(int totalTransactions) {
		this.totalTransactions = totalTransactions;
	}

	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	private String memberId, transactionDate, transactionType;
	
	public Transaction(int transactionId, int employeeId, String memberId, String transactionDate,
			String transactionType) {
		super();
		this.transactionId = transactionId;
		this.employeeId = employeeId;
		this.memberId = memberId;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
	}
	
	public Transaction(String memberId, int employeeId, String transactionDate,
			String transactionType) {
		super();
		this.employeeId = employeeId;
		this.memberId = memberId;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
	}
	
	public Transaction(String memberId, int employeeId, String transactionDate,
			String transactionType, int loyaltyPoints) {
		super();
		this.employeeId = employeeId;
		this.memberId = memberId;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.loyaltyPoints = loyaltyPoints;
	}
	
	public Transaction(int totalTransactions, int totalRevenue) {
		super();
		this.totalTransactions = totalTransactions;
		this.totalRevenue = totalRevenue;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public static int createNewTransactionHeader (Transaction transaction, boolean isMember) {
		
		Transaction newTransaction = new Transaction(transaction.getMemberId(), transaction.getEmployeeId(), transaction.getTransactionDate(), transaction.getTransactionType(), transaction.getLoyaltyPoints());
		
		String insertQuery = "INSERT INTO TrTransactionHeader (TransactionType, EmployeeId, MemberId, TransactionDate) VALUES (?, ?, ?, ?)";
		PreparedStatement preparedStatement = Connect.getConnection().prepareStatement(insertQuery);
		
		try {
			preparedStatement.setString(1, newTransaction.getTransactionType());
			preparedStatement.setInt(2, newTransaction.getEmployeeId());
			preparedStatement.setString(3, newTransaction.getMemberId());
			preparedStatement.setString(4, newTransaction.getTransactionDate());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String getLatestTransactionIdQuery = "";
		if (isMember) {
			getLatestTransactionIdQuery = "SELECT TransactionHeaderId FROM TrTransactionHeader WHERE MemberId = '" + transaction.getMemberId() + "' ORDER BY TransactionDate DESC LIMIT 1";			
			String updateLoyaltyPointsQuery = "UPDATE MsMember SET LoyaltyPoint = LoyaltyPoint + ? WHERE MemberId = '" + transaction.getMemberId() + "'";
			
			PreparedStatement prepStatement = Connect.getConnection().prepareStatement(updateLoyaltyPointsQuery);
			
			try {
				prepStatement.setInt(1, transaction.getLoyaltyPoints());
				prepStatement.execute();
				prepStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else {
			getLatestTransactionIdQuery = "SELECT TransactionHeaderId FROM TrTransactionHeader WHERE TransactionType = 'NONMBR' ORDER BY TransactionDate DESC LIMIT 1";						
		}
		
		
		int latestTransactionId = 0;
		
		ResultSet res = Connect.getConnection().executeQuery(getLatestTransactionIdQuery);
		
		try {
			while(res.next()) {
				latestTransactionId = res.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return latestTransactionId;
	}
	
	public static boolean createNewTransactionDetail (Transaction transaction, Vector<Menu> selectedMenus, boolean isMember) {
		
		int transactionId = 0;
		transactionId = createNewTransactionHeader(transaction, isMember);
		
		String insertDetailQuery = "INSERT INTO TrTransactionDetail (TransactionHeaderId, ItemId, Quantity) VALUES (?, ?, ?)";
		PreparedStatement preparedStatement = Connect.getConnection().prepareStatement(insertDetailQuery);
		
		for (Menu m : selectedMenus) {
			try {
				preparedStatement.setInt(1, transactionId);
				preparedStatement.setInt(2, m.getItemId());
				preparedStatement.setInt(3, m.getQuantity());
				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		try {
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static boolean approveDailyTransaction (int employeeId) {
		
		String approveTransactionQuery = "UPDATE TrTransactionHeader SET IsApproved = 1 WHERE EmployeeId = ?";
		PreparedStatement preparedStatement = Connect.getConnection().prepareStatement(approveTransactionQuery);
		
		try {
			preparedStatement.setInt(1, employeeId);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static Transaction getUnapprovedTransactionSummary (int employeeId) {
		
		String getTotalTransactionQuery = "SELECT COUNT (TransactionHeaderId), SUM (ItemPrice * Quantity) FROM TrTransactionHeader H JOIN TrTransactionDetail D ON H.TransactionHeaderId = D.TransactionHeaderId JOIN MsMenu I ON D.ItemId = I.ItemId WHERE IsApproved = 0 AND EmployeeId = " + employeeId;
		ResultSet res = Connect.getConnection().executeQuery(getTotalTransactionQuery);
		
		int totalTransaction = 0, totalRevenue = 0;
		
		try {
			while(res.next()) {
				totalTransaction = res.getInt(1);
				totalRevenue = res.getInt(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		Transaction transactionSummary = new Transaction(totalTransaction, totalRevenue);
		return transactionSummary;
	}
}
