package com.vaadin.tutorial.addressbook.backend;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * A simple DTO for the address book example.
 *
 * Serializable and cloneable Java Object that are typically persisted
 * in the database and can also be easily converted to different formats like JSON.
 */
// Backend DTO class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class ToDoItem implements Serializable, Cloneable {

	private Long id;
	
    private String firstName = "";
    private String lastName = "";
    private String Task = "";
	private Date StartDate;
    private Date ExpectedEndDate;
    
   	public Long getId(){
    	
    	return id;
    }
    
    public void setId(Long id) {
        this.id = id;
}
    
    public String getTask() {
		return Task;
	}

	public void setTask(String task) {
		Task = task;
	}

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getExpectedEndDate() {
		return ExpectedEndDate;
	}

	public void setExpectedEndDate(Date expectedEndDate) {
		ExpectedEndDate = expectedEndDate;
	}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public ToDoItem clone() throws CloneNotSupportedException {
        try {
            return (ToDoItem) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "ToDoItem{" + "firstName=" + firstName
                + ", lastName=" + lastName + ", Task=" + Task + ", StartDate"
                + StartDate + ", ExpectedEndDate=" + ExpectedEndDate + '}';
    }

}
