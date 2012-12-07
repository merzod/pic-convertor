package com.merzod.pic;

import java.io.FileInputStream;

import org.apache.log4j.Logger;

public class BmpInfoHeader {
	private final BmpFile m_file;

	private final FileInputStream m_is;

	private int m_headerSize = 0;

	private final int m_sizeLength = 4;

	private static Logger s_logger = Logger.getLogger(BmpInfoHeader.class);

	public BmpInfoHeader(FileInputStream is, BmpFile file) {
		m_is = is;
		m_file = file;
	}

	public void load() throws Exception {
		loadHeaderSize();
		s_logger.info("Header Size: " + m_headerSize);

		int count = 0;
		int i;
		while (count < (m_headerSize - m_sizeLength)) {
			// read input stream
			i = m_is.read();
			// if end of file - throw exception
			if (i == -1) {
				throw new Exception("Wrong format: end of file in header");
			}
			s_logger.debug(count + ")\t" + Integer.toHexString(i) + "\t"
					+ (char) i);

			switch (count) {
			// width
			case 0:
				m_file.addWidth(i);
				break;
			case 1:
				m_file.addWidth(i * 0x100);
				break;
			case 2:
				m_file.addWidth(i * 0x10000);
				break;
			case 3:
				m_file.addWidth(i * 0x1000000);
				break;
			// height
			case 4:
				m_file.addHeight(i);
				break;
			case 5:
				m_file.addHeight(i * 0x100);
				break;
			case 6:
				m_file.addHeight(i * 0x10000);
				break;
			case 7:
				m_file.addHeight(i * 0x1000000);
				break;
			// bit count
			case 10:
				m_file.addBitCount(i);
				break;
			case 11:
				m_file.addBitCount(i * 0x100);
				break;
			}
			count++;
		}
	}

	private void loadHeaderSize() throws Exception {
		int count = 0;
		boolean isCorrectOut = false;
		int i;

		while ((i = m_is.read()) != -1) {
			s_logger.debug(count + ")\t" + Integer.toHexString(i) + "\t"
					+ (char) i);
			// size only in 4 bytes
			if (count == m_sizeLength - 1) {
				isCorrectOut = true;
				break;
			}

			// header size
			switch (count) {
			case 0:
				addHeaderSize(i);
				break;
			case 1:
				addHeaderSize(i * 0x100);
				break;
			case 2:
				addHeaderSize(i * 0x10000);
				break;
			case 3:
				addHeaderSize(i * 0x1000000);
				break;
			}
			count++;
		}

		if (!isCorrectOut) {
			throw new Exception("Wrong format: end of file in header");
		}
	}

	private void addHeaderSize(int i) {
		m_headerSize += i;
	}

}
