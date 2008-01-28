package com.stateofflow.ergo;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "com.stateofflow.ergo";

	private static Activator plugin;
	private CommandRecorder recorder;
	private CommandSequenceAnalyser commandSequenceAnalyser;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		recorder = new CommandRecorder(commandService, getStateLocation().append(CommandRecorder.RECORDER_STATE_FILE_NAME).toFile());
		recorder.start();
		commandSequenceAnalyser = new CommandSequenceAnalyser(commandService, getStateLocation().append(CommandSequenceAnalyser.STATE_FILE_NAME).toFile());
		commandSequenceAnalyser.start();
		recorder.addListener(commandSequenceAnalyser);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		commandSequenceAnalyser.stop();
		recorder.stop();
		plugin = null;
		super.stop(context);
	}

	public static CommandRecorder getCommandRecorder() {
		return getDefault().recorder;
	}

	public static CommandSequenceAnalyser getCommandSequenceAnalyser() {
		return getDefault().commandSequenceAnalyser;
	}

	public static Activator getDefault() {
		return plugin;
	}

	public static void log(String message, Exception e) {
		if (message == null || message.trim().length() == 0) {
			message = e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
		}
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, message, e));
	}
}
