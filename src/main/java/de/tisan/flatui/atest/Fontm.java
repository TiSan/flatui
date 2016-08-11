package de.tisan.flatui.atest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Fontm {
	public static void main(String... args) throws IOException {
		 mainFlatUI();
		//mainALTE();
	}

	public static void mainFlatUI() throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(new File("D:\\font-awesome.css")));
		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<String[]> font = new ArrayList<String[]>();
		String tmp = null;
		while ((tmp = r.readLine()) != null) {
			lines.add(tmp);
		}
		String name = "";
		String value = "";
		// System.out.println("Hi");
		for (String str : lines) {
			if (str.startsWith(".")) {
				name = str.split(":")[0].substring(4).replaceAll("-", "_");
			} else if (str.startsWith("  content:")) {
				value = str.substring(13, 17);
				font.add(new String[] { name, value });
			}
			// System.out.println(str);
			// System.out.println(name + ";" + value);
		}
		for (String[] t : font) {
			System.out.println("public final static FlatIcon " + t[0].toUpperCase() + " = new FlatIcon(String.valueOf('\\u" + t[1] + "'));");
		}
		r.close();
	}

	public static void mainALTE() throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(new File("D:\\font-awesome.css")));
		String tmp, name = null;
		while ((tmp = r.readLine()) != null) {
			if (tmp.startsWith(".")) {
				name = tmp.split(":")[0].substring(1);
			} else if (tmp.startsWith("  content:")) {
				System.out.println("public final static String " + name.toUpperCase().substring(3).replaceAll("-", "_") + " = \"" + name + "\";");
			}
		}
		r.close();
	}
}
