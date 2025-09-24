package junit;

import model.User;
import model.User.UserPermissions;
import junit.framework.TestCase;

public class UserTest extends TestCase {

	public void testUser() {
		new User();
	}

	public void testUserStringStringString() {
		new User("name", "loginId", "password");
	}

	public void testGetName() {
		User user = new User("name", "loginId", "password");
		assertTrue(user.getName() == "name");
	}

	public void testSetName() {
		User user = new User("name", "loginId", "password");
		user.setName("newName");
		assertTrue(user.getName() == "newName");
	}

	public void testGetPassword() {
		User user = new User("name", "loginId", "password");
		assertTrue(user.getPassword() == "password");
	}

	public void testSetPassword() {
		User user = new User("name", "loginId", "password");
		user.setPassword("newPassword");
		assertTrue(user.getPassword() == "newPassword");
	}

	public void testGetGroup() {
		User user = new User("name", "loginId", "password");
		user.setPermissions(UserPermissions.ADMIN);
		assertTrue(user.getPermissions() == UserPermissions.ADMIN);
	}

	public void testSetGroup() {
		User user = new User("name", "loginId", "password");
		user.setPermissions(UserPermissions.NON_ADMIN);
		assertTrue(user.getPermissions() == UserPermissions.NON_ADMIN);
	}

}
