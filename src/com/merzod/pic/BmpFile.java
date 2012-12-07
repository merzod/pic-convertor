package com.merzod.pic;

import java.io.FileInputStream;

import org.apache.log4j.Logger;

public class BmpFile {

	private String m_inputFileName = null;

	private String m_outputFileName = null;

	private BmpFileHeader m_fileHeader = null;

	private BmpInfoHeader m_infoHeader = null;

	private BmpBody m_body = null;

	private int m_fileSize = 0;

	private int m_imageDisplacement = 0;

	private int m_width;

	private int m_height;

	private int m_bitCount;
	
	private static Logger s_logger = Logger.getLogger(BmpFile.class);

	public BmpFile(String inputFileName, String outputFileName) {
		m_inputFileName = inputFileName;
		m_outputFileName = outputFileName;
	}

	public void load() throws Exception {
		FileInputStream is = null;
		try {
			s_logger.info("Read file: "+m_inputFileName);
			is = new FileInputStream(m_inputFileName);
			m_fileHeader = new BmpFileHeader(is, this);
			m_fileHeader.load();
			s_logger.info("File Size: " + m_fileSize + "K");
			s_logger.info("Image Diplacement: " + m_imageDisplacement);
			m_infoHeader = new BmpInfoHeader(is, this);
			m_infoHeader.load();
			s_logger.info("Image Size: " + m_width + "x" + m_height);
			m_body = new BmpBody(is, this);
			m_body.load();
			s_logger.info("Write file: "+m_outputFileName);
			BodyWriter writer = new BodyWriter(this, m_outputFileName);
			writer.write();
		} catch (Exception e) {
			throw e;
		} finally {
			is.close();
		}
	}

	public BmpBody getBody() {
		return m_body;
	}

	public void addToFileSize(int i) {
		m_fileSize += i;
	}

	public void addImageDisplacement(int i) {
		m_imageDisplacement += i;
	}

	public void addWidth(int i) {
		m_width += i;
	}

	public void addHeight(int i) {
		m_height += i;
	}

	public void addBitCount(int i) {
		m_bitCount += i;
	}

	public int getWidth() {
		return m_width;
	}

	public int getHeight() {
		return m_height;
	}
}
