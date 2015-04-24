package simpledb;

import java.sql.*;
import java.util.Random;

import simpledb.remote.SimpleDriver;

public class testDB {
	long millisStart = 0;
	long millisEnd = 0;
	int[] randNum = new int[10000];

	public static int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public long searchTime() {
		Connection conn = null;
		try {
			Driver d = new SimpleDriver();
			conn = d.connect("jdbc:simpledb://localhost", null);
			Statement stmt = conn.createStatement();
			for (int i = 0; i < randNum.length; i++) {
				randNum[i] = randInt(1, 9);
			}
			millisStart = System.currentTimeMillis();
			for (int i = 0; i < randNum.length; i++) {
				String qry = "select sname,dname " + "from student, dept "
						+ "where did = majorid " + "and sid = '"
						+ Integer.toString(randNum[i]) + "'";
				ResultSet rs = stmt.executeQuery(qry);
			}
			millisEnd = System.currentTimeMillis();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return millisEnd - millisStart;
	}
	public long average(long [] time){
		long sum=0;
		for (int i = 0; i < time.length; i++) {
			sum+=time[i];
		}
		return sum/(time.length);
	}
	public static void main(String[] args) {
		long [] times={0,0,0};
		testDB test= new testDB();
		for (int i = 0; i < 3; i++) {
			times[i]=test.searchTime();
			System.out.println(times[i]);
		}
		System.out.println("average time="+test.average(times));
	}
}
