package com.stateofflow.ergo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.commands.ICommandService;

public class CommandRecorder {

	public static final String RECORDER_STATE_FILE_NAME = "commands.properties";

	private final IExecutionListener executionListener = new IExecutionListener() {
		public void notHandled(String commandId, NotHandledException exception) {
		}

		public void postExecuteFailure(String commandId, ExecutionException exception) {
		}

		public void postExecuteSuccess(String commandId, Object returnValue) {
		}

		public void preExecute(String commandId, ExecutionEvent event) {
			boolean key = event.getTrigger() instanceof Event ? ((Event) event.getTrigger()).keyCode > 0 : false;
			commandExecuted(commandId, key);
		}
	};

	private final ICommandService commandService;
	private final HashMap<Command, CommandCount> commands = new HashMap<Command, CommandCount>();
	private final Set<CommandListener> listeners = new HashSet<CommandListener>();

	private final File stateFile;

	public CommandRecorder(ICommandService commandService, File stateFile) {
		this.commandService = commandService;
		this.stateFile = stateFile;
	}

	private void commandExecuted(String commandId, boolean keyPress) {
		CommandCount commandCount = getCommandCount(commandId);
		commandCount.increment(keyPress);
		for (CommandListener listener : listeners) {
			listener.commandExecuted(commandCount.getCommand());
		}
	}

	private CommandCount getCommandCount(String commandId) {
		Command command = commandService.getCommand(commandId);
		CommandCount count = commands.get(command);
		if (count == null) {
			count = new CommandCount(command);
			commands.put(command, count);
		}
		return count;
	}

	public void start() {
		loadState();
		commandService.addExecutionListener(executionListener);
	}

	public void stop() {
		commandService.removeExecutionListener(executionListener);
		saveState();
	}

	public void addListener(CommandListener listener) {
		listeners.add(listener);
	}

	public void removeListener(CommandListener listener) {
		listeners.remove(listener);
	}

	public CommandCount[] getCommandCounts() {
		return commands.values().toArray(new CommandCount[commands.size()]);
	}

	private void loadState() {
		try {
			if (!stateFile.exists()) {
				return;
			}
			Properties properties = new Properties();
			properties.load(new FileInputStream(stateFile));
			for (Object key : properties.keySet()) {
				String commandId = (String) key;
				String[] counts = properties.getProperty(commandId).split(",");
				getCommandCount(commandId).setKeypressCount(Integer.parseInt(counts[0]));
				getCommandCount(commandId).setMenuCount(Integer.parseInt(counts[1]));
			}
		} catch (Exception e) {
			Activator.log("Unable to initialise state", e);
		}
	}

	private void saveState() {
		try {
			Properties properties = new Properties();
			for (Command command : commands.keySet()) {
				properties.put(command.getId(), commands.get(command).getKeypressCount() + "," + commands.get(command).getMenuCount());
			}
			FileOutputStream out = new FileOutputStream(stateFile);
			properties.store(out, "");
			out.flush();
			out.close();
		} catch (IOException e) {
			Activator.log("Unable to save state", e);
		}
	}

	public void clear() {
		commands.clear();
	}
}
