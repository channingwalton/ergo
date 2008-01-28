package com.stateofflow.ergo.ergoview;

import com.stateofflow.ergo.CommandCount;

public class NameSorter extends ErgoViewSorter<CommandCount> {
	@Override
	protected int compare(CommandCount first, CommandCount second) {
		return first.getName().compareTo(second.getName());
	}
}
