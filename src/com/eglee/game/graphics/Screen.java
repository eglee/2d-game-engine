package com.eglee.game.graphics;

public class Screen
{
	public static int MAP_WIDTH=64;
	public static int MAP_WIDTH_MASK = MAP_WIDTH -1;
	
	public int[] tiles= new int[MAP_WIDTH*MAP_WIDTH];
	//public int[] colors = new int[MAP_WIDTH*MAP_WIDTH*4]; //contain which 4 colors in reference to what in each tile, info on bits of data
	
	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;
	
	public int[] pixels;
	
	public int xOffset = 0; //offsets for screen movement
	public int yOffset = 0;
	
	public int width;
	public int height;
	
	public SpriteSheet  sheet;
	
	
	public Screen (int width, int height, SpriteSheet sheet)
	{
		this.width = width;
		this.height = height;
		this.sheet = sheet;
		
		pixels = new int[width*height];
		
		/*for (int i=0; i<MAP_WIDTH*MAP_WIDTH; i++)
		{
			colors[i*4+0] = 0xff00ff; //pink color of spriteSheet background
			colors[i*4+1] = 0x00ffff;
			colors[i*4+2] = 0xffff00; 
			colors[i*4+3] = 0xffffff;
		} //assigns a color to each of our numbers that corresponds with pixel array in SpiteSheet
		*/
	}
	
	public void clear(int color) {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = color;
	}
	
	public void render(int xPosition, int yPosition, int tile, int color, int mirrorDirection, int scale)
	{
		xPosition = xPosition - xOffset;
		yPosition = yPosition - yOffset;
		
		boolean mirrorX = (mirrorDirection & BIT_MIRROR_X) > 0; //1 & 0 = 0, 1 & 1 = 1
		boolean mirrorY = (mirrorDirection & BIT_MIRROR_Y) > 0;
		
		int scaleMap = scale -1;
		int xTile = tile % 32; //divides our spritesheet in coordinates for each tile.  Since our sheet is 256x256, and each tile is 8x8, we have 32x32 tiles
		int yTile = tile / 32;
		int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width; //gives us offset value for each tile
		
		for (int y = 0; y < 8; y++) //8 pixels, then render each individually
		{
			int ySheet = y;
			if(mirrorY) ySheet = 7-y;
			
			
			int yPixel = y + yPosition + (y * scaleMap) - ((scaleMap << 3)/2); //multiply by 8 (size), divide by two, centers pixel for us
			
			for (int x = 0; x < 8; x++)
			{
				int xSheet = x;
				if(mirrorX)xSheet = 7-x;
				int xPixel = x + xPosition + (x * scaleMap) - ((scaleMap << 3)/2);
				int colorSheet = (color >>(sheet.pixels[xSheet + ySheet * sheet.width + tileOffset] * 8)) & 255; 
				if(colorSheet < 255) 
				{	
					for (int yScale = 0; yScale < scale; yScale++)
					{
						if (yPixel + yScale < 0 || yPixel + yScale >= height) 
							continue; //check we're within bounds of screen.
						for (int xScale = 0; xScale < scale; xScale++)
						{
							if (xPixel + xScale < 0 || xPixel + xScale >= width) 
								continue; //check we're within bounds of screen.
							pixels[(xPixel + xScale) + (yPixel + yScale) * width] = colorSheet;
						}
					}

				}
			}
		}
			
	}
	
    public void setOffset(int xOffset, int yOffset)
    {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}
