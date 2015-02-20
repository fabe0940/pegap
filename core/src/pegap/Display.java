package pegap;

import java.util.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import pegap.Model;
import pegap.Tile;

class Display {
	private static final int TILE_WIDTH = 128;
	private static final int TILE_HEIGHT = 64;

	private static Vector2 worldToScreen(Vector2 pos) {
		Vector2 res = new Vector2();

		res.x = ((Display.TILE_WIDTH / 2) * pos.x) - ((Display.TILE_WIDTH / 2) * pos.y);
		res.y = ((Display.TILE_HEIGHT / 2) * pos.x) + ((Display.TILE_HEIGHT / 2) * pos.y);

		return res;
	}

	private static Vector2 screenToWorld(Vector2 pos) {
		Vector2 res = new Vector2();

		res.x = (pos.x / Display.TILE_WIDTH) + (pos.y / Display.TILE_HEIGHT);
		res.y = (pos.x / (-1 * Display.TILE_WIDTH)) + (pos.y / Display.TILE_HEIGHT);

		return res;
	}

	private SpriteBatch batch;
	private Sprite sprite;
	private Texture tex;

	Display() {
		batch = new SpriteBatch();
	}

	public void render(Model m) {
		List<Tile> map = m.getMap();
		Tile t;
		Vector2 pos;
 
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		for(int i = 0; i < map.size(); i++) {
			t = map.get(i);
			pos = Display.worldToScreen(t.getPos());
			tex = new Texture(Gdx.files.internal("img/tiles/" + String.format("%04d", t.getType()) + ".png"));
			sprite = new Sprite(tex);
			sprite.setPosition(pos.x, pos.y);

			sprite.draw(batch);
		}

		batch.end();
	}
}
