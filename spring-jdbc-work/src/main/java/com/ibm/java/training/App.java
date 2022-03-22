package com.ibm.java.training;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App
{
	public static void main( String[] args ) throws SQLException
    {
//      Load the context
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
    	
//    	UsingJdbcTemplate dao = context.getBean("dao", UsingJdbcTemplate.class);
    	bankjdbc dao = context.getBean("dao", bankjdbc.class);
    	
    	Scanner sc = new Scanner(System.in);
    	
//    	System.out.println("Customers already registered : " + dao.getCountOfLearners());
    	
    	/*System.out.println("Enter id, name and domain of new learner");
    	
    	int id = sc.nextInt();
    	sc.nextLine();
    	
    	String name = sc.nextLine();
    	
    	String domain = sc.nextLine();
    	
    	dao.addNewLearner(new Learner(id, name, domain));*/
    	
//    	Get a reference to the bean
    	
    	int accno, choice;
    	String name;
    	String acctype;
    	long amount;
    	
    	
    	do {
    		
    		System.out.println("1.Search a Customer\n2.Deposit amount\n3.Withdraw amount\n4.Display details of all customers\n"
    							+ "5.Add new customer\n6.Amount transfer\n7.exit");
    		
    		System.out.println("Enter your choice : ");
    		
	    	choice = sc.nextInt();
	//    	Call the method
	    	//dao.connectToDb();
	    	switch(choice)
	    	{
	    	case 1:
		    	System.out.println("Enter account number to search");
		    	accno = sc.nextInt();
		    	Customer customer = dao.getCustomerDetailsByAccno(accno);
		    	System.out.println(customer);
		    	break;
		    
	    	case 2:
		    	System.out.println("Enter account number and amount to deposit money");
		    	accno = sc.nextInt();
		    	amount = sc.nextLong();
		    	dao.depositAmount(accno,amount);
		    	break;
		    	
	    	case 3:
		    	System.out.println("Enter account number and amount to withdraw");
		    	accno = sc.nextInt();
		    	amount = sc.nextLong();
		    	dao.withdrawAmount(accno,amount);
		    	break;
		    	
	    	case 4:
		    	System.out.println("Details of all the customers present...\n");
		    	List<Customer> customers = dao.getAllCustomersDetails();
		    	for(Customer theCustomer : customers)
		    		System.out.println(theCustomer);
		    	break;
		    	
	    	case 5:
	    		System.out.println("Enter account number, name, account type, balance ");
	    		accno = sc.nextInt();
	    		name = sc.next();
	    		acctype = sc.next();
	    		amount = sc.nextLong();
	    		dao.addCustomer(new Customer(accno, name, acctype, amount));
	    		break;
	    		
	    	case 6:
	    		System.out.println("Enter account no of sender and receiver and amount to transfer...");
	    		int accnoOfSender = sc.nextInt();
	    		int accnoOfReceiver = sc.nextInt();
	    		amount = sc.nextLong();
	    		dao.transferAmount(accnoOfSender, accnoOfReceiver, amount);
	    		break;
	    	
	    	case 7:
	    		
	    		break;
	    		
	    	default:
	    		System.out.println("Invalid choice...");
	    		break;
	    	}
    	}while(choice!=7);
    	
    	context.close();
    	sc.close();
    }
}

