package pegap;

import java.util.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import pegap.Input;
import pegap.Model;
import pegap.Tile;

class Display {
	public static final int TILE_WIDTH = 128;
	public static final int TILE_HEIGHT = 64;
	public static final int WINDOW_WIDTH = Gdx.graphics.getWidth();
	public static final int WINDOW_HEIGHT = Gdx.graphics.getHeight();

	private static final int OFFSET_MIN_X = (int) (-0.5 * Model.WORLD_SIZE * TILE_WIDTH);
	private static final int OFFSET_MAX_X = (int) (WINDOW_WIDTH + (0.5 * Model.WORLD_SIZE * TILE_WIDTH));
	private static final int OFFSET_MIN_Y = (int) (-1.0 * Model.WORLD_SIZE * TILE_HEIGHT);
	private static final int OFFSET_MAX_Y = (int) (WINDOW_HEIGHT + (Model.WORLD_SIZE * TILE_HEIGHT));
	private static final int OFFSET_INIT_X = (int) (0.5 * Model.WORLD_SIZE * TILE_WIDTH);
	private static final int OFFSET_INIT_Y = (int) (0.5 * Model.WORLD_SIZE * TILE_HEIGHT);
	private static final int SCROLL_RATE = 7;

	public static Vector2 worldToScreen(Vector2 pos) {
		Vector2 res = new Vector2();

		res.x = ((TILE_WIDTH / 2) * pos.x) - ((TILE_WIDTH / 2) * pos.y);
		res.y = ((TILE_HEIGHT / 2) * pos.x) + ((TILE_HEIGHT / 2) * pos.y);

		return res;
	}

	public static Vector2 screenToWorld(Vector2 pos) {
		Vector2 res = new Vector2();

		res.x = (pos.x / TILE_WIDTH) + (pos.y / TILE_HEIGHT);
		res.y = (pos.x / (-1 * TILE_WIDTH)) + (pos.y / TILE_HEIGHT);

		return res;
	}

	public Vector2 offset;
	private SpriteBatch batch;
	private Sprite sprite;
	private Map<Integer, Texture> textures;
	private BitmapFont font;
	private FreeTypeFontGenerator fgen;

	Display() {
		String fname;

		batch = new SpriteBatch();
		textures = new HashMap<Integer, Texture>();
		offset = new Vector2(OFFSET_INIT_X, OFFSET_INIT_Y);

		fname = new String("font/komika.ttf");

		Gdx.app.log("Display:Display", "Generating font " + fname);
		fgen = new FreeTypeFontGenerator(Gdx.files.internal(fname));
		font = fgen.generateFont(12);
		font.setColor(Color.WHITE);
	}

	public void update(Input in) {
		if(in.scrollUp) offset.y -= SCROLL_RATE;
		if(in.scrollDown) offset.y += SCROLL_RATE;
		if(in.scrollLeft) offset.x += SCROLL_RATE;
		if(in.scrollRight) offset.x -= SCROLL_RATE;

		if(offset.x < OFFSET_MIN_X) offset.x = OFFSET_MIN_X;
		if(offset.x > OFFSET_MAX_X) offset.x = OFFSET_MAX_X;
		if(offset.y < OFFSET_MIN_Y) offset.y = OFFSET_MIN_Y;
		if(offset.y > OFFSET_MAX_Y) offset.y = OFFSET_MAX_Y;
	}

	public void render(Model m) {
		int type;
		List<Tile> map = m.getMap();
		String fname;
		Tile t;
		Vector2 pos;
 
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		for(int i = 0; i < map.size(); i++) {
			t = map.get(i);
			pos = worldToScreen(t.pos);
			pos.x += offset.x - (TILE_WIDTH / 2);
			pos.y += offset.y;

			type = t.type;

			if((textures.get(type)) == null) {
				fname = new String("img/" + String.format("%04d", type) + ".png");
				Gdx.app.debug("Display:render", "Adding texture " + fname);
				textures.put(type, new Texture(Gdx.files.internal(fname)));
			}

			sprite = new Sprite(textures.get(type));
			sprite.setPosition(pos.x, pos.y);

			sprite.draw(batch);

		}

		pos = new Vector2();
		pos.x = (0.5f * WINDOW_WIDTH) - offset.x;
		pos.y = (0.5f * WINDOW_HEIGHT) - offset.y;
		pos = screenToWorld(pos);
		font.draw(batch, "[ " + ((int) pos.x) + " , " + ((int) pos.y) + " ]", WINDOW_WIDTH - 100, WINDOW_HEIGHT);

		batch.end();
	}

	public void dispose() {
		fgen.dispose();

		for(Map.Entry item : textures.entrySet()) {
			((Texture) item.getValue()).dispose();
		}
	}
}
