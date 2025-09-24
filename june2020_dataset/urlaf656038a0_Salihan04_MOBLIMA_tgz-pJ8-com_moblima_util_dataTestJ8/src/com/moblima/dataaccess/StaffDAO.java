package com.moblima.dataaccess;

import java.util.List;

import com.moblima.model.Staff;

public interface StaffDAO {
	public void createStaff(Staff staff);
	//public Staff getStaffBy(String username, String password);
	public List<Staff> getStaffList();
}
