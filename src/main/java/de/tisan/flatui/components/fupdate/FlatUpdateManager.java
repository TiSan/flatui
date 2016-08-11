package de.tisan.flatui.components.fupdate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import de.tisan.flatui.components.fcommons.FlatUI;

public class FlatUpdateManager {
	private static boolean already_updated = false;

	public static FlatUpdate checkUpdate() {
		if (!already_updated)
			try {
				String ver = FlatUI.version;
				URL updater = new URL("http://code.tisan.de/update/flatui/last_version.ver");
				BufferedReader in = new BufferedReader(new InputStreamReader(updater.openStream()));
				String line = null;
				String version = null;
				String version_state = null;
				String path = null;
				ArrayList<String> changelog = new ArrayList<String>();
				String date = null;
				while ((line = in.readLine()) != null) {
					if (line.startsWith("VER:")) {
						version = line.replace("VER:", "");
					} else if (line.startsWith("VERSTATE:")) {
						version_state = line.replace("VERSTATE:", "");
					} else if (line.startsWith("PATH:")) {
						path = line.replace("PATH:", "");
					} else if (line.startsWith("CHANGELOG:")) {
						changelog.add(line.replace("CHANGELOG:", ""));
					} else if (line.startsWith("DATE:")) {
						date = line.replace("DATE:", "");
					}
				}
				String[] tmpRunning = ver.split("\\.");
				String[] tmpNew = version.split("\\.");
				boolean isNewer = false;
				int tmp = 0;
				while (true) {
					if (tmpRunning.length > tmp && tmpNew.length > tmp) {
						try {
							if (Integer.valueOf(tmpRunning[tmp]) < Integer.valueOf(tmpNew[tmp])) {
								isNewer = true;
								break;
							} else if (Integer.valueOf(tmpRunning[tmp]) == Integer.valueOf(tmpNew[tmp])) {
								// Gleiche Zahl, weiterschauen.
							} else {
								break;
							}
						} catch (Exception ex) {
							isNewer = false;
							break;
						}

					} else if (tmpNew.length > tmp) {
						isNewer = true;
						break;
					} else {
						// Kleinere aktuelle Version als verfügbare Version.
						break;
					}
					tmp++;
				}
				already_updated = true;
				if (isNewer) {
					return new FlatUpdate(version, version_state, path, changelog, date);
				} else {
					return null;
				}
			} catch (FileNotFoundException e) {
				System.err.println("[FlatUI Updater] An error occured while checking for updates. Do you have a connection to the internet?");
			} catch (IOException e) {
				System.err.println("[FlatUI Updater] An error occured while checking for updates. Do you have a connection to the internet?");
			}
		already_updated = true;
		return null;

	}
}
