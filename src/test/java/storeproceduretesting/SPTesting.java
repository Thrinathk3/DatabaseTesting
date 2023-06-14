package storeproceduretesting;

import java.sql.SQLException;
import java.sql.Statement;

import org.testng.Assert;
import org.testng.annotations.*;

public class SPTesting extends BaseTest{

	
	@Test(enabled = true)
	void test_test_SelectAllCustomersByCityAndByPin() throws SQLException {
		cStmt = con.prepareCall("{CALL SelectAllCustomersByCityAndByPin(?,?)}");
		cStmt.setString(1, "Singapore");
		cStmt.setString(2, "079903");
		rs1 = cStmt.executeQuery();

		Statement stmt = con.createStatement();
		rs2 = stmt.executeQuery("Select * from Customers where city = 'Singapore' and postalCode = '079903'");

		Assert.assertEquals(compareResultsSets(rs1,rs2),true);
		
	}

	@Test(enabled = false)
	void test_test_SelectAllCustomersByCity() throws SQLException {
		cStmt = con.prepareCall("{CALL SelectAllCustomersByCity(?)}");
		cStmt.setString(1, "Singapore");
		rs1 = cStmt.executeQuery();

		Statement stmt = con.createStatement();
		rs2 = stmt.executeQuery("Select * from Customers where city = 'Singapore'");

		Assert.assertEquals(compareResultsSets(rs1,rs2),true);
		
	}

	@Test(enabled = false)
	void test_SelectAllCustomers() throws SQLException {
		cStmt = con.prepareCall("{CALL SelectAllCustomers()}");
		rs1 = cStmt.executeQuery();

		Statement stmt = con.createStatement();
		rs2 = stmt.executeQuery("Select * from Customers");

		Assert.assertEquals(compareResultsSets(rs1,rs2),true);

	}

	
	@Test(enabled = false)
	void test_storedProcedureExists() throws SQLException {
		stmt = con.createStatement();
		rs = stmt.executeQuery("SHOW PROCEDURE STATUS WHERE Name='SelectAllCustomers'");
		rs.next();
		Assert.assertEquals(rs.getString("Name"),"selectAllCustomers");
	}
	
}
