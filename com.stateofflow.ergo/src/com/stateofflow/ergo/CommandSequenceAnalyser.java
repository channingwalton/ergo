package com.stateofflow.ergo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.commands.Command;
import org.eclipse.ui.commands.ICommandService;

public class CommandSequenceAnalyser implements CommandListener {

	public static final String STATE_FILE_NAME = "sequence.properties";

	private static final int HISTORY_SIZE = 4;

	private final List<Command> history = new ArrayList<Command>();
	private final Map<List<Command>, SequenceCount> counts = new HashMap<List<Command>, SequenceCount>();
	private final File stateFile;

	private final ICommandService commandService;

	public CommandSequenceAnalyser(ICommandService commandService, File stateFile) {
		this.commandService = commandService;
		this.stateFile = stateFile;
	}

	public void commandExecuted(Command command) {
		addToHistory(command);
		for (int i = 0; i < history.size(); i++) {
			List<Command> sequence = new ArrayList<Command>(history.subList(0, i + 1));
			if (!counts.containsKey(sequence)) {
				counts.put(sequence, new SequenceCount(sequence));
			}
			counts.get(sequence).increment();
		}
	}

	private void addToHistory(Command command) {
		history.add(0, command);
		if (history.size() > HISTORY_SIZE) {
			history.remove(history.size() - 1);
		}
	}

	public SequenceCount[] getSequenceCounts() {
		return counts.values().toArray(new SequenceCount[counts.size()]);
	}

	public void clear() {
		counts.clear();
		history.clear();
	}

	public void start() {
		try {
			if (!stateFile.exists()) {
				return;
			}
			Properties properties = new Properties();
			properties.load(new FileInputStream(stateFile));
			for (Object keyObject : properties.keySet()) {
				String key = (String) keyObject;
				List<Command> sequence = getCommands(key);
				counts.put(sequence, new SequenceCount(sequence, Integer.parseInt(properties.getProperty(key))));
			}
		} catch (Exception e) {
			Activator.log("Unable to initialise state", e);
		}
	}

	private List<Command> getCommands(String key) {
		String[] ids = key.split("\\|");
		List<Command> sequence = new ArrayList<Command>();
		for (String id : ids) {
			sequence.add(commandService.getCommand(id));
		}
		return sequence;
	}

	public void stop() {
		try {
			Properties properties = new Properties();
			for (List<Command> sequence : counts.keySet()) {
				properties.put(asString(sequence), counts.get(sequence).getCount().toString());
			}
			FileOutputStream out = new FileOutputStream(stateFile);
			properties.store(out, "");
			out.flush();
			out.close();
		} catch (IOException e) {
			Activator.log("Unable to save state", e);
		}
	}

	private String asString(List<Command> sequence) {
		StringBuffer buffer = new StringBuffer();
		for (Command command : sequence) {
			buffer.append(command.getId()).append("|");
		}
		return buffer.toString();
	}
}
