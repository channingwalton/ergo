package com.stateofflow.ergo;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;

public class CommandCount {

	private final Command command;
	private int menuCount;
	private int keypressCount;

	public CommandCount(Command command) {
		this.command = command;
	}

	public void increment(boolean keyPress) {
		if (keyPress) {
			keypressCount++;
		} else {
			menuCount++;
		}
	}

	public Integer getKeypressCount() {
		return keypressCount;
	}

	public Integer getMenuCount() {
		return menuCount;
	}

	public Command getCommand() {
		return command;
	}

	public String getName() {
		if (command.isDefined()) {
			try {
				return command.getName();
			} catch (NotDefinedException e) {
				Activator.log("", e); //$NON-NLS-1$
			}
		}
		return Messages.CommandCount_UNKNOWN;
	}

	public String getCategoryName() {
		if (command.isDefined()) {
			try {
				return command.getCategory().getName();
			} catch (NotDefinedException e) {
				Activator.log("", e); //$NON-NLS-1$
			}
		}
		return Messages.CommandCount_UNKNOWN;
	}

	public String getKeyBinding() {
		IBindingService bindingService = (IBindingService) PlatformUI.getWorkbench().getService(IBindingService.class);
		String keyStroke = bindingService.getBestActiveBindingFormattedFor(command.getId());
		return keyStroke == null ? Messages.CommandCount_NONE : keyStroke;
	}

	public String getDescription() {
		if (command.isDefined()) {
			try {
				return command.getDescription();
			} catch (NotDefinedException e) {
				Activator.log("", e); //$NON-NLS-1$
			}
		}
		return ""; //$NON-NLS-1$
	}

	public void setKeypressCount(int keypressCount) {
		this.keypressCount = keypressCount;
	}

	public void setMenuCount(int menuCount) {
		this.menuCount = menuCount;
	}

}
