package com.merzod.pic;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class Config {
	public static double s_mashtabKof = 1.5;

	public static int s_maxWith = 150;

	public static int s_maxHeight = 80;

	private static Logger s_logger = Logger.getLogger(Config.class);

	public static char[] s_table = new char[] { 0xFC, 'W', 'M', 'N', 'Q', 'R',
			'K',// 0,10,13,19,22,29,30
			'A', 'E', 'O', 'P', 'g', 'S',// 31,35,37,38,40,41
			'm', 'j', 'w', 'u', 'o', 'r',// 45,48,49,50,52,54
			'z', '?', 'c', '(', '+', '=',// 56,57,60,63,65,68
			',', '.', ' ' // 72,80,100
	};

	public static void load(String name) {
		try {
			s_logger.info("Using external config");
			FileInputStream stream = new FileInputStream(name);
			load(stream);
		} catch (Exception e) {
			s_logger.error("Error creating stream", e);
		}
	}

	public static void load() {
		try {
			s_logger.info("Using internal jar config");
			InputStream stream = ClassLoader
					.getSystemClassLoader().getResourceAsStream(
							"convertor.properties");
			load(stream);
		} catch (Exception e) {
			s_logger.warn("Error getting resource. Using standart configuration", e);
		}
	}

	private static void load(InputStream stream) {
		Properties props = new Properties();
		try {
			props.load(stream);
			s_mashtabKof = Double.parseDouble(props.getProperty("mas_koef"));
			s_maxWith = Integer.parseInt(props.getProperty("max_width"));
			s_maxHeight = Integer.parseInt(props.getProperty("max_height"));
			StringTokenizer tk = new StringTokenizer(
					props.getProperty("table"), "|");
			s_table = new char[tk.countTokens()];
			int i = 0;
			while (tk.hasMoreTokens()) {
				s_table[i] = tk.nextToken().charAt(0);
				i++;
			}

		} catch (Exception e) {
			s_logger.error("Error loading config", e);
		}
	}
}
