package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import main.TicTacToeGame.GAMESTATE;

public class MediumComputer extends MouseAdapter {

	private TicTacToeGame game;
	private MainMenu mainMenu;
	private Handler handler;

	private Random r; // This is a random generation

	// char for normal game mode
	private char[][] board = {
								{ ' ', ' ', ' ' },
								{ ' ', ' ', ' ' },
								{ ' ', ' ', ' ' }
							 };

	// char for extreme game mode
	private char[][][] extremeBoard = {
										{ { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } }, 
										{ { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } },
										{ { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } },
										{ { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } },
										{ { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } },
										{ { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } },
										{ { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } },
										{ { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } },
										{ { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } }									
									  };

	// This is for the extreme game mode to prevent picking the same inner square twice
	private boolean[][] innerBoard = { { false, false, false },
								 	  { false, false, false },
								   	  { false, false, false } };					   		 					   		 							   		 							   		 							   		 							   		 

	// Other variables
	private int boardLW = 400; // board length and width
	private int innerSquare = boardLW / 3;
	private int innerInnerSquare = innerSquare / 3;
	private int boardXPosition = game.WIDTH / 6, // X position the board starts on
				boardYPosition = 30; // Y position the board starts on
	private boolean xWinner = false, 
					oWinner = false;
	private boolean gameOver = false;
	private boolean gameBegin = false;

	private boolean firstMove = false; // This is when O is the single player against comp

