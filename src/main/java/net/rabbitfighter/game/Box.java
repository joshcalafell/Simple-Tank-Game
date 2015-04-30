package net.rabbitfighter.game;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

/**
 * @author Joshua Michael Waggoner (@rabbitfighter81) and Dylan Otto Krider 
 * Git Hub: github.com/rabbitfighter81/SimpleJOGLGame
 * 
 */
public class Box {
	private static double SPEEDCHANGEAMOUNT = .05;
	private static double ANGLECHANGEAMOUNT = 5;
	public double MAX_SPEED = .20;
	public String kind;
	public double x; // location of the box---its center point
	public double y;
	public double w, h; // half-width, half-height of the box
	public double angle; // angle of the velocity vector in degrees
	public double speed; // magnitude of the velocity vector
	static final double T = .001;
	private Triple trip;
	private boolean kill;
	public Triple Vector;
	public Box collide;
	public boolean xBounce = false;
	public boolean yBounce = false;
	public double collideLam = 0;
	boolean shotFired;
	public int shotFrames;
	public boolean canFire;

	// list.add(new Box(50, 50, 5, 5, 90, "player"));
	public Box(Triple tripIn, double wIn, double hIn, double angleIn,
	String kindIn) {
		x = tripIn.x;
		y = tripIn.y;
		w = wIn;
		h = hIn;
		trip = tripIn;
		speed = 0;
		this.kill = false;
		Vector = new Triple(0, 0, 0);
		kind = kindIn;
		if (kind.equals("monster")) {
			speed = .07f;
		}
		angle = angleIn;
		shotFired = false;
		shotFrames = 0;
		canFire = true;
	} // end constructor

	public void move(double Time) {

		if (angle < 0) {
			angle += 360;
		}

		// update x and y
		if (kind != "wall") {
			trip = trip.add(Vector.scalarMult(Time));
			x = trip.x;
			y = trip.y;
		}

	} // end move

	public Triple getPosition() {
		return new Triple(x, y, 0);
	}

	public Triple updateVector() {
		return Vector = new Triple(
		speed * Math.cos(Math.toRadians(this.angle)), speed * Math.sin(Math.toRadians(this.angle)), 0);
	}

	public void draw(String state, int ammo) {

		double c, s;
		// variables to handle sin and soc easily
		c = Math.cos(Math.toRadians(angle));
		s = Math.sin(Math.toRadians(angle));

		// determine box color
		if (kind.equals("player")) glColor3f(0, 0, 1);
		else if (kind.equals("monster")) glColor3f(1, 0, 0);
		else if (kind.equals("food")) glColor3f(1, 1, 0);
		else if (kind.equals("wall")) glColor3f(0.5f, 0.5f, 0.5f);
		else if (kind.equals("energy")) glColor3f(.9f, .8f, .8f);
		else if (kind.equals("bullet")) glColor3f(1, 1, 1);

		// openGlStuff
		glPushMatrix(); {
			glTranslated(x, y, 0);
			if (kind == "bullet") {

				glRotated(this.angle, 0, 0, 1);
			}
			glScaled(w, h, 1);
			// glEnable(GL_TEXTURE_2D);

			glBegin(GL_POLYGON); {

				glVertex3d(-1, -1, 0);
				glVertex3d(1, -1, 0);
				glVertex3d(1, 1, 0);
				glVertex3d(-1, 1, 0);
			}
			glEnd();
		}
		glPopMatrix();

		// add the gun barrel (the line in the middle of the player)
		// if (kind.equals("player")) {
		glColor3f(1, 1, 1);

		glPushMatrix(); {
			if (kind == "player" || kind == "monster") {
				// cleverly put it on top by setting z to .1
				glTranslated(x, y, .1);
				glScaled(w, h, 1);

				glBegin(GL_LINES); {
					glVertex3d(0, 0, 0);
					glVertex3d(c, s, 0);
				}
				glEnd();
			}
		}
		glPopMatrix();

		if (state == "notStarted") {
			glPushMatrix(); {
				glColor3f(0, 0, 1);
				glScaled(.2, .2, 1);
				drawString("Press Enter To Start", 175, 300);
			}
			glPopMatrix();
		}

		if (state == "won") {
			glPushMatrix(); {
				glColor3f(0, 0, 1);
				glScaled(.2, .2, 1);
				drawString("You Won... Press Enter To Restart", 140, 300);
			}
			glPopMatrix();
		}

		if (state == "lost") {
			glPushMatrix(); {
				glColor3f(0, 0, 1);
				glScaled(.2, .2, 1);
				drawString("You Lost... Press Enter To Restart", 130, 300);
			}
			glPopMatrix();
		}

		glPushMatrix(); {
			glColor3f(0, 0, 1);
			glScaled(.2, .2, 1);
			drawString("Ammo X " + ammo, 10, 476);
		}
		glPopMatrix();

		// }
	} // end draw

