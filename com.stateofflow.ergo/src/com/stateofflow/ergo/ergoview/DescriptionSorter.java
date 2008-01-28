package com.stateofflow.ergo.ergoview;

import com.stateofflow.ergo.CommandCount;

public class DescriptionSorter extends ErgoViewSorter<CommandCount> {
	@Override
	protected int compare(CommandCount first, CommandCount second) {
		return nullSafe(first.getDescription()).compareTo(nullSafe(second.getDescription()));
	}
}
