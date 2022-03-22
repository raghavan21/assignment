package com.ibm.java.training;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

//import com.ibm.java.training.BankAppUsingJdbcTemplate.CustomerMapper;

@Repository("dao")
public class bankjdbc{

	JdbcTemplate jdbcTemplate;

	DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
//	For getching the count of learners
	int getCountOfLearners() {
		String qry = "select count(*) from learners";
		
		return jdbcTemplate.queryForObject(qry, Integer.class);
	}
	
	
	
//	Get learner name by id
	/*String getLearnerNameById(int learnerId) {

		return jdbcTemplate.queryForObject("select name from learners where id = ?", String.class, new Object[] {learnerId} );
		
	}*/
	
//	Get learner name by id and domain
	/*String getLearnerNameByIdAndDomain(int id, String domain) {
		
		return jdbcTemplate.queryForObject(
				"select name from learners where id = ? and domain = ?",
				String.class, 
				new Object[] {id, domain});
	}*/
	
//	Get all learner details by id
	Customer getCustomerDetailsByAccno(int accno){
		return jdbcTemplate.queryForObject(
				"select * from customers where accno = ?", new CustomerMapper() , new Object[] {accno});
				
	}
	
//	Get all details of all learners
	List<Customer> getAllCustomersDetails(){
		return jdbcTemplate.query("select * from customers", new CustomerMapper());
	}
	
	
	
//	Add new Learner
	void addCustomer(Customer theCustomer) {
		
		jdbcTemplate.update("insert into customers values (?, ?, ?, ?)", new Object[] {theCustomer.accno(), theCustomer.name(), theCustomer.acctype(), theCustomer.balance()});
		
//		publish the event
		//WorkEvent event = new WorkEvent(this);
		//publisher.publishEvent(event);
	}
	
	void depositAmount(int accno, long amount){
		
		String qry = "update customers set balance = balance + '"+amount+"' where accno = '"+ accno +"'";
		
		jdbcTemplate.update(qry, new Object[] {accno,amount});
		
		System.out.println("Amount is deposited...");
	}
	
	void withdrawAmount(int accno, long amount) throws SQLException {
		
		String qry = "update customers set balance = balance - '"+ amount +"' where accno = '"+ accno +"'";
		jdbcTemplate.update(qry, new Object[] {accno,amount});
		System.out.println("amount is withdrawn....");	
	}
	
	void transferAmount(int accnoOfSender, int accnoOfReceiver, long amount) throws SQLException{

		String qry = "update customers set balance = balance - ? where accno = ?";
		jdbcTemplate.update(qry, new Object[] {amount, accnoOfSender});
		System.out.print("Amount is debited from "+ accnoOfSender);
		
		qry = "update customers set balance = balance + ? where accno = ?";
		jdbcTemplate.update(qry, new Object[] {amount, accnoOfReceiver});
		System.out.println(" and credited to "+ accnoOfReceiver);
		
	}
	
	
	class CustomerMapper implements RowMapper<Customer>{

		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Customer theCustomer = new Customer(
					rs.getInt("accno"),
					rs.getString("name"),
					rs.getString("acctype"),
					rs.getLong("balance")
					);
			
			return theCustomer;
		}
		
	}


	//@Override
	//public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
	//this.publisher = publisher;	
		
}
	


