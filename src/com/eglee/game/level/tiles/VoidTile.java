package com.eglee.game.level.tiles;

public class VoidTile extends BasicSolidTile
{

	public VoidTile(int id, int x, int y, int tileColor, int levelColor)
	{
		super(id, x, y, tileColor, levelColor);
		this.isVoidTile = true;
	}
}
