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

	private static final int SCROLL_RATE = 7;
	private static final int TILE_PLAYER = 1000;
	private static final int TILE_ENEMY = 1001;
	private static final int UI_TEX_BACK = 100;
	private static final int UI_TEX_INTERFACE = 101;
	private static final int UI_TEX_STATUS = 102;
	private static final int UI_TEX_OVERLAY = 103;

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
	private BitmapFont interfaceFont;
	private BitmapFont popupFont;
	private FreeTypeFontGenerator fgen;

	Display() {
		String fname;

		batch = new SpriteBatch();
		textures = new HashMap<Integer, Texture>();
		offset = new Vector2(0, 0);

		fname = new String("font/komika.ttf");

		Gdx.app.log("Display:Display", "Generating font " + fname);
		fgen = new FreeTypeFontGenerator(Gdx.files.internal(fname));

		interfaceFont = fgen.generateFont(12);
		interfaceFont.setColor(Color.BLACK);

		popupFont = fgen.generateFont(60);
		popupFont.setColor(Color.BLACK);
	}

	public void update(Input in, Model m) {
		int x;
		int y;
		Vector2 pos;

		if(in.scrollNorth) offset.y -= SCROLL_RATE;
		if(in.scrollSouth) offset.y += SCROLL_RATE;
		if(in.scrollEast) offset.x -= SCROLL_RATE;
		if(in.scrollWest) offset.x += SCROLL_RATE;

		if(in.resetOffset) {
			pos = worldToScreen(m.p.pos);
			pos.x = (int) ((0.5 * WINDOW_WIDTH) - pos.x);
			pos.y = (int) ((0.5 * WINDOW_HEIGHT) - pos.y);
			offset = pos;
			in.resetOffset = false;
		}
	}

	public void render(Model m) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.enableBlending();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		renderModel(m);
		renderStatus(m);
		renderInterface(m);
		if(m.level == Model.LEVEL_DEATH) renderDeath();
		if(m.level == Model.LEVEL_WIN) renderWin();
	}

	public void dispose() {
		fgen.dispose();

		for(Map.Entry item : textures.entrySet()) {
			((Texture) item.getValue()).dispose();
		}
	}

	private Texture getTexture(int type) {
		String fname;

		if((textures.get(type)) == null) {
			fname = new String("img/" + String.format("%04d", type) + ".png");
			Gdx.app.debug("Display:render", "Adding texture " + fname);
			textures.put(type, new Texture(Gdx.files.internal(fname)));
		}

		return textures.get(type);
	}

	private void renderModel(Model m) {
		int type;
		Vector2 pos;

		batch.begin();

		sprite = new Sprite(getTexture(UI_TEX_BACK));
		sprite.setPosition(0, 0);
		sprite.draw(batch);
 
		for(Tile t : m.terrain.values()) {
			pos = worldToScreen(t.pos);
			pos.x += offset.x - (TILE_WIDTH / 2);
			pos.y += offset.y;

			type = t.type;

			if(type == Tile.TYPE_NORMAL) {
				if((t.pos.x + t.pos.y) % 2 == 0) {
					type = Tile.TYPE_NORMAL_LIGHT;
				} else {
					type = Tile.TYPE_NORMAL_DARK;
				}
			}

			sprite = new Sprite(getTexture(type));
			sprite.setPosition(pos.x, pos.y);
			sprite.draw(batch);
		}

		for(Enemy e : m.world.values()) {
			pos = worldToScreen(e.pos);
			pos.x += offset.x - (TILE_WIDTH / 2);
			pos.y += offset.y;

			sprite = new Sprite(getTexture(TILE_ENEMY));
			sprite.setPosition(pos.x, pos.y);
			sprite.draw(batch);
		}

		sprite = new Sprite(getTexture(TILE_PLAYER));
		pos = worldToScreen(m.p.pos);
		pos.x += offset.x - (TILE_WIDTH / 2);
		pos.y += offset.y;
		sprite.setPosition(pos.x, pos.y);
		sprite.draw(batch);

		batch.end();
	}

	private void renderStatus(Model m) {
		String fname;
		String msg;
		Vector2 pos;

		batch.begin();

		sprite = new Sprite(getTexture(UI_TEX_STATUS));
		sprite.setPosition(0, WINDOW_HEIGHT - 16);
		sprite.draw(batch);

		msg = new String("Level " + String.format("%02d", m.level) + "/" + String.format("%02d", Model.MAX_LEVEL));
		interfaceFont.draw(batch, msg, 5, WINDOW_HEIGHT - 1);

		msg = new String("Turn " + String.format("%03d", m.turn));
		interfaceFont.draw(batch, msg, 105, WINDOW_HEIGHT - 1);

		pos = new Vector2();
		pos.x = (0.5f * WINDOW_WIDTH) - offset.x;
		pos.y = (0.5f * WINDOW_HEIGHT) - offset.y;
		pos = screenToWorld(pos);

		msg = new String("(" + ((int) pos.x) + "," + ((int) pos.y) + ")");
		interfaceFont.draw(batch, msg, WINDOW_WIDTH - 50, WINDOW_HEIGHT - 1);

		batch.end();
	}

	private void renderInterface(Model m) {
		String fname;
		String msg;

		batch.begin();

		sprite = new Sprite(getTexture(UI_TEX_INTERFACE));
		sprite.setPosition(0, 0);
		sprite.draw(batch);

		interfaceFont.draw(batch, "Controls:", 5, 95);
		interfaceFont.draw(batch, "Scroll West - H", 5, 70);
		interfaceFont.draw(batch, "Scroll South - J", 5, 55);
		interfaceFont.draw(batch, "Scroll North - K", 5, 40);
		interfaceFont.draw(batch, "Scroll East - L", 5, 25);

		interfaceFont.draw(batch, "Move Northwest - Y", 155, 70);
		interfaceFont.draw(batch, "Move Northeast - U", 155, 55);
		interfaceFont.draw(batch, "Move Southwest - B", 155, 40);
		interfaceFont.draw(batch, "Move Souteast - N", 155, 25);

		interfaceFont.draw(batch, "Wait - W", 325, 70);
		interfaceFont.draw(batch, "Center Screen - Space", 325, 55);
		interfaceFont.draw(batch, "Exit - Q", 325, 40);

		interfaceFont.draw(batch, "Notes:", 500, 95);
		switch(m.level) {
			case 1:
				interfaceFont.draw(batch, "Proceed to the exit to complete a level.", 500, 70);
				break;
			case 2:
				interfaceFont.draw(batch, "Avoid enemies to prevent death.", 500, 70);
				break;
			case 3:
				interfaceFont.draw(batch, "Jump over enemies by moving towards", 500, 70);
				interfaceFont.draw(batch, "them. Destroying an enemy gives a bonus", 500, 55);
				interfaceFont.draw(batch, "move.", 500, 40);
				break;
			case 4:
				interfaceFont.draw(batch, "There may be multiple paths to victory.", 500, 70);
				break;
			case 5:
				interfaceFont.draw(batch, "Use strategic waiting to position", 500, 70);
				interfaceFont.draw(batch, "enemies.", 500, 55);
				break;
			case 6:
				interfaceFont.draw(batch, "Escape from the 10th level to win.", 500, 70);
				interfaceFont.draw(batch, "Good luck!", 500, 55);
		}

		batch.end();
	}

	public void renderDeath() {
		batch.begin();

		sprite = new Sprite(getTexture(UI_TEX_OVERLAY));
		sprite.setPosition(0, 0);
		sprite.draw(batch);

		popupFont.draw(batch, "You Died", 250, 400);

		batch.end();
	}

	public void renderWin() {
		batch.begin();

		sprite = new Sprite(getTexture(UI_TEX_OVERLAY));
		sprite.setPosition(0, 0);
		sprite.draw(batch);

		popupFont.draw(batch, "You Win!!!", 250, 400);

		batch.end();
	}
}
