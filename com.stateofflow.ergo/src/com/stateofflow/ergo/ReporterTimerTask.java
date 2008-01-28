package com.stateofflow.ergo;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.commands.Command;
import org.eclipse.core.runtime.Preferences;

/**
 * Task that makes request to a reporting service. If no reporting service is
 * set in the preference no reports are made.
 * 
 * @author "Toomas Römer <toomasr[at]gmail.com"
 */
public class ReporterTimerTask extends TimerTask {
	public ReporterTimerTask() {
		super();
	}

	@Override
	public void run() {
		try {
			Map<Command, CommandCount> commands = Activator.getCommandRecorder().getCommands();

			Properties properties = new Properties();
			for (Command command : commands.keySet()) {
				properties.put(command.getId(), commands.get(command).getKeypressCount() + "," + commands.get(command).getMenuCount());
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			properties.store(out, "");

			makeRequest(out.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void makeRequest(String buff) {
		DataOutputStream printout = null;
		URL url = getUrl();
		if (url == null)
			return;
		try {
			// URL connection channel.
			URLConnection urlConn = url.openConnection();
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			// Specify the content type.
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// Send POST output.
			printout = new DataOutputStream(urlConn.getOutputStream());
			String content = "uuid=" + URLEncoder.encode(Activator.getUUIDStr(), "UTF-8") + "&contents=" + URLEncoder.encode(buff, "UTF-8");
			printout.writeBytes(content);
			printout.flush();
			printout.close();

			// Get response data.
			DataInputStream input = new DataInputStream(urlConn.getInputStream());
			while (null != input.readLine())
				;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	private URL getUrl() {
		Preferences prefs = Activator.getDefault().getPluginPreferences();
		String str = prefs.getString(Activator.REPORTING_PREFERENCES);

		str = str.replaceAll("http://", "");

		String[] split = str.split("/", 2);
		if (split.length < 2)
			return null;

		String host = split[0];
		int port = 80;
		if (host.indexOf(":") != -1) {
			Pattern pattern = Pattern.compile(":([0-9]*)");
			Matcher matcher = pattern.matcher(host);
			if (matcher.find()) {
				host = host.replaceAll(matcher.group(), "");
				try {
					port = Integer.parseInt(matcher.group());
				} catch (NumberFormatException e) {
					port = 80;
				}
			}
		}

		String path = "/" + split[1];

		try {
			URL url = new URL("http", host, port, path);
			return url;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
