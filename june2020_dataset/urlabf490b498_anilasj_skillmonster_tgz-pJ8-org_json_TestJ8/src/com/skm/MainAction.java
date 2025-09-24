package com.skm;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class MainAction extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String nextPage ="";
		ActionForward forward = super.execute(mapping, form, request, response);
				
		String action= (String) request.getAttribute("ac");
		if (action == null || action.trim().equals("")){
			action = request.getParameter("ac");
			if (action == null || action.trim().equals(""))
				action = "main";
		}
		
		try {
			nextPage = processAction(request, response, action, form);
			if (nextPage == null)
				nextPage = "error";
		}catch (Exception ex){
			//TODO error Handling
			ex.printStackTrace();
			nextPage = "error";
		}
		forward = mapping.findForward(nextPage);
		return forward;
		
	}
	
	private String processAction(HttpServletRequest request, HttpServletResponse response, String action, ActionForm form) throws Exception{
		
		if ("main".equals(action))
			return get(request, response);
		else if ("jobs".equals(action))
			return getJobs(request, response);
		else if ("add".equals(action))
			return addSkills(request, response);
		return null;
	}
	
	private String get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//get user skills
		return getJobs(request, response);

	}
	
	private String addSkills(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String skillName = request.getParameter("addSkill1Inp");
		if (!StringUtils.isBlank(skillName)){
			User user = (User) request.getSession().getAttribute("User");
			user.getSkills().add(new Skill(skillName));
		}
		return getJobs(request, response);
	}
	
	private String getJobs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//get all jobs from AngelList if not in session
		Jobs allJobs = (Jobs) request.getSession().getAttribute("AllJobs");
		if (allJobs == null){
			List<Job> jobs = getJobs();
			allJobs = new Jobs();
			allJobs.addJobs(jobs);
			request.getSession().setAttribute("AllJobs", allJobs);
			
		}
		
		User user = (User) request.getSession().getAttribute("User");
		
		//if you get skilled jobs - recalculate and set skilledJobs
		List<SkillJob> skillJobs = new ArrayList();
		if (!allJobs.isEmpty()){
			
			List<Skill> uSkills = new ArrayList<Skill>();
			//get User Selected Skills
			String[] skillSrchArry = request.getParameterValues("skillSel");
			if (skillSrchArry != null){
				for (int i=0; i < skillSrchArry.length; i++)
					uSkills.add(new Skill(skillSrchArry[i]));
				
				request.setAttribute("selSkills", uSkills);
			}else{
				uSkills = user.getSkills();
			}
			skillJobs = allJobs.getJobs(uSkills);
		}
		request.getSession().setAttribute("SkilledJobs", skillJobs);
		return "home";
		
			
	}
	private List<Job> getJobs(){
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
}