	// change the speed of the player
	public void changeSpeed(double d) {
		speed += d * SPEEDCHANGEAMOUNT;
		if (speed >= MAX_SPEED) speed = MAX_SPEED;
		if (speed <= -MAX_SPEED) speed = -MAX_SPEED;
		// updateVector();
	}

	public void getRewardForFood() {
		MAX_SPEED += .10;
	}

	// turn the player by a given angle amount
	public void turn(int amount) {
		angle += amount * ANGLECHANGEAMOUNT;
		updateVector();
	}

	// stop the player
	public void stop() {
		speed = 0;
		Vector = new Triple(0, 0, 0);
		updateVector();
	}

	public boolean determineIfWon(ArrayList < Box > list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).kind.compareToIgnoreCase("food") == 0) {
				return false;
			}
		}
		return true;
	}

	public void setKill(boolean b) {
		this.kill = b;
	}

	public boolean getKill() {
		return this.kill;
	}

	public void bounce() {

		if (kind != "player") {
			angle = Math.toDegrees(Vector.findAngle());
			// System.out.println(angle);
		}

		if (xBounce == true) {
			angle = -angle;
		}

		if (yBounce == true) {
			angle = 180 - angle;
		}
	}

	public void changeDirectionAccordingToPlayer(Box playerBox) {

		double distY, distX;

		if (this.x > playerBox.x) {
			distX = this.x - playerBox.x;

		} else {
			distX = playerBox.x - this.x;
		}

		if (this.y > playerBox.y) {
			distY = this.y - playerBox.y;

		} else {
			distY = playerBox.y - this.y;
		}

		if (distX < distY) {
			if (this.y > playerBox.y) {
				this.angle = 270;
			} else {
				this.angle = 90;
			}
		} else {
			if (this.x > playerBox.x) {
				this.angle = 180;
			} else {
				this.angle = 0;
			}
		}
	}

	public void fireShot(ArrayList < Box > list) {
		double c = Math.cos(Math.toRadians(this.angle));
		double s = Math.sin(Math.toRadians(this.angle));
		Triple bullet = new Triple(this.x + 8 * c, this.y + 8 * s, 0);
		Box b3 = new Box(bullet, 1, .5, 0, "bullet");
		b3.speed = this.speed + .5;
		// b3.changeSpeed(playerBox.speed+35);
		b3.angle = this.angle;
		b3.updateVector();
		list.add(b3);
	}

	public boolean isInRange(Box playerBox) {

		if (this.angle == 0 || this.angle == 180) {
			if (this.y > playerBox.y) {
				if ((this.y - playerBox.y) < 10) {
					return true;
				}
			}
			if (this.y < playerBox.y) {
				if ((playerBox.y - this.y) < 10) {
					return true;
				}
			}
		}
		if (this.angle == 270 || this.angle == 90) {
			if (this.x > playerBox.x) {
				if ((this.x - playerBox.x) < 10) {
					return true;
				}
			}
			if (this.x < playerBox.x) {
				if ((playerBox.x - this.x) < 10) {
					return true;
				}
			}
		}
		return false;
	} // end range

	public static void drawString(String s, int x, int y) {
		int startX = x;
		glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_POINTS);
		for (char c: s.toLowerCase().toCharArray()) {
			if (c == 'a') {
				for (int i = 0; i < 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == 'b') {
				for (int i = 0; i < 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 4);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 7, y + 5);
				GL11.glVertex2f(x + 7, y + 7);
				GL11.glVertex2f(x + 7, y + 6);

				GL11.glVertex2f(x + 7, y + 1);
				GL11.glVertex2f(x + 7, y + 2);
				GL11.glVertex2f(x + 7, y + 3);
				x += 8;
			} else if (c == 'c') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 6, y + 2);

				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 7);

				x += 8;
			} else if (c == 'd') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 6, y + 2);
				GL11.glVertex2f(x + 6, y + 3);
				GL11.glVertex2f(x + 6, y + 4);
				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 7);

				x += 8;
			} else if (c == 'e') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 0);
					GL11.glVertex2f(x + i, y + 8);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == 'f') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == 'g') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 6, y + 2);
				GL11.glVertex2f(x + 6, y + 3);
				GL11.glVertex2f(x + 5, y + 3);
				GL11.glVertex2f(x + 7, y + 3);

				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 7);

				x += 8;
			} else if (c == 'h') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == 'i') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 3, y + i);
				}
				for (int i = 1; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 0);
					GL11.glVertex2f(x + i, y + 8);
				}
				x += 7;
			} else if (c == 'j') {
				for (int i = 1; i <= 8; i++) {
					GL11.glVertex2f(x + 6, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 0);
				}
				GL11.glVertex2f(x + 1, y + 3);
				GL11.glVertex2f(x + 1, y + 2);
				GL11.glVertex2f(x + 1, y + 1);
				x += 8;
			} else if (c == 'k') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				GL11.glVertex2f(x + 6, y + 8);
				GL11.glVertex2f(x + 5, y + 7);
				GL11.glVertex2f(x + 4, y + 6);
				GL11.glVertex2f(x + 3, y + 5);
				GL11.glVertex2f(x + 2, y + 4);
				GL11.glVertex2f(x + 2, y + 3);
				GL11.glVertex2f(x + 3, y + 4);
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 5, y + 2);
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 7, y);
				x += 8;
			} else if (c == 'l') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
				}
				x += 7;
			} else if (c == 'm') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				GL11.glVertex2f(x + 3, y + 6);
				GL11.glVertex2f(x + 2, y + 7);
				GL11.glVertex2f(x + 4, y + 5);

				GL11.glVertex2f(x + 5, y + 6);
				GL11.glVertex2f(x + 6, y + 7);
				GL11.glVertex2f(x + 4, y + 5);
				x += 8;
			} else if (c == 'n') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				GL11.glVertex2f(x + 2, y + 7);
				GL11.glVertex2f(x + 2, y + 6);
				GL11.glVertex2f(x + 3, y + 5);
				GL11.glVertex2f(x + 4, y + 4);
				GL11.glVertex2f(x + 5, y + 3);
				GL11.glVertex2f(x + 6, y + 2);
				GL11.glVertex2f(x + 6, y + 1);
				x += 8;
			} else if (c == 'o' || c == '0') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 0);
				}
				x += 8;
			} else if (c == 'p') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 4);
				}
				GL11.glVertex2f(x + 6, y + 7);
				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 6, y + 6);
				x += 8;
			} else if (c == 'q') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
					if (i != 1) GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					if (i != 6) GL11.glVertex2f(x + i, y + 0);
				}
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 5, y + 2);
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 7, y);
				x += 8;
			} else if (c == 'r') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 4);
				}
				GL11.glVertex2f(x + 6, y + 7);
				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 6, y + 6);

				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 5, y + 2);
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 7, y);
				x += 8;
			} else if (c == 's') {
				for (int i = 2; i <= 7; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 1, y + 7);
				GL11.glVertex2f(x + 1, y + 6);
				GL11.glVertex2f(x + 1, y + 5);
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 4);
					GL11.glVertex2f(x + i, y);
				}
				GL11.glVertex2f(x + 7, y + 3);
				GL11.glVertex2f(x + 7, y + 2);
				GL11.glVertex2f(x + 7, y + 1);
				GL11.glVertex2f(x + 1, y + 1);
				GL11.glVertex2f(x + 1, y + 2);
				x += 8;
			} else if (c == 't') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 4, y + i);
				}
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				x += 7;
			} else if (c == 'u') {
				for (int i = 1; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 0);
				}
				x += 8;
			} else if (c == 'v') {
				for (int i = 2; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 6, y + i);
				}
				GL11.glVertex2f(x + 2, y + 1);
				GL11.glVertex2f(x + 5, y + 1);
				GL11.glVertex2f(x + 3, y);
				GL11.glVertex2f(x + 4, y);
				x += 7;
			} else if (c == 'w') {
				for (int i = 1; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				GL11.glVertex2f(x + 2, y);
				GL11.glVertex2f(x + 3, y);
				GL11.glVertex2f(x + 5, y);
				GL11.glVertex2f(x + 6, y);
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + 4, y + i);
				}
				x += 8;
			} else if (c == 'x') {
				for (int i = 1; i <= 7; i++)
				GL11.glVertex2f(x + i, y + i);
				for (int i = 7; i >= 1; i--)
				GL11.glVertex2f(x + i, y + 8 - i);
				x += 8;
			} else if (c == 'y') {
				GL11.glVertex2f(x + 4, y);
				GL11.glVertex2f(x + 4, y + 1);
				GL11.glVertex2f(x + 4, y + 2);
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 4, y + 4);

				GL11.glVertex2f(x + 3, y + 5);
				GL11.glVertex2f(x + 2, y + 6);
				GL11.glVertex2f(x + 1, y + 7);
				GL11.glVertex2f(x + 1, y + 8);

				GL11.glVertex2f(x + 5, y + 5);
				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 7, y + 7);
				GL11.glVertex2f(x + 7, y + 8);
				x += 8;
			} else if (c == 'z') {
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + i);
				}
				GL11.glVertex2f(x + 6, y + 7);
				x += 8;
			} else if (c == '1') {
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 1; i <= 8; i++) {
					GL11.glVertex2f(x + 4, y + i);
				}
				GL11.glVertex2f(x + 3, y + 7);
				x += 8;
			} else if (c == '2') {
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 1, y + 7);
				GL11.glVertex2f(x + 1, y + 6);

				GL11.glVertex2f(x + 6, y + 7);
				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 5, y + 4);
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 3, y + 2);
				GL11.glVertex2f(x + 2, y + 1);
				x += 8;
			} else if (c == '3') {
				for (int i = 1; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 6, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == '4') {
				for (int i = 2; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 7; i++) {
					GL11.glVertex2f(x + i, y + 1);
				}
				for (int i = 0; i <= 4; i++) {
					GL11.glVertex2f(x + 4, y + i);
				}
				x += 8;
			} else if (c == '5') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				for (int i = 4; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				GL11.glVertex2f(x + 1, y + 1);
				GL11.glVertex2f(x + 2, y);
				GL11.glVertex2f(x + 3, y);
				GL11.glVertex2f(x + 4, y);
				GL11.glVertex2f(x + 5, y);
				GL11.glVertex2f(x + 6, y);

				GL11.glVertex2f(x + 7, y + 1);
				GL11.glVertex2f(x + 7, y + 2);
				GL11.glVertex2f(x + 7, y + 3);

				GL11.glVertex2f(x + 6, y + 4);
				GL11.glVertex2f(x + 5, y + 4);
				GL11.glVertex2f(x + 4, y + 4);
				GL11.glVertex2f(x + 3, y + 4);
				GL11.glVertex2f(x + 2, y + 4);
				x += 8;
			} else if (c == '6') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 4);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 7, y + 1);
				GL11.glVertex2f(x + 7, y + 2);
				GL11.glVertex2f(x + 7, y + 3);
				GL11.glVertex2f(x + 6, y + 4);
				x += 8;
			} else if (c == '7') {
				for (int i = 0; i <= 7; i++)
				GL11.glVertex2f(x + i, y + 8);
				GL11.glVertex2f(x + 7, y + 7);
				GL11.glVertex2f(x + 7, y + 6);

				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 5, y + 4);
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 3, y + 2);
				GL11.glVertex2f(x + 2, y + 1);
				GL11.glVertex2f(x + 1, y);
				x += 8;
			} else if (c == '8') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 0);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == '9') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 5; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 0);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				GL11.glVertex2f(x + 1, y + 0);
				x += 8;
			} else if (c == '.') {
				GL11.glVertex2f(x + 1, y);
				x += 2;
			} else if (c == ',') {
				GL11.glVertex2f(x + 1, y);
				GL11.glVertex2f(x + 1, y + 1);
				x += 2;
			} else if (c == '\n') {
				y -= 10;
				x = startX;
			} else if (c == ' ') {
				x += 8;
			}
		}
		GL11.glEnd();
	}

} //EOF
