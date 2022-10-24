package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public final class Connect {
	
	private final String PATH_URL = "jdbc:ucanaccess://D:\\File 1\\File 2\\Final Exam\\";
	private final String DATABASE = "LanCoffeeShop_DB.accdb"; 
	
	private Connection con;
	private Statement st;
	private static Connect connect;

    private Connect() {
    	try {  
            con = DriverManager.getConnection(PATH_URL + DATABASE);  
            st = con.createStatement();
        } catch(Exception e) {
        	e.printStackTrace();
        	System.out.println("Failed to connect the database, the system is terminated!");
        	System.exit(0);
        }  
    }
    
    public static synchronized Connect getConnection() {
		return connect = (connect == null) ? new Connect() : connect;
    }

    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
    	try {
            rs = st.executeQuery(query);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return rs;
    }

    public void executeUpdate(String query) {
    	try {
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public PreparedStatement prepareStatement(String query) {
    	PreparedStatement ps = null;
    	try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return ps;
    }
}
