package com.stateofflow.ergo.ergoview;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TableViewer;

import com.stateofflow.ergo.Activator;
import com.stateofflow.ergo.views.BasicView;

public class ErgoView extends BasicView {

	@Override
	protected void clearAll() {
		Activator.getCommandRecorder().clear();
	}

	@Override
	protected IContentProvider getViewContentProvider(TableViewer viewer) {
		return new ErgoViewContentProvider(viewer);
	}

	@Override
	protected IBaseLabelProvider getViewLabelProvider() {
		return new ViewLabelProvider();
	}

	@Override
	protected void initialiseColumns(TableViewer viewer) {
		for (Column column : Column.values()) {
			column.create(viewer);
		}

		viewer.setInput(getViewSite());
	}
}