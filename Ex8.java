import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

/**
 * @author Joshua Michael Waggoner (@rabbitfighter81) and Dylan Otto Krider 
 * Git Hub: github.com/rabbitfighter81/SimpleJOGLGame
 * 
 */
public class Ex8 extends Basic {

	public static void main(String[] args) {
		Ex8 app = new Ex8();
		app.start();
	}

	// Instance variables
	private ArrayList < Box > list; // all boxes, including player
	private Box playerBox; // convenience reference to player
	private double lambda;
	private int ammo;
	private long startTime;
	int frames;
	int shotFrames;
	private boolean started;
	private boolean lost;
	private boolean won;
	String state;

	// Final variables
	final double PLAYER_WIDTH = 4;
	final double PLAYER_HEIGHT = 4;
	static final double PLAYER_SPEED = 20;

	public Ex8() {

		// defines the actual size of the window.
		super(
			"Ex8 | Dylan & Josh | Controls: UP, DOWN, LEFT, RIGHT, SPACEBAR to shoot",
		800, 800, 60);

	} // end constructor

	public void init() {

		// load all the boxes:
		list = new ArrayList < Box > ();

		// xBounce=false; yBounce=false;
		startTime = (System.nanoTime() / 100000000) % 1000;

		// ===============hard coded boxes========================

		list.add(new Box(new Triple(50, 50, 0), PLAYER_WIDTH, PLAYER_HEIGHT,
		90, "player"));
		playerBox = list.get(0);
		playerBox.angle = 90;
		playerBox.Vector = new Triple(0, 1, 0);

		// bottom wall
		list.add(new Box(new Triple(50, 0.5, 0), 50, .5, 0, "wall"));

		// left wall
		list.add(new Box(new Triple(0.5, 50, 0), .5, 50, 0, "wall"));

		// right wall
		list.add(new Box(new Triple(99.5, 50, 0), .5, 50, 0, "wall"));

		// top wall
		list.add(new Box(new Triple(50, 99.5, 0), 50, .5, 0, "wall"));

		// add foods
		list.add(new Box(new Triple(20, 30, 0), 2, 2, 0, "food"));
		list.add(new Box(new Triple(30, 70, 0), 2, 2, 0, "food"));
		list.add(new Box(new Triple(70, 10, 0), 2, 2, 0, "food"));
		list.add(new Box(new Triple(45, 80, 0), 2, 2, 0, "food"));
		list.add(new Box(new Triple(90, 50, 0), 2, 2, 0, "food"));

		// add energy
		list.add(new Box(new Triple(25, 89, 0), 1, 1, 0, "energy"));
		list.add(new Box(new Triple(16, 42, 0), 1, 1, 0, "energy"));
		list.add(new Box(new Triple(88, 80, 0), 1, 1, 0, "energy"));
		list.add(new Box(new Triple(44, 66, 0), 1, 1, 0, "energy"));

		// add monsters
		list.add(new Box(new Triple(80, 70, 0), 3, 3, 0, "monster"));
		list.add(new Box(new Triple(30, 15, 0), 3, 3, 0, "monster"));
		list.add(new Box(new Triple(10, 84, 0), 3, 3, 0, "monster"));

		// frames
		frames = 0;
		// variables to keep track of states
		started = false;
		lost = false;
		won = false;
		// ammo
		ammo = 3;

		// set up projection once and for all:
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 100, 0, 100, -1, 1);
		// set the background color:
		glClearColor(0.08f, 0.08f, 0.08f, 0.5f);

	}

	public void display() {
		// clear the screen to background color and clear the depth buffer:
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		// switch to model view from this point on:
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// Different states for output
		if ((!started && !won) || (!started && !lost)) {
			state = "notStarted";
		}
		if (started) {
			state = "started";
		}
		if (lost) {
			state = "lost";
		}
		if (won) {
			state = "won";
		} // end else/if

		// draw the world:
		for (int k = 0; k < list.size(); k++) {
			list.get(k).draw(state, ammo);
		}

	} // end display

	public void processInputs() {
		Keyboard.poll();

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) playerBox.changeSpeed(1);

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) playerBox.changeSpeed(-1);

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) playerBox.turn(1);

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) playerBox.turn(-1);

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) playerBox.stop();

		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			if (started == false) {
				started = true;
				if (lost || won) {
					init();
				}
			}

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {

			if (ammo > 0) {
				if ((((System.nanoTime() / 100000000) % 1000) - startTime) > 2) {
					// System.out.println((((System.nanoTime()/100000000)%1000)-startTime));
					startTime = ((System.nanoTime() / 100000000) % 1000);

					double c = Math.cos(Math.toRadians(playerBox.angle));
					double s = Math.sin(Math.toRadians(playerBox.angle));
					Triple bullet = new Triple(playerBox.x + 8 * c, playerBox.y + 8 * s, 0);
					Box b3 = new Box(bullet, 1, .5, 0, "bullet");
					b3.speed = playerBox.speed + 2;
					// b3.changeSpeed(playerBox.speed+35);
					b3.angle = playerBox.angle;
					b3.updateVector();
					list.add(b3);
					ammo--;
				}
			}
		}

	} // end process inputs

	public void update() {
		int n = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).kind == "monster") {
				n++;
			}
		}

		if (!list.contains(playerBox)) {
			won = false;
			lost = true;
			started = false;
		}

		if (n == 0) {
			won = true;
			started = false;
			lost = false;
		}

		if (started) {

			frames++;
			frames %= 30 * 2; // when it gets to 4 seconds, reset frames

			if (!list.contains(playerBox)) {
				for (int j = list.size() - 1; j > 3; j--) {
					if (list.get(j).kind != "monster") {
						list.remove(j);
					} else {
						list.get(j).speed = 0;
						list.get(j).stop();
					}
				}
			}

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).kind == "monster") {
					list.get(i).shotFrames++;
				}
			}

			// System.out.println(System.nanoTime());
			check(lambda);

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getKill() == true) {
					// playerBox.stop();
					list.remove(i);
				}
			}

			@SuppressWarnings("unused")
			double elapsedTime = 0;
			lambda = 1;
			updateMonsters();
		}

	}

	private void moveAll(double lam) {

		if (playerBox.collideLam == lam) {
			if (playerBox.collide.kind == "wall") {
				playerBox.move(lam - .01);
				playerBox.collideLam = lam;

				playerBox.stop();
				playerBox.angle += 180;

			} else if (playerBox.collide.kind == "food") {
				playerBox.getRewardForFood();
				playerBox.collide.setKill(true);
				playerBox.collideLam = lam;

			} else if (playerBox.collide.kind == "bullet") {
				playerBox.collide.setKill(false);
				list.remove(playerBox);
			} else if (playerBox.collide.kind == "energy") {
				playerBox.collide.setKill(true);
				playerBox.collideLam = lam;
				ammo += 1;
			} else if (playerBox.collide.kind == "monster") {

				list.remove(playerBox);

				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).kind == "monster") {
						list.get(i).canFire = false;
						list.get(i).stop();
						list.get(i).speed = 0;
					}
				}
			}
		} else {

			playerBox.move(lam);
			playerBox.updateVector();

			if (frames == 5) {

				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).kind == "monster") {

						list.get(i).changeDirectionAccordingToPlayer(playerBox);

					} // end if
				} // end for
			} // end if

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).kind == "monster") {
					if (list.get(i).isInRange(playerBox)) {

						if (list.get(i).shotFrames > 30 * 8 && list.get(i).canFire) {
							list.get(i).fireShot(list);
							list.get(i).shotFired = true;
							list.get(i).shotFrames = 0;
						} // end if
					} // end if
				} // end if
			} // end for
		} // end if

		playerBox.collideLam = 0;

		for (int i = 1; i < list.size(); i++) {
			Box b = list.get(i);
			if (b.collideLam == lam) {
				b.move(lam - .01);
				if (b.kind != "bullet") {
					b.bounce();
					b.updateVector();
				} else {
					if (b.collide.kind != "bullet" && b.collide.kind != "player") b.setKill(true);
					if (b.collide.kind == "monster") {
						b.collide.setKill(true);
					}
					if (b.collide.kind == "bullet") {
						b.collide.setKill(true);
					}
				}
			} else {
				b.move(lam);
				b.updateVector();
			} // end if/else
		} // end for

	} // end moveAll()

	public void check(double lam) {

		double temp = 0;
		int num = 0;
		if (lam <= 0) {} else {
			while (num <= list.size() - 1) {
				temp = checkCollision(list.get(num), lam, num);
				num++;
				if (temp < lam) lam = temp;
			}
			moveAll(lam);
			check(lam - temp);
		}
	}

	public double findLambda(Box box, Box other, double lam) {
		double lambdaX = lam;
		double lambdaY = lam;
		double distW = (box.w + other.w + .001);
		double distH = (box.h + other.h + .001);
		double scalar = lam;

		// get the two lambdas when player.x=other.x
		lambdaX = (distW + box.x - other.x) / (other.Vector.x - box.Vector.x);
		double tempX = (-distW + box.x - other.x) / (other.Vector.x - box.Vector.x);

		// is the y within the bounds for either lambda?
		if ((Math.abs((other.Vector.scalarMult(lambdaX).y + other.y) - (box.Vector.scalarMult(lambdaX).y + box.y)) <= distH) && (lambdaX < lam && lambdaX > 0)) {
			box.xBounce = false;
			box.yBounce = true;
			scalar = lambdaX;
		} else if ((Math.abs((other.Vector.scalarMult(tempX).y + other.y) - (box.Vector.scalarMult(tempX).y + box.y)) <= distH) && (tempX < lam && tempX > 0)) {

			box.xBounce = false;
			box.yBounce = true;
			scalar = tempX;
		}

		lambdaY = (distH + box.y - other.y) / (other.Vector.y - box.Vector.y);

		double tempY = (-distH + box.y - other.y) / (other.Vector.y - box.Vector.y);

		if ((Math.abs((other.Vector.scalarMult(lambdaY).x + other.x) - (box.Vector.scalarMult(lambdaY).x + box.x)) <= distW) && (lambdaY < lam && lambdaY > 0)) {
			box.xBounce = true;
			box.yBounce = false;
			scalar = lambdaY;

		} else if ((Math.abs((other.Vector.scalarMult(tempY).x + other.x) - (box.Vector.scalarMult(tempY).x + box.x)) <= distW) && (tempY < lam && tempY > 0)) {
			box.xBounce = true;
			box.yBounce = false;
			scalar = tempY;
		}

		if (scalar < lam) {
			other.collide = box;
			box.collide = other;
			other.collideLam = scalar;
			box.collideLam = scalar;
			other.xBounce = box.xBounce;
			other.yBounce = box.yBounce;
		}
		return scalar;
	}

	public double checkCollision(Box box, double lam, int n) {
		double temp = 1;
		double temp2 = 1;
		box.xBounce = false;
		box.yBounce = false;
		temp2 = lam;
		for (int i = n + 1; i < list.size(); i++) {
			temp = findLambda(box, list.get(i), lam);
			if (temp < temp2) {
				temp2 = temp;
			}
		}
		return temp2;
	}

	public void updateMonsters() {
		for (int i = 0; i > list.size(); i++) {
			if (list.get(i).kind == "monster") {
				list.get(i).changeDirectionAccordingToPlayer(playerBox);
			}
		}
	}

}