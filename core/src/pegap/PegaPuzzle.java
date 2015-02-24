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
	private Input screenInput;
	private Input modelInput;
	private Model game;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.input.setInputProcessor(this);

		screen = new Display();
		game = new Model();
		screenInput = new Input();
		modelInput = new Input();
	}

	@Override
	public void dispose() {
		screen.dispose();
	}

	@Override
	public void render() {
		screen.update(screenInput);
		game.update(modelInput);

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
		if(keycode == Keys.K) screenInput.scrollUp = true;
		if(keycode == Keys.J) screenInput.scrollDown = true;
		if(keycode == Keys.H) screenInput.scrollLeft = true;
		if(keycode == Keys.L) screenInput.scrollRight = true;

		return true;
	}

	@Override
	public boolean keyUp (int keycode) {
		if(keycode == Keys.K) screenInput.scrollUp = false;
		if(keycode == Keys.J) screenInput.scrollDown = false;
		if(keycode == Keys.H) screenInput.scrollLeft = false;
		if(keycode == Keys.L) screenInput.scrollRight = false;

		return true;
	}

	@Override
	public boolean keyTyped (char character) {
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
