package com.eglee.game.level.tiles;

import com.eglee.game.graphics.Screen;
import com.eglee.game.level.Level;

public class AnimatedTile extends BasicTile
{
	private int[][] animationTileCoords;
	private int currentAnimationIndex;
	private long lastIterationTime; //ms since last update
	private int animationSwitchDelay;

	public AnimatedTile(int id, int[][] animationCoords, int tileColor, int levelColor, int animationSwitchDelay)
	{
		super(id, animationCoords[0][0], animationCoords[0][1], tileColor, levelColor);
		this.animationTileCoords = animationCoords;
		this.currentAnimationIndex = 0;
		this.lastIterationTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;
	}

	public void update()
	{
		if ((System.currentTimeMillis() - lastIterationTime) >= (animationSwitchDelay))
		{
			lastIterationTime = System.currentTimeMillis();
			currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length; //mod to keep within bounds of array 
			this.tileId = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1] * 32)); //update tile id with what id it should be on the fly
		} //past delay time, so update
	}
	
	public void render(Screen screen, Level level, int x, int y)
	{
		screen.render(x, y, tileId, tileColor, 0x00, 1);
	}
}
