package storeproceduretesting;
// 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
import java.sql.SQLException;
import java.sql.Types;

import org.testng.Assert;
import org.testng.annotations.Test;

public class StoreFunctionTest  extends BaseTest {
	
	@Test  (enabled=true)
	void test_CustomerLevel_with_StoredProcedure() throws SQLException  {
		
		
		cStmt=con.prepareCall("{call GetCustomerLevel(?,?)}");
		cStmt.setInt(1,131);
		cStmt.registerOutParameter(2, Types.VARCHAR);
		cStmt.executeQuery();
		
		String custlevel=cStmt.getString(2);
		
		rs= con.createStatement().executeQuery("select customerName,\r\n"
				+ "	case \r\n"
				+ "		when creditLimit > 50000 then 'PLATINUM'\r\n"
				+ "        when creditLimit >=10000 and creditLimit <=50000 then 'GOLD'\r\n"
				+ "        when creditLimit <10000 then 'SILVER'\r\n"
				+ "	end as customerLevel From customers where customerNumber =131");
		rs.next();
		String exp_custlevel=rs.getString(2);
		
		Assert.assertEquals(custlevel, exp_custlevel);
	}
	
	
	@Test  (enabled=false)
	void test_CustomerLevel_with_SQLStatement() throws SQLException  {
		
		rs1= con.createStatement().executeQuery("SELECT customerName,CustomerLevel(creditLimit) from customers");
		rs2= con.createStatement().executeQuery("select customerName,\r\n"
				+ "	case \r\n"
				+ "		when creditLimit > 50000 then 'PLATINUM'\r\n"
				+ "        when creditLimit >=10000 and creditLimit <=50000 then 'GOLD'\r\n"
				+ "        when creditLimit <10000 then 'SILVER'\r\n"
				+ "	end as customerLevel From customers");
		Assert.assertEquals(compareResultsSets(rs1, rs2), true);
	}
	
	@Test  (enabled=false)
	void test_storedFunctionExists() throws SQLException  {
		
		stmt = con.createStatement();
		rs= stmt.executeQuery("SHOW FUNCTION STATUS WHERE Name='CustomerLevel'");
		rs.next();
		Assert.assertEquals(rs.getString("Name"), "CustomerLevel");
	}
	
	

}
