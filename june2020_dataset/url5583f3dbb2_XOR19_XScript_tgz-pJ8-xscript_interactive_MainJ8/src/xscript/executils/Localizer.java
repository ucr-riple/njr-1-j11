package xscript.executils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localizer {

	private ResourceBundle lang;
	
	public Localizer(ResourceBundle lang) {
		this.lang = lang;
	}

	public String localize(String key, Object...args){
		if(lang==null){
			return key;
		}else{
			String msg;
			try{
				msg = lang.getString(key);
			}catch(MissingResourceException e){
				msg = "compiler message file broken: key=" + key + " arguments={0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}";
			}
			return MessageFormat.format(msg, args);
		}
	}
	
}
