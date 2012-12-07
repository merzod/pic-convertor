package com.merzod.pic;

public class Point {
	
	public int R = 0;
	public int G = 0;
	public int B = 0;
	
	private int m_count = 0;
	
	public Point(){}
	
	public Point(Point[] mas)
	{
		for(int i=0; i<mas.length; i++)
		{
			R += mas[i].R;
			G += mas[i].G;
			B += mas[i].B;
		}
		R /= mas.length;
		G /= mas.length;
		B /= mas.length;
	}
	
	public void addColor(int i)
	{
		if(m_count == 0)
			B = i;
		else if(m_count == 1)
			G = i;
		else if(m_count == 2)
		{
			R = i;
			m_count = -1;
		}
		m_count++;
	}
	
	public int getNumber()
	{
		int res=R+G+B;
		return res;
	}
	
	public char getChar()
	{
		int max = 0xFF*3;
		double count = (double)max/(double)Config.s_table.length;
		for(int i=0; i<Config.s_table.length; i++)
		{
			int dig = (int)(count * ((double)i+1.));
			if(getNumber() <= dig)
			{
				return Config.s_table[i];
			}
		}
		return '$';
	}
}
