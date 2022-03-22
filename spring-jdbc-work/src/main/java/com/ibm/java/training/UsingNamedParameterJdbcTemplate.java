package com.ibm.java.training;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository("namedParamDao")
public class UsingNamedParameterJdbcTemplate {

	DataSource dataSource;
	
	NamedParameterJdbcTemplate namedParamTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		namedParamTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
//	Get learner name by id and domain
	String getLearnerNameByIdAndDomain(int id, String domain) {
		
		String qry = "select name from learners where id = :lId and domain = :lDomain";
		
		SqlParameterSource paramSource = new MapSqlParameterSource("lId", id).addValue("lDomain", domain);
		
		return namedParamTemplate.queryForObject(qry, paramSource, String.class);
	}
	
}

