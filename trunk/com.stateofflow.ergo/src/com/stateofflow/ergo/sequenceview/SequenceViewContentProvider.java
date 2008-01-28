package com.stateofflow.ergo.sequenceview;

import static com.stateofflow.ergo.Activator.getCommandSequenceAnalyser;

import org.eclipse.jface.viewers.TableViewer;

import com.stateofflow.ergo.views.BasicContentProvider;

class SequenceViewContentProvider extends BasicContentProvider {

	public SequenceViewContentProvider(TableViewer viewer) {
		super(viewer);
	}

	public Object[] getElements(Object parent) {
		return getCommandSequenceAnalyser().getSequenceCounts();
	}

}