	public MediumComputer(TicTacToeGame game, MainMenu mainMenu, Handler handler) {
		this.game = game;
		this.mainMenu = mainMenu;
		this.handler = handler;

		r = new Random();
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if(game.gameState == GAMESTATE.Game && game.numPlayers == 1) {
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

			// Normal and Medium game mode handling
			if(game.gameMode == 0 && gameBegin == true && game.difficulty == 1) {
				
				// X Player selected
				if(game.xTurn == true && game.xPlayer == true) {
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*i), innerSquare, innerSquare)) {
								if(board[i][j] == ' ') {
									board[i][j] = 'X';
									game.xTurn = false;
									game.oTurn = true;

									checkWinner(); checkBoardFilled();
									// Can use a pause transition if I do not want the next move to show up immediately
									while(game.oTurn == true && gameOver == false) {
										// If the middle is available, take it
										if(board[1][1] == ' ') {
											board[1][1] = 'O';
											game.xTurn = true;
											game.oTurn = false;
										} else if(checkCanWin('O')) {
											game.xTurn = true;
											game.oTurn = false;
										} else {
											int row = r.nextInt(3),
												col = r.nextInt(3);
											if(board[row][col] == ' ') {
												board[row][col] = 'O';
												game.xTurn = true;
												game.oTurn = false;
											}
										}
									}
									return;
								} 
							}
						}
					}
				// Game is over and O turn is next
				} else if(game.oTurn == true && game.xPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3);
					board[row][col] = 'O';
					game.xTurn = true;
					game.oTurn = false;
					return;
				} 

				// O Player selected but X will go first
				if(game.oTurn == true && game.oPlayer == true) {
					while(firstMove == false) {
						int rTemp = r.nextInt(3),
							cTemp = r.nextInt(3);
						board[rTemp][cTemp] = 'X';
						game.xTurn = false;
						game.oTurn = true;
						firstMove = true;
					}

					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*i), innerSquare, innerSquare)) {
								if(board[i][j] == ' ') {
									board[i][j] = 'O';
									game.xTurn = true;
									game.oTurn = false;

									checkWinner(); checkBoardFilled();
									// Can use a pause transition is I do not want the next move to show up immediately
									while(game.xTurn == true && gameOver == false) {
										// If the middle is available, take it
										if(board[1][1] == ' ') {
											board[1][1] = 'O';
											game.xTurn = true;
											game.oTurn = false;
										} else if(checkCanWin('O')) {
											game.xTurn = true;
											game.oTurn = false;
										} else {
											int row = r.nextInt(3),
											col = r.nextInt(3);
											if(board[row][col] == ' ') {
												board[row][col] = 'X';
												game.xTurn = false;
												game.oTurn = true;
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and X turn is next
				} else if(game.xTurn == true && game.oPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3);
					board[row][col] = 'X';
					game.xTurn = false;
					game.oTurn = true;
					return;
				} 
				
			}

			// -------------------------------------------
			// This is included for the extreme game mode.
			// -------------------------------------------

			// Extreme and Medium game mode handling
			if(game.gameMode == 1 && gameBegin == true && game.difficulty == 1) {

				// Inner Square 1 row 1 column 1
				// X Player selected, so X goes first
				if(innerBoard[0][0] == false && game.xTurn == true && game.xPlayer == true) {
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[0][i][j] == ' ') {
									extremeBoard[0][i][j] = 'X';
									game.xTurn = false;
									game.oTurn = true;
									changeBoardSelection(0);

									checkWinner(); checkBoardFilled();
									while(game.oTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 1) {
											innerRandom = r.nextInt(9) + 1;
										}

										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(checkCanWin('O')) {
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// This occurs when the game is over and it is O player's turn next
				} else if(game.oTurn == true && game.xPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'O';
							game.xTurn = true;
							game.oTurn = false;
							changeBoardSelection(i);
						}
					}
					return;
				// O Player selected, so X goes first	
				} else if(game.oTurn == true && game.oPlayer == true) {
					while(firstMove == false) {
						int rTemp = r.nextInt(3),
							cTemp = r.nextInt(3),
							iRandomTemp = r.nextInt(9) + 1;

						for(int i = 0; i < 9; i++) {
							if(i == iRandomTemp-1) {
								extremeBoard[i][rTemp][cTemp] = 'X';
								game.xTurn = false;
								game.oTurn = true;
								firstMove = true;
								changeBoardSelection(i);
							}
						}
					}
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[0][i][j] == ' ') {
									extremeBoard[0][i][j] = 'O';
									game.xTurn = true;
									game.oTurn = false;
									changeBoardSelection(0);

									checkWinner(); checkBoardFilled();
									while(game.xTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 1) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'X';
													game.xTurn = false;
													game.oTurn = true;
												} else if(checkCanWin('X')) {
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				} else if(game.xTurn == true && game.oPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'X';
							game.xTurn = false;
							game.oTurn = true;
							changeBoardSelection(i);
						}
					}
					return;
				}

				// Inner Square 2 row 1 column 2
				// X Player selected, so X goes first
				if(innerBoard[0][1] == false && game.xTurn == true && game.xPlayer == true) {
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[1][i][j] == ' ') {
									extremeBoard[1][i][j] = 'X';
									game.xTurn = false;
									game.oTurn = true; 
									changeBoardSelection(1);

									checkWinner(); checkBoardFilled();
									while(game.oTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 2) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(checkCanWin('O')) {
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					} // Stopped here
				// Game is over and O turn is next
				} else if(game.oTurn == true && game.xPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;
					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'O';
							game.xTurn = true;
							game.oTurn = false;
							changeBoardSelection(i);
						}
					}
					return;
				// O Player selected, so X goes first
				} else if(game.oTurn == true && game.oPlayer == true) {
					while(firstMove == false) {
						int rTemp = r.nextInt(3),
							cTemp = r.nextInt(3),
							iRandomTemp = r.nextInt(9) + 1;

						for(int i = 0; i < 9; i++) {
							if(i == iRandomTemp-1) {
								extremeBoard[i][rTemp][cTemp] = 'X';
								game.xTurn = false;
								game.oTurn = true;
								firstMove = true;
								changeBoardSelection(i);
							}
						}
					}
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[1][i][j] == ' ') {
									extremeBoard[1][i][j] = 'O';
									game.xTurn = true;
									game.oTurn = false;
									changeBoardSelection(1);

									checkWinner(); checkBoardFilled();
									while(game.xTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 2) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(checkCanWin('X')) {
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and X turn is next
				} else if(game.xTurn == true && game.oPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'X';
							game.xTurn = false;
							game.oTurn = true;
							changeBoardSelection(i);
						}
					}
					return;
				}


				// Inner Square 3 row 1 column 3
				// X Player selected, so X goes first
				if(innerBoard[0][2] == false && game.xTurn == true && game.xPlayer == true) {
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[2][i][j] == ' ') {
									extremeBoard[2][i][j] = 'X';
									game.xTurn = false;
									game.oTurn = true; 
									changeBoardSelection(2);
								
									checkWinner(); checkBoardFilled();
									while(game.oTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 3) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(checkCanWin('O')) {
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and it is O turn's next
				} else if(game.oTurn == true && game.xPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'O';
							game.xTurn = true;
							game.oTurn = false;
							changeBoardSelection(i);
						}
					}
					return;
				// O Player selected, so X goes first
				} else if(game.oTurn == true && game.oPlayer == true) {
					while(firstMove == false) {
						int rTemp = r.nextInt(3),
							cTemp = r.nextInt(3),
							iRandomTemp = r.nextInt(9) + 1;

						for(int i = 0; i < 9; i++) {
							if(i == iRandomTemp-1) {
								extremeBoard[i][rTemp][cTemp] = 'X';
								game.xTurn = false;
								game.oTurn = true;
								firstMove = true;
								changeBoardSelection(i);
							}
						}
					}
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[2][i][j] == ' ') {
									extremeBoard[2][i][j] = 'O';
									game.xTurn = true;
									game.oTurn = false;
									changeBoardSelection(2);

									checkWinner(); checkBoardFilled();
									while(game.xTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 3) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(checkCanWin('X')) {
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and O turn is next
				} else if(game.xTurn == true && game.oPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'X';
							game.xTurn = false;
							game.oTurn = true;
							changeBoardSelection(i);
						}
					}
					return;
				}

				// Inner Square 4 row 2 column 1
				// X Player selected, so X goes first
				if(innerBoard[1][0] == false && game.xTurn == true && game.xPlayer == true) {
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[3][i][j] == ' ') {
									extremeBoard[3][i][j] = 'X';
									game.xTurn = false;
									game.oTurn = true;
									changeBoardSelection(3);

									checkWinner(); checkBoardFilled();
									while(game.oTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 4) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(checkCanWin('O')) {
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					} 
				// Game is over, O turn is next 
				} else if(game.oTurn == true && game.xPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'O';
							game.xTurn = true;
							game.oTurn = false;
							changeBoardSelection(i);
						}
					}
					return;
				// O Player selected, so X goes first
				} else if(game.oTurn == true && game.oPlayer == true) {
					while(firstMove == false) {
						int rTemp = r.nextInt(3),
							cTemp = r.nextInt(3),
							iRandomTemp = r.nextInt(9) + 1;

						for(int i = 0; i < 9; i++) {
							if(i == iRandomTemp-1) {
								extremeBoard[i][rTemp][cTemp] = 'X';
								game.xTurn = false;
								game.oTurn = true;
								firstMove = true;
								changeBoardSelection(i);
							}
						}
					}
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[3][i][j] == ' ') {
									extremeBoard[3][i][j] = 'O';
									game.xTurn = true;
									game.oTurn = false;
									changeBoardSelection(3);

									checkWinner(); checkBoardFilled();
									while(game.xTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 4) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(checkCanWin('X')) {
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and X turn is next 
				} else if(game.xTurn == true && game.oPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;
					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'X';
							game.xTurn = false;
							game.oTurn = true;
							changeBoardSelection(i);
						}
					}
					return;
				}

				// Inner Square 5 row 2 column 2
				// X Player selected, so X goes first
				if(innerBoard[1][1] == false && game.xTurn == true && game.xPlayer == true) {
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[4][i][j] == ' ') {
									extremeBoard[4][i][j] = 'X';
									game.xTurn = false;
									game.oTurn = true;
									changeBoardSelection(4);

									checkWinner(); checkBoardFilled();
									while(game.oTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 5) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(checkCanWin('O')) {
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and O turn is next
				} else if(game.oTurn == true && game.xPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;
					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'O';
							game.xTurn = true;
							game.oTurn = false;
							changeBoardSelection(i);
						}
					}
					return;
				// O Player selected, so X goes first
				} else if(game.oTurn == true && game.oPlayer == true) {
					while(firstMove == false) {
						int rTemp = r.nextInt(3),
							cTemp = r.nextInt(3),
							iRandomTemp = r.nextInt(9) + 1;

						for(int i = 0; i < 9; i++) {
							if(i == iRandomTemp-1) {
								extremeBoard[i][rTemp][cTemp] = 'X';
								game.xTurn = false;
								game.oTurn = true;
								changeBoardSelection(i);
							}
						}
					}
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[4][i][j] == ' ') {
									extremeBoard[4][i][j] = 'O';
									game.xTurn = true;
									game.oTurn = false;
									changeBoardSelection(4);

									checkWinner(); checkBoardFilled();
									while(game.xTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 5) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(checkCanWin('X')) {
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and X turn is next
				} else if(game.xTurn == true && game.oPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'X';
							game.xTurn = false;
							game.oTurn = true;
							changeBoardSelection(i);
						}
					}
					return;
				}

				// Inner Square 6 row 2 column 3
				// X Player selected, so X goes first
				if(innerBoard[1][2] == false && game.xTurn == true && game.xPlayer == true) {
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[5][i][j] == ' ') {
									extremeBoard[5][i][j] = 'X';
									game.xTurn = false;
									game.oTurn = true;
									AdjustInnerBoard(1, 2);

									checkWinner(); checkBoardFilled();
									while(game.oTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 6) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(checkCanWin('O')) {
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and O turn is next
				} else if(game.oTurn == true && game.xPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'O';
							game.xTurn = true;
							game.oTurn = false;
							changeBoardSelection(i);
						}
					}
					return;
				// O Player selected, so X goes first
				} else if(game.oTurn == true && game.oPlayer == true) {
					while(firstMove == false) {
						int rTemp = r.nextInt(3),
							cTemp = r.nextInt(3),
							iRandomTemp = r.nextInt(9) + 1;

						for(int i = 0; i < 9; i++) {
							if(i == iRandomTemp-1) {
								extremeBoard[i][rTemp][cTemp] = 'X';
								game.xTurn = false;
								game.oTurn = true;
								firstMove = true;
								changeBoardSelection(i);
							}
						}
					}
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[5][i][j] == ' ') {
									extremeBoard[5][i][j] = 'O';
									game.xTurn = true;
									game.oTurn = false;
									changeBoardSelection(5); 

									checkWinner(); checkBoardFilled();
									while(game.xTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 6) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(checkCanWin('X')) {
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// GAme is over and X turn is next
				} else if(game.xTurn == true && game.oPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'X';
							game.xTurn = false;
							game.oTurn = true;
							changeBoardSelection(i);
						}
					}
					return;
				}

				// Inner Square 7 row 3 column 1
				// X Player selected, so X goes first
				if(innerBoard[2][0] == false && game.xTurn == true && game.xPlayer == true) {
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[6][i][j] == ' ') {
									extremeBoard[6][i][j] = 'X';
									game.xTurn = false;
									game.oTurn = true;
									changeBoardSelection(6);

									checkWinner(); checkBoardFilled();
									while(game.oTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 7) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(checkCanWin('O')) {
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and O turn is next
				} else if(game.oTurn == true && game.xPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;
					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'O';
							game.xTurn = true;
							game.oTurn = false;
							changeBoardSelection(i);
						}
					}
					return;
				// O Player selected, so X goes first
				} else if(game.oTurn == true && game.oPlayer == true) {
					while(firstMove == false) {
						int rTemp = r.nextInt(3),
							cTemp = r.nextInt(3),
							iRandomTemp = r.nextInt(9) + 1;

						for(int i = 0; i < 9; i++) {
							if(i == iRandomTemp-1) {
								extremeBoard[i][rTemp][cTemp] = 'X';
								game.xTurn = false;
								game.oTurn = true;
								firstMove = true;
								changeBoardSelection(i);
							}
						}
					}
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[6][i][j] == ' ') {
									extremeBoard[6][i][j] = 'O';
									game.xTurn = true;
									game.oTurn = false;
									changeBoardSelection(6);

									checkWinner(); checkBoardFilled();
									while(game.xTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 7) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(checkCanWin('X')) {
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and X turn is next
				} else if(game.xTurn == true && game.oPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'X';
							game.xTurn = false;
							game.oTurn = true;
							changeBoardSelection(i);
						}
					}
					return;
				}

				// Inner Square 8 row 3 column 2
				// X Player selected, so X goes first
				if(innerBoard[2][1] == false && game.xTurn == true && game.xPlayer == true) {
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[7][i][j] == ' ') {
									extremeBoard[7][i][j] = 'X';
									game.xTurn = false;
									game.oTurn = true;
									changeBoardSelection(7);

									checkWinner(); checkBoardFilled();
									while(game.oTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 8) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(checkCanWin('O')) {
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and O turn is next
				} else if(game.oTurn == true && game.xPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'O';
							game.xTurn = true;
							game.oTurn = false;
							changeBoardSelection(i);
						}
					}
					return;
				// O Player selected, so X goes first
				} else if(game.oTurn == true && game.oPlayer == true) {
					while(firstMove == false) {
						int rTemp = r.nextInt(3),
							cTemp = r.nextInt(3),
							iRandomTemp = r.nextInt(9) + 1;

						for(int i = 0; i < 9; i++) {
							if(i == iRandomTemp-1) {
								extremeBoard[i][rTemp][cTemp] = 'X';
								game.xTurn = false;
								game.oTurn = true;
								firstMove = true;
								changeBoardSelection(i);
							}
						}
					}
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[7][i][j] == ' ') {
									extremeBoard[7][i][j] = 'O';
									game.xTurn = true;
									game.oTurn = false;
									changeBoardSelection(7);

									checkWinner(); checkBoardFilled();
									while(game.xTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 8) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(checkCanWin('X')) {
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and X turn is next
				} else if(game.xTurn == true && game.oPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom) {
							extremeBoard[i][row][col] = 'X';
							game.xTurn = false;
							game.oTurn = true;
							changeBoardSelection(i);
						}
					}
					return;
				}

				// Inner Square 9 row 2 column 3
				// X Player selected, so X goes first
				if(innerBoard[2][2] == false && game.xTurn == true && game.xPlayer == true) {
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[8][i][j] == ' ') {
									extremeBoard[8][i][j] = 'X';
									game.xTurn = false;
									game.oTurn = true;
									changeBoardSelection(8);

									while(game.oTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 9) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(checkCanWin('O')) {
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'O';
													game.xTurn = true;
													game.oTurn = false;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and O turn is next
				} else if(game.oTurn == true && game.xPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom) {
							extremeBoard[i][row][col] = 'O';
							game.xTurn = true;
							game.oTurn = false;
							changeBoardSelection(i);
						}
					}
					return;
				// O Player selected, so X goes first
				} else if(game.oTurn == true && game.oPlayer == true) {
					while(firstMove == false) {
						int rTemp = r.nextInt(3),
							cTemp = r.nextInt(3),
							iRandomTemp = r.nextInt(9) + 1;

						for(int i = 0; i < 9; i++) {
							if(i == iRandomTemp-1) {
								extremeBoard[i][rTemp][cTemp] = 'X';
								game.xTurn = false;
								game.oTurn = true;
								firstMove = true;
								changeBoardSelection(i);
							}
						}
					}
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							if(mouseOver(mx, my, boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare)) {
								if(extremeBoard[8][i][j] == ' ') {
									extremeBoard[8][i][j] = 'O';
									game.xTurn = true;
									game.oTurn = false;
									changeBoardSelection(8);

									checkWinner(); checkBoardFilled();
									while(game.xTurn == true && gameOver == false) {
										int row = r.nextInt(3),
											col = r.nextInt(3),
											innerRandom = r.nextInt(9) + 1;
										while(innerRandom == 9) {
											innerRandom = r.nextInt(9) + 1;
										}
										for(int n = 0; n < 9; n++) {
											if(n == innerRandom-1) {
												if(extremeBoard[n][1][1] == ' ') {
													extremeBoard[n][1][1] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(checkCanWin('X')) {
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												} else if(extremeBoard[n][row][col] == ' ') {
													extremeBoard[n][row][col] = 'X';
													game.xTurn = false;
													game.oTurn = true;
													changeBoardSelection(n);
												}
											}
										}
									}
									return;
								}
							}
						}
					}
				// Game is over and X turn is next
				} else if(game.xTurn == true && game.oPlayer == true) {
					int row = r.nextInt(3),
						col = r.nextInt(3),
						innerRandom = r.nextInt(9) + 1;

					for(int i = 0; i < 9; i++) {
						if(i == innerRandom-1) {
							extremeBoard[i][row][col] = 'X';
							game.xTurn = false;
							game.oTurn = true;
							changeBoardSelection(i);
						}
					}
					return;
				}
				
			}
		}
	} // end mouseOver

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

		if(game.gameState == GAMESTATE.Game && game.numPlayers == 1) {
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

			// Which location cannot be picked (for Extreme game mode only)
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
						if(board[i][j] == 'X') {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*i), boardXPosition+(innerSquare*(j+1)), boardYPosition+(innerSquare*(i+1)));
							g.drawLine(boardXPosition+(innerSquare*(j+1)), boardYPosition+(innerSquare*i), boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*(i+1)));
						} else if(board[i][j] == 'O') {
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
						if(extremeBoard[0][i][j] == 'X') {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i), boardXPosition+(innerInnerSquare*(j+1)), boardYPosition+(innerInnerSquare*(i+1)));
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1)), boardYPosition+(innerInnerSquare*i), boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*(i+1)));
						} else if(extremeBoard[0][i][j] == 'O') {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 2 row 1 column 2
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(extremeBoard[1][i][j] == 'X') {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i), boardXPosition+(innerInnerSquare*(j+1))+innerSquare, boardYPosition+(innerInnerSquare*(i+1)));
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1))+innerSquare, boardYPosition+(innerInnerSquare*i), boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*(i+1)));
						} else if(extremeBoard[1][i][j] == 'O') {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 3 row 1 column 3
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(extremeBoard[2][i][j] == 'X') {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i), boardXPosition+(innerInnerSquare*(j+1))+(innerSquare*2), boardYPosition+(innerInnerSquare*(i+1)));
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1))+(innerSquare*2), boardYPosition+(innerInnerSquare*i), boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*(i+1)));
						} else if(extremeBoard[2][i][j] == 'O') {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i), innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 4 row 2 column 1
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(extremeBoard[3][i][j] == 'X') {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+innerSquare, boardXPosition+(innerInnerSquare*(j+1)), boardYPosition+(innerInnerSquare*(i+1))+innerSquare);
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1)), boardYPosition+(innerInnerSquare*i)+innerSquare, boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*(i+1))+innerSquare);
						} else if(extremeBoard[3][i][j] == 'O') {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 5 row 2 column 2
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(extremeBoard[4][i][j] == 'X') {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+innerSquare, boardXPosition+(innerInnerSquare*(j+1))+innerSquare, boardYPosition+(innerInnerSquare*(i+1))+innerSquare);
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1))+innerSquare, boardYPosition+(innerInnerSquare*i)+innerSquare, boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*(i+1))+innerSquare);
						} else if(extremeBoard[4][i][j] == 'O') {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 6 row 2 column 3
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(extremeBoard[5][i][j] == 'X') {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+innerSquare, boardXPosition+(innerInnerSquare*(j+1))+(innerSquare*2), boardYPosition+(innerInnerSquare*(i+1))+innerSquare);
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1))+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+innerSquare, boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*(i+1))+innerSquare);
						} else if(extremeBoard[5][i][j] == 'O') {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+innerSquare, innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 7 row 3 column 1
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(extremeBoard[6][i][j] == 'X') {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), boardXPosition+(innerInnerSquare*(j+1)), boardYPosition+(innerInnerSquare*(i+1))+(innerSquare*2));
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1)), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*(i+1))+(innerSquare*2));
						} else if(extremeBoard[6][i][j] == 'O') {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 8 row 3 column 2
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(extremeBoard[7][i][j] == 'X') {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+(innerSquare*2), boardXPosition+(innerInnerSquare*(j+1))+innerSquare, boardYPosition+(innerInnerSquare*(i+1))+(innerSquare*2));
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1))+innerSquare, boardYPosition+(innerInnerSquare*i)+(innerSquare*2), boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*(i+1))+(innerSquare*2));
						} else if(extremeBoard[7][i][j] == 'O') {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j)+innerSquare, boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Inner 9 row 9 column 3
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(extremeBoard[8][i][j] == 'X') {
							g.setColor(Color.red);
							g.drawLine(boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), boardXPosition+(innerInnerSquare*(j+1))+(innerSquare*2), boardYPosition+(innerInnerSquare*(i+1))+(innerSquare*2));
							g.drawLine(boardXPosition+(innerInnerSquare*(j+1))+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*(i+1))+(innerSquare*2));
						} else if(extremeBoard[8][i][j] == 'O') {
							g.setColor(Color.blue);
							g.drawOval(boardXPosition+(innerInnerSquare*j)+(innerSquare*2), boardYPosition+(innerInnerSquare*i)+(innerSquare*2), innerInnerSquare, innerInnerSquare);
						}
					}
				}

				// Drawing of the elements
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(board[i][j] == 'X') {
							g2.setColor(Color.red);
							g2.setStroke(new BasicStroke(3));
							g2.drawLine(boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*i), boardXPosition+(innerSquare*(j+1)), boardYPosition+(innerSquare*(i+1)));
							g2.drawLine(boardXPosition+(innerSquare*(j+1)), boardYPosition+(innerSquare*i), boardXPosition+(innerSquare*j), boardYPosition+(innerSquare*(i+1)));
						} else if(board[i][j] == 'O') {
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
	} // End render() 

	public void clearBoard() { 
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				// Normal game clear
				board[i][j] = ' ';

				innerBoard[i][j] = false;
			}
		}

		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					extremeBoard[i][j][k] = ' ';
				}
			}
		}

		gameBegin = false;
		gameOver = false;
		xWinner = false;
		oWinner = false;

		firstMove = false;
	}

	public void checkWinner() {
		if(game.gameMode == 0 && game.numPlayers == 1) {
			// Normal game mode check
			if(gameOver == false) {
				if(board[0][0] == 'X' && board[0][1] == 'X' && board[0][2] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[1][0] == 'X' && board[1][1] == 'X' && board[1][2] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[2][0] == 'X' && board[2][1] == 'X' && board[2][2] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[0][0] == 'X' && board[1][0] == 'X' && board[2][0] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[0][1] == 'X' && board[1][1] == 'X' && board[2][1] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[0][2] == 'X' && board[1][2] == 'X' && board[2][2] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[0][2] == 'X' && board[1][1] == 'X' && board[2][0] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[0][0] == 'O' && board[0][1] == 'O' && board[0][2] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[1][0] == 'O' && board[1][1] == 'O' && board[1][2] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[2][0] == 'O' && board[2][1] == 'O' && board[2][2] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[0][0] == 'O' && board[1][0] == 'O' && board[2][0] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[0][1] == 'O' && board[1][1] == 'O' && board[2][1] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[0][2] == 'O' && board[1][2] == 'O' && board[2][2] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[0][0] == 'O' && board[1][1] == 'O' && board[2][2] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[0][2] == 'O' && board[1][1] == 'O' && board[2][0] == 'O') {
					oWinner = true;
					gameOver = true;
				}
			}
		}

		if(game.gameMode == 1 && game.numPlayers == 1) {

			if(gameOver == false) {
				for(int i = 0; i < 9; i++) {
					if(extremeBoard[i][0][0] == 'X' && extremeBoard[i][0][1] == 'X' && extremeBoard[i][0][2] == 'X') {
						alterBoard(i, 'X');
					} else if(extremeBoard[i][1][0] == 'X' && extremeBoard[i][1][1] == 'X' && extremeBoard[i][1][2] == 'X') {
						alterBoard(i, 'X');
					} else if(extremeBoard[i][2][0] == 'X' && extremeBoard[i][2][1] == 'X' && extremeBoard[i][2][2] == 'X') {
						alterBoard(i, 'X');
					} else if(extremeBoard[i][0][0] == 'X' && extremeBoard[i][1][0] == 'X' && extremeBoard[i][2][0] == 'X') {
						alterBoard(i, 'X');
					} else if(extremeBoard[i][0][1] == 'X' && extremeBoard[i][1][1] == 'X' && extremeBoard[i][2][1] == 'X') {
						alterBoard(i, 'X');
					} else if(extremeBoard[i][0][2] == 'X' && extremeBoard[i][1][2] == 'X' && extremeBoard[i][2][2] == 'X') {
						alterBoard(i, 'X');
					} else if(extremeBoard[i][0][0] == 'X' && extremeBoard[i][1][1] == 'X' && extremeBoard[i][2][2] == 'X') {
						alterBoard(i, 'X');
					} else if(extremeBoard[i][0][2] == 'X' && extremeBoard[i][1][1] == 'X' && extremeBoard[i][2][0] == 'X') {
						alterBoard(i, 'X');
					} else if(extremeBoard[i][0][0] == 'O' && extremeBoard[i][0][1] == 'O' && extremeBoard[i][0][2] == 'O') {
						alterBoard(i, 'O');
					} else if(extremeBoard[i][1][0] == 'O' && extremeBoard[i][1][1] == 'O' && extremeBoard[i][1][2] == 'O') {
						alterBoard(i, 'O');
					} else if(extremeBoard[i][2][0] == 'O' && extremeBoard[i][2][1] == 'O' && extremeBoard[i][2][2] == 'O') {
						alterBoard(i, 'O');
					} else if(extremeBoard[i][0][0] == 'O' && extremeBoard[i][1][0] == 'O' && extremeBoard[i][2][0] == 'O') {
						alterBoard(i, 'O');
					} else if(extremeBoard[i][0][1] == 'O' && extremeBoard[i][1][1] == 'O' && extremeBoard[i][2][1] == 'O') {
						alterBoard(i, 'O');
					} else if(extremeBoard[i][0][2] == 'O' && extremeBoard[i][1][2] == 'O' && extremeBoard[i][2][2] == 'O') {
						alterBoard(i, 'O');
					} else if(extremeBoard[i][0][0] == 'O' && extremeBoard[i][1][1] == 'O' && extremeBoard[i][2][2] == 'O') {
						alterBoard(i, 'O');
					} else if(extremeBoard[i][0][2] == 'O' && extremeBoard[i][1][1] == 'O' && extremeBoard[i][2][0] == 'O') {
						alterBoard(i, 'O');
					}
				}

				if(board[0][0] == 'X' && board[0][1] == 'X' && board[0][2] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[1][0] == 'X' && board[1][1] == 'X' && board[1][2] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[2][0] == 'X' && board[2][1] == 'X' && board[2][2] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[0][0] == 'X' && board[1][0] == 'X' && board[2][0] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[0][1] == 'X' && board[1][1] == 'X' && board[2][1] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[0][2] == 'X' && board[1][2] == 'X' && board[2][2] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[0][2] == 'X' && board[1][1] == 'X' && board[2][0] == 'X') {
					xWinner = true;
					gameOver = true;
				} else if(board[0][0] == 'O' && board[0][1] == 'O' && board[0][2] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[1][0] == 'O' && board[1][1] == 'O' && board[1][2] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[2][0] == 'O' && board[2][1] == 'O' && board[2][2] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[0][0] == 'O' && board[1][0] == 'O' && board[2][0] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[0][1] == 'O' && board[1][1] == 'O' && board[2][1] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[0][2] == 'O' && board[1][2] == 'O' && board[2][2] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[0][0] == 'O' && board[1][1] == 'O' && board[2][2] == 'O') {
					oWinner = true;
					gameOver = true;
				} else if(board[0][2] == 'O' && board[1][1] == 'O' && board[2][0] == 'O') {
					oWinner = true;
					gameOver = true;
				}
			}
		}
	}

	//
	//	This function will choose the third square if there is a possibility of winning.
	//
	public boolean checkCanWin(char player) {
		// First row
		if(board[0][0] == player && board[0][1] == player && board[0][2] == ' ') { board[0][2] = player; return true; } 
		else if(board[0][0] == player && board[0][1] == ' ' && board[0][2] == player) { board[0][1] = player; return true; }
		else if(board[0][0] == ' ' && board[0][1] == player && board[0][2] == player) { board[0][0] = player; return true; }
		
		// Second row 
		else if(board[1][0] == player && board[1][1] == player && board[1][2] == ' ') { board[1][2] = player; return true; }
		else if(board[1][0] == player && board[1][1] == ' ' && board[1][2] == player) { board[1][1] = player; return true; }
		else if(board[1][0] == ' ' && board[1][1] == player && board[1][2] == player) { board[1][0] = player; return true; }

		// Third row
		else if(board[2][0] == player && board[2][1] == player && board[2][2] == ' ') { board[2][2] = player; return true; } 
		else if(board[2][0] == player && board[2][1] == ' ' && board[2][2] == player) { board[2][1] = player; return true; }
		else if(board[2][0] == ' ' && board[2][1] == player && board[2][2] == player) { board[2][0] = player; return true; }

		// Left column
		else if(board[0][0] == player && board[1][0] == player && board[2][0] == ' ') { board[2][0] = player; return true; }
		else if(board[0][0] == player && board[1][0] == ' ' && board[2][0] == player) { board[1][0] = player; return true; }
		else if(board[0][0] == ' ' && board[1][0] == player && board[2][0] == player) { board[0][0] = player; return true; }

		// Middle column
		else if(board[0][1] == player && board[1][1] == player && board[2][1] == ' ') { board[2][1] = player; return true; }
		else if(board[0][1] == player && board[1][1] == ' ' && board[2][1] == player) { board[1][1] = player; return true; }
		else if(board[0][1] == ' ' && board[1][1] == player && board[2][1] == player) { board[0][1] = player; return true; }

		// Right column
		else if(board[0][2] == player && board[1][2] == player && board[2][2] == ' ') { board[2][2] = player; return true; }
		else if(board[0][2] == player && board[1][2] == ' ' && board[2][2] == player) { board[1][2] = player; return true; }
		else if(board[0][2] == ' ' && board[1][2] == player && board[2][2] == player) { board[0][2] = player; return true; }

		// Diagonal starting from top left
		else if(board[0][0] == player && board[1][1] == player && board[2][2] == ' ') { board[2][2] = player; return true; }
		else if(board[0][0] == player && board[1][1] == ' ' && board[2][2] == player) { board[1][1] = player; return true; }
		else if(board[0][0] == ' ' && board[1][1] == player && board[2][2] == player) { board[0][0] = player; return true; }

		// Diagonal starting from top right
		else if(board[0][2] == player && board[1][1] == player && board[2][0] == ' ') { board[2][0] = player; return true; }
		else if(board[0][2] == player && board[1][1] == ' ' && board[2][0] == player) { board[1][1] = player; return true; }
		else if(board[0][2] == ' ' && board[1][1] == player && board[2][0] == player) { board[0][2] = player; return true; }

		// ------------------------------------------------------------------------------------
		for(int i = 0; i < 9; i++) {
			// First row
			if(extremeBoard[i][0][0] == player && extremeBoard[i][0][1] == player && extremeBoard[i][0][2] == ' ') { extremeBoard[i][0][2] = player; return true; }
			else if(extremeBoard[i][0][0] == player && extremeBoard[i][0][1] == ' ' && extremeBoard[i][0][2] == player) { extremeBoard[i][0][1] = player; return true; }
			else if(extremeBoard[i][0][0] == ' ' && extremeBoard[i][0][1] == player && extremeBoard[i][0][2] == player) { extremeBoard[i][0][0] = player; return true; }
			
			// Second row 
			else if(extremeBoard[i][1][0] == player && extremeBoard[i][1][1] == player && extremeBoard[i][1][2] == ' ') { extremeBoard[i][1][2] = player; return true; }
			else if(extremeBoard[i][1][0] == player && extremeBoard[i][1][1] == ' ' && extremeBoard[i][1][2] == player) { extremeBoard[i][1][1] = player; return true; }
			else if(extremeBoard[i][1][0] == ' ' && extremeBoard[i][1][1] == player && extremeBoard[i][1][2] == player) { extremeBoard[i][1][0] = player; return true; }

			// Third row
			else if(extremeBoard[i][2][0] == player && extremeBoard[i][2][1] == player && extremeBoard[i][2][2] == ' ') { extremeBoard[i][2][2] = player; return true; } 
			else if(extremeBoard[i][2][0] == player && extremeBoard[i][2][1] == ' ' && extremeBoard[i][2][2] == player) { extremeBoard[i][2][1] = player; return true; }
			else if(extremeBoard[i][2][0] == ' ' && extremeBoard[i][2][1] == player && extremeBoard[i][2][2] == player) { extremeBoard[i][2][0] = player; return true; }

			// Left column
			else if(extremeBoard[i][0][0] == player && extremeBoard[i][1][0] == player && extremeBoard[i][2][0] == ' ') { extremeBoard[i][2][0] = player; return true; }
			else if(extremeBoard[i][0][0] == player && extremeBoard[i][1][0] == ' ' && extremeBoard[i][2][0] == player) { extremeBoard[i][1][0] = player; return true; }
			else if(extremeBoard[i][0][0] == ' ' && extremeBoard[i][1][0] == player && extremeBoard[i][2][0] == player) { extremeBoard[i][0][0] = player; return true; }

			// Middle column
			else if(extremeBoard[i][0][1] == player && extremeBoard[i][1][1] == player && extremeBoard[i][2][1] == ' ') { extremeBoard[i][2][1] = player; return true; }
			else if(extremeBoard[i][0][1] == player && extremeBoard[i][1][1] == ' ' && extremeBoard[i][2][1] == player) { extremeBoard[i][1][1] = player; return true; }
			else if(extremeBoard[i][0][1] == ' ' && extremeBoard[i][1][1] == player && extremeBoard[i][2][1] == player) { extremeBoard[i][0][1] = player; return true; }

			// Right column
			else if(extremeBoard[i][0][2] == player && extremeBoard[i][1][2] == player && extremeBoard[i][2][2] == ' ') { extremeBoard[i][2][2] = player; return true; }
			else if(extremeBoard[i][0][2] == player && extremeBoard[i][1][2] == ' ' && extremeBoard[i][2][2] == player) { extremeBoard[i][1][2] = player; return true; }
			else if(extremeBoard[i][0][2] == ' ' && extremeBoard[i][1][2] == player && extremeBoard[i][2][2] == player) { extremeBoard[i][0][2] = player; return true; }

			// Diagonal starting from top left
			else if(extremeBoard[i][0][0] == player && extremeBoard[i][1][1] == player && extremeBoard[i][2][2] == ' ') { extremeBoard[i][2][2] = player; return true; }
			else if(extremeBoard[i][0][0] == player && extremeBoard[i][1][1] == ' ' && extremeBoard[i][2][2] == player) { extremeBoard[i][1][1] = player; return true; }
			else if(extremeBoard[i][0][0] == ' ' && extremeBoard[i][1][1] == player && extremeBoard[i][2][2] == player) { extremeBoard[i][0][0] = player; return true; }

			// Diagonal starting from top right
			else if(extremeBoard[i][0][2] == player && extremeBoard[i][1][1] == player && extremeBoard[i][2][0] == ' ') { extremeBoard[i][2][0] = player; return true; }
			else if(extremeBoard[i][0][2] == player && extremeBoard[i][1][1] == ' ' && extremeBoard[i][2][0] == player) { extremeBoard[i][1][1] = player; return true; }
			else if(extremeBoard[i][0][2] == ' ' && extremeBoard[i][1][1] == player && extremeBoard[i][2][0] == player) { extremeBoard[i][0][2] = player; return true; }
		}

		// Nothing worked
		return false;
	}

	public void checkBoardFilled() {
		// Normal game over check
		// ----------------------
		int normalMoves = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j] == ' ' && board[i][j] == ' ') {
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

		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					if(extremeBoard[i][j][k] != ' ') {
						extremeMoves++;
					}
				}
			}
		}

		if(extremeMoves == 81) {
			gameOver = true;
		}
	} // End checkBoardFilled

	public void AdjustInnerBoard(int row, int column) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(i == row && j == column) {
					innerBoard[i][j] = true;
				} else {
					innerBoard[i][j] = false;
				}
			}
		}
	} // end AdjustInnerBoard

	public void changeBoardSelection(int number) {
		switch(number) {
			case 0: AdjustInnerBoard(0, 0); break;
			case 1: AdjustInnerBoard(0, 1); break;
			case 2: AdjustInnerBoard(0, 2); break;
			case 3: AdjustInnerBoard(1, 0); break;
			case 4: AdjustInnerBoard(1, 1); break;
			case 5: AdjustInnerBoard(1, 2); break;
			case 6: AdjustInnerBoard(2, 0); break;
			case 7: AdjustInnerBoard(2, 1); break;
			case 8: AdjustInnerBoard(2, 2); break;
		}
	} // end changeBoardSelection

	/* Changes the board number to true */
	public void alterBoard(int number, char letter) {
		switch(number) {
			case 0: board[0][0] = letter; break;
			case 1: board[0][1] = letter; break;
			case 2: board[0][2] = letter; break;
			case 3: board[1][0] = letter; break;
			case 4: board[1][1] = letter; break;
			case 5: board[1][2] = letter; break;
			case 6: board[2][0] = letter; break;
			case 7: board[2][1] = letter; break;
			case 8: board[2][2] = letter; break;
		}
	} // end alterBoard

	public int getInnerBoardNumber(int row, int column) {
		if(row == 0 && column == 0) { return 0; }
		else if(row == 0 && column == 1) { return 1; }
		else if(row == 0 && column == 2) { return 2; }
		else if(row == 1 && column == 0) { return 3; }
		else if(row == 1 && column == 1) { return 4; }
		else if(row == 1 && column == 2) { return 5; }
		else if(row == 2 && column == 0) { return 6; }
		else if(row == 2 && column == 1) { return 7; }
		else if(row == 2 && column == 2) { return 8; }
		else return 10;
	} // end getInnerBoardNumber

}

