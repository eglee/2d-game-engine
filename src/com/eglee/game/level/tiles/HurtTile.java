package com.eglee.game.level.tiles;

public class HurtTile extends BasicSolidTile
{

	public HurtTile(int id, int x, int y, int tileColor, int levelColor)
	{
		super(id, x, y, tileColor, levelColor);
		// TODO Auto-generated constructor stub
		this.hurt = true;
	}

}
