package com.stateofflow.ergo.ergoview;

import com.stateofflow.ergo.CommandCount;

public class MenuCountSorter extends ErgoViewSorter<CommandCount> {
	@Override
	protected int compare(CommandCount first, CommandCount second) {
		return first.getMenuCount().compareTo(second.getMenuCount());
	}
}