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

	private void  processInput() {
		Vector2 mousePos;

		mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
		if(mousePos.y < Display.SCROLL_BAND) {
			screenInput.scrollUp = true;
		}
		if(mousePos.y > (Display.WINDOW_HEIGHT - Display.SCROLL_BAND)) {
			screenInput.scrollDown = true;
		}
		if(mousePos.x < Display.SCROLL_BAND) {
			screenInput.scrollLeft = true;
		}
		if(mousePos.x > (Display.WINDOW_WIDTH - Display.SCROLL_BAND)) {
			screenInput.scrollRight = true;
		}
	}

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
		processInput();
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
		if(keycode == Keys.UP) screenInput.scrollUp = true;
		if(keycode == Keys.DOWN) screenInput.scrollDown = true;
		if(keycode == Keys.LEFT) screenInput.scrollLeft = true;
		if(keycode == Keys.RIGHT) screenInput.scrollRight = true;

		return true;
	}

	@Override
	public boolean keyUp (int keycode) {
		if(keycode == Keys.UP) screenInput.scrollUp = false;
		if(keycode == Keys.DOWN) screenInput.scrollDown = false;
		if(keycode == Keys.LEFT) screenInput.scrollLeft = false;
		if(keycode == Keys.RIGHT) screenInput.scrollRight = false;

		return true;
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

		if((screenPos.y < Display.UI_SIDE_HEIGHT && Math.abs((Display.WINDOW_WIDTH / 2) - screenPos.x) > (Display.UI_CENTER_WIDTH / 2)) || (screenPos.y < Display.UI_CENTER_HEIGHT)) {
			if(button == Buttons.LEFT) {
				screenInput.clicks.add(screenPos);
			}
		} else {
			screenPos.x -= screen.offset.x;
			screenPos.y -= screen.offset.y;

			worldPos = Display.screenToWorld(screenPos);
			worldPos.x = (int) Math.floor(worldPos.x);
			worldPos.y = (int) Math.floor(worldPos.y);

			if(button == Buttons.LEFT) {
				modelInput.clicks.add(worldPos);
			}
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
