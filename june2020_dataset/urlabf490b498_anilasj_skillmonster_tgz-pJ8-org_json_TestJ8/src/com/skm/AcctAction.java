package com.skm;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;


public class AcctAction extends Action{
	
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
				action = "noAction";
		}
		
		try {
			nextPage = processAction(request, response, action, form);
		}catch (Exception ex){
			//TODO error Handling
			ex.printStackTrace();
			nextPage = "error";
		}
		forward = mapping.findForward(nextPage);
		return forward;
		
	}
	
	private String processAction(HttpServletRequest request, HttpServletResponse response, String action, ActionForm form) throws Exception{
		
		if ("noAction".equals(action)){
			return  action;
		}else if ("signUp".equals(action))
			return signUp(request, response);
		else if ("logoff".equals(action))
			return logoff(request, response);
		else if ("main".equals(action))
			return action;
		return null;
	}
	
	private String signUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String id = request.getParameter("lid");
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		
		User user = new User();
		user.setLinkedId(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		
		List<Skill> skills = new ArrayList();
		//Get the skills
		Map<String,String[]> parms = request.getParameterMap();
		java.util.Iterator it = parms.keySet().iterator();
		while (it.hasNext()){
			String key= (String) it.next();
			if (key.startsWith("skills") && key.endsWith("[name]")){
				String[] strs = parms.get(key);
				for (int i=0; i < strs.length; i++){
					Skill skill = new Skill(strs[i]);
					skills.add(skill);
				}
				
					
			}
		}
		user.setSkills(skills);
		
		request.getSession().setAttribute("User", user);
		JSONObject wiObj = new JSONObject();
		response.setContentType("text/html");
		response.setHeader("Cache-control", "no-cache");
		response.getWriter().write(wiObj.toString());
		return null;
	}
	
	private String logoff(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		JSONObject wiObj = new JSONObject();
		response.setContentType("text/html");
		response.setHeader("Cache-control", "no-cache");
		response.getWriter().write(wiObj.toString());
		return null;
	}
}
