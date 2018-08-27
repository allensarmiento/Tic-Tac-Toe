package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.TicTacToeGame.GAMESTATE;

public class MainMenu extends MouseAdapter {

	private TicTacToeGame game;
	private GameInterface gameInterface;
	private EasyComputer easyComputer;
	private MediumComputer mediumComputer;
	private Handler handler;

	private int squareX = game.WIDTH/10, squareY = game.HEIGHT/8;

	private int squareX1 = squareX, squareX2 = squareX * 2, 
				squareX3 = squareX * 3, squareX4 = squareX * 4, 
				squareX5 = squareX * 5, squareX6 = squareX * 6,
				squareX7 = squareX * 7, squareX8 = squareX * 8,
				squareX9 = squareX * 9, squareX10 = squareX * 10,

				squareY1 = squareY, squareY2 = squareY * 2,
				squareY3 = squareY * 3, squareY4 = squareY * 4,
				squareY5 = squareY * 5, squareY6 = squareY * 6,
				squareY7 = squareY * 7, squareY8 = squareY * 8;

	private int titleWordXPosition1 = squareX1, titleWordYPosition1 = squareY2,
				titleWordXPosition2 = squareX1, titleWordYPosition2 = squareY3,
				titleWordXPosition3 = squareX2, titleWordYPosition3 = squareY4,
				titleWordXPosition4 = squareX3, titleWordYPosition4 = squareY5;

	public MainMenu(TicTacToeGame game, GameInterface gameInterface, EasyComputer easyComputer, MediumComputer mediumComputer, Handler handler) {
		this.game = game;
		this.gameInterface = gameInterface;
		this.easyComputer = easyComputer;
		this.mediumComputer = mediumComputer;
		this.handler = handler;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		// Menu state
		if(game.gameState == GAMESTATE.Menu) {
			// normal mode button
			if(mouseOver(mx, my, 400, 50, 200, 64)) {
				game.gameState = GAMESTATE.Player;
				game.gameMode = 0;
				return;
			}

			// extreme mode button
			if(mouseOver(mx, my, 400, 150, 200, 64)) {
				game.gameState = GAMESTATE.Player;
				game.gameMode = 1;
				return;
			}

			// help button
			if(mouseOver(mx, my, 400, 250, 200, 64)) {
				game.gameState = GAMESTATE.Help;
				return;
			}

			// quit button
			if(mouseOver(mx, my, 400, 350, 200, 64)) {
				System.exit(1);
			}
		}

		// Player selection state
		if(game.gameState == GAMESTATE.Player) {
			// one player -> difficulty
			if(mouseOver(mx, my, 210, 150, 200, 64)) {
				game.gameState = GAMESTATE.Difficulty;
				game.numPlayers = 1;
				return;
			}

			// two player -> start game
			if(mouseOver(mx, my, 210, 250, 200, 64)) {
				game.gameState = GAMESTATE.Icon;
				game.numPlayers = 2;
				return;
			}

			// back button
			if(mouseOver(mx, my, 210, 350, 200, 64)) {
				game.gameState = GAMESTATE.Menu;
				return;
			}
		}

		// Difficulty selection state
		if(game.gameState == GAMESTATE.Difficulty) {
			// easy difficulty
			if(mouseOver(mx, my, 210, 100, 200, 64)) {
				game.gameState = GAMESTATE.Icon;
				game.difficulty = 0;
				return;
			}

			// medium difficulty
			if(mouseOver(mx, my, 210, 200, 200, 64)) {
				game.gameState = GAMESTATE.Icon;
				game.difficulty = 1;
				return;
			}

			// hard difficulty
			if(mouseOver(mx, my, 210, 300, 200, 64)) {
				game.gameState = GAMESTATE.Icon;
				game.difficulty = 2;
				return;
			}

			// Back button
			if(mouseOver(mx, my, 210, 400, 200, 64)) {
				game.gameState = GAMESTATE.Player;
				return;
			}
		}

		// Icon selection state
		if(game.gameState == GAMESTATE.Icon) {
			// X icon
			if(mouseOver(mx, my, 100, 100, 200, 200)) {
				if(game.numPlayers == 1) {
					game.gameState = GAMESTATE.Game;
					game.xTurn = true;
					game.xPlayer = true;
					return;
				} else if(game.numPlayers == 2) {
					game.gameState = GAMESTATE.Icon2;
					game.xTurn = true;
					return;
				}
			}

			// O Icon
			if(mouseOver(mx, my, 350, 100, 200, 200)) {
				if(game.numPlayers == 1) {
					game.gameState = GAMESTATE.Game;
					game.oTurn = true;
					game.oPlayer = true;
					return;
				} else if(game.numPlayers == 2) {
					game.gameState = GAMESTATE.Icon2;
					game.oTurn = true;
					return;
				}
			}

			// Back button
			if(mouseOver(mx, my, 210, 350, 200, 64)) {
				game.gameState = GAMESTATE.Difficulty;
				game.xTurn = false;
				game.oTurn = false;
				game.xPlayer = false;
				game.oPlayer = false;
				return;
			}
		}

		// Icon 2 selection state
		if(game.gameState == GAMESTATE.Icon2) {
			// X icon
			if(mouseOver(mx, my, 100, 100, 200, 200)) {
				game.gameState = GAMESTATE.Game;
				return;
			}

			// O Icon
			if(mouseOver(mx, my, 350, 100, 200, 200)) {
				game.gameState = GAMESTATE.Game;
				return;
			}

			// Back button
			if(mouseOver(mx, my, 210, 350, 200, 64)) {
				game.gameState = GAMESTATE.Icon;
				return;
			}
		}

		// back button for help
		if(game.gameState == GAMESTATE.Help) {
			if(mouseOver(mx, my, 210, 350, 200, 64)) {
				game.gameState = GAMESTATE.Menu;
				return;
			}
		}

		// end state 
		if(game.gameState == GAMESTATE.End) {

		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	private boolean mouseOver(int mx, int my, int x, int y,
							  int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			} else return false;
		} else return false;
	}

