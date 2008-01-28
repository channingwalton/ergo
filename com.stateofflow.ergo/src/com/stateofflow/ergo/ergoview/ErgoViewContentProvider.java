package com.stateofflow.ergo.ergoview;

import static com.stateofflow.ergo.Activator.getCommandRecorder;

import org.eclipse.jface.viewers.TableViewer;

import com.stateofflow.ergo.views.BasicContentProvider;

class ErgoViewContentProvider extends BasicContentProvider {

	public ErgoViewContentProvider(TableViewer viewer) {
		super(viewer);
	}

	public Object[] getElements(Object parent) {
		return getCommandRecorder().getCommandCounts();
	}

}