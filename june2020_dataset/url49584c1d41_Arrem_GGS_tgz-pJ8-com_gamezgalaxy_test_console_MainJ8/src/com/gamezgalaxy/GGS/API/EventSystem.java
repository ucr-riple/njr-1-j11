/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.API;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.gamezgalaxy.GGS.server.Server;

public class EventSystem {
	
	private Server server;
	
	public EventSystem(Server server) {
		this.server = server;
	}
	
	public void callEvent(Event event) {
		EventList events = event.getEvents();
		RegisteredListener[] listeners = events.getRegisteredListeners();
		for (RegisteredListener listen : listeners) {
			try {
				listen.execute(event);
			}
			catch (Exception e) {
				server.Log("==!EVENT ERROR!==");
				e.printStackTrace();
			}
		}
	}
	public void registerEvents(Listener l) {
		for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : addMuffins(l).entrySet()) {
            try {
				getEventListeners(getRegistrationClass(entry.getKey())).registerAll(entry.getValue());
			} catch (IllegalAccessException e) {
				server.Log("==!EVENT ERROR!==");
				e.printStackTrace();
			}
        }
	}
	
	private EventList getEventListeners(Class<? extends Event> type) {
        try {
            Method method = getRegistrationClass(type).getDeclaredMethod("getEventList");
            method.setAccessible(true);
            return (EventList) method.invoke(null);
        } catch (Exception e) {
        	server.Log("==!EVENT ERROR!==");
            e.printStackTrace();
            return null;
        }
    }
	
	private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) throws IllegalAccessException {
        try {
            clazz.getDeclaredMethod("getEventList");
            return clazz;
        } catch (NoSuchMethodException e) {
            if (clazz.getSuperclass() != null
                    && !clazz.getSuperclass().equals(Event.class)
                    && Event.class.isAssignableFrom(clazz.getSuperclass())) {
                return getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
            } else {
                throw new IllegalAccessException("Unable to find event list for event " + clazz.getName());
            }
        }
    }
	
	public Map<Class<? extends Event>, Set<RegisteredListener>> addMuffins(Listener listen) {
		Map<Class<? extends Event>, Set<RegisteredListener>> ret = new HashMap<Class<? extends Event>, Set<RegisteredListener>>();
        Method[] methods;
        try {
            methods = listen.getClass().getDeclaredMethods();
        } catch (NoClassDefFoundError e) {
            return null;
        }
        for (final Method m : methods) {
        	if (m.getAnnotation(EventHandler.class) == null)
        		continue;
        	if (!Event.class.isAssignableFrom(m.getParameterTypes()[0]) || m.getParameterTypes().length > 1)
        		continue;
        	final Class<? extends Event> eventClass = m.getParameterTypes()[0].asSubclass(Event.class);
        	m.setAccessible(true);
        	Set<RegisteredListener> events = ret.get(eventClass);
        	if (events == null) {
        		events = new HashSet<RegisteredListener>();
        		ret.put(eventClass, events);
        	}
        	Executor exe = new Executor() {
        		public void execute(Listener listen, Event e) {
        			try {
        				if (!eventClass.isAssignableFrom(e.getClass()))
        					return;
        				m.invoke(listen, e);
        			} catch (Exception e1) {
        				e1.printStackTrace();
        			}
        		}
        	};
        	events.add(new RegisteredListener(listen, exe, m.getAnnotation(EventHandler.class).priority()));
        }
        return ret;
	}
}
