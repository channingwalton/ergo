package com.stateofflow.ergo.sequenceview;

import com.stateofflow.ergo.SequenceCount;
import com.stateofflow.ergo.ergoview.ErgoViewSorter;

public class CountSorter extends ErgoViewSorter<SequenceCount> {
	@Override
	protected int compare(SequenceCount first, SequenceCount second) {
		return first.getCount().compareTo(second.getCount());
	}
}