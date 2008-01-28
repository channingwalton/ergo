package com.stateofflow.ergo.ergoview;

import com.stateofflow.ergo.CommandCount;

public class KeypressCountSorter extends ErgoViewSorter<CommandCount> {
	@Override
	protected int compare(CommandCount first, CommandCount second) {
		return first.getKeypressCount().compareTo(second.getKeypressCount());
	}
}