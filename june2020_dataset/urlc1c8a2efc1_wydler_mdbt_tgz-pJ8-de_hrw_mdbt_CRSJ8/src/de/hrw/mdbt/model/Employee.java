package de.hrw.mdbt.model;

import java.util.ArrayList;
import com.db4o.ObjectContainer;
import com.db4o.config.CommonConfigurationProvider;
import com.db4o.constraints.UniqueFieldValueConstraint;
import com.db4o.ext.Db4oException;

import de.hrw.mdbt.model.Branch;
import de.hrw.mdbt.model.Person;

public class Employee extends Person {
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private float salary;
	private String ssn;
	private Branch branch;
	private Employee supervisor;

	public Employee(int id, String lastname) {
		super(id, lastname);
	}

	public void addTask(Task t) {
		tasks.add(t);
	}

	public void removeTask(Task t) {
		tasks.remove(t);
	}

	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public Employee getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(Employee supervisor) {
		this.supervisor = supervisor;
	}
	
	public static void configure(CommonConfigurationProvider config) {
		config.common().objectClass(Customer.class).objectField("id").indexed(true);
		config.common().add(new UniqueFieldValueConstraint(Employee.class, "id"));
	}
	
	public boolean objectCanNew (ObjectContainer container) throws Db4oException {
		if(super.getEmail() != null)
			if(!super.getEmail().endsWith("@crc.com")) 
				throw new Db4oException("E-Mail must end with @crc.com!");
		return true;
	}

	public boolean objectCanUpdate (ObjectContainer container) throws Db4oException {
		if(super.getEmail() == null || super.getEmail().endsWith("@crc.com")) throw new Db4oException("E-Mail must end with @crc.com!");
		return true;
	}
}
