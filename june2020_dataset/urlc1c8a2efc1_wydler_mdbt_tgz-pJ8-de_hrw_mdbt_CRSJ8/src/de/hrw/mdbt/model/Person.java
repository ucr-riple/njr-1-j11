package de.hrw.mdbt.model;

import java.util.ArrayList;
import java.util.Date;

import com.db4o.ObjectContainer;
import com.db4o.config.CommonConfigurationProvider;
import com.db4o.constraints.UniqueFieldValueConstraint;
import com.db4o.ext.Db4oException;


public class Person extends Customer {
	private String firstname;
	private String secondname;
	private String lastname;
	private String salutation;
	private String title;
	private Date dateOfBirth;
	private String email;
	private String sex;
	private ArrayList<Phone> numbers = new ArrayList<Phone>();
	private ArrayList<Address> addresses = new ArrayList<Address>();

	public Person() {
		super();
	}

	public Person(int id, String lastname) {
		super(id);

		this.lastname = lastname;
	}

	public Person(int id, String first, String second, String last, String sal, String title, Date dob, String sex) {
		super(id);

		this.firstname = first;
		this.secondname = second;
		this.lastname = last;
		this.salutation = sal;
		this.title = title;
		this.dateOfBirth = dob;
		this.sex = sex;
	}

	public void addAddress(Address adr) {
		addresses.add(adr);
	}
	public void removeAddress(Address adr) {
		addresses.remove(adr);
	}

	public void addPhone(Phone ph) {
		numbers.add(ph);
	}
	public void removePhone(Phone ph) {
		numbers.remove(ph);
	}

	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String name) {
		this.firstname = name;
	}

	public String getSecondname() {
		return secondname;
	}
	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}

	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dob) {
		this.dateOfBirth = dob;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public static void configure(CommonConfigurationProvider config) {
		config.common().objectClass(Customer.class).objectField("id").indexed(true);
		config.common().add(new UniqueFieldValueConstraint(Person.class, "id"));
	}

	private boolean checkConstraints() {
		if (this.lastname == null) throw new Db4oException("A person must have a last name!");
		Date today = new Date();
		if (this.dateOfBirth != null) if (this.dateOfBirth.after(today)) throw new Db4oException("<dob> must not be in the future!");
		return true;
	}

	public boolean objectCanNew (ObjectContainer container) throws Db4oException {
		return checkConstraints();
	}

	public boolean objectCanUpdate (ObjectContainer container) throws Db4oException {
		return checkConstraints();
	}
}
