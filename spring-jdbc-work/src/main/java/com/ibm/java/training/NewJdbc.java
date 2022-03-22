//Hariharan
package com.ibm.java.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

@Repository("doa")
public class NewJdbc {
	
    static String qry;
    static PreparedStatement pstmt;
    static ResultSet relset;
	Connection dbCon;
	Scanner sc = new Scanner (System.in);

	void connectToDb() {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			
			dbCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment", "root", "");
			
			System.out.println("Connected to Db...");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Issues while connecting to db : " + e.getMessage());
		}
	
	}
	void AddAccount(int ID,String NAME,Float BALANCE) throws SQLException
	{
		qry = "insert into paymentdb values(?,?,?)";
        pstmt = dbCon.prepareStatement(qry);
        pstmt.setInt(1,ID);
        pstmt.setString(2, NAME);
        pstmt.setDouble(3, BALANCE);
        if (pstmt.executeUpdate() > 0) {
			System.out.println("New Customer added...");
		}
	}
     void Checkbal(int ID) throws SQLException
    	{
    		qry = "select BALANCE from paymentdb where ID = ?";
            pstmt = dbCon.prepareStatement(qry);
            pstmt.setInt(1,ID);
            relset= pstmt.executeQuery();
            while (relset.next()) {
    			System.out.println(relset.getFloat("BALANCE"));
            }
    		}
     void Deposit(int ID) throws SQLException
       	{
    	 		System.out.println("enter the amount to be deposited");
    	 	
    	 		float depo = sc.nextFloat();
    	 		qry = "select BALANCE from paymentdb where ID = ?";
                pstmt = dbCon.prepareStatement(qry);
                pstmt.setInt(1,ID);
                relset= pstmt.executeQuery();
                double newbal = 0;
                while (relset.next()) {
        			newbal= relset.getDouble("BALANCE");
                }
                newbal=newbal+depo;
                String qry2 ="update paymentdb set BALANCE = ? where ID = ?";
                dbCon.prepareStatement(qry2);
                PreparedStatement pstmt2 =dbCon.prepareStatement(qry2);
                pstmt2.setDouble(1, newbal);
                pstmt2.setInt(2, ID);
                pstmt2.executeUpdate();
                if (pstmt2.executeUpdate() > 0) {
        			System.out.println("Deposited");
        		}
                }
     void Withdraw(int ID) throws SQLException
    	{
 	 		 System.out.println("enter the amount to be withdrawn");
 	 		 float withd = sc.nextFloat();
 	 		 qry = "select BALANCE from paymentdb where ID = ?";
             pstmt = dbCon.prepareStatement(qry);
             pstmt.setInt(1,ID);
             relset= pstmt.executeQuery();
             double newbal = 0;
             while (relset.next()) {
     			newbal= relset.getDouble("BALANCE");
             }
             
             newbal=newbal-withd;
             if(newbal<1000)
             {
            	 System.out.println("cannot be withdrawn");
            	 
             }
             else {
      
             String qry2 ="update paymentdb set BALANCE = ? where ID = ?";
             dbCon.prepareStatement(qry2);
             PreparedStatement pstmt2 =dbCon.prepareStatement(qry2);
             pstmt2.setDouble(1, newbal);
             pstmt2.setInt(2, ID);
             pstmt2.executeUpdate();
             if (pstmt2.executeUpdate() > 0) {
     			System.out.println("Withdrawn");
     		}
             }
    	}
             
             void Transfer(int ID,int ID1) throws SQLException
         	{
      	 		  System.out.println("enter the amount to be transfered");
      	 		  double tamount = sc.nextFloat();
      	 		  qry = "select BALANCE from paymentdb where ID = ?";
                  pstmt = dbCon.prepareStatement(qry);
                  pstmt.setInt(1,ID);
                  relset= pstmt.executeQuery();
                  double newbal=0 ;
                  while (relset.next()) {
          			newbal= relset.getDouble("BALANCE");
          			
                  }	
                  		
                     if(newbal-tamount>1000)
                     {
                    	 newbal=newbal-tamount;
                      String qry2 ="update paymentdb set BALANCE =? where ID = ?";
                      dbCon.prepareStatement(qry2);
                      PreparedStatement pstmt2 =dbCon.prepareStatement(qry2);
                      pstmt2.setDouble(1,newbal);
                      pstmt2.setInt(2, ID);
                      pstmt2.executeUpdate();
                                           
                      String qry4 = "select BALANCE from paymentdb where ID = ?";
                      PreparedStatement pstmt4 =dbCon.prepareStatement(qry4);
                      pstmt4 = dbCon.prepareStatement(qry4);
                      pstmt4.setInt(1,ID1);
                      ResultSet relset2= pstmt4.executeQuery();
                      double newbal1 = 0;
                      while (relset2.next()) {
              			newbal1= relset2.getDouble("BALANCE");
                      }               
       
                      newbal1 =newbal1 + tamount;
                      String qry3 = "update paymentdb set BALANCE = ? where ID = ?";
                      dbCon.prepareStatement(qry3);
                      PreparedStatement pstmt3 =dbCon.prepareStatement(qry3);
                      pstmt3.setDouble(1,newbal1);
                      pstmt3.setInt(2,ID1);
                      pstmt3.executeUpdate();
                      if (pstmt3.executeUpdate() > 0|| pstmt2.executeUpdate() > 0 ) {
               			System.out.println("Transfered succesfully");
 
        				String op = "transfer";
        				transaction(ID, op, tamount,ID1);
               			
               		} 
                      else
                      {
                    	  System.out.println("transfer not done");
                      }
                  }
            
         	}
        	 void transactionview(int id) throws SQLException {

        			qry = "select * from trasaction where id = ?";
        			pstmt = dbCon.prepareStatement(qry);

        			pstmt.setInt(1, id);

        			relset = pstmt.executeQuery();

        			while (relset.next()) {
        				System.out.print("id " + relset.getInt("id"));
        				System.out.print(", Operation: " + relset.getString("op"));
        				System.out.println(" amount: " + relset.getFloat("amount"));
        				System.out.println("rid:" + relset.getInt("rid"));

        			}
        		}
             
             void transaction( int id, String operation, double amount,int id1) throws SQLException {

         		qry = "insert into trasaction values(?, ?, ?, ?)";

         		pstmt = dbCon.prepareStatement(qry);

         		pstmt.setInt(1, id);

         		pstmt.setString(2, operation);

         		pstmt.setDouble(3,amount);
         		
         		pstmt.setInt(4, id1);

         		if (pstmt.executeUpdate() > 0) {
         			System.out.println("transaction list  added...");
         		}

             }
             
             
             }