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
	private static final int UI_TEX_INTERFACE = 101;
	private static final int UI_TEX_STATUS = 102;

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
		font.setColor(Color.BLACK);
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
		String msg;
		Tile t;
		Vector2 pos;
 
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		renderModel(m);
		renderStatus(m);
		renderInterface(m);

		batch.end();
	}

	public void dispose() {
		fgen.dispose();

		for(Map.Entry item : textures.entrySet()) {
			((Texture) item.getValue()).dispose();
		}
	}

	private void renderModel(Model m) {
		int type;
		List<Tile> map = m.getMap();
		String fname;
		Tile t;
		Vector2 pos;
 
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
	}

	private void renderStatus(Model m) {
		String fname;
		String msg;
		Vector2 pos;

		if((textures.get(UI_TEX_STATUS)) == null) {
			fname = new String("img/" + String.format("%04d", UI_TEX_STATUS) + ".png");
			Gdx.app.debug("Diplay:render", "Adding texture " + fname);
			textures.put(UI_TEX_STATUS, new Texture(Gdx.files.internal(fname)));
		}

		sprite = new Sprite(textures.get(UI_TEX_STATUS));
		sprite.setPosition(0, WINDOW_HEIGHT - 16);
		sprite.draw(batch);

		msg = new String("Level " + String.format("%02d", m.level));
		font.draw(batch, msg, 5, WINDOW_HEIGHT - 1);

		msg = new String("Turn " + String.format("%03d", m.turn));
		font.draw(batch, msg, 105, WINDOW_HEIGHT - 1);

		pos = new Vector2();
		pos.x = (0.5f * WINDOW_WIDTH) - offset.x;
		pos.y = (0.5f * WINDOW_HEIGHT) - offset.y;
		pos = screenToWorld(pos);

		msg = new String("(" + ((int) pos.x) + "," + ((int) pos.y) + ")");
		font.draw(batch, msg, WINDOW_WIDTH - 50, WINDOW_HEIGHT - 1);
	}

	private void renderInterface(Model m) {
		String fname;
		String msg;

		if((textures.get(UI_TEX_INTERFACE)) == null) {
			fname = new String("img/" + String.format("%04d", UI_TEX_INTERFACE) + ".png");
			Gdx.app.debug("Diplay:render", "Adding texture " + fname);
			textures.put(UI_TEX_INTERFACE, new Texture(Gdx.files.internal(fname)));
		}

		sprite = new Sprite(textures.get(UI_TEX_INTERFACE));
		sprite.setPosition(0, 0);
		sprite.draw(batch);

		font.draw(batch, "Controls:", 5, 95);
		font.draw(batch, "Scroll West - H", 5, 70);
		font.draw(batch, "Scroll South - J", 5, 55);
		font.draw(batch, "Scroll North - K", 5, 40);
		font.draw(batch, "Scroll East - L", 5, 25);
		font.draw(batch, "Move Northwest - Y", 155, 70);
		font.draw(batch, "Move Northeast - U", 155, 55);
		font.draw(batch, "Move Southwest - B", 155, 40);
		font.draw(batch, "Move Souteast - N", 155, 25);
		font.draw(batch, "Exit - Q", 325, 70);
	}
}
