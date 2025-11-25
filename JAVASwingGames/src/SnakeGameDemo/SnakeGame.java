package SnakeGameDemo;

import javax.swing.*;

public class SnakeGame {

	public static void main(String[] args) 
	{
		//set width and height variables
		int width = 800;
		int height = width;
		
		//create new frame
		JFrame f = new JFrame("Snake");
		f.setVisible(true);
		f.setSize(width,height);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create instance of game
		SnakeGameMechanics sgm = new SnakeGameMechanics(width, height);
		f.add(sgm);
		f.pack();
		
		//allows sgm to listener for user inputs
		sgm.requestFocus();
	}

}
