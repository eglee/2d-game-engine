package com.eglee.game.entity;

import com.eglee.game.InputHandler;
import com.eglee.game.graphics.Colors;
import com.eglee.game.graphics.Screen;
import com.eglee.game.level.Level;

public class Player extends Mob
{

	private InputHandler input;
	private int color = Colors.get(-1, 210, 432, 543); //111, 145, 543
	private int scale = 1;
	protected boolean isSwimming = false;
	private int updateCount = 0;
	
	public Player(Level level, int x, int y, InputHandler input, int health)
	{
		super(level, "Player", x, y, 1, health);
		this.input = input;
		
	}

	public void update()
	{
		int xA = 0;
		int yA = 0;
		
		
		if (input.up.isPressed())
		{
			yA--;
			if(input.action.isPressed()) { yA = yA - 2;}
		}
		if (input.down.isPressed())
		{
			yA++;
			if(input.action.isPressed()) { yA = yA + 2;}
		}
		if (input.left.isPressed())
		{
			xA--;
			if(input.action.isPressed()) { xA = xA - 2;}
		}
		if (input.right.isPressed())
		{
			xA++;
			if(input.action.isPressed()) { xA = xA + 2;}
		}
		
		if (xA != 0 || yA !=0)
		{
			move(xA, yA);
			isMoving = true;
		} else
			{
				isMoving = false;
			} //if moving in some direction, move!
		if (level.getTile(this.x >> 3, this.y>>3).getId() == 3)
		{
			isSwimming = true;
		}
		
		if (level.getTile(this.x >> 3, this.y>>3).getId() != 3)
		{
			isSwimming = false;
		}
		updateCount++;
	}

	public void render(Screen screen)
	{
		int xTile = 0; //x on spriteSheet
		int yTile = 28;
		int walkingSpeed = 4;
		int flipTop = (numberMoves >> walkingSpeed) & 1; //divide by 2^4 & 1, number between 0 and 1
		int flipBottom = (numberMoves >> walkingSpeed) & 1;
		
		if (movingDirection == 1)
		{
			xTile = xTile + 2; //shift everything over two on x to next player sprite
		} else if (movingDirection > 1)
			{
				xTile = xTile + 4 + ((numberMoves >> walkingSpeed) & 1) * 2; //moves over to third player sprite, gives us number between 0 and 1, if 0 give us 0, if give us 2
				flipTop = (movingDirection - 1) % 2; //number between 0-1
				flipBottom = (movingDirection - 1) % 2;
			}
		
		int modifier = 8 * scale;
		int xOffset = x - modifier/2; //gets player in center
		int yOffset = y - modifier/2 - 4; //mid section of sprite actual center
		
		if (isSwimming)
		{
			int waterColor = 0;
			yOffset = yOffset + 4;
			if (updateCount % 60 > 15)
			{
				waterColor = Colors.get(-1, -1, 135, -1);
			} else if (15 <= updateCount % 60 && updateCount % 60 < 30)
				{
					yOffset = yOffset - 1;
					waterColor = Colors.get(-1, 135, 125, -1);
				} else if (30 <= updateCount % 60 && updateCount % 60 < 45)
					{
						waterColor = Colors.get(-1, 125, -1, 135);
					} else
						{
							yOffset = yOffset - 1;
							waterColor = Colors.get(-1, 135, 125, -1);
						}
			screen.render(xOffset, yOffset + 3, 0 + 26 * 32, waterColor, 0x00, 1);
			screen.render(xOffset + 8, yOffset + 3, 0 + 26 * 32, waterColor, 0x01, 1);

		}
		
		
		//upper body
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale); //times 32 to get actual tile number
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
		
		//lower body
		if(!isSwimming)
		{
			screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale);
			screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);
		}
	}
	
	public boolean hasCollided(int xA, int yA)
	{
		int xMin = 0;
		int xMax = 7;
		int yMin = 3;
		int yMax = 7;
		
		for (int x = xMin; x < xMax; x++)
		{
			if (isSolidTile(xA, yA, x, yMin))
				return true;
		} //top of collision box
		
		for (int x = xMin; x < xMax; x++)
		{
			if (isSolidTile(xA, yA, x, yMax))
				return true;
		} //bottom of collision box
		
		for (int y = yMin; y < yMax; y++)
		{
			if (isSolidTile(xA, yA, xMin, y))
				return true;
		} //left side of collision box
		
		for (int y = yMin; y < xMax; y++)
		{
			if (isSolidTile(xA, yA, xMax, y))
				return true;
		} //right side of collision box
		
		
		return false;
	} //collision box


}
