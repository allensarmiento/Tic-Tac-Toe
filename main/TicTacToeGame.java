package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;
import java.util.Random;

// This is the main class where everything is handled
public class TicTacToeGame extends Canvas implements Runnable {

	public static final int WIDTH = 700, //WIDTH = 650,
							HEIGHT = WIDTH/12 * 9 + 10;

	// Game is run through this thread
	private Thread thread;
	private boolean running = false;

	public static boolean paused = false;
	
	// Game selection to run
	public int gameMode = 0; // 0 for normal, 1 for extreme
	public int difficulty = 0; // 0 for easy, 1 for medium, 2 for hard
	public int numPlayers = 0; // number of players
	public boolean xTurn = false, oTurn = false;
	public boolean xPlayer = false, oPlayer = false; // This is used when playing against a comp
	public boolean gameOver = false;

	private Random r;
	private Handler handler;
	private MainMenu mainMenu;
	private GameInterface gameInterface;
	private EasyComputer easyComputer;
	private MediumComputer mediumComputer;

	public enum GAMESTATE { 
							Menu, 
							Player, 
							Difficulty,
							Icon,
							Icon2,
							Help, 
							Game,
							End
						};
	public static GAMESTATE gameState = GAMESTATE.Menu;

	public TicTacToeGame() {

		handler = new Handler();

		r = new Random();

		mainMenu = new MainMenu(this, gameInterface, easyComputer, mediumComputer, handler);
		gameInterface = new GameInterface(this, mainMenu, handler);
		easyComputer = new EasyComputer(this, mainMenu, handler);
		mediumComputer = new MediumComputer(this, mainMenu, handler);
		this.addMouseListener(mainMenu);
		this.addMouseListener(gameInterface);
		this.addMouseListener(easyComputer);
		this.addMouseListener(mediumComputer);

		new Window(WIDTH, HEIGHT, "Extreme Tic Tac Toe", this);
	}

	// Starting the thread
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	// Check for an error
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		// Game Loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}

			if(running)
				render();
			frames++;

			if(System.currentTimeMillis()-timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		if(gameState == GAMESTATE.Game) {
			if(!paused) {
				gameInterface.tick();
				handler.tick();
			}
		} else if(gameState == GAMESTATE.Menu || 
			gameState == GAMESTATE.Player ||
			gameState == GAMESTATE.Difficulty || 
			gameState == GAMESTATE.Icon || gameState == GAMESTATE.Icon2 ||
			gameState == GAMESTATE.End) {
			mainMenu.tick();
			handler.tick();
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3); // This creates 3 buffers
			return;
		}

		Graphics g = bs.getDrawGraphics();

		// Window Display
		g.setColor(Color.blue);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Displays what state we are in
		if(gameState == GAMESTATE.Game) {
			gameInterface.render(g);
			easyComputer.render(g);
			mediumComputer.render(g);
			handler.render(g);
		} else if(gameState == GAMESTATE.Menu || 
				  gameState == GAMESTATE.Player || 
				  gameState == GAMESTATE.Difficulty || 
				  gameState == GAMESTATE.Icon || gameState == GAMESTATE.Icon2 ||
				  gameState == GAMESTATE.Help) {
			mainMenu.render(g);
			handler.render(g);
		}

		g.dispose();
		bs.show();
	}

	public static float clamp(float var, float min, float max) {
		if(var >= max) return var = max;
		else if(var <= min) return var = min;
		else return var;
	}

	public static void main(String args[]) {
		new TicTacToeGame();
	}
}