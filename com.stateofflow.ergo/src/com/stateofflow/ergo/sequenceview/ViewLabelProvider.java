package com.stateofflow.ergo.sequenceview;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.stateofflow.ergo.SequenceCount;

class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {

	public String getColumnText(Object obj, int index) {
		SequenceCount sequenceCount = (SequenceCount) obj;
		Column column = Column.values()[index];
		return column.getLabel(sequenceCount);
	}

	public Image getColumnImage(Object obj, int index) {
		return getImage(obj);
	}

	@Override
	public Image getImage(Object obj) {
		return null;
	}
}