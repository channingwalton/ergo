package com.stateofflow.ergo.ergoview;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;

import com.stateofflow.ergo.CommandCount;
import com.stateofflow.ergo.Messages;

public enum Column {

	MENU_COUNT(Messages.ErgoView_MENUCOUNT, 100) {
		MenuCountSorter commandCountSorter = new MenuCountSorter();

		@Override
		ErgoViewSorter<CommandCount> getSorter() {
			return commandCountSorter;
		}

		@Override
		String getLabel(CommandCount commandCount) {
			return commandCount.getMenuCount().toString();
		}
	},
	KEYPRESS_COUNT(Messages.ErgoView_KEYPRESS_COUNT, 100) {
		KeypressCountSorter commandCountSorter = new KeypressCountSorter();

		@Override
		ErgoViewSorter<CommandCount> getSorter() {
			return commandCountSorter;
		}

		@Override
		String getLabel(CommandCount commandCount) {
			return commandCount.getKeypressCount().toString();
		}
	},
	NAME(Messages.ErgoView_NAME, 100) {
		NameSorter commandNameSorter = new NameSorter();

		@Override
		ErgoViewSorter<CommandCount> getSorter() {
			return commandNameSorter;
		}

		@Override
		String getLabel(CommandCount commandCount) {
			return commandCount.getName();
		}
	},
	CATEGORY(Messages.ErgoView_CATEGORY, 100) {
		CategorySorter categorySorter = new CategorySorter();

		@Override
		ErgoViewSorter<CommandCount> getSorter() {
			return categorySorter;
		}

		@Override
		String getLabel(CommandCount commandCount) {
			return commandCount.getCategoryName();
		}
	},
	DESCRIPTION(Messages.ErgoView_DESCRIPTION, 500) {
		DescriptionSorter commandDescriptionSorter = new DescriptionSorter();

		@Override
		ErgoViewSorter<CommandCount> getSorter() {
			return commandDescriptionSorter;
		}

		@Override
		String getLabel(CommandCount commandCount) {
			return commandCount.getDescription();
		}
	},
	BINDING(Messages.ErgoView_BINDING, 100) {
		BindingSorter commandBindingSorter = new BindingSorter();

		@Override
		ErgoViewSorter<CommandCount> getSorter() {
			return commandBindingSorter;
		}

		@Override
		String getLabel(CommandCount commandCount) {
			return commandCount.getKeyBinding();
		}
	};

	private final String name;
	private final int width;
	private TableColumn tableColumn;

	Column(String name, int width) {
		this.name = name;
		this.width = width;
	}

	abstract ErgoViewSorter<CommandCount> getSorter();

	abstract String getLabel(CommandCount commandCount);

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
		ErgoViewSorter<CommandCount> sorter = getSorter();
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
