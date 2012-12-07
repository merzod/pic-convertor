package com.merzod.pic;

import java.io.FileOutputStream;

import org.apache.log4j.Logger;

public class BodyWriter {
	private BmpFile m_bmp;

	private String m_file;

	private static Logger s_logger = Logger.getLogger(BodyWriter.class);

	public BodyWriter(BmpFile bmp, String file) {
		m_bmp = bmp;
		m_file = file;
	}

	public void write() throws Exception {
		int width = m_bmp.getWidth();
		int height = m_bmp.getHeight();

		double delta;

		if (width > height) {
			if (width > Config.s_maxWith)
				delta = (double) width / (double) Config.s_maxWith;
			else
				delta = 1;
		} else {
			if (height > Config.s_maxHeight)
				delta = (double) height / (double) Config.s_maxHeight;
			else
				delta = 1;
		}
		s_logger.debug("Dx: " + delta);
		write(delta, delta * Config.s_mashtabKof);
		// write(1,1);
	}

	private void write(double dx, double dy) throws Exception {
		int width = m_bmp.getWidth();
		int height = m_bmp.getHeight();

		int resWidth = (int) (width / dx);
		int resHeight = (int) (height / dy);

		// step1
		Point table1[][] = new Point[resWidth][height];
		for (int y = 0; y < height; y++) {
			int rx = 0; // real x
			for (int x = 0; x < resWidth; x++) {
				int a = (int) ((dx * (x + 1)) - rx);
				Point[] mas = new Point[a];
				for (int i = 0; i < a; i++) {
					// collect points
					mas[i] = m_bmp.getBody().getPointAt(rx + i, y);
				}
				Point res = new Point(mas);
				table1[x][y] = res;
				rx += a;
			}
		}
		// step2
		Point table2[][] = new Point[resWidth][resHeight];
		for (int x = 0; x < resWidth; x++) {
			int ry = 0; // real y
			for (int y = 0; y < resHeight; y++) {
				int a = (int) ((dy * (y + 1)) - ry);
				Point[] mas = new Point[a];
				for (int i = 0; i < a; i++) {
					// collect points
					mas[i] = table1[x][ry + i];
				}
				Point res = new Point(mas);
				table2[x][y] = res;
				ry += a;
			}
		}

		FileOutputStream os = new FileOutputStream(m_file);
		for (int y = 0; y < resHeight; y++) {
			for (int x = 0; x < resWidth; x++) {
				os.write(table2[x][y].getChar());
			}
			os.write('\n');
		}
		os.close();
	}
}
