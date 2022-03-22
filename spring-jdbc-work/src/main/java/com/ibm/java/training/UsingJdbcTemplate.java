package com.ibm.java.training;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

//@Repository("dao")
public class UsingJdbcTemplate implements ApplicationEventPublisherAware {
	
	ApplicationEventPublisher publisher;

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
	String getLearnerNameById(int learnerId) {

		return jdbcTemplate.queryForObject("select name from learners where id = ?", String.class, new Object[] {learnerId} );
		
	}
	
//	Get learner name by id and domain
	String getLearnerNameByIdAndDomain(int id, String domain) {
		
		return jdbcTemplate.queryForObject(
				"select name from learners where id = ? and domain = ?",
				String.class, 
				new Object[] {id, domain});
	}
	
//	Get all learner details by id
	Learner getAllLearnerDetailsById(int id) {
		return jdbcTemplate.queryForObject(
				"select * from learners where id = ?", new LearnerMapper() , new Object[] {id});
				
	}
	
//	Get all details of all learners
	List<Learner> getAllLearnerDetails() {
		return jdbcTemplate.query("select * from learners", new LearnerMapper());
	}
	
	
	
//	Add new Learner
	void addNewLearner(Learner theLearner) {
		
		jdbcTemplate.update("insert into learners values (?, ?, ?)", new Object[] {theLearner.id(), theLearner.name(), theLearner.domain()});
		
//		publish the event
		WorkEvent event = new WorkEvent(this);
		publisher.publishEvent(event);
	}
	
	
	class LearnerMapper implements RowMapper<Learner>{

		@Override
		public Learner mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Learner theLearner = new Learner(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("domain")
					);
			
			return theLearner;
		}
		
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
	this.publisher = publisher;	
		
	}
	

}
