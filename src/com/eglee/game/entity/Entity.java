package com.eglee.game.entity;

import java.util.Random;

import com.eglee.game.graphics.Screen;
import com.eglee.game.level.Level;


public abstract class Entity
{
	public int x;
	public int y;
	protected Level level; //protected so only inherited classes can see
	public boolean removed = false;
	protected final Random random = new Random();
	
	
	public Entity(Level level)
	{
		initialize(level);
	
	}
	
	public final void initialize(Level level)
	{
		this.level = level;
		
	}
	
	public void remove() {
		removed = true;
	}
	
	public abstract void update();
	
	public abstract void render(Screen screen);
	
	
}
