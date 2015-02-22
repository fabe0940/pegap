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
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int TILE_WIDTH = 128;
	public static final int TILE_HEIGHT = 64;

	public static Vector2 worldToScreen(Vector2 pos) {
		Vector2 res = new Vector2();

		res.x = ((Display.TILE_WIDTH / 2) * pos.x) - ((Display.TILE_WIDTH / 2) * pos.y);
		res.y = ((Display.TILE_HEIGHT / 2) * pos.x) + ((Display.TILE_HEIGHT / 2) * pos.y);

		return res;
	}

	public static Vector2 screenToWorld(Vector2 pos) {
		Vector2 res = new Vector2();

		res.x = (pos.x / Display.TILE_WIDTH) + (pos.y / Display.TILE_HEIGHT);
		res.y = (pos.x / (-1 * Display.TILE_WIDTH)) + (pos.y / Display.TILE_HEIGHT);

		return res;
	}

	public Vector2 offset;
	private SpriteBatch batch;
	private Sprite sprite;
	private Map<Integer, Texture> textures;

	Display() {
		batch = new SpriteBatch();
		textures = new HashMap<Integer, Texture>();
		offset = new Vector2(Display.WINDOW_WIDTH / 2, 200);
	}

	public void render(Model m) {
		int type;
		List<Tile> map = m.getMap();
		Tile t;
		Vector2 pos;
 
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		for(int i = 0; i < map.size(); i++) {
			t = map.get(i);
			pos = Display.worldToScreen(t.pos);
			pos.x += offset.x - (Display.TILE_WIDTH / 2);
			pos.y += offset.y;

			type = t.type;

			if((textures.get(type)) == null) {
				String fname = "img/tiles/" + String.format("%04d", type) + ".png";
				Gdx.app.debug("Display:render", "Adding texture " + fname);
				textures.put(type, new Texture(Gdx.files.internal(fname)));
			}

			sprite = new Sprite(textures.get(type));
			sprite.setPosition(pos.x, pos.y);

			sprite.draw(batch);

		}

		batch.end();
	}

	public void dispose() {
		for(Map.Entry item : textures.entrySet()) {
			((Texture) item.getValue()).dispose();
		}
	}
}
