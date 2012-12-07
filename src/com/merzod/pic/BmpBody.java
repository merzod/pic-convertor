package com.merzod.pic;

import java.io.FileInputStream;

import org.apache.log4j.Logger;

public class BmpBody {
	private final BmpFile m_file;
	private final FileInputStream m_is;
	private Point [][] m_lines;
	private Logger s_logger = Logger.getLogger(BmpBody.class);
	
	public BmpBody(FileInputStream is, BmpFile file)
	{
		m_is = is;
		m_file = file;
		m_lines = new Point[m_file.getWidth()][m_file.getHeight()];
	}
	
	public Point getPointAt(int x, int y)
	{
		y = m_file.getHeight() - (y+1); 
		return m_lines[x][y];
	}

	public Point getRealPointAt(int x, int y)
	{
		return m_lines[x][y];
	}
	
	public void load() throws Exception
	{
		for(int y=0; y<m_file.getHeight();y++)
		{
			int x;
			for(x=0; x<m_file.getWidth(); x++)
			{
				Point point = new Point();
				for(int i=0; i<3; i++)
				{
					int c = m_is.read();
					if(c == -1)
					{
						s_logger.debug("x="+x+" y="+y);
						throw new Exception("Wrong format: end of file in body");
					}					
					point.addColor(c);
				}
				//add point
				m_lines[x][y]=point;
			}
			//read empty bytes
			int count = m_file.getWidth() % 4;
			long tmp = m_is.skip(count);
			if(tmp != count)
			{
				throw new Exception("Wrong format: end of file in body");
			}					
		}
	}
}
