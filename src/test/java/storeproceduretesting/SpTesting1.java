package storeproceduretesting;

import java.sql.SQLException;
import java.sql.Types;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SpTesting1 extends BaseTest {
	
	@Test(enabled = true)
	void test_GetCustomerShipping() throws SQLException {
		
		cStmt = con.prepareCall("{call GetCustomerShipping(?,?)}");
		cStmt.setInt(1, 112);
		
		cStmt.registerOutParameter(2,Types.VARCHAR);
		
		cStmt.executeQuery();
		String shippingTime = cStmt.getString(2);
		
		stmt = con.createStatement();
		rs = stmt.executeQuery("SELECT country,\r\n"
				+ "CASE \r\n"
				+ "	WHEN country ='USA' THEN '2-day Shipping'\r\n"
				+ "    WHEN country = 'Canada' THEN '3-day Shipping'\r\n"
				+ "    ELSE '5-day Shipping'\r\n"
				+ "END as ShippingTime\r\n"
				+ "FROM customers WHERE customerNumber=112");
		rs.next();
		String exp_shippingTime = rs.getString("ShippingTime");
		
		Assert.assertEquals(exp_shippingTime, shippingTime);
		
	}
	
	
	@Test(enabled = false)
	void test_get_order_by_cust() throws SQLException {
		cStmt = con.prepareCall("{call get_order_by_cust(?,?,?,?,?,?)}");
		cStmt.setInt(1, 141);
		
		cStmt.registerOutParameter(2, Types.INTEGER);
		cStmt.registerOutParameter(3, Types.INTEGER);
		cStmt.registerOutParameter(4, Types.INTEGER);
		cStmt.registerOutParameter(5, Types.INTEGER);
		cStmt.registerOutParameter(6, Types.INTEGER);
		
		cStmt.executeQuery();
		
		int shipped=cStmt.getInt(2);
		int cancelled=cStmt.getInt(3);
		int resolved=cStmt.getInt(4);
		int disputed=cStmt.getInt(5);
		int in_process=cStmt.getInt(6);
		
		stmt = con.createStatement();
		rs = stmt.executeQuery("select \r\n"
				+ "(SELECT count(*) as 'shipped' FROM orders WHERE customerNumber = 141 AND status = 'Shipped') as Shipped,\r\n"
				+ "(SELECT count(*) as 'cancelled' FROM orders WHERE customerNumber = 141 AND status = 'Cancelled') as Cancelled,\r\n"
				+ "(SELECT count(*) as 'resolved' FROM orders WHERE customerNumber = 141 AND status = 'Resolved') as Resolved,\r\n"
				+ "(SELECT count(*) as 'disputed' FROM orders WHERE customerNumber = 141 AND status = 'Disputed') as Disputed,\r\n"
				+ "(SELECT count(*) as 'in_process' FROM orders WHERE customerNumber = 141 AND status = 'In process') as In_process");
		
		rs.next();
		int exp_shipped = rs.getInt("shipped");
		int exp_cancelled = rs.getInt("cancelled");
		int exp_resolved = rs.getInt("resolved");
		int exp_disputed = rs.getInt("disputed");
		int exp_in_process = rs.getInt("in_process");
		
		if (shipped == exp_shipped && cancelled == exp_cancelled && resolved == exp_resolved 
			&& disputed == exp_disputed && in_process == exp_in_process)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);
	}
	
	
	
}
