package com.eglee.game.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;


import com.eglee.game.entity.Mob;


import com.eglee.game.entity.Entity;
import com.eglee.game.entity.Player;
import com.eglee.game.graphics.Colors;
import com.eglee.game.graphics.Font;
import com.eglee.game.graphics.Screen;
import com.eglee.game.level.tiles.Tile;

public class Level
{

	private byte[] tiles;
	public int width;
	public int height;
	public List<Mob> entities = new ArrayList<Mob>();
	public List<Entity>[] entitiesInTiles;
	private String imagePath;
	private BufferedImage image;
	private Font font;

	public Level (String imagePath)
	{
		if (imagePath != null)
		{
			this.imagePath = imagePath;
			this.loadLevelFromFile();
		} else
			{
				tiles = new byte[width * height];
				this.width = 64;
				this.height = 64;
				this.generateLevel();
			}
		
		entitiesInTiles = new ArrayList[this.width * this.height];
		for (int i = 0; i < this.width * this.height; i++) {
			entitiesInTiles[i] = new ArrayList<Entity>();
		}
		
	}
	
	public Player player;
	
	private void loadLevelFromFile()
	{
		try
		{
			this.image = ImageIO.read(Level.class.getResource(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width * height];
			this.loadTiles();
		} catch (IOException e)
			{
				e.printStackTrace();
			}
		
	}
	
	private void loadTiles()
	{
		int[] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width); //translate all buffered image data into int
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				tileCheck: for (Tile tile : Tile.tiles)
				{
					if (tile != null & tile.getLevelColor() == tileColors[x + y * width])
					{
						this.tiles[x + y * width] = tile.getId();
						break tileCheck;
					}
				} //loops through every single tile in tiles var, and can be accessed with var tile
			}	
		}
	}
	
	@SuppressWarnings("unused")
	private void saveLevelToFile()
	{
		try
		{
			ImageIO.write(image,  "png", new File(Level.class.getResource(this.imagePath).getFile())); //take image, insert into this file
		} catch (IOException e)
			{
				e.printStackTrace();
			}
	}
	
	public void alterTile(int x, int y, Tile newTile)
	{
		this.tiles[x + y * width] = newTile.getId(); //update locally within level
		image.setRGB(x, y, newTile.getLevelColor()); //set image with new RGB data
	}
	
	public void generateLevel()
	{
		for(int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				if (x * y % 10 < 7)
				{
					tiles[x + y * width] = Tile.GRASS.getId();
				} else
					{
						tiles[x + y * width] = Tile.STONE.getId();
					}
			}
		}
		
	} //random level
	
	public void update()
	{
		synchronized (this.entities)
		{
			for (Iterator<Mob> iteration = this.entities.iterator(); iteration.hasNext();)
			{
				Mob entity = (Mob) iteration.next();
				entity.update();
				System.out.println(entity.isHit);
				if (entity.removed)
				{
					iteration.remove();
				} //if entity boolean removed is true, remove the entity from the array List
			}
		}
		
		for (Tile tile : Tile.tiles)
		{
			if (tile == null) 
			{
				break;
			}
			tile.update();
		}
	}
	
	public void renderTiles(Screen screen, int xOffset, int yOffset)
	{
		if (xOffset < 0) xOffset = 0;
		if (xOffset > ((width << 3) -screen.width)) xOffset = ((width <<3) -screen.width);
		if (yOffset < 0) yOffset = 0;
		if (yOffset > ((height << 3) -screen.height)) yOffset = ((height <<3) -screen.height);
		
		screen.setOffset(xOffset, yOffset); //corners of screen
		
		for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++) //tile id of topmost tile
		{
			for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++)
			{
				getTile(x,y).render(screen, this, x<<3, y<<3);
			}
		} //offsets are so that only viewable screen (and one extra tile on the y and x) is rendered, instead of entire map
	}

	public void renderEntities(Screen screen)
	{
		for(Mob e: entities)
		{
			e.render(screen);
		} //loop through all variables in list
		
		
	} //so we can render on top of tiles
	
	public Tile getTile(int x, int y)
	{
		if (0 > x || x >= width || 0 > y || y >= height) 
			return Tile.VOID;
		return Tile.tiles[tiles[x + y  * width]];
	}
	
	public void addEntity(Mob entity)
	{
		if (entity instanceof Player) {
			player = (Player) entity;
		}
		entity.removed = false;
		this.entities.add(entity); //adds entity to array list
		entity.initialize(this);
	
	}
	
	public void removeEntity(Entity entity)
	{
		entity.removed = true;
		this.entities.remove(entity);
		
	}

}
	