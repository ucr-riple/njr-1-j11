package com.skm;

import java.util.List;

public class SkillJob {
	private Job job;
	private int matchPercent = 0;
	
	
	public SkillJob(Job job) {
		super();
		this.job = job;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public int getMatchPercent() {
		return matchPercent;
	}
	public void setMatchPercent(int matchPercent) {
		this.matchPercent = matchPercent;
	}
	
	public void calculateSkillPercent(List<Skill> skills){
		
		List<Skill> jobSkills = job.getSkills();
		if (!jobSkills.isEmpty() && !skills.isEmpty()){
			int matchCount = 0;
			for (Skill jobSkill: jobSkills){
				for (Skill skill: skills){
					if (jobSkill.getName().equalsIgnoreCase(skill.getName()))
						matchCount ++;
				}
			}
			setMatchPercent((matchCount *100)/jobSkills.size());
		}else
			setMatchPercent(0);
		
	}
	
	public String toString(){
		StringBuilder strb = new StringBuilder();
		strb.append("Title:" + job.getTitle() + ", ");
		strb.append("Startup: " + job.getCreatorName() + ", ");
		if (!job.getSkills().isEmpty()){
			strb.append("Skills: ");
			for (Skill skill: job.getSkills())
				strb.append(skill.getName() + ", ");
		}
		strb.append(" %:" + getMatchPercent());
		return strb.toString();
	}
	
	
}
