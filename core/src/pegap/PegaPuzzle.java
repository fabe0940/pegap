package pegap;

import java.lang.*;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import pegap.Display;
import pegap.Input;
import pegap.Model;

public class PegaPuzzle implements ApplicationListener, InputProcessor {
	private Display screen;
	private Input input;
	private Model game;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.input.setInputProcessor(this);

		game = new Model();
		screen = new Display();
		input = new Input();
	}

	@Override
	public void dispose() {
		screen.dispose();
	}

	@Override
	public void render() {
		screen.update(input);
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

	@Override
	public boolean keyDown (int keycode) {
		if(keycode == Keys.K) input.scrollNorth = true;
		if(keycode == Keys.J) input.scrollSouth = true;
		if(keycode == Keys.H) input.scrollWest = true;
		if(keycode == Keys.L) input.scrollEast = true;

		return true;
	}

	@Override
	public boolean keyUp (int keycode) {
		if(keycode == Keys.K) input.scrollNorth = false;
		if(keycode == Keys.J) input.scrollSouth = false;
		if(keycode == Keys.H) input.scrollWest = false;
		if(keycode == Keys.L) input.scrollEast = false;

		return true;
	}

	@Override
	public boolean keyTyped (char character) {
		if(character == 'q') Gdx.app.exit();

		if(character == 'y') {
			input.move = true;
			input.moveNorthwest = true;
		}
		if(character == 'u') {
			input.move = true;
			input.moveNortheast = true;
		}
		if(character == 'b') {
			input.move = true;
			input.moveSouthwest = true;
		}
		if(character == 'n') {
			input.move = true;
			input.moveSoutheast = true;
		}

		return false;
	}

	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved (int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled (int amount) {
		return false;
	}
}
