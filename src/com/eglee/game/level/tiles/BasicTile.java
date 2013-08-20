package com.eglee.game.level.tiles;

import com.eglee.game.graphics.Screen;
import com.eglee.game.level.Level;

public class BasicTile extends Tile
{
	protected int tileId;
	protected int tileColor;

	public BasicTile(int id, int x,int y, int tileColor, int levelColor)
	{
		super(id, false, false, false, levelColor);
		this.tileId = x + y * 32;
		this.tileColor = tileColor;
	}
	
	public void update()
	{
		
	}
	
	public void render(Screen screen, Level level, int x, int y)
	{
		screen.render(x, y, tileId, tileColor, 0x00, 1);
	}

}
