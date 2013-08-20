package com.eglee.game;

import java.applet.Applet;
import java.awt.BorderLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameLauncher extends Applet
{
	private static Game game = new Game(); //creates new instance of game
	
	@Override
	public void init()
	{
		System.out.println("initialize running");
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER);
		setMaximumSize(Game.DIMENSIONS);
		setMinimumSize(Game.DIMENSIONS);
		setPreferredSize(Game.DIMENSIONS);
		
		 setLayout(new BorderLayout());
	        add(game, BorderLayout.CENTER);
	        setMaximumSize(Game.DIMENSIONS);
	        setMinimumSize(Game.DIMENSIONS);
	        setPreferredSize(Game.DIMENSIONS);
	}
	
	@Override
	public void start()
	{
		System.out.println("START running");
		game.start();
	}
	
	@Override
	public void stop()
	{
		game.stop();
	}
	
	
	public static void main(String[] args)
	{	
		System.out.println("SET DIMENSIONS");
		game.setMinimumSize(Game.DIMENSIONS);
		game.setMaximumSize(Game.DIMENSIONS);
		game.setPreferredSize(Game.DIMENSIONS); //part of Canvas class and Component, sets preferred size of our Canvas
		System.out.println("NEW JFRAME");
		game.frame = new JFrame(Game.TITLE);// creates new instance of JFrame
		
		System.out.println("BORDER LAYOUT");
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close button, terminates application thread
		game.frame.setLayout(new BorderLayout());

		//game.frame.setTitle(Game.TITLE); //set window title
		
		System.out.println("ADD GAME");
		game.frame.add(game, BorderLayout.CENTER); //add method adds a component to our frame, fills window with something, works because our class is a subtype of Canvas aka Component
		game.frame.pack(); //sets size of frame according to component
		System.out.println("SET VISIBLE");
		game.frame.setResizable(false); //resizing not allow
		game.frame.setLocationRelativeTo(null); //window at center of screen
		game.frame.setVisible(true); //actually shows our window
		System.out.println("START");
		game.start(); //starts our game
	}

}
