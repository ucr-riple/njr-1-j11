package com.moblima.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.moblima.model.Staff;

public class StaffDAOImpl implements StaffDAO {
	
	private static StaffDAO staffDAO;
	private SerializeDB serializeDB;
	private List<Staff> staffList;
	
	public static StaffDAO getInstance() {
		if(staffDAO == null)
			staffDAO = new StaffDAOImpl();
		
		return staffDAO;
	}
	
	private StaffDAOImpl() {
		serializeDB = SerializeDB.getInstance();
		staffList = serializeDB.getStaffs();
	}

	public void createStaff(Staff staff) {
		int staffID = serializeDB.getStaffID();
		staff.setStaffID(staffID);
		serializeDB.setStaffID(staffID+1);
		
		staffList.add(staff);
		
		serializeDB.saveData();
	}

	public List<Staff> getStaffList() {
		return staffList;
	}

}
