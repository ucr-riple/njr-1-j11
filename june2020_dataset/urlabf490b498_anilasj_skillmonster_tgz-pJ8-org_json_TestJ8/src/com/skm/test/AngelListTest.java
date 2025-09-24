package com.skm.test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONArray;
import org.json.JSONObject;


import com.skm.Job;
import com.skm.Jobs;
import com.skm.Skill;
import com.skm.SkillJob;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class AngelListTest {
	public static List<Job> getJobs(){
		List<Job> jobs = new ArrayList();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		try{
			WebResource service = client.resource(getBaseURI());
			String result =  service.path("jobs").accept(MediaType.APPLICATION_JSON).get(String.class);
			
			if (result != null){
				JSONObject jsonRes = new JSONObject(result);
				
				JSONArray jobsArry = jsonRes.getJSONArray("jobs");
				if (jobsArry.length() > 0){
					for (int i=0; i < jobsArry.length(); i++){
						JSONObject jobsObj = jobsArry.getJSONObject(i);
						Job job = new Job();
						job.setTitle(jobsObj.getString("title"));
						job.setUrl(jobsObj.getString("angellist_url"));
						
						//Created date
						Calendar dtCal = DatatypeConverter.parseDateTime(jobsObj.getString("created_at"));
						job.setCreated(dtCal.getTime());
						
						//Startup name
						if (!jobsObj.isNull("startup")){
							JSONObject startup = jobsObj.getJSONObject("startup");
							if (!startup.isNull("name")) job.setCreatorName(startup.getString("name"));
							if (!startup.isNull("logo_url")) job.setCreatorLogo(startup.getString("logo_url"));
							if (!startup.isNull("thumb_url")) job.setCreatorThumbnail(startup.getString("thumb_url"));
							if (!startup.isNull("product_desc")) job.setDesc(startup.getString("product_desc"));
						}
						//add skills
						if (!jobsObj.isNull("tags")){
							JSONArray tagsArry = jobsObj.getJSONArray("tags");
							if (tagsArry.length() > 0){
								List<Skill> skills = new ArrayList();
								for (int j=0; j < tagsArry.length(); j++){
									JSONObject tag = tagsArry.getJSONObject(j);
									String tagType = tag.getString("tag_type");
									if (tagType.equalsIgnoreCase("SkillTag")){
										Skill skill = new Skill(tag.getString("display_name"));
										skills.add(skill);
									}
								}
								job.setSkills(skills);
							}
						}
						jobs.add(job);
					}
					
				}
			}
			
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return jobs;
	}
	
	private static URI getBaseURI(){
		return UriBuilder.fromPath("https://api.angel.co/1").build();
	}
	public static void main(String[] args){
		List<Job> jobs = getJobs();
		Jobs jobMgr = new Jobs();
		jobMgr.addJobs(jobs);
		
		//get Skill Match
		List<Skill> skills = new ArrayList();
		skills.add(new Skill("Web Application Security"));
		skills.add(new Skill("team player"));
		skills.add(new Skill("web development"));
		skills.add(new Skill("ruby"));
		skills.add(new Skill("Information Security"));
		
		List<SkillJob> sjobs = jobMgr.getJobs(skills);
		System.out.println("job List");
		for (SkillJob job: sjobs){
			System.out.println(job.toString());
		}
	}
}
