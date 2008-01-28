package com.stateofflow.ergo;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.stateofflow.ergo.messages"; //$NON-NLS-1$
	public static String ErgoView_CATEGORY;
	public static String CommandCount_NONE;
	public static String CommandCount_UNKNOWN;
	public static String ErgoView_COUNT;
	public static String ErgoView_SEQUENCE;
	public static String ErgoView_MENUCOUNT;
	public static String ErgoView_KEYPRESS_COUNT;
	public static String ErgoView_DESCRIPTION;
	public static String ErgoView_BINDING;
	public static String ErgoView_NAME;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
