package pegap;

import java.lang.*;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
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

		screen = new Display();
		game = new Model();
		input = new Input();
	}

	@Override
	public void dispose() {
		screen.dispose();
	}

	@Override
	public void render() {
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
		return false;
	}

	@Override
	public boolean keyUp (int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		return false;
	}

	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		Vector2 screenPos;
		Vector2 worldPos;

		screenPos = new Vector2(x, Display.WINDOW_HEIGHT - y);
		screenPos.x -= screen.offset.x;
		screenPos.y -= screen.offset.y;

		worldPos = Display.screenToWorld(screenPos);
		worldPos.x = (int) (worldPos.x > 0f ? Math.floor(worldPos.x) : Math.floor(worldPos.x));
		worldPos.y = (int) (worldPos.y > 0f ? Math.floor(worldPos.y) : Math.floor(worldPos.y));

		Gdx.app.debug("PegaPuzzle:touchDown", "(" + x + "," + y + ") --> (" + screenPos.x + "," + screenPos.y + ") --> (" + worldPos.x + "," + worldPos.y + ")");

		if(button == Buttons.LEFT) {
			input.clicks.add(worldPos);
		}

		return true;
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
