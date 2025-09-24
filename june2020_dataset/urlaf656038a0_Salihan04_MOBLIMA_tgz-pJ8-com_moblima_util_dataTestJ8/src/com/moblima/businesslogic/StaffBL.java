package com.moblima.businesslogic;

import java.util.List;

import com.moblima.dataaccess.StaffDAO;
import com.moblima.dataaccess.StaffDAOImpl;
import com.moblima.model.Staff;


public class StaffBL {
	
	private StaffDAO staffDAO;
	
	public StaffBL() {
		staffDAO = StaffDAOImpl.getInstance();
	}
	
	public void createStaff(String username, String password) {
		Staff staff = new Staff(username, password);
		staffDAO.createStaff(staff);
	}
	
	public boolean isValid(String username, String password) {
		for(Staff staff : staffDAO.getStaffList()) {
			if(username.equals(staff.getUsername()) && password.equals(staff.getPassword()))
				return true;
		}
		return false;
	}
	
	public List<Staff> getStaffList() {
		return staffDAO.getStaffList();
	}
}
