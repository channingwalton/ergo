package com.stateofflow.ergo.views;

import static com.stateofflow.ergo.Activator.getCommandRecorder;

import org.eclipse.core.commands.Command;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

import com.stateofflow.ergo.CommandListener;

public abstract class BasicContentProvider implements IStructuredContentProvider, CommandListener {

	private final TableViewer viewer;

	public BasicContentProvider(TableViewer viewer) {
		this.viewer = viewer;
		getCommandRecorder().addListener(this);
	}

	public void dispose() {
		getCommandRecorder().removeListener(this);
	}

	public void commandExecuted(Command commandId) {
		viewer.refresh();
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

}