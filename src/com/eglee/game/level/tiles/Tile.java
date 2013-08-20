package com.eglee.game.level.tiles;

import com.eglee.game.graphics.Colors;
import com.eglee.game.graphics.Screen;
import com.eglee.game.level.Level;

public abstract class Tile
{
	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new VoidTile(0,0,0, Colors.get(000,-1,-1,-1), 0xff000000);
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colors.get(-1, 333, 444, -1), 0xff555555);
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.get(-1, 232, 343, 453), 0xff00ff00);


	//232, 343, 453 grass pallete
	//125, 135, 555 water/sky pallet
	
	public static final Tile WATER = new AnimatedTile (3, new int[][] {{0,5},{1,5},{2,5},{1,5}}, Colors.get(-1, 125, 135, -1), 0xff0000ff, 1000);
	public static final Tile THORN = new HurtTile(4, 3, 0, Colors.get(-1, 232, 343, 453), 0xffff4e00);
	
	
	protected byte id;
	protected boolean solid;
	protected boolean hurt;
	protected boolean emitter;
	private int levelColor;
	protected boolean isVoidTile;
	
	public Tile(int id, boolean isSolid, boolean isHurt, boolean isEmitter, int levelColor)
	{
		this.id = (byte) id;
		if (tiles[id]!=null) throw new RuntimeException("Duplicate tile id on" + id);
		this.solid = isSolid;
		this.hurt = isHurt;
		this.emitter = isEmitter;
		this.levelColor = levelColor;
		tiles[id]=this;
		this.isVoidTile = false;
		
	}
	
	public byte getId()
	{
		return id;
	}
	
	public boolean isSolid()
	{
		return solid;
	}
	
	public boolean isHurt()
	{
		return hurt;
	}
	
	public boolean isVoidTile()
	{
		return isVoidTile;
	}
	
	public boolean isEmitter()
	{
		return emitter;
	}
	
	public int getLevelColor()
	{
		return levelColor;
	}
	
	public abstract void update();
	
	public abstract void render(Screen screen, Level level, int x, int y);
	
}
