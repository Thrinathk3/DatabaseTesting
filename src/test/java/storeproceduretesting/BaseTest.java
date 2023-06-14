package storeproceduretesting;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

	Connection con=null;
	Statement stmt = null; 
	ResultSet rs, rs1,rs2;
	CallableStatement cStmt;

	@BeforeClass
	void setup() throws SQLException {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels","root","root");
	}

	@AfterClass
	void tearDown() throws SQLException {
		con.close();
	}
	
	
	public boolean compareResultsSets(ResultSet resultSet1, ResultSet resultSet2) throws SQLException {
		while(resultSet1.next()) {
			resultSet2.next();
			int count = resultSet1.getMetaData().getColumnCount();
			for(int i= 1 ; i<=count ; i++) {
				if(!StringUtils.equals(resultSet1.getString(i), resultSet2.getString(i))) {
					return false;
				}
			}
		}

		return true;
	}

}
