package com.stateofflow.ergo.ergoview;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;

public abstract class ErgoViewSorter<T> extends ViewerSorter {

	private int direction = SWT.DOWN;

	@SuppressWarnings("unchecked")
	@Override
	public final int compare(Viewer viewer, Object e1, Object e2) {
		T first = (T) e1;
		T second = (T) e2;
		int comparison = compare(first, second);
		return direction == SWT.UP ? comparison : -comparison;
	}

	protected abstract int compare(T first, T second);

	public void reverse() {
		if (direction == SWT.UP) {
			direction = SWT.DOWN;
		} else {
			direction = SWT.UP;
		}
	}

	public void sortAscending() {
		direction = SWT.UP;
	}

	public void sortDescending() {
		direction = SWT.DOWN;
	}

	protected String nullSafe(String value) {
		return value == null ? "" : value;
	}

	public void setDirection(Table table) {
		table.setSortDirection(direction);
	}
}
