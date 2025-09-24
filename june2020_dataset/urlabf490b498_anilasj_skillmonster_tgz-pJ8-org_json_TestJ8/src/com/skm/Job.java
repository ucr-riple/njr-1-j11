package com.skm;

import java.util.Date;
import java.util.List;

public class Job {
	private String url;
	private String title;
	private String desc;
	private Date created;
	private String creatorName;
	private String creatorLogo;
	private String creatorThumbnail;
	private List<Skill> skills;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getCreatorLogo() {
		return creatorLogo;
	}
	public void setCreatorLogo(String creatorLogo) {
		this.creatorLogo = creatorLogo;
	}
	public String getCreatorThumbnail() {
		return creatorThumbnail;
	}
	public void setCreatorThumbnail(String creatorThumbnail) {
		this.creatorThumbnail = creatorThumbnail;
	}
	public List<Skill> getSkills() {
		return skills;
	}
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	
	public String toString(){
		StringBuilder strb = new StringBuilder();
		strb.append("Title:" + getTitle() + ", ");
		strb.append("Desc: " + getDesc() + ", ");
		strb.append("Url: " + getUrl() + ", ");
		strb.append("Startup: " + getCreatorName() + ", ");
		strb.append("CreatedAt: " + getCreated() + ", ");
		strb.append("StartupLogo: " + getCreatorLogo() + ", ");
		strb.append("Thumbnail: " + getCreatorThumbnail() + "\n ");
		if (!skills.isEmpty()){
			strb.append("Skills: ");
			for (Skill skill: skills)
				strb.append(skill.getName() + ", ");
		}
		return strb.toString();
	}
}
