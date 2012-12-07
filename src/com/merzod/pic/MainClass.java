package com.merzod.pic;

import org.apache.log4j.Logger;

public class MainClass {

	private static Logger s_logger = Logger.getLogger(MainClass.class);

	public MainClass(String input, String output) {
		String inputFileName = input;
		String outputFileName = output;
		BmpFile file = new BmpFile(inputFileName, outputFileName);
		try {
			file.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length == 2) {
			Config.load();
			new MainClass(args[0], args[1]);
		} else if (args.length == 3) {
			Config.load(args[2]);
			new MainClass(args[0], args[1]);
		} else {
			s_logger
					.warn("Should receive 2/3 params: input bmp file, output file, [config file]");
		}
	}
}
