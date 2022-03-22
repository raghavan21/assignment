package com.ibm.java.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//@Repository("dao")
public class JdbcDao {
	
	Connection dbCon;
	String qry;
	PreparedStatement pstmt;
	ResultSet resultSet;
	ResultSet resultSet1;
	ResultSet resultSet2;

	void connectToDb() {

		try {
//			Load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
//			Establish the connection
			dbCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/schemaforjdbcconnection", "root", "");
			
//			System.out.println("Connected to Db...");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Issues while connecting to db : " + e.getMessage());
		} 
	}
	
	/*void getNameById(int id) throws SQLException {
		qry = "select name from learners where id = ?";
		
		//PreparedStatement pstmt = dbCon.prepareStatement(qry);
		
		pstmt.setInt(1, id);
		
		resultSet = pstmt.executeQuery();
		
		while(resultSet.next()) {
			System.out.println(resultSet.getString("name"));
		}
	}*/
	void getCustomerDetailsByAccno(Integer accno) throws SQLException {
		qry = "select name, acctype, balance from customers where accno = '" + accno + "'";
		pstmt = dbCon.prepareStatement(qry);
		resultSet = pstmt.executeQuery(qry);

		while (resultSet.next()) {
			System.out.println(resultSet.getString("name")+" with account type : " + resultSet.getString("acctype")+" and balance : "
								+ resultSet.getLong("balance"));
		}
	}
	
	void depositAmount(int accno, long amount) throws SQLException {
		
		qry = "update customers set balance = balance + '"+amount+"' where accno = '"+ accno +"'";
		pstmt = dbCon.prepareStatement(qry);
		
		if(pstmt.executeUpdate(qry)>0)
			System.out.println("amount is deposited....");
	}
	
	void withdrawAmount(int accno, long amount) throws SQLException {
		qry = "update customers set balance = balance - '"+ amount +"' where accno = '"+ accno +"'";
		pstmt = dbCon.prepareStatement(qry);
		
		if(pstmt.executeUpdate(qry)>0)
			System.out.println("amount is withdrawn....");	
	}
	
	void getAllCustomersDetails() throws SQLException {
		qry = "select * from customers";
		pstmt = dbCon.prepareStatement(qry);
		resultSet = pstmt.executeQuery(qry);
		//System.out.println("Account_No Name")
		while(resultSet.next())
		{
			System.out.println(resultSet.getInt("accno")+" "+resultSet.getString("name")+" " + resultSet.getString("acctype")+" "+ resultSet.getLong("balance"));	
			System.out.println();
		}
	}
	
	void addCustomer(int accno, String name, String acctype, long balance) throws SQLException{
		qry = "insert into customers value ('"+ accno +"','"+ name +"','"+ acctype +"','"+ balance +"')";
		pstmt = dbCon.prepareStatement(qry);
		if(pstmt.executeUpdate(qry)>0) {
			System.out.println("New customer details are added...");
		}
	}
	
	void transferAmount(int accno, int accnoOfReceiver, long amount) throws SQLException{
		String qry = "select * from customers where accno = '"+ accno +"'";
		pstmt = dbCon.prepareStatement(qry);
		resultSet = pstmt.executeQuery(qry);
		while (resultSet.next()) {
		}
		resultSet = pstmt.executeQuery(qry);
		qry = "update customers set balance = balance - '"+amount+"' where accno = '"+ accno +"'";
		pstmt = dbCon.prepareStatement(qry);
		if(pstmt.executeUpdate(qry)>0) {
			while(resultSet.next()) {
				System.out.print("Amount is debited from "+ resultSet.getString("name"));
			}
		}
		else {
			System.out.println("oops something went wrong");
		}
		qry = "update customers set balance = balance + '"+amount+"' where accno = '"+ accnoOfReceiver +"'";
		pstmt = dbCon.prepareStatement(qry);
		if(pstmt.executeUpdate(qry)>0) {
			qry = "select * from customers where accno = '"+ accnoOfReceiver +"'";
			pstmt = dbCon.prepareStatement(qry);
			resultSet = pstmt.executeQuery(qry);
			while(resultSet.next()) {
				System.out.println(" and credited to "+ resultSet.getString("name"));
			}
		}
		else {
			System.out.println("oops something went wrong");
		}
		
	}
 }
	
	
	


