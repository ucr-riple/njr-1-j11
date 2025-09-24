package pl.cc.core.cmd;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.cc.core.GroupData;
import pl.cc.real.RealAgent;
import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;

/**
 * Zdarzenie ProxyClient CallTag
 * 
 * @since 2012-08-17
 */
public class EventProxyClientCallTag extends Command {

	private String callID;
	private String group;
	private String topic;
	private String comment;
	private String extrafields;

	// Event [ProxyClient]
	private EventProxyClientCallTag(String orginalLine, String callID, String group,
			String topic, String description, String extrafields) {
		super(orginalLine);
		this.callID=callID;
		this.group = group;
		this.topic = topic;
		this.comment = description;
		if (extrafields==null) {
			this.extrafields="";
		} else {
			this.extrafields=extrafields;
		}
	}

	public static Command factoryInt(String s) {
		String[] names = { "event", "action", "callid", "group", "topic", "comment", "extrafields" };
		String[] v = getValues(s, names, 6);

		if (v == null)
			return null;
		if (!v[0].toLowerCase().equals("proxyclient"))
			return null;
		if (!v[1].toLowerCase().equals("calltag"))
			return null;
		return new EventProxyClientCallTag(s, v[2], v[3], v[4], v[5], v[6]);
	}

	public static void main(String[] args) {
		EventProxyClientCallTag ec = (EventProxyClientCallTag) Command
				.factory("EVENT [ProxyClient], Action [CallTag], Callid [1234.11], Group [Zmiana], Topic [Ubezpieczenia], Comment [sdf]","1");
		//EventProxyClientCallTag ec = (EventProxyClientCallTag) Command
		//		.factory("EVENT [ProxyClient], Action [CallTag], Callid [1234.11], Group [Zmiana], Topic [Ubezpieczenia], Comment [sdf], Extrafields [{NIP,677-310-51-48}]","1");
		System.out.println(ec.getGroup());
		System.out.println(ec.getTopic());
		System.out.println(ec.getComment());
		System.out.println(ec.getExtraFields());
	}

	@Override
	public int getType() {
		return CMD_EVENT_PROXYCLIENT_CALL_TAG;
	}

	public String getCallID(){
		return callID;
	}
	
	public String getGroup() {
		return group;
	}

	public String getTopic() {
		return topic;
	}

	public String getComment() {
		return comment;
	}

	public String getExtraFields() {
		return extrafields;
	}

}