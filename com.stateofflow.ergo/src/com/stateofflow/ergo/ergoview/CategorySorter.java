package com.stateofflow.ergo.ergoview;

import com.stateofflow.ergo.CommandCount;

public class CategorySorter extends ErgoViewSorter<CommandCount> {
	@Override
	protected int compare(CommandCount first, CommandCount second) {
		return first.getCategoryName().compareTo(second.getCategoryName());
	}
}
