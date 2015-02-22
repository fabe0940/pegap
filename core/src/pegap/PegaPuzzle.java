package pegap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import pegap.Display;
import pegap.Input;
import pegap.Model;

public class PegaPuzzle implements ApplicationListener {
	private Display screen;
	private Input input;
	private Model game;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		screen = new Display();
		game = new Model();
	}

	@Override
	public void dispose() {
		screen.dispose();
	}

	@Override
	public void render() {
		input = new Input();

		game.update(input);
		screen.render(game);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
