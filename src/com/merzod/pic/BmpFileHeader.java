package com.merzod.pic;

import java.io.FileInputStream;

import org.apache.log4j.Logger;

public class BmpFileHeader {
	private final BmpFile m_file;

	private final FileInputStream m_is;

	private final int m_headerSize = 14;
	
	private static Logger s_logger = Logger.getLogger(BmpFileHeader.class);

	public BmpFileHeader(FileInputStream is, BmpFile file) {
		m_is = is;
		m_file = file;
	}

	public void load() throws Exception {
		int count = 0;
		int i;
		while (count < m_headerSize) {
			// read input stream
			i = m_is.read();
			// if end of file - throw exception
			if (i == -1) {
				throw new Exception("Wrong format: end of file in header");
			}
			s_logger.debug(count + ")\t" + Integer.toHexString(i) + "\t"
					+ (char) i);

			switch (count) {
			case 0:
				if (i != 0x42) // 'B'
				{
					throw new Exception("Wrong format: should start from BM");
				}
				break;
			case 1:
				if (i != 0x4D) // 'M'
				{
					throw new Exception("Wrong format: should start from BM");
				}
				break;
			// size of file
			case 2:
				m_file.addToFileSize(i);
				break;
			case 3:
				m_file.addToFileSize(i * 0x100);
				break;
			case 4:
				m_file.addToFileSize(i * 0x10000);
				break;
			case 5:
				m_file.addToFileSize(i * 0x1000000);
				break;
			// image date displacement
			case 10:
				m_file.addImageDisplacement(i);
				break;
			case 11:
				m_file.addImageDisplacement(i * 0x100);
				break;
			case 12:
				m_file.addImageDisplacement(i * 0x10000);
				break;
			case 13:
				m_file.addImageDisplacement(i * 0x1000000);
				break;
			}
			count++;
		}
	}
}
