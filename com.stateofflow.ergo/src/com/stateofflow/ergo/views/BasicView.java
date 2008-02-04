package com.stateofflow.ergo.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public abstract class BasicView extends ViewPart {

	private Action clearAction;

	private TableViewer viewer;

	@Override
	public final void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.getTable().setLinesVisible(true);
		viewer.setContentProvider(getViewContentProvider(viewer));
		viewer.setLabelProvider(getViewLabelProvider());
		viewer.getTable().setHeaderVisible(true);

		initialiseColumns(viewer);

		clearAction = new Action() {
			@Override
			public void run() {
				clearAll();
				viewer.refresh();
			}
		};
		clearAction.setText("Clear counts");
		clearAction.setToolTipText("Remove all the counts from the view");
		clearAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		getViewSite().getActionBars().getToolBarManager().add(clearAction);

		PlatformUI.getWorkbench().addWindowListener(new IWindowListener() {

			public void windowActivated(IWorkbenchWindow window) {
				// causes an error message on Eclipse 3.3
				// added a try-catch to supress it
				// probably the line is not needed
				// Channing?
				try {
					viewer.refresh();
				} catch (org.eclipse.swt.SWTException e) {
				}
			}

			public void windowClosed(IWorkbenchWindow window) {
			}

			public void windowDeactivated(IWorkbenchWindow window) {
			}

			public void windowOpened(IWorkbenchWindow window) {
			}

		});
	}

	protected abstract void clearAll();

	protected abstract void initialiseColumns(TableViewer viewer2);

	protected abstract IBaseLabelProvider getViewLabelProvider();

	protected abstract IContentProvider getViewContentProvider(TableViewer viewer2);

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