	public void tick() {

	}

	public void render(Graphics g) {
		if(game.gameState == GAMESTATE.Menu) {
			Font font = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 30);

			// Title of the game
			g.setFont(font);
			g.setColor(Color.yellow);
			g.drawString("Extreme", 55, 130);
			g.setColor(Color.white);
			g.drawString("Tic", 35, 200);
			g.drawString("Tac", 120, 290);
			g.drawString("Toe", 225, 380);
			g.setColor(Color.black);
			g.drawLine(115, 150, 115, 400);
			g.drawLine(215, 150, 215, 400);
			g.drawLine(40, 225, 310, 225);
			g.drawLine(40, 325, 310, 325);

			// normal button
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(400, 50, 200, 64);
			g.drawString("Normal", 450, 93);

			// extreme button
			g.drawRect(400, 150, 200, 64);
			g.drawString("Extreme", 445, 193);

			// help button
			g.drawRect(400, 250, 200, 64);
			g.drawString("Help", 465, 293);

			// quit button
			g.drawRect(400, 350, 200, 64);
			g.drawString("Quit", 465, 393);
		} else if(game.gameState == GAMESTATE.Player) {
			Font font = new Font("arial", 1, 40);
			g.setFont(font);
			g.setColor(Color.white);

			// One player
			g.drawRect(210, 150, 200, 64);
			g.drawString("1 Player", 230, 195);

			// Two player
			g.drawRect(210, 250, 200, 64);
			g.drawString("2 Player", 230, 295);

			// Back button
			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 265, 395);
		} else if(game.gameState == GAMESTATE.Difficulty) {
			Font font = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 30);

			// Title
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Select Difficulty", 150, 55);

			// Easy difficulty
			g.setFont(font2);
			g.drawRect(210, 100, 200, 64);
			g.drawString("Easy", 270, 147);

			// Medium difficulty
			g.drawRect(210, 200, 200, 64);
			g.drawString("Medium", 255, 245);

			// Hard difficulty
			g.drawRect(210, 300, 200, 64);
			g.drawString("Hard", 275, 340);

			// Back button
			g.drawRect(210, 400, 200, 64);
			g.drawString("Back", 270, 445);
		} else if(game.gameState == GAMESTATE.Icon) {
			Font font = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 80);
			Font font3 = new Font("arial", 1, 30);

			if(game.numPlayers == 1) {
				g.setFont(font);
				g.setColor(Color.white);
				g.drawString("Select Your Icon", 125, 55);

				g.setFont(font2);
				g.setColor(Color.white);
				g.fillRect(100, 100, 200, 200);
				g.setColor(Color.red);
				g.drawRect(100, 100, 200, 200);
				g.drawString("X", 170, 235);

				g.setColor(Color.white);
				g.fillRect(350, 100, 200, 200);
				g.setColor(Color.blue);
				g.drawRect(350, 100, 200, 200);
				g.drawString("O", 425, 235);

				g.setFont(font3);
				g.setColor(Color.white);
				g.drawRect(210, 350, 200, 64);
				g.drawString("Back", 275, 390);
			} else if (game.numPlayers == 2) {
				g.setFont(font);
				g.setColor(Color.white);
				g.drawString("Select Player 1 Icon", 125, 55);

				g.setFont(font2);
				g.setColor(Color.white);
				g.fillRect(100, 100, 200, 200);
				g.setColor(Color.red);
				g.drawRect(100, 100, 200, 200);
				g.drawString("X", 170, 235);

				g.setColor(Color.white);
				g.fillRect(350, 100, 200, 200);
				g.setColor(Color.blue);
				g.drawRect(350, 100, 200, 200);
				g.drawString("Y", 425, 235);

				g.setFont(font3);
				g.setColor(Color.white);
				g.drawRect(210, 350, 200, 64);
				g.drawString("Back", 275, 390);
			}
		} else if(game.gameState == GAMESTATE.Icon2) {
				Font font = new Font("arial", 1, 50);
				Font font2 = new Font("arial", 1, 80);
				Font font3 = new Font("arial", 1, 30);

				g.setFont(font);
				g.setColor(Color.white);
				g.drawString("Select Player 2 Icon", 125, 55);

				g.setFont(font2);
				g.setColor(Color.white);
				g.fillRect(100, 100, 200, 200);
				g.setColor(Color.red);
				g.drawRect(100, 100, 200, 200);
				g.drawString("X", 170, 235);

				g.setColor(Color.white);
				g.fillRect(350, 100, 200, 200);
				g.setColor(Color.blue);
				g.drawRect(350, 100, 200, 200);
				g.drawString("Y", 425, 235);

				g.setFont(font3);
				g.setColor(Color.white);
				g.drawRect(210, 350, 200, 64);
				g.drawString("Back", 275, 390);
		} else if(game.gameState == GAMESTATE.Help) {
			Font font = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 30);

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", 240, 70);

			g.setFont(font2);
			g.drawString("Normal game mode:", game.WIDTH/3, game.HEIGHT/4);
			g.drawString("Regular game of tic tac toe.", game.WIDTH/3+20, game.HEIGHT/4+20);

			g.drawString("Extreme game mode:", game.WIDTH/3, game.HEIGHT - game.HEIGHT/4);
			g.drawString("In Extreme game mode, you alternate boxes and try to win tic tac toe.", game.WIDTH/3+20, game.HEIGHT-game.HEIGHT/4+20);

			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 270, 390);
		}
	}
}