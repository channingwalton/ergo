package com.stateofflow.ergo.sequenceview;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;

import com.stateofflow.ergo.Messages;
import com.stateofflow.ergo.SequenceCount;
import com.stateofflow.ergo.ergoview.ErgoViewSorter;

public enum Column {

	COUNT(Messages.ErgoView_COUNT, 100) {
		CountSorter countSorter = new CountSorter();

		@Override
		ErgoViewSorter<SequenceCount> getSorter() {
			return countSorter;
		}

		@Override
		String getLabel(SequenceCount sequenceCount) {
			return sequenceCount.getCount().toString();
		}
	},
	SEQUENCE(Messages.ErgoView_SEQUENCE, 500) {
		SequenceSorter sequenceSorter = new SequenceSorter();

		@Override
		ErgoViewSorter<SequenceCount> getSorter() {
			return sequenceSorter;
		}

		@Override
		String getLabel(SequenceCount sequenceCount) {
			return sequenceCount.getSequenceDescription();
		}
	};

	private final String name;
	private final int width;
	private TableColumn tableColumn;

	Column(String name, int width) {
		this.name = name;
		this.width = width;
	}

	abstract ErgoViewSorter<SequenceCount> getSorter();

	abstract String getLabel(SequenceCount sequenceCount);

	void create(final TableViewer tableViewer) {
		tableColumn = new TableColumn(tableViewer.getTable(), SWT.LEFT);
		tableColumn.setText(name);
		tableColumn.setWidth(width);
		tableColumn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setSorter(tableViewer);
			}
		});
	}

	public void setSorter(final TableViewer tableViewer) {
		ErgoViewSorter<SequenceCount> sorter = getSorter();
		if (sorter == tableViewer.getSorter()) {
			sorter.reverse();
		} else {
			sorter.sortAscending();
			tableViewer.setSorter(sorter);
		}
		tableViewer.getTable().setSortColumn(tableColumn);
		sorter.setDirection(tableViewer.getTable());
		tableViewer.refresh();
	}

}
