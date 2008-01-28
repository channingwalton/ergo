package com.stateofflow.ergo.ergoview;

import com.stateofflow.ergo.CommandCount;

public class BindingSorter extends ErgoViewSorter<CommandCount> {
	@Override
	protected int compare(CommandCount first, CommandCount second) {
		return first.getKeyBinding().compareTo(second.getKeyBinding());
	}
}
