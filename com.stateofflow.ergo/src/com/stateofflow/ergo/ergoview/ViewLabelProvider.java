package com.stateofflow.ergo.ergoview;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.stateofflow.ergo.CommandCount;

class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {

	public String getColumnText(Object obj, int index) {
		CommandCount commandCount = (CommandCount) obj;
		Column column = Column.values()[index];
		return column.getLabel(commandCount);
	}

	public Image getColumnImage(Object obj, int index) {
		return getImage(obj);
	}

	public Image getImage(Object obj) {
		return null;
	}
}