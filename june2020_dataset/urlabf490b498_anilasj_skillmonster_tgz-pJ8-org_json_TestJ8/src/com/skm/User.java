package com.skm;

import java.util.List;

/**
 * Session User
 * @author anila
 *
 */
public class User {
	private String firstName;
	private String lastName;
	private String linkedId;
	
	private List<Skill> skills;

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

	public String getLinkedId() {
		return linkedId;
	}

	public void setLinkedId(String linkedId) {
		this.linkedId = linkedId;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	
	public boolean doesSkillExist(Skill skill, List<Skill> sskills){
		if (sskills == null || sskills.isEmpty())
			sskills = skills;
		for (Skill sskill: sskills){
			if (sskill.getName().equalsIgnoreCase(skill.getName()))
				return true;
		}
		return false;
	}
}
