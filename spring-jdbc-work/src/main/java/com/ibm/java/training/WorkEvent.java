package com.ibm.java.training;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class WorkEvent extends ApplicationEvent {

	public WorkEvent(Object source) {
		super(source);
	
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "New Learner added...";
	}

}

