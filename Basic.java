import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
/**
 * Framework to be extended to make desired graphical, event-driven application
 * 
 * Usage: User should make a constructor that calls super and initializes things
 * that should happen before Display is created and override the following:
 * 
 * - init: initialize things that happen after Display is created -
 * - processInputs: respond to input events 
 * - update: update instance variables to
 *   simulate advancing time and/or response to inputs 
 * - display: draw graphics
 *   showing current state of things
 * 
 * @author rabbitfighter81 and memekiller
 *
 */
public class Basic {

	// test this basic application
	public static void main(String[] args) {

	} // main

	private int frameNumber; // total number of frames displayed
	private int fps; // target fps
	private int pixelWidth, pixelHeight; // pixel dimension of drawing area
	private boolean running; // is the application running
	private boolean closeable; // is the window manually closeable
	private boolean resizeable; // is the window manually resizable

	/*
	 * Construct basic application with given title, pixel width and height of
	 * drawing area, and frames per second
	 */
	public Basic(String title, int pw, int ph, int fps) {
		this.setTitle(title);
		this.setFrameNumber(0);
		this.setFps(fps);
		this.setPixelWidth(pw);
		this.setPixelHeight(ph);
		this.setRunning(false);
		this.setCloseable(true);
		this.setResizeable(false);
	}

	public void start() {
		/*
		 * Create the window
		 */
		try {
			Display.setTitle(title);
			Display.setVSyncEnabled(true);
			Display.setDisplayMode(new DisplayMode(pixelWidth, pixelHeight));
			Display.create();
		} catch (LWJGLException e) {
			System.out.println("Basic constructor failed to create window");
			e.printStackTrace();
			System.exit(1);
		}

		init();

		running = true;

		while (running && (!closeable || !Display.isCloseRequested())) {
			frameNumber++;

			/*
			 * These three methods should be overridden by extending
			 * application:
			 */
			init();
			processInputs();
			update();
			display();

			Display.update();
			Display.sync(fps);
		}

		Display.destroy();

	} // start

	// halt the application under program control
	protected void halt() {
		running = false;
	}

	/*
	 * Methods to be overridden
	 */

	protected void init() {}

	protected void processInputs() {}

	protected void update() {}

	protected void display() {}

	/***** Getters/Setters *****/

	// instance variables
	private String title; // title of the application

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public int getPixelWidth() {
		return pixelWidth;
	}

	public void setPixelWidth(int pixelWidth) {
		this.pixelWidth = pixelWidth;
	}

	public int getPixelHeight() {
		return pixelHeight;
	}

	public void setPixelHeight(int pixelHeight) {
		this.pixelHeight = pixelHeight;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isCloseable() {
		return closeable;
	}

	public void setCloseable(boolean closeable) {
		this.closeable = closeable;
	}

	public boolean isResizeable() {
		return resizeable;
	}

	public void setResizeable(boolean resizeable) {
		this.resizeable = resizeable;
	}

} // EOF