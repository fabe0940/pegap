package pegap.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pegap.PegaPuzzle;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pegasus Puzzle";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new PegaPuzzle(), config);
	}
}
