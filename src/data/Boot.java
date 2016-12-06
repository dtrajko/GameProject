package data;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

import static helpers.Artist.*;

public class Boot {

	public Boot() {

		BeginSession();

		float x = 100;
		float y = 100;
		float width = 50;
		float height = 50;

		while(!Display.isCloseRequested()) {

			DrawQuad(50, 50, 100, 100);
			DrawQuad(150, 150, 100, 100);

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}

	public static void main(String[] args) {
		new Boot();
	}
}
