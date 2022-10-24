package main;

import java.sql.ResultSet;

import utils.Connect;

public class Employee {
	private String username, employeeName, password, employeeRole;
	private int employeeId;
	
	public Employee(String username, String employeeName, String password, String employeeRole, int employeeId) {
		super();
		this.username = username;
		this.employeeName = employeeName;
		this.password = password;
		this.employeeRole = employeeRole;
		this.employeeId = employeeId;
	}
	
	public Employee(String employeeName, String employeeRole, int employeeId) {
		super();
		this.employeeName = employeeName;
		this.employeeRole = employeeRole;
		this.employeeId = employeeId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(String employeeRole) {
		this.employeeRole = employeeRole;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public static Employee login(String username, String password){
		String query = "SELECT EmployeeId, EmployeeName, EmployeeRole FROM MsEmployee WHERE EmployeeUsername = '" + username + "' AND password = '" + password + "'";
		ResultSet rs = Connect.getConnection().executeQuery(query);
		int employeeId = 0;
		String employeeName = "", employeeRole = "";
		
		try {
			while (rs.next()) {
				employeeId = rs.getInt(1);
				employeeName = rs.getString(2);
				employeeRole = rs.getString(3);
			}
			rs.close();
		} catch (Exception e) {
			return null;
		}
		
		if (employeeId == 0) return null;

		Employee employee = new Employee(employeeName, employeeRole, employeeId);

		return employee;
	}
	
	public static String getSupervisor (String spvUsername) {
		
		String getSpvQuery = "SELECT Password FROM MsEmployee WHERE EmployeeUsername = '" + spvUsername + "' AND EmployeeRole = 'SPV'";
		String password = "";
		
		ResultSet res = Connect.getConnection().executeQuery(getSpvQuery);
		
		try {
			while (res.next()) {
				password = res.getString(1);
			}
			res.close();
		} catch (Exception e) {
			return null;
		}
		return password;
	}
}
