package com.eglee.game.entity;

import com.eglee.game.level.Level;
import com.eglee.game.level.tiles.Tile;

//all mobile entities, players, enemies etc

public abstract class Mob extends Entity
{
	protected String name;
	protected int speed;
	protected int numberMoves = 0; //how far traveled
	protected boolean isMoving;
	protected int movingDirection = 1; //0 = up, 1 = down, 2 = left, 3 = right
	public int tickTime = 0;
	protected int health;
	public boolean isHit = false;
	
	public Mob(Level level, String name, int x, int y, int speed, int health)
	{
		super(level); //calls blueprint class Entity
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.health = health;
		//System.out.println(isHit);
		
	}
	
	public void update()
	{
		
		tickTime++;
		/* if (isHit()) {
		      health--;
		      isHit = false;
		    }
		 if (health == 0) {
		      removed = true;
		    }*/
		//update method not being inherited correctly by sub classes
	} //check if hurt or dead
	
	public void move(int xA, int yA)
	{
		if (xA != 0 && yA != 0)
		{
			move(xA, 0);
			move(0, yA);
			numberMoves--; //doesn't double count, since technically two moves
			return;
		} //prevents moving diagonal, move on direction, then the other, not both at same time
		
		numberMoves++;
		
		if(!hasCollided(xA,yA))
		{
			if(yA < 0) movingDirection = 0; //up
			if(yA > 0) movingDirection = 1; //down
			if(xA < 0) movingDirection = 2; //left
			if(xA > 0) movingDirection = 3; //right
			
			x = x + xA * speed; //moves mob!
			y = y + yA * speed;
		}
		
	}
	
	public abstract boolean hasCollided(int xA, int yA);
	
	protected boolean isSolidTile(int xA, int yA, int x, int y)
	{
		if (level == null) {return false;}
		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3); //tile currently standing on before movement
		Tile newTile = level.getTile((this.x + x + xA)  >> 3, (this.y + y + yA) >> 3); //tile we're moving to
		if (!lastTile.equals(newTile) && newTile.isSolid())
		{
			return true;
		} //make sure tiles aren't same, and new tile is actually solid
		return false;
	} //relative movement function, get last tile standing on and new tile, compare them
	
	protected boolean isHurtTile(int xA, int yA, int x, int y)
	{
		if (level == null) {return false;}
		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3); //tile currently standing on before movement
		Tile newTile = level.getTile((this.x + x + xA)  >> 3, (this.y + y + yA) >> 3); //tile we're moving to
		if (!lastTile.equals(newTile) && newTile.isHurt())
		{
			return true;
		} //make sure tiles aren't same, and new tile is actually solid
		return false;
	} //relative movement function, get last tile standing on and new tile, compare them
	
	protected boolean isVoidTile(int xA, int yA, int x, int y)
	{
		if (level == null) {return false;}
		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3); //tile currently standing on before movement
		Tile newTile = level.getTile((this.x + x + xA)  >> 3, (this.y + y + yA) >> 3); //tile we're moving to
		if (!lastTile.equals(newTile) && newTile.isVoidTile())
		{
			return true;
		} //make sure tiles aren't same, and new tile is actually solid
		return false;
	} //relative movement function, get last tile standing on and new tile, compare them
	
	public String getName()
	{
		return name;
	}
	
	public boolean isHit() {
	    return isHit;
	  }
	
	protected void die() {
		remove();
	}
}
