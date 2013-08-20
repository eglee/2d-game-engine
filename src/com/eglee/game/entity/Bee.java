package com.eglee.game.entity;


import com.eglee.game.graphics.Colors;
import com.eglee.game.graphics.Screen;
import com.eglee.game.level.Level;

public class Bee extends Mob
{
	private int color = Colors.get(-1, 111, 541, 555);
	private int scale = 1;
	private int randomWalkTime = 0;
	private int xa;
	private int ya;
	public int id;
	private int health;

	public Bee(Level level, int x, int y, int id, int health)
	{
		super(level, "Bee", x, y, 1, health);
		x = random.nextInt(64 * 16);
		y = random.nextInt(64 * 16);
		this.id = id;
		this.health = health;
	}


	public boolean hasCollided(int xA, int yA)
	{
		int xMin = 0;
		int xMax = 3;
		int yMin = 3;
		int yMax = 3;
		
		for (int x = xMin; x < xMax; x++)
		{

			if (isSolidTile(xA, yA, x, yMin))
			{
				if (isHurtTile(xA, yA, x, yMin))
					isHit = true;
				return true;
			}
				
		} //top of collision box
		
		for (int x = xMin; x < xMax; x++)
		{
			if (isSolidTile(xA, yA, x, yMax))
			{
				if (isHurtTile(xA, yA, x, yMax))
					isHit = true;
				return true;
			}
		} //bottom of collision box
		
		for (int y = yMin; y < yMax; y++)
		{
			if (isSolidTile(xA, yA, xMin, y))
			{
				if (isHurtTile(xA, yA, xMin, y))
					isHit = true;
				return true;
			}
		} //left side of collision box
		
		for (int y = yMin; y < xMax; y++)
		{
			if (isSolidTile(xA, yA, xMax, y))
			{
				if (isSolidTile(xA, yA, xMax, y))
					isHit = true;
				return true;
			}
		} //right side of collision box
	
		
		
		return false;
	}


	public void update()
	{
		if (xa != 0 || ya !=0)
		{
			move(xa, ya);
			isMoving = true;
				
		}
		
		
		
		if (level.player != null && randomWalkTime == 0 && !level.player.isSwimming) {
			int xd = level.player.x - x;
			int yd = level.player.y - y;
			if (xd * xd + yd * yd < 150 * 150) {
				xa = 0;
				ya = 0;
				if (xd < 0) xa = -1;
				if (xd > 0) xa = +1;
				if (yd < 0) ya = -1;
				if (yd > 0) ya = +1;
				
			}
		}

		//int speed = tickTime & 1;
		if (!isMoving || random.nextInt(200) == 0) {
			randomWalkTime = 30;
			xa = (random.nextInt(3) - 1) * random.nextInt(2);
			ya = (random.nextInt(3) - 1) * random.nextInt(2);
		}
		if (randomWalkTime > 0) {randomWalkTime--;}
		
		 if (isHit())
		 {
		      health--;
		      color = Colors.get(-1, 500, 500, 500);
		      isHit = false;
		 } else
		    {
		    	color = Colors.get(-1, 111, 541, 555);
		    } //take damage
		 
		 if (health == 0) 
		 {
			 removed = true;
		 } //remove when dead
		
	}


	public void render(Screen screen)
	{	int xTile = 0; //x on spriteSheet
		int yTile = 27;
		int walkingSpeed = 1;
		int flip = (numberMoves >> walkingSpeed) & 1; //divide by 2^4 & 1, number between 0 and 1
		
		
		int modifier = 8 * scale;
		int xOffset = x - modifier/2; //gets player in center
		int yOffset = y - modifier/2; //mid section of sprite actual center
		
	
		if (movingDirection == 1)
		{
			xTile = xTile + 1; //move sprite over one for down movement
		} else if (movingDirection > 1)
			{
				xTile = xTile + 2; //move sprite over two for left right movement
				flip = (movingDirection) % 2; //flips along y axis for left right movement
			}
		
		screen.render(xOffset + modifier, yOffset, xTile + yTile * 32, color, flip, scale); //times 32 to get actual tile number
	}
	
	protected void die() {
		super.die();
	}
}
