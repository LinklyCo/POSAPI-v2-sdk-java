package com.linkly.pos.demoPos.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DemoLogger extends Logger {

	private List<String> logs = new ArrayList<>();
	
	protected DemoLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
	}
	
	public DemoLogger() {
		this("SDK", null);
	}

	@Override
	public void log(Level level, String msg, Object[] params) {
		String message = MessageFormat.format(msg, params);
		logs.add(level.getName() + ": " + message);
		if(logs.size() > 100) {
			// clear logs, this means that trace is not enabled
			logs.clear();
		}
		super.log(level, msg, params);
    }
	
	public List<String> getLogs() {
		List<String> currentLogs = new ArrayList<>(this.logs);
		logs.clear();
		return currentLogs;
	}
}
