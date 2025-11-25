package SnakeGameDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGameMechanics extends JPanel implements ActionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private class Tile
	{
		int x; int y;
		
		Tile(int x, int y)
		{
			this.x = x; this.y = y;
		}
	}
	
	//Board
	int boardWidth;
	int boardHeight;
	int tileSize = 25;
	
	//Snake
	Tile snakeHead;
	ArrayList<Tile> snakeBody;
	
	//Food
	Tile food;
	Random random;
	
	//game logic
	Timer gameLoop;
	int velocityX;
	int velocityY;
	boolean gameOver = false;
	
	SnakeGameMechanics(int width, int height)
	{
		//build JPanel
		this.boardWidth = width;
		this.boardHeight = height;
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(Color.BLACK);
		
		addKeyListener(this);
		setFocusable(true);
		
		//create snake head and body
		snakeHead = new Tile(5,5);
		snakeBody = new ArrayList<Tile>();
		
		//create food and use random to place it randomly
		food = new Tile(10,10);
		random = new Random();
		placeFood();
		
		//variables for snakes move auto set to 0
		velocityX = 0;
		velocityY = 0;
		
		//create game loop
		gameLoop = new Timer(100,this);
		gameLoop.start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); draw(g);
	}

	public void draw(Graphics g)//this draws snake and food
	{	
		//Snake Head
		g.setColor(Color.GREEN);
		g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize,true);
		
		//Snake Body
		for(int i = 0; i < snakeBody.size(); i++)//draw each body part
		{
			Tile snakePart = snakeBody.get(i);
			g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize,true);
		}
		
		//Food
		g.setColor(Color.RED);
		g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
		
		//Score
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		if(gameOver)
		{
			g.setColor(Color.RED);
			g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
		}
		
		else 
		{
			g.setColor(Color.WHITE);
			g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
		}
	}
	
	public void placeFood()//this places the food at a random spot
	{
		while(true)//run loop until food wont be placed on top of snake
		{
			//X and Y variables for food
			int foodX = random.nextInt(boardWidth/tileSize);
			int foodY = random.nextInt(boardHeight/tileSize);
			
			boolean onSnake =false;//variable to check if food is on snake
			
			if(snakeHead.x == foodX && snakeHead.y == foodX)//checks food vs head
				onSnake = true;
			
			for(Tile part : snakeBody)//checks body vs food
			{
				if(part.x == foodX && part.y == foodY)
					onSnake = true; break;
			}
			
			if(!onSnake)
				food.x = foodX; food.y = foodY; break;
		}
	}
	
	public boolean collision(Tile t1, Tile t2)//checks if two tiles hit (overlap)
	{
		return t1.x == t2.x && t1.y == t2.y;
	}
	
	public void move()//this moves the snake
	{	//eating food
		if(collision(snakeHead,food))
		{
			snakeBody.add(new Tile(food.x,food.y));//creates new body part where food was
			placeFood();//add more food to game board
		}
		
		//Snake body
		for(int i = snakeBody.size() -1; i >=0; i--)//runs through the body
		{
			Tile snakePart = snakeBody.get(i);
			if(i == 0)//this moves first index in list to the heads position on board
			{
				snakePart.x = snakeHead.x;
				snakePart.y = snakeHead.y;
			}
			else//this moves all over body parts to the next index in list position on board
			{
				Tile prevSnakePart = snakeBody.get(i-1);
				snakePart.x = prevSnakePart.x;
				snakePart.y = prevSnakePart.y;
			}
		}
		
		//Snake head, this changes the X and Y value for snakeHead
		snakeHead.x += velocityX;
		snakeHead.y += velocityY;
		
		//game over conditions
		for(int i = 0; i < snakeBody.size(); i++)//checks iif snake head hits body
		{
			Tile snakePart = snakeBody.get(i);
			if(collision(snakeHead,snakePart))
				gameOver = true;
		}
		
		if(snakeHead.x * tileSize == 0 || snakeHead.x * tileSize > boardWidth ||
		   snakeHead.y * tileSize == 0 || snakeHead.y * tileSize > boardHeight)//checks if snake goes of screen
				gameOver = true;
	}

	@Override
	public void actionPerformed(ActionEvent e)//this calls draw every 100 mili seconds
	{
		move();
		repaint();
		if(gameOver)
			gameLoop.stop();
	}

	@Override
	public void keyPressed(KeyEvent e) //this track user inputs aloows for ASDW and arrow keys
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_W,KeyEvent.VK_UP:
			if(velocityY == 1)
				break;
			velocityX = 0;
			velocityY = -1;
			break;
		case KeyEvent.VK_S,KeyEvent.VK_DOWN:
			if(velocityY == -1)
				break;
			velocityX = 0;
			velocityY = 1;
			break;
		case KeyEvent.VK_D,KeyEvent.VK_RIGHT:
			if(velocityX == -1)
				break;
			velocityX = 1;
			velocityY = 0;
			break;
		case KeyEvent.VK_A,KeyEvent.VK_LEFT:
			if(velocityX == 1)
				break;
			velocityX = -1;
			velocityY = 0;
			break;
		}
		
	}
	
	
	//We do not need this
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {

	}
}
