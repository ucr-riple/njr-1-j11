package com.skm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Jobs {
	public List<Job> jobs = new ArrayList();
	
	public void addJobs(List<Job> jobs){
		this.jobs = jobs;
	}
	
	public boolean isEmpty(){
		return jobs.isEmpty();
	}
	
	public List<SkillJob> getJobs(List<Skill> skills){
		List<SkillJob> sjobs = new ArrayList();
		if (!isEmpty()){
			for (Job job: jobs){
				SkillJob sjob = new SkillJob(job);
				sjob.calculateSkillPercent(skills);
				sjobs.add(sjob);
			}
			Collections.sort(sjobs, new MatchComparator());
		}
		return sjobs;
	}
	
	private class MatchComparator implements Comparator{

		@Override
		public int compare(Object arg0, Object arg1) {
			if (((SkillJob) arg0).getMatchPercent() == ((SkillJob)arg1).getMatchPercent())
				return 0;
			if (((SkillJob) arg0).getMatchPercent() < ((SkillJob)arg1).getMatchPercent())
				return 1;
			return -1;
		}
		
	}
	
}
