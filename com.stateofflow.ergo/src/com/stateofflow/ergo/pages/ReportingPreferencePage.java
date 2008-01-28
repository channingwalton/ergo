package com.stateofflow.ergo.pages;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.stateofflow.ergo.Activator;

/**
 * Preference page of the plugin. Lets you set the URL of the reporting service.
 * Saves the preference in plugin preferences.
 * 
 * @author "Toomas Römer <toomasr[at]gmail.com"
 */
public class ReportingPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	@Override
	protected Control createContents(Composite parent) {
		Composite content = new Composite(parent, SWT.NONE);

		content.setLayout(new GridLayout(2, false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL);

		(new Label(content, SWT.NONE)).setText("Reporting host: ");

		final Text text = new Text(content, SWT.BORDER);
		text.setLayoutData(data);

		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				Activator.getDefault().getPluginPreferences().setValue(Activator.REPORTING_PREFERENCES, text.getText());
				Activator.getDefault().savePluginPreferences();
			}
		});
		text.setText(Activator.getDefault().getPluginPreferences().getString(Activator.REPORTING_PREFERENCES));

		data = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL);
		data.horizontalSpan = 2;
		Label label = new Label(content, SWT.NONE);
		label.setText("When set to empty, no reporting is done. ");
		label.setLayoutData(data);

		return content;
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

}
