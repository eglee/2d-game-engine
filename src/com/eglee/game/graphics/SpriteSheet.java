package com.eglee.game.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet
{
	public String path; //path of image
	public int width;
	public int height;
	
	public int[] pixels; //pixel data of sprite sheet
	
	public SpriteSheet(String path)
	{
		BufferedImage image = null;
		
		try
		{
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path)); //creates new BufferedImage object, sets it to path of path variable
		} catch (IOException e)
			{
				e.printStackTrace();
			}
		
		if (image == null) {return;}
		
		this.path = path;
		this.width = image.getWidth(); //width of image loaded into BufferedImage
		this.height = image.getHeight(); //height of image loaded into BufferedImage
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width); //scans BufferedImage horizontally, puts each pixel into int pixels array
	
		for (int i=0; i < pixels.length; i++)
		{
			pixels[i] = (pixels[i] & 0xff) / 64;  //&0xff gets rids of alpha channel for color, 4 colors, puts 0,1,2,3 or 4 into pixels array for SpriteSheet
			
		}
		
		
		// each greyscale rgb color on our spriteSheet is assigned 0-3 in our array above
		// 255/3*0 = 0, 255/3*1 = 85 etc. to get our rgb values
		// pixels[i] = 0 represents rgb(0,0,0) for black, pixels[i] = 1 represents (85,85,85) for dark grey etc.
		// this results in 0 = black, 3 = white, and two in between colors
	}
}


