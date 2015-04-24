package simpledb;
import java.sql.*;
import simpledb.remote.SimpleDriver;

import java.util.Calendar;
import java.util.Date;

public class StudentMajor {
	
    @SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Connection conn = null;
		try {
			long millisStart;
			long millisEnd;
			// Step 1: connect to database server
			Driver d = new SimpleDriver();
			conn = d.connect("jdbc:simpledb://localhost", null);

			// Step 2: execute the query
			Statement stmt = conn.createStatement();
			String qry = "select SName, DName "
			           + "from DEPT, STUDENT "
			           + "where MajorId = DId";
			//Date date2 = new Date(); 
			ResultSet rs = stmt.executeQuery(qry);
			
			// Step 3: loop through the result set
			System.out.println("Name\tMajor");
			while (rs.next()) {
				String sname = rs.getString("SName");
				String dname = rs.getString("DName");
				System.out.println(sname + "\t" + dname);
			}
			rs = stmt.executeQuery("deleteDomain DEPT");
			
			rs.close();
			
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			// Step 4: close the connection
			try {
				if (conn != null)
					conn.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
