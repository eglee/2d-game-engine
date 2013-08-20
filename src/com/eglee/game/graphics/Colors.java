package com.eglee.game.graphics;

public class Colors
{
	public static int get(int color1, int color2, int color3, int color4)
	{
		return (get(color4)<<24) + (get(color3)<<16) + (get(color2)<<8) + get(color1); //shift 8 each time to add up to one big number, 2^8 bits for each color
	} //converts our 4 colors to one big long number

	private static int get(int color)
	{
		if (color < 0) return 255; //for transparency
		int r = color/100%10; //for example rgb(555) for a color, get first bit of data, mod 10 to get it between 0-10
		int g = color/10%10;  //get second bit of data mod 10
		int b = color%10; //get third bit of data mod 10
		
		return r * 36 + g * 6 + b;
	} //return value from 0-5 inside array
}
