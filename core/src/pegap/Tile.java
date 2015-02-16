package pegap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tile extends Actor {
	public static final int WIDTH = 128;
	public static final int HEIGHT = 64;

	public static Vector2 screenToIso(Vector2 v) {
		float x;
		float y;
		Vector2 res;

		x = ((v.x / Tile.WIDTH) + (v.y / Tile.HEIGHT));
		y = ((v.y / Tile.HEIGHT) - (v.x / Tile.WIDTH));
		res  = new Vector2(x, y);

		return res;
	}

	public static Vector2 isoToScreen(Vector2 v) {
		float x;
		float y;
		Vector2 res;

		x = ((v.x - v.y) * (Tile.WIDTH / 2));
		y = ((v.x + v.y) * (Tile.HEIGHT / 2));
		res  = new Vector2(x, y);

		return res;
	}

	private Vector2 pos;
	private Texture texture;

	Tile() {
		setPos(0.0f, 0.0f);
		setTexture("img/tiles/tile.png");
	}

	Tile(float y, float x, String fname) {
		setPos(x, y);
		setTexture(fname);
	}

	Tile(Vector2 v, String fname) {
		setPos(v);
		setTexture(fname);
	}

	public void setPos(float x, float y) {
		pos = new Vector2(x, y);
	}

	public void setPos(Vector2 v) {
		pos = new Vector2(v);
	}

	public void setTexture(String fname) {
		texture = new Texture(Gdx.files.internal(fname));
	}

	@Override
	public void draw(Batch batch, float alpha) {
		Vector2 pixPos;

		pixPos = Tile.isoToScreen(pos);
		pixPos.x -= (Tile.WIDTH / 2);

		batch.draw(texture, pixPos.x, pixPos.y);
	}
}
