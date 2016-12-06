package data;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Boot {

	public Boot() {
		Display.setTitle("CCG Game");
		try {
			Display.setDisplayMode(new DisplayMode(600, 400));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Boot();
	}
}
