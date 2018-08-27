package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.TicTacToeGame.GAMESTATE;

public class GameInterface extends MouseAdapter {

	private TicTacToeGame game;
	private MainMenu mainMenu;
	private Handler handler;

	// boolean for normal game mode
	private boolean[][] xBoard = { { false, false, false },
								   { false, false, false },
								   { false, false, false } };
	private boolean[][] oBoard = { { false, false, false },
								   { false, false, false },
								   { false, false, false } };

	// boolean for extreme game mode
	private boolean[][] xBoardInner1 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] xBoardInner2 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] xBoardInner3 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] xBoardInner4 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] xBoardInner5 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] xBoardInner6 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] xBoardInner7 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] xBoardInner8 = { { false, false, false }, { false, false, false }, { false, false, false } };		
	private boolean[][] xBoardInner9 = { { false, false, false }, { false, false, false }, { false, false, false } };		

	private boolean[][] oBoardInner1 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] oBoardInner2 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] oBoardInner3 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] oBoardInner4 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] oBoardInner5 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] oBoardInner6 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] oBoardInner7 = { { false, false, false }, { false, false, false }, { false, false, false } };
	private boolean[][] oBoardInner8 = { { false, false, false }, { false, false, false }, { false, false, false } };		
	private boolean[][] oBoardInner9 = { { false, false, false }, { false, false, false }, { false, false, false } };

	// This is for the extreme game mode to prevent picking the same inner square twice
	private boolean[][] innerBoard = { { false, false, false },
								 	  { false, false, false },
								   	  { false, false, false } };					   		 					   		 							   		 							   		 							   		 							   		 

	private int boardLW = 400; // board length and width
	private int innerSquare = boardLW / 3;
	private int innerInnerSquare = innerSquare / 3;
	private int boardXPosition = game.WIDTH / 6, // X position the board starts on
				boardYPosition = 30; // Y position the board starts on
	private boolean xWinner = false, 
					oWinner = false;
	private boolean gameOver = false;
	private boolean gameBegin = false;

	public GameInterface(TicTacToeGame game, MainMenu mainMenu, Handler handler) {
		this.game = game;
		this.mainMenu = mainMenu;
		this.handler = handler;
	}

	public void clearBoard() { 
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				// Normal game clear
				xBoard[i][j] = false;
				oBoard[i][j] = false;

				// Extreme game clear
				xBoardInner1[i][j] = false;
				xBoardInner2[i][j] = false;
				xBoardInner3[i][j] = false;
				xBoardInner4[i][j] = false;
				xBoardInner5[i][j] = false;
				xBoardInner6[i][j] = false;
				xBoardInner7[i][j] = false;
				xBoardInner8[i][j] = false;
				xBoardInner9[i][j] = false;

				oBoardInner1[i][j] = false;
				oBoardInner2[i][j] = false;
				oBoardInner3[i][j] = false;
				oBoardInner4[i][j] = false;
				oBoardInner5[i][j] = false;
				oBoardInner6[i][j] = false;
				oBoardInner7[i][j] = false;
				oBoardInner8[i][j] = false;
				oBoardInner9[i][j] = false;

				innerBoard[i][j] = false;
			}
		}

		gameBegin = false;
		gameOver = false;
		xWinner = false;
		oWinner = false;
	}

	public void checkWinner() {
		if(game.gameMode == 0 && game.numPlayers == 2) {
			// Normal game mode check
			if(gameOver == false) {
				if(xBoard[0][0] == true && xBoard[0][1] == true && xBoard[0][2] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[1][0] == true && xBoard[1][1] == true && xBoard[1][2] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[2][0] == true && xBoard[2][1] == true && xBoard[2][2] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[0][0] == true && xBoard[1][0] == true && xBoard[2][0] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[0][1] == true && xBoard[1][1] == true && xBoard[2][1] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[0][2] == true && xBoard[1][2] == true && xBoard[2][2] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[0][0] == true && xBoard[1][1] == true && xBoard[2][2] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[0][2] == true && xBoard[1][1] == true && xBoard[2][0] == true) {
					xWinner = true;
					gameOver = true;
				} else if(oBoard[0][0] == true && oBoard[0][1] == true && oBoard[0][2] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[1][0] == true && oBoard[1][1] == true && oBoard[1][2] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[2][0] == true && oBoard[2][1] == true && oBoard[2][2] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[0][0] == true && oBoard[1][0] == true && oBoard[2][0] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[0][1] == true && oBoard[1][1] == true && oBoard[2][1] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[0][2] == true && oBoard[1][2] == true && oBoard[2][2] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[0][0] == true && oBoard[1][1] == true && oBoard[2][2] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[0][2] == true && oBoard[1][1] == true && oBoard[2][0] == true) {
					oWinner = true;
					gameOver = true;
				}
			}
		}

		if(game.gameMode == 1 && game.numPlayers == 2) {
			// Extreme game mode
			if(gameOver == false) {
				// Inner 1
				if(xBoardInner1[0][0] == true && xBoardInner1[0][1] == true && xBoardInner1[0][2] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					xBoard[0][0] = true;
				} else if(xBoardInner1[1][0] == true && xBoardInner1[1][1] == true && xBoardInner1[1][2] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					xBoard[0][0] = true;
				} else if(xBoardInner1[2][0] == true && xBoardInner1[2][1] == true && xBoardInner1[2][2] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					xBoard[0][0] = true;
				} else if(xBoardInner1[0][0] == true && xBoardInner1[1][0] == true && xBoardInner1[2][0] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					xBoard[0][0] = true;
				} else if(xBoardInner1[0][1] == true && xBoardInner1[1][1] == true && xBoardInner1[2][1] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					xBoard[0][0] = true;
				} else if(xBoardInner1[0][2] == true && xBoardInner1[1][2] == true && xBoardInner1[2][2] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					xBoard[0][0] = true;
				} else if(xBoardInner1[0][0] == true && xBoardInner1[1][1] == true && xBoardInner1[2][2] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					xBoard[0][0] = true;
				} else if(xBoardInner1[0][2] == true && xBoardInner1[1][1] == true && xBoardInner1[2][0] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					xBoard[0][0] = true;
				} else if(oBoardInner1[0][0] == true && oBoardInner1[0][1] == true && oBoardInner1[0][2] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					oBoard[0][0] = true;
				} else if(oBoardInner1[1][0] == true && oBoardInner1[1][1] == true && oBoardInner1[1][2] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					oBoard[0][0] = true;
				} else if(oBoardInner1[2][0] == true && oBoardInner1[2][1] == true && oBoardInner1[2][2] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					oBoard[0][0] = true;
				} else if(oBoardInner1[0][0] == true && oBoardInner1[1][0] == true && oBoardInner1[2][0] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					oBoard[0][0] = true;
				} else if(oBoardInner1[0][1] == true && oBoardInner1[1][1] == true && oBoardInner1[2][1] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					oBoard[0][0] = true;
				} else if(oBoardInner1[0][2] == true && oBoardInner1[1][2] == true && oBoardInner1[2][2] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					oBoard[0][0] = true;
				} else if(oBoardInner1[0][0] == true && oBoardInner1[1][1] == true && oBoardInner1[2][2] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					oBoard[0][0] = true;
				} else if(oBoardInner1[0][2] == true && oBoardInner1[1][1] == true && oBoardInner1[2][0] == true && xBoard[0][0] == false && oBoard[0][0] == false) {
					oBoard[0][0] = true;
				}

				// Inner 2
				if(xBoardInner2[0][0] == true && xBoardInner2[0][1] == true && xBoardInner2[0][2] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					xBoard[0][1] = true;
				} else if(xBoardInner2[1][0] == true && xBoardInner2[1][1] == true && xBoardInner2[1][2] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					xBoard[0][1] = true;
				} else if(xBoardInner2[2][0] == true && xBoardInner2[2][1] == true && xBoardInner2[2][2] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					xBoard[0][1] = true;
				} else if(xBoardInner2[0][0] == true && xBoardInner2[1][0] == true && xBoardInner2[2][0] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					xBoard[0][1] = true;
				} else if(xBoardInner2[0][1] == true && xBoardInner2[1][1] == true && xBoardInner2[2][1] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					xBoard[0][1] = true;
				} else if(xBoardInner2[0][2] == true && xBoardInner2[1][2] == true && xBoardInner2[2][2] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					xBoard[0][1] = true;
				} else if(xBoardInner2[0][0] == true && xBoardInner2[1][1] == true && xBoardInner2[2][2] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					xBoard[0][1] = true;
				} else if(xBoardInner2[0][2] == true && xBoardInner2[1][1] == true && xBoardInner2[2][0] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					xBoard[0][1] = true;
				} else if(oBoardInner2[0][0] == true && oBoardInner2[0][1] == true && oBoardInner2[0][2] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					oBoard[0][1] = true;
				} else if(oBoardInner2[1][0] == true && oBoardInner2[1][1] == true && oBoardInner2[1][2] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					oBoard[0][1] = true;
				} else if(oBoardInner2[2][0] == true && oBoardInner2[2][1] == true && oBoardInner2[2][2] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					oBoard[0][1] = true;
				} else if(oBoardInner2[0][0] == true && oBoardInner2[1][0] == true && oBoardInner2[2][0] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					oBoard[0][1] = true;
				} else if(oBoardInner2[0][1] == true && oBoardInner2[1][1] == true && oBoardInner2[2][1] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					oBoard[0][1] = true;
				} else if(oBoardInner2[0][2] == true && oBoardInner2[1][2] == true && oBoardInner2[2][2] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					oBoard[0][1] = true;
				} else if(oBoardInner2[0][0] == true && oBoardInner2[1][1] == true && oBoardInner2[2][2] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					oBoard[0][1] = true;
				} else if(oBoardInner2[0][2] == true && oBoardInner2[1][1] == true && oBoardInner2[2][0] == true && xBoard[0][1] == false && oBoard[0][1] == false) {
					oBoard[0][1] = true;
				}

				// Inner 3
				if(xBoardInner3[0][0] == true && xBoardInner3[0][1] == true && xBoardInner3[0][2] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					xBoard[0][2] = true;
				} else if(xBoardInner3[1][0] == true && xBoardInner3[1][1] == true && xBoardInner3[1][2] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					xBoard[0][2] = true;
				} else if(xBoardInner3[2][0] == true && xBoardInner3[2][1] == true && xBoardInner3[2][2] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					xBoard[0][2] = true;
				} else if(xBoardInner3[0][0] == true && xBoardInner3[1][0] == true && xBoardInner3[2][0] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					xBoard[0][2] = true;
				} else if(xBoardInner3[0][1] == true && xBoardInner3[1][1] == true && xBoardInner3[2][1] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					xBoard[0][2] = true;
				} else if(xBoardInner3[0][2] == true && xBoardInner3[1][2] == true && xBoardInner3[2][2] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					xBoard[0][2] = true;
				} else if(xBoardInner3[0][0] == true && xBoardInner3[1][1] == true && xBoardInner3[2][2] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					xBoard[0][2] = true;
				} else if(xBoardInner3[0][2] == true && xBoardInner3[1][1] == true && xBoardInner3[2][0] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					xBoard[0][2] = true;
				} else if(oBoardInner3[0][0] == true && oBoardInner3[0][1] == true && oBoardInner3[0][2] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					oBoard[0][2] = true;
				} else if(oBoardInner3[1][0] == true && oBoardInner3[1][1] == true && oBoardInner3[1][2] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					oBoard[0][2] = true;
				} else if(oBoardInner3[2][0] == true && oBoardInner3[2][1] == true && oBoardInner3[2][2] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					oBoard[0][2] = true;
				} else if(oBoardInner3[0][0] == true && oBoardInner3[1][0] == true && oBoardInner3[2][0] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					oBoard[0][2] = true;
				} else if(oBoardInner3[0][1] == true && oBoardInner3[1][1] == true && oBoardInner3[2][1] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					oBoard[0][2] = true;
				} else if(oBoardInner3[0][2] == true && oBoardInner3[1][2] == true && oBoardInner3[2][2] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					oBoard[0][2] = true;
				} else if(oBoardInner3[0][0] == true && oBoardInner3[1][1] == true && oBoardInner3[2][2] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					oBoard[0][2] = true;
				} else if(oBoardInner3[0][2] == true && oBoardInner3[1][1] == true && oBoardInner3[2][0] == true && xBoard[0][2] == false && oBoard[0][2] == false) {
					oBoard[0][2] = true;
				}

				// Inner 4
				if(xBoardInner4[0][0] == true && xBoardInner4[0][1] == true && xBoardInner4[0][2] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					xBoard[1][0] = true;
				} else if(xBoardInner4[1][0] == true && xBoardInner4[1][1] == true && xBoardInner4[1][2] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					xBoard[1][0] = true;
				} else if(xBoardInner4[2][0] == true && xBoardInner4[2][1] == true && xBoardInner4[2][2] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					xBoard[1][0] = true;
				} else if(xBoardInner4[0][0] == true && xBoardInner4[1][0] == true && xBoardInner4[2][0] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					xBoard[1][0] = true;
				} else if(xBoardInner4[0][1] == true && xBoardInner4[1][1] == true && xBoardInner4[2][1] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					xBoard[1][0] = true;
				} else if(xBoardInner4[0][2] == true && xBoardInner4[1][2] == true && xBoardInner4[2][2] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					xBoard[1][0] = true;
				} else if(xBoardInner4[0][0] == true && xBoardInner4[1][1] == true && xBoardInner4[2][2] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					xBoard[1][0] = true;
				} else if(xBoardInner4[0][2] == true && xBoardInner4[1][1] == true && xBoardInner4[2][0] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					xBoard[1][0] = true;
				} else if(oBoardInner4[0][0] == true && oBoardInner4[0][1] == true && oBoardInner4[0][2] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					oBoard[1][0] = true;
				} else if(oBoardInner4[1][0] == true && oBoardInner4[1][1] == true && oBoardInner4[1][2] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					oBoard[1][0] = true;
				} else if(oBoardInner4[2][0] == true && oBoardInner4[2][1] == true && oBoardInner4[2][2] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					oBoard[1][0] = true;
				} else if(oBoardInner4[0][0] == true && oBoardInner4[1][0] == true && oBoardInner4[2][0] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					oBoard[1][0] = true;
				} else if(oBoardInner4[0][1] == true && oBoardInner4[1][1] == true && oBoardInner4[2][1] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					oBoard[1][0] = true;
				} else if(oBoardInner4[0][2] == true && oBoardInner4[1][2] == true && oBoardInner4[2][2] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					oBoard[1][0] = true;
				} else if(oBoardInner4[0][0] == true && oBoardInner4[1][1] == true && oBoardInner4[2][2] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					oBoard[1][0] = true;
				} else if(oBoardInner4[0][2] == true && oBoardInner4[1][1] == true && oBoardInner4[2][0] == true && xBoard[1][0] == false && oBoard[1][0] == false) {
					oBoard[1][0] = true;
				}

				// Inner 5
				if(xBoardInner5[0][0] == true && xBoardInner5[0][1] == true && xBoardInner5[0][2] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					xBoard[1][1] = true;
				} else if(xBoardInner5[1][0] == true && xBoardInner5[1][1] == true && xBoardInner5[1][2] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					xBoard[1][1] = true;
				} else if(xBoardInner5[2][0] == true && xBoardInner5[2][1] == true && xBoardInner5[2][2] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					xBoard[1][1] = true;
				} else if(xBoardInner5[0][0] == true && xBoardInner5[1][0] == true && xBoardInner5[2][0] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					xBoard[1][1] = true;
				} else if(xBoardInner5[0][1] == true && xBoardInner5[1][1] == true && xBoardInner5[2][1] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					xBoard[1][1] = true;
				} else if(xBoardInner5[0][2] == true && xBoardInner5[1][2] == true && xBoardInner5[2][2] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					xBoard[1][1] = true;
				} else if(xBoardInner5[0][0] == true && xBoardInner5[1][1] == true && xBoardInner5[2][2] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					xBoard[1][1] = true;
				} else if(xBoardInner5[0][2] == true && xBoardInner5[1][1] == true && xBoardInner5[2][0] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					xBoard[1][1] = true;
				} else if(oBoardInner5[0][0] == true && oBoardInner5[0][1] == true && oBoardInner5[0][2] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					oBoard[1][1] = true;
				} else if(oBoardInner5[1][0] == true && oBoardInner5[1][1] == true && oBoardInner5[1][2] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					oBoard[1][1] = true;
				} else if(oBoardInner5[2][0] == true && oBoardInner5[2][1] == true && oBoardInner5[2][2] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					oBoard[1][1] = true;
				} else if(oBoardInner5[0][0] == true && oBoardInner5[1][0] == true && oBoardInner5[2][0] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					oBoard[1][1] = true;
				} else if(oBoardInner5[0][1] == true && oBoardInner5[1][1] == true && oBoardInner5[2][1] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					oBoard[1][1] = true;
				} else if(oBoardInner5[0][2] == true && oBoardInner5[1][2] == true && oBoardInner5[2][2] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					oBoard[1][1] = true;
				} else if(oBoardInner5[0][0] == true && oBoardInner5[1][1] == true && oBoardInner5[2][2] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					oBoard[1][1] = true;
				} else if(oBoardInner5[0][2] == true && oBoardInner5[1][1] == true && oBoardInner5[2][0] == true && xBoard[1][1] == false && oBoard[1][1] == false) {
					oBoard[1][1] = true;
				}

				// Inner 6
				if(xBoardInner6[0][0] == true && xBoardInner6[0][1] == true && xBoardInner6[0][2] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					xBoard[1][2] = true;
				} else if(xBoardInner6[1][0] == true && xBoardInner6[1][1] == true && xBoardInner6[1][2] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					xBoard[1][2] = true;
				} else if(xBoardInner6[2][0] == true && xBoardInner6[2][1] == true && xBoardInner6[2][2] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					xBoard[1][2] = true;
				} else if(xBoardInner6[0][0] == true && xBoardInner6[1][0] == true && xBoardInner6[2][0] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					xBoard[1][2] = true;
				} else if(xBoardInner6[0][1] == true && xBoardInner6[1][1] == true && xBoardInner6[2][1] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					xBoard[1][2] = true;
				} else if(xBoardInner6[0][2] == true && xBoardInner6[1][2] == true && xBoardInner6[2][2] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					xBoard[1][2] = true;
				} else if(xBoardInner6[0][0] == true && xBoardInner6[1][1] == true && xBoardInner6[2][2] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					xBoard[1][2] = true;
				} else if(xBoardInner6[0][2] == true && xBoardInner6[1][1] == true && xBoardInner6[2][0] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					xBoard[1][2] = true;
				} else if(oBoardInner6[0][0] == true && oBoardInner6[0][1] == true && oBoardInner6[0][2] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					oBoard[1][2] = true;
				} else if(oBoardInner6[1][0] == true && oBoardInner6[1][1] == true && oBoardInner6[1][2] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					oBoard[1][2] = true;
				} else if(oBoardInner6[2][0] == true && oBoardInner6[2][1] == true && oBoardInner6[2][2] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					oBoard[1][2] = true;
				} else if(oBoardInner6[0][0] == true && oBoardInner6[1][0] == true && oBoardInner6[2][0] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					oBoard[1][2] = true;
				} else if(oBoardInner6[0][1] == true && oBoardInner6[1][1] == true && oBoardInner6[2][1] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					oBoard[1][2] = true;
				} else if(oBoardInner6[0][2] == true && oBoardInner6[1][2] == true && oBoardInner6[2][2] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					oBoard[1][2] = true;
				} else if(oBoardInner6[0][0] == true && oBoardInner6[1][1] == true && oBoardInner6[2][2] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					oBoard[1][2] = true;
				} else if(oBoardInner6[0][2] == true && oBoardInner6[1][1] == true && oBoardInner6[2][0] == true && xBoard[1][2] == false && oBoard[1][2] == false) {
					oBoard[1][2] = true;
				}

				// Inner 7
				if(xBoardInner7[0][0] == true && xBoardInner7[0][1] == true && xBoardInner7[0][2] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					xBoard[2][0] = true;
				} else if(xBoardInner7[1][0] == true && xBoardInner7[1][1] == true && xBoardInner7[1][2] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					xBoard[2][0] = true;
				} else if(xBoardInner7[2][0] == true && xBoardInner7[2][1] == true && xBoardInner7[2][2] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					xBoard[2][0] = true;
				} else if(xBoardInner7[0][0] == true && xBoardInner7[1][0] == true && xBoardInner7[2][0] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					xBoard[2][0] = true;
				} else if(xBoardInner7[0][1] == true && xBoardInner7[1][1] == true && xBoardInner7[2][1] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					xBoard[2][0] = true;
				} else if(xBoardInner7[0][2] == true && xBoardInner7[1][2] == true && xBoardInner7[2][2] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					xBoard[2][0] = true;
				} else if(xBoardInner7[0][0] == true && xBoardInner7[1][1] == true && xBoardInner7[2][2] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					xBoard[2][0] = true;
				} else if(xBoardInner7[0][2] == true && xBoardInner7[1][1] == true && xBoardInner7[2][0] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					xBoard[2][0] = true;
				} else if(oBoardInner7[0][0] == true && oBoardInner7[0][1] == true && oBoardInner7[0][2] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					oBoard[2][0] = true;
				} else if(oBoardInner7[1][0] == true && oBoardInner7[1][1] == true && oBoardInner7[1][2] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					oBoard[2][0] = true;
				} else if(oBoardInner7[2][0] == true && oBoardInner7[2][1] == true && oBoardInner7[2][2] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					oBoard[2][0] = true;
				} else if(oBoardInner7[0][0] == true && oBoardInner7[1][0] == true && oBoardInner7[2][0] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					oBoard[2][0] = true;
				} else if(oBoardInner7[0][1] == true && oBoardInner7[1][1] == true && oBoardInner7[2][1] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					oBoard[2][0] = true;
				} else if(oBoardInner7[0][2] == true && oBoardInner7[1][2] == true && oBoardInner7[2][2] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					oBoard[2][0] = true;
				} else if(oBoardInner7[0][0] == true && oBoardInner7[1][1] == true && oBoardInner7[2][2] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					oBoard[2][0] = true;
				} else if(oBoardInner7[0][2] == true && oBoardInner7[1][1] == true && oBoardInner7[2][0] == true && xBoard[2][0] == false && oBoard[2][0] == false) {
					oBoard[2][0] = true;
				}

				// Inner 8
				if(xBoardInner8[0][0] == true && xBoardInner8[0][1] == true && xBoardInner8[0][2] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					xBoard[2][1] = true;
				} else if(xBoardInner8[1][0] == true && xBoardInner8[1][1] == true && xBoardInner8[1][2] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					xBoard[2][1] = true;
				} else if(xBoardInner8[2][0] == true && xBoardInner8[2][1] == true && xBoardInner8[2][2] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					xBoard[2][1] = true;
				} else if(xBoardInner8[0][0] == true && xBoardInner8[1][0] == true && xBoardInner8[2][0] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					xBoard[2][1] = true;
				} else if(xBoardInner8[0][1] == true && xBoardInner8[1][1] == true && xBoardInner8[2][1] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					xBoard[2][1] = true;
				} else if(xBoardInner8[0][2] == true && xBoardInner8[1][2] == true && xBoardInner8[2][2] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					xBoard[2][1] = true;
				} else if(xBoardInner8[0][0] == true && xBoardInner8[1][1] == true && xBoardInner8[2][2] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					xBoard[2][1] = true;
				} else if(xBoardInner8[0][2] == true && xBoardInner8[1][1] == true && xBoardInner8[2][0] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					xBoard[2][1] = true;
				} else if(oBoardInner8[0][0] == true && oBoardInner8[0][1] == true && oBoardInner8[0][2] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					oBoard[2][1] = true;
				} else if(oBoardInner8[1][0] == true && oBoardInner8[1][1] == true && oBoardInner8[1][2] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					oBoard[2][1] = true;
				} else if(oBoardInner8[2][0] == true && oBoardInner8[2][1] == true && oBoardInner8[2][2] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					oBoard[2][1] = true;
				} else if(oBoardInner8[0][0] == true && oBoardInner8[1][0] == true && oBoardInner8[2][0] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					oBoard[2][1] = true;
				} else if(oBoardInner8[0][1] == true && oBoardInner8[1][1] == true && oBoardInner8[2][1] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					oBoard[2][1] = true;
				} else if(oBoardInner8[0][2] == true && oBoardInner8[1][2] == true && oBoardInner8[2][2] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					oBoard[2][1] = true;
				} else if(oBoardInner8[0][0] == true && oBoardInner8[1][1] == true && oBoardInner8[2][2] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					oBoard[2][1] = true;
				} else if(oBoardInner8[0][2] == true && oBoardInner8[1][1] == true && oBoardInner8[2][0] == true && xBoard[2][1] == false && oBoard[2][1] == false) {
					oBoard[2][1] = true;
				}

				// Inner 9
				if(xBoardInner9[0][0] == true && xBoardInner9[0][1] == true && xBoardInner9[0][2] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					xBoard[2][2] = true;
				} else if(xBoardInner9[1][0] == true && xBoardInner9[1][1] == true && xBoardInner9[1][2] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					xBoard[2][2] = true;
				} else if(xBoardInner9[2][0] == true && xBoardInner9[2][1] == true && xBoardInner9[2][2] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					xBoard[2][2] = true;
				} else if(xBoardInner9[0][0] == true && xBoardInner9[1][0] == true && xBoardInner9[2][0] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					xBoard[2][2] = true;
				} else if(xBoardInner9[0][1] == true && xBoardInner9[1][1] == true && xBoardInner9[2][1] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					xBoard[2][2] = true;
				} else if(xBoardInner9[0][2] == true && xBoardInner9[1][2] == true && xBoardInner9[2][2] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					xBoard[2][2] = true;
				} else if(xBoardInner9[0][0] == true && xBoardInner9[1][1] == true && xBoardInner9[2][2] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					xBoard[2][2] = true;
				} else if(xBoardInner9[0][2] == true && xBoardInner9[1][1] == true && xBoardInner9[2][0] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					xBoard[2][2] = true;
				} else if(oBoardInner9[0][0] == true && oBoardInner9[0][1] == true && oBoardInner9[0][2] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					oBoard[2][2] = true;
				} else if(oBoardInner9[1][0] == true && oBoardInner9[1][1] == true && oBoardInner9[1][2] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					oBoard[2][2] = true;
				} else if(oBoardInner9[2][0] == true && oBoardInner9[2][1] == true && oBoardInner9[2][2] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					oBoard[2][2] = true;
				} else if(oBoardInner9[0][0] == true && oBoardInner9[1][0] == true && oBoardInner9[2][0] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					oBoard[2][2] = true;
				} else if(oBoardInner9[0][1] == true && oBoardInner9[1][1] == true && oBoardInner9[2][1] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					oBoard[2][2] = true;
				} else if(oBoardInner9[0][2] == true && oBoardInner9[1][2] == true && oBoardInner9[2][2] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					oBoard[2][2] = true;
				} else if(oBoardInner9[0][0] == true && oBoardInner9[1][1] == true && oBoardInner9[2][2] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					oBoard[2][2] = true;
				} else if(oBoardInner9[0][2] == true && oBoardInner9[1][1] == true && oBoardInner9[2][0] == true && xBoard[2][2] == false && oBoard[2][2] == false) {
					oBoard[2][2] = true;
				}

				// Normal game board
				if(xBoard[0][0] == true && xBoard[0][1] == true && xBoard[0][2] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[1][0] == true && xBoard[1][1] == true && xBoard[1][2] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[2][0] == true && xBoard[2][1] == true && xBoard[2][2] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[0][0] == true && xBoard[1][0] == true && xBoard[2][0] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[0][1] == true && xBoard[1][1] == true && xBoard[2][1] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[0][2] == true && xBoard[1][2] == true && xBoard[2][2] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[0][0] == true && xBoard[1][1] == true && xBoard[2][2] == true) {
					xWinner = true;
					gameOver = true;
				} else if(xBoard[0][2] == true && xBoard[1][1] == true && xBoard[2][0] == true) {
					xWinner = true;
					gameOver = true;
				} else if(oBoard[0][0] == true && oBoard[0][1] == true && oBoard[0][2] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[1][0] == true && oBoard[1][1] == true && oBoard[1][2] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[2][0] == true && oBoard[2][1] == true && oBoard[2][2] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[0][0] == true && oBoard[1][0] == true && oBoard[2][0] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[0][1] == true && oBoard[1][1] == true && oBoard[2][1] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[0][2] == true && oBoard[1][2] == true && oBoard[2][2] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[0][0] == true && oBoard[1][1] == true && oBoard[2][2] == true) {
					oWinner = true;
					gameOver = true;
				} else if(oBoard[0][2] == true && oBoard[1][1] == true && oBoard[2][0] == true) {
					oWinner = true;
					gameOver = true;
				}
			}
		}

		/*
		// Doing this method would have to return a vector or array of true/falses
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(xBoard[i][j] == true) {
					
				} else if(oBoard[i][j] == true) {
					
				}
			}
		}
		*/
	} // End checkWinner 

	public void checkBoardFilled() {
		// Normal game over check
		// ----------------------
		int normalMoves = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(xBoard[i][j] == false && oBoard[i][j] == false) {
					// Exit if the game is not over
					return;
				} else normalMoves++;
			}
		}

		if(normalMoves == 9) 
			gameOver = true;

		// Extreme game over check
		// -----------------------			
		int extremeMoves = 0;

		//Inner 1
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(xBoardInner1[i][j] == true || oBoardInner1[i][j] == true) {
					extremeMoves++;
				}
			}
		}

		// Inner 2
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(xBoardInner2[i][j] == true || oBoardInner2[i][j] == true) {
					extremeMoves++;
				}
			}
		}

		// Inner 3
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(xBoardInner3[i][j] == true || oBoardInner3[i][j] == true) {
					extremeMoves++;
				}
			}
		}

		// Inner 4
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(xBoardInner4[i][j] == true || oBoardInner4[i][j] == true) {
					extremeMoves++;
				}
			}
		}

		// Inner 5
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(xBoardInner5[i][j] == true || oBoardInner5[i][j] == true) {
					extremeMoves++;
				}
			}
		}

		// Inner 6
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(xBoardInner6[i][j] == true || oBoardInner6[i][j] == true) {
					extremeMoves++;
				}
			}
		}

		// Inner 7
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(xBoardInner7[i][j] == true || oBoardInner7[i][j] == true) {
					extremeMoves++;
				}
			}
		}

		// Inner 8
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(xBoardInner8[i][j] == true || oBoardInner8[i][j] == true) {
					extremeMoves++;
				}
			}
		}

		// Inner 9
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(xBoardInner9[i][j] == true || oBoardInner9[i][j] == true) {
					extremeMoves++;
				}
			}
		}

		if(extremeMoves == 81) {
			gameOver = true;
		}
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if(game.gameState == GAMESTATE.Game && game.numPlayers == 2) {
			// Begin button
			if(mouseOver(mx, my, boardXPosition, boardYPosition+420, 100, 50)) {
				gameBegin = true;
			}

			// New game button
			if(mouseOver(mx, my, boardXPosition+100, boardYPosition+420, 100, 50)) {
				clearBoard();
			}

			// Menu button
			if(mouseOver(mx, my, boardXPosition+200, boardYPosition+420, 100, 50)) {
				game.xTurn = false;
				game.oTurn = false;
				clearBoard();
				game.gameState = GAMESTATE.Menu;
				return;
			}

			// quit button
			if(mouseOver(mx, my, boardXPosition+300, boardYPosition+420, 100, 50)) {
				System.exit(1);
			}

			// Normal game mode handling
			if(game.gameMode == 0 && gameBegin == true && gameOver == false) {
				for(int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if(mouseOver(mx, my, boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*i), innerSquare, innerSquare)) {
							if((xBoard[i][j] == false) && (oBoard[i][j] == false)) {
								if(game.xTurn == true) {
									xBoard[i][j] = true;
									game.xTurn = false;
									game.oTurn = true;
								} else if(game.oTurn == true) {
									oBoard[i][j] = true;
									game.xTurn = true;
									game.oTurn = false;
								}
								return;
							}
						}
					}
				}
			}

			// -------------------------------------------
			// This is included for the extreme game mode.
			// -------------------------------------------
			if(game.gameMode == 1 && gameBegin == true && gameOver == false) {
				// Inner Square 1 row 1 column 1
				if(innerBoard[0][0] == false) {
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare)) {
							if((xBoardInner1[i][j] == false) && (oBoardInner1[i][j] == false)) {
								if(game.xTurn == true) {
									xBoardInner1[i][j] = true;
									game.xTurn = false;
									game.oTurn = true;

									innerBoard[0][0] = true;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								} else if(game.oTurn == true) {
									oBoardInner1[i][j] = true;
									game.xTurn = true;
									game.oTurn = false;

									innerBoard[0][0] = true;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								}
								return;
							}
						}
					}
				}
				}

				// Inner Square 2 row 1 column 2
				if(innerBoard[0][1] == false) {
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare)) {
							if((xBoardInner2[i][j] == false) && (oBoardInner2[i][j] == false)) {
								if(game.xTurn == true) {
									xBoardInner2[i][j] = true;
									game.xTurn = false;
									game.oTurn = true;

									innerBoard[0][0] = false;
									innerBoard[0][1] = true;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								} else if(game.oTurn == true) {
									oBoardInner2[i][j] = true;
									game.xTurn = true;
									game.oTurn = false;

									innerBoard[0][0] = false;
									innerBoard[0][1] = true;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								}
								return;
							}
						}
					}
				}
				}

				// Inner Square 3 row 1 column 3
				if(innerBoard[0][2] == false) {
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare)) {
							if((xBoardInner3[i][j] == false) && (oBoardInner3[i][j] == false)) {
								if(game.xTurn == true) {
									xBoardInner3[i][j] = true;
									game.xTurn = false;
									game.oTurn = true;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = true;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								} else if(game.oTurn == true) {
									oBoardInner3[i][j] = true;
									game.xTurn = true;
									game.oTurn = false;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = true;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								}
								return;
							}
						}
					}
				}
				}

				// Inner Square 4 row 2 column 1
				if(innerBoard[1][0] == false) {
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare)) {
							if((xBoardInner4[i][j] == false) && (oBoardInner4[i][j] == false)) {
								if(game.xTurn == true) {
									xBoardInner4[i][j] = true;
									game.xTurn = false;
									game.oTurn = true;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = true;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								} else if(game.oTurn == true) {
									oBoardInner4[i][j] = true;
									game.xTurn = true;
									game.oTurn = false;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = true;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								}
								return;
							}
						}
					}
				}
				}

				// Inner Square 5 row 2 column 2
				if(innerBoard[1][1] == false) {
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare)) {
							if((xBoardInner5[i][j] == false) && (oBoardInner5[i][j] == false)) {
								if(game.xTurn == true) {
									xBoardInner5[i][j] = true;
									game.xTurn = false;
									game.oTurn = true;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = true;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								} else if(game.oTurn == true) {
									oBoardInner5[i][j] = true;
									game.xTurn = true;
									game.oTurn = false;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = true;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								}
								return;
							}
						}
					}
				}
				}

				// Inner Square 6 row 2 column 3
				if(innerBoard[1][2] == false) {
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare)) {
							if((xBoardInner6[i][j] == false) && (oBoardInner6[i][j] == false)) {
								if(game.xTurn == true) {
									xBoardInner6[i][j] = true;
									game.xTurn = false;
									game.oTurn = true;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = true;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								} else if(game.oTurn == true) {
									oBoardInner6[i][j] = true;
									game.xTurn = true;
									game.oTurn = false;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = true;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								}
								return;
							}
						}
					}
				}
				}

				// Inner Square 7 row 3 column 1
				if(innerBoard[2][0] == false) {
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare)) {
							if((xBoardInner7[i][j] == false) && (oBoardInner7[i][j] == false)) {
								if(game.xTurn == true) {
									xBoardInner7[i][j] = true;
									game.xTurn = false;
									game.oTurn = true;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = true;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								} else if(game.oTurn == true) {
									oBoardInner7[i][j] = true;
									game.xTurn = true;
									game.oTurn = false;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = true;
									innerBoard[2][1] = false;
									innerBoard[2][2] = false;
								}
								return;
							}
						}
					}
				}
				}

				// Inner Square 8 row 3 column 2
				if(innerBoard[2][1] == false) {
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare)) {
							if((xBoardInner8[i][j] == false) && (oBoardInner8[i][j] == false)) {
								if(game.xTurn == true) {
									xBoardInner8[i][j] = true;
									game.xTurn = false;
									game.oTurn = true;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = true;
									innerBoard[2][2] = false;
								} else if(game.oTurn == true) {
									oBoardInner8[i][j] = true;
									game.xTurn = true;
									game.oTurn = false;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = true;
									innerBoard[2][2] = false;
								}
								return;
							}
						}
					}
				}
				}

				// Inner Square 9 row 2 column 3
				if(innerBoard[2][2] == false) {
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare)) {
							if((xBoardInner9[i][j] == false) && (oBoardInner9[i][j] == false)) {
								if(game.xTurn == true) {
									xBoardInner9[i][j] = true;
									game.xTurn = false;
									game.oTurn = true;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = true;
								} else if(game.oTurn == true) {
									oBoardInner9[i][j] = true;
									game.xTurn = true;
									game.oTurn = false;

									innerBoard[0][0] = false;
									innerBoard[0][1] = false;
									innerBoard[0][2] = false;
									innerBoard[1][0] = false;
									innerBoard[1][1] = false;
									innerBoard[1][2] = false;
									innerBoard[2][0] = false;
									innerBoard[2][1] = false;
									innerBoard[2][2] = true;
								}
								return;
							}
						}
					}
				}
				}

			}
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
		Graphics2D g2 = (Graphics2D) g;

		if(game.gameState == GAMESTATE.Game && game.numPlayers == 2) {
			Font defaultFont = new Font("arial", 1, 14);
			Font font = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 75);
			Font font3 = new Font("arial", 1, 20);
			// Not using the font here

			// Tic tac toe box
			g.setColor(Color.white);
			g.fillRect(boardXPosition, boardYPosition, boardLW, boardLW); 
			
			// Draw tic tac toe board
			g.setColor(Color.black);
			g.drawLine(boardXPosition+innerSquare, boardYPosition, boardXPosition+innerSquare, boardYPosition+boardLW); // left line vertical
			g.drawLine(boardXPosition+innerSquare+innerSquare, boardYPosition, boardXPosition+innerSquare+innerSquare, boardYPosition+boardLW); // right line vertical
			g.drawLine(boardXPosition, boardYPosition+innerSquare, boardXPosition+boardLW, boardYPosition+innerSquare); // top line horizontal
			g.drawLine(boardXPosition, boardYPosition+innerSquare+innerSquare, boardXPosition+boardLW, boardYPosition+innerSquare+innerSquare); // bottom line horizontal

			// Player turn
			g.setFont(font3);
			g.setColor(Color.white);
			g.drawString("Player turn:", boardXPosition+boardLW+17, 60);
			g.fillRect(boardXPosition+boardLW+41, 92, 60, 60);
			g.setColor(Color.black);
			g.drawRect(boardXPosition+boardLW+41, 92, 60, 60);
			if(game.xTurn == true) {
				g.setFont(font);
				g.setColor(Color.red);
				g.drawString("X", boardXPosition+boardLW+55, 140);
			} else if(game.oTurn == true) {
				g.setFont(font);
				g.setColor(Color.blue);
				g.drawString("O", boardXPosition+boardLW+52, 140);
			}

			// Location cannot pick (for Extreme game mode only)
			if(game.gameMode == 1) {
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(innerBoard[i][j] == true) {
							g.setColor(Color.green);
							g.fillRect(boardXPosition+boardLW+10, 230, 125, 100);
							g.setColor(Color.black);
							g.drawRect(boardXPosition+boardLW+10, 230, 125, 100);

							g.setFont(defaultFont);
							g.setColor(Color.black);
							g.drawString("Cannot select:", boardXPosition+boardLW+14, 250);
							g.drawString("Row " + (i+1), boardXPosition+boardLW+14, 275);
							g.drawString("Column " + (j+1), boardXPosition+boardLW+14, 300);
						}
					}
				}
			}

			// Begin button
			if(gameBegin == false) {
				g.setFont(defaultFont);
				g.setColor(Color.yellow);
				g.fillRect(boardXPosition, boardYPosition+420, 100, 50);
				g.setColor(Color.black);
				g.drawRect(boardXPosition, boardYPosition+420, 100, 50);
				g.drawString("Begin", boardXPosition+35, boardYPosition+450);
			} else {
				g.setFont(defaultFont);
				g.setColor(Color.gray);
				g.fillRect(boardXPosition, boardYPosition+420, 100, 50);
				g.setColor(Color.black);
				g.drawRect(boardXPosition, boardYPosition+420, 100, 50);
				g.drawString("Begin", boardXPosition+35, boardYPosition+450);
			}

			g.setFont(defaultFont);
			// New game button
			g.setColor(Color.yellow);
			g.fillRect(boardXPosition+100, boardYPosition+420, 100, 50);
			g.setColor(Color.black);
			g.drawRect(boardXPosition+100, boardYPosition+420, 100, 50);
			g.drawString("New Game", boardXPosition+118, boardYPosition+450);

			// Menu button
			g.setColor(Color.yellow);
			g.fillRect(boardXPosition+200, boardYPosition+420, 100, 50);
			g.setColor(Color.black);
			g.drawRect(boardXPosition+200, boardYPosition+420, 100, 50);
			g.drawString("Main Menu", boardXPosition+217, boardYPosition+450);

			// Quit button
			g.setColor(Color.yellow);
			g.fillRect(boardXPosition+300, boardYPosition+420, 100, 50);
			g.setColor(Color.black);
			g.drawRect(boardXPosition+300, boardYPosition+420, 100, 50);
			g.drawString("Quit", boardXPosition+335, boardYPosition+450);

			// Normal game mode - Handling the x drawing options
			if(game.gameMode == 0) {
				// Drawing of the elements
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(xBoard[i][j] == true) {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*i), boardXPosition+(innerSquare*(j+1)), boardYPosition+(innerSquare*(i+1)));
							g.drawLine(boardXPosition+(innerSquare*(j+1)), boardYPosition+(innerSquare*i), boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*(i+1)));
						} else if(oBoard[i][j] == true) {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*i), innerSquare, innerSquare);
						}
					}
				}

				// Check if there is a winner
				checkWinner();
				if(xWinner == true) {
					g.setFont(font2);
					g.setColor(Color.magenta);
					g.drawString("Player X Wins!", 100, 200);
				} else if(oWinner == true) {
					g.setFont(font2);
					g.setColor(Color.magenta);
					g.drawString("Player O Wins!", 100, 200);
				}

				// Check is the game is over by board filled
				checkBoardFilled();
				if(gameOver == true) {
					g.setFont(font2);
					g.setColor(Color.black);
					g.drawString("GAME OVER", 100, 100);
				}
			}


			// Extreme game mode has more graphics
			if(game.gameMode == 1) {
				// -----------------------------
				// Complete drawing of the board
				// -----------------------------
				g.setColor(Color.lightGray);

				// Inner square for row 1 column 1
				g.drawLine(boardXPosition+innerInnerSquare, boardYPosition, boardXPosition+innerInnerSquare, boardYPosition+innerSquare);
				g.drawLine(boardXPosition+innerInnerSquare+innerInnerSquare, boardYPosition, boardXPosition+innerInnerSquare+innerInnerSquare, boardYPosition+innerSquare);
				g.drawLine(boardXPosition, boardYPosition+innerInnerSquare, boardXPosition+innerSquare, boardYPosition+innerInnerSquare);
				g.drawLine(boardXPosition, boardYPosition+innerInnerSquare+innerInnerSquare, boardXPosition+innerSquare, boardYPosition+innerInnerSquare+innerInnerSquare);

				// Inner square for row 1 column 2
				g.drawLine(boardXPosition+innerSquare+innerInnerSquare, boardYPosition, boardXPosition+innerSquare+innerInnerSquare, boardYPosition+innerSquare);
				g.drawLine(boardXPosition+innerSquare+(innerInnerSquare*2), boardYPosition, boardXPosition+innerSquare+(innerInnerSquare*2), boardYPosition+innerSquare);
				g.drawLine(boardXPosition+innerSquare, boardYPosition+innerInnerSquare, boardXPosition+(innerSquare*2), boardYPosition+innerInnerSquare);
				g.drawLine(boardXPosition+innerSquare, boardYPosition+(innerInnerSquare*2), boardXPosition+(innerSquare*2), boardYPosition+(innerInnerSquare*2));

				// Inner square for row 1 column 3
				g.drawLine(boardXPosition+(innerSquare*2)+innerInnerSquare, boardYPosition, boardXPosition+(innerSquare*2)+innerInnerSquare, boardYPosition+innerSquare);
				g.drawLine(boardXPosition+(innerSquare*2)+(innerInnerSquare*2), boardYPosition, boardXPosition+(innerSquare*2)+(innerInnerSquare*2), boardYPosition+innerSquare);
				g.drawLine(boardXPosition+(innerSquare*2), boardYPosition+innerInnerSquare, boardXPosition+(innerSquare*3), boardYPosition+innerInnerSquare);
				g.drawLine(boardXPosition+(innerSquare*2), boardYPosition+(innerInnerSquare*2), boardXPosition+(innerSquare*3), boardYPosition+(innerInnerSquare*2));

				// Inner square for row 2 column 1
				g.drawLine(boardXPosition+innerInnerSquare, boardYPosition+innerSquare, boardXPosition+innerInnerSquare, boardYPosition+(innerSquare*2));
				g.drawLine(boardXPosition+innerInnerSquare+innerInnerSquare, boardYPosition+innerSquare, boardXPosition+innerInnerSquare+innerInnerSquare, boardYPosition+(innerSquare*2));
				g.drawLine(boardXPosition, boardYPosition+innerInnerSquare+innerSquare, boardXPosition+innerSquare, boardYPosition+innerInnerSquare+innerSquare);
				g.drawLine(boardXPosition, boardYPosition+(innerInnerSquare*2)+innerSquare, boardXPosition+innerSquare, boardYPosition+(innerInnerSquare*2)+innerSquare);

				// Inner square for row 2 column 2
				g.drawLine(boardXPosition+innerSquare+innerInnerSquare, boardYPosition+innerSquare, boardXPosition+innerSquare+innerInnerSquare, boardYPosition+(innerSquare*2));
				g.drawLine(boardXPosition+innerSquare+(innerInnerSquare*2), boardYPosition+innerSquare, boardXPosition+innerSquare+(innerInnerSquare*2), boardYPosition+(innerSquare*2));
				g.drawLine(boardXPosition+innerSquare, boardYPosition+innerInnerSquare+innerSquare, boardXPosition+(innerSquare*2), boardYPosition+innerInnerSquare+innerSquare);
				g.drawLine(boardXPosition+innerSquare, boardYPosition+(innerInnerSquare*2)+innerSquare, boardXPosition+(innerSquare*2), boardYPosition+(innerInnerSquare*2)+innerSquare);

				// Inner square for row 2 column 3
				g.drawLine(boardXPosition+(innerSquare*2)+innerInnerSquare, boardYPosition+innerSquare, boardXPosition+(innerSquare*2)+innerInnerSquare, boardYPosition+(innerSquare*2));
				g.drawLine(boardXPosition+(innerSquare*2)+(innerInnerSquare*2), boardYPosition+innerSquare, boardXPosition+(innerSquare*2)+(innerInnerSquare*2), boardYPosition+(innerSquare*2));
				g.drawLine(boardXPosition+(innerSquare*2), boardYPosition+innerInnerSquare+innerSquare, boardXPosition+(innerSquare*3), boardYPosition+innerInnerSquare+innerSquare);
				g.drawLine(boardXPosition+(innerSquare*2), boardYPosition+(innerInnerSquare*2)+innerSquare, boardXPosition+(innerSquare*3), boardYPosition+(innerInnerSquare*2)+innerSquare);

				// Inner square for row 3 column 1
				g.drawLine(boardXPosition+innerInnerSquare, boardYPosition+(innerSquare*2), boardXPosition+innerInnerSquare, boardYPosition+(innerSquare*3));
				g.drawLine(boardXPosition+innerInnerSquare+innerInnerSquare, boardYPosition+(innerSquare*2), boardXPosition+innerInnerSquare+innerInnerSquare, boardYPosition+(innerSquare*3));
				g.drawLine(boardXPosition, boardYPosition+innerInnerSquare+(innerSquare*2), boardXPosition+innerSquare, boardYPosition+innerInnerSquare+(innerSquare*2));
				g.drawLine(boardXPosition, boardYPosition+(innerInnerSquare*2)+(innerSquare*2), boardXPosition+innerSquare, boardYPosition+(innerInnerSquare*2)+(innerSquare*2));

				// Inner square for row 3 column 2
				g.drawLine(boardXPosition+innerSquare+innerInnerSquare, boardYPosition+(innerSquare*2), boardXPosition+innerSquare+innerInnerSquare, boardYPosition+(innerSquare*3));
				g.drawLine(boardXPosition+innerSquare+(innerInnerSquare*2), boardYPosition+(innerSquare*2), boardXPosition+innerSquare+(innerInnerSquare*2), boardYPosition+(innerSquare*3));
				g.drawLine(boardXPosition+innerSquare, boardYPosition+innerInnerSquare+(innerSquare*2), boardXPosition+(innerSquare*2), boardYPosition+innerInnerSquare+(innerSquare*2));
				g.drawLine(boardXPosition+innerSquare, boardYPosition+(innerInnerSquare*2)+(innerSquare*2), boardXPosition+(innerSquare*2), boardYPosition+(innerInnerSquare*2)+(innerSquare*2));

				// Inner square for row 3 column 3
				g.drawLine(boardXPosition+(innerSquare*2)+innerInnerSquare, boardYPosition+(innerSquare*2), boardXPosition+(innerSquare*2)+innerInnerSquare, boardYPosition+(innerSquare*3));
				g.drawLine(boardXPosition+(innerSquare*2)+(innerInnerSquare*2), boardYPosition+(innerSquare*2), boardXPosition+(innerSquare*2)+(innerInnerSquare*2), boardYPosition+(innerSquare*3));
				g.drawLine(boardXPosition+(innerSquare*2), boardYPosition+innerInnerSquare+(innerSquare*2), boardXPosition+(innerSquare*3), boardYPosition+innerInnerSquare+(innerSquare*2));
				g.drawLine(boardXPosition+(innerSquare*2), boardYPosition+(innerInnerSquare*2)+(innerSquare*2), boardXPosition+(innerSquare*3), boardYPosition+(innerInnerSquare*2)+(innerSquare*2));
			
				// --------------------
				// Handling mouse input
				// --------------------

				// Inner 1 row 1 column 1
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(xBoardInner1[i][j] == true) {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i), boardXPosition+(innerInnerSquare*(j+1)), boardYPosition+(innerInnerSquare*(i+1)));
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1)), boardYPosition+(innerInnerSquare*i), boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*(i+1)));
						} else if(oBoardInner1[i][j] == true) {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 2 row 1 column 2
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(xBoardInner2[i][j] == true) {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i), boardXPosition+(innerInnerSquare*(j+1))+innerSquare, boardYPosition+(innerInnerSquare*(i+1)));
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1))+innerSquare, boardYPosition+(innerInnerSquare*i), boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*(i+1)));
						} else if(oBoardInner2[i][j] == true) {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 3 row 1 column 3
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(xBoardInner3[i][j] == true) {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i), boardXPosition+(innerInnerSquare*(j+1))+(innerSquare*2), boardYPosition+(innerInnerSquare*(i+1)));
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1))+(innerSquare*2), boardYPosition+(innerInnerSquare*i), boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*(i+1)));
						} else if(oBoardInner3[i][j] == true) {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 4 row 2 column 1
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(xBoardInner4[i][j] == true) {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+innerSquare, boardXPosition+(innerInnerSquare*(j+1)), boardYPosition+(innerInnerSquare*(i+1))+innerSquare);
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1)), boardYPosition+(innerInnerSquare*i)+innerSquare, boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*(i+1))+innerSquare);
						} else if(oBoardInner4[i][j] == true) {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 5 row 2 column 2
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(xBoardInner5[i][j] == true) {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+innerSquare, boardXPosition+(innerInnerSquare*(j+1))+innerSquare, boardYPosition+(innerInnerSquare*(i+1))+innerSquare);
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1))+innerSquare, boardYPosition+(innerInnerSquare*i)+innerSquare, boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*(i+1))+innerSquare);
						} else if(oBoardInner5[i][j] == true) {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 6 row 2 column 3
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(xBoardInner6[i][j] == true) {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+innerSquare, boardXPosition+(innerInnerSquare*(j+1))+(innerSquare*2), boardYPosition+(innerInnerSquare*(i+1))+innerSquare);
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1))+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+innerSquare, boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*(i+1))+innerSquare);
						} else if(oBoardInner6[i][j] == true) {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 7 row 3 column 1
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(xBoardInner7[i][j] == true) {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), boardXPosition+(innerInnerSquare*(j+1)), boardYPosition+(innerInnerSquare*(i+1))+(innerSquare*2));
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1)), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*(i+1))+(innerSquare*2));
						} else if(oBoardInner7[i][j] == true) {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 8 row 3 column 2
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(xBoardInner8[i][j] == true) {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+(innerSquare*2), boardXPosition+(innerInnerSquare*(j+1))+innerSquare, boardYPosition+(innerInnerSquare*(i+1))+(innerSquare*2));
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1))+innerSquare, boardYPosition+(innerInnerSquare*i)+(innerSquare*2), boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*(i+1))+(innerSquare*2));
						} else if(oBoardInner8[i][j] == true) {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 9 row 9 column 3
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(xBoardInner9[i][j] == true) {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), boardXPosition+(innerInnerSquare*(j+1))+(innerSquare*2), boardYPosition+(innerInnerSquare*(i+1))+(innerSquare*2));
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1))+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*(i+1))+(innerSquare*2));
						} else if(oBoardInner9[i][j] == true) {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Drawing of the elements
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(xBoard[i][j] == true) {
							g2.setColor(Color.red);
							g2.setStroke(new BasicStroke(3));
							g2.drawLine(boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*i), boardXPosition+(innerSquare*(j+1)), boardYPosition+(innerSquare*(i+1)));
							g2.drawLine(boardXPosition+(innerSquare*(j+1)), boardYPosition+(innerSquare*i), boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*(i+1)));
						} else if(oBoard[i][j] == true) {
							g2.setColor(Color.blue);
							g2.setStroke(new BasicStroke(3));
							g2.drawOval(boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*i), innerSquare, innerSquare);
						}
					}
				}

				// Check if there is a winner
				checkWinner();
				if(xWinner == true) {
					g.setFont(font2);
					g.setColor(Color.magenta);
					g.drawString("Player X Wins!", 100, 200);
				} else if(oWinner == true) {
					g.setFont(font2);
					g.setColor(Color.magenta);
					g.drawString("Player O Wins!", 100, 200);
				}

				checkBoardFilled();
				if(gameOver == true) {
					g.setFont(font2);
					g.setColor(Color.black);
					g.drawString("GAME OVER", 100, 100);
				}
			}
		}
	}
}