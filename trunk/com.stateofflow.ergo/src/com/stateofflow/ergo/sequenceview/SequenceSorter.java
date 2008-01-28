package com.stateofflow.ergo.sequenceview;

import com.stateofflow.ergo.SequenceCount;
import com.stateofflow.ergo.ergoview.ErgoViewSorter;

public class SequenceSorter extends ErgoViewSorter<SequenceCount> {
	@Override
	protected int compare(SequenceCount first, SequenceCount second) {
		return first.getSequenceDescription().compareTo(second.getSequenceDescription());
	}
}
