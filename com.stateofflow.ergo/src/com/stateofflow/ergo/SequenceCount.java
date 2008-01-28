package com.stateofflow.ergo;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.common.NotDefinedException;

public class SequenceCount {

	private final List<Command> sequence;
	private int count;

	public SequenceCount(List<Command> sequence) {
		this.sequence = sequence;
	}

	public SequenceCount(List<Command> sequence, int count) {
		this(sequence);
		this.count = count;
	}

	public void increment() {
		count++;
	}

	public String getSequenceDescription() {
		StringBuffer buffer = new StringBuffer();
		Iterator<Command> iterator = sequence.iterator();
		while (iterator.hasNext()) {
			buffer.append(getNameSafely(iterator.next()));
			if (iterator.hasNext()) {
				buffer.append("|");
			}
		}
		return buffer.toString();
	}

	private String getNameSafely(Command command) {
		try {
			if (command.isDefined()) {
				return command.getName();
			}
		} catch (NotDefinedException e) {
			Activator.log("really shouldn't happen", e);
		}
		return command.getId();
	}

	public Integer getCount() {
		return count;
	}
}
