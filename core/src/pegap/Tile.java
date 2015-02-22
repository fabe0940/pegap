package pegap;

import com.badlogic.gdx.math.Vector2;

public class Tile {
	private Vector2 pos;
	private int type;

	Tile() {
		setPos(0.0f, 0.0f);
		setType(0001);
	}

	Tile(int x, int y, int t) {
		setPos((float) x, (float) y);
		setType(t);
	}

	Tile(float x, float y, int t) {
		setPos(x, y);
		setType(t);
	}

	Tile(Vector2 v, int t) {
		setPos(v);
		setType(t);
	}

	public void setPos(float x, float y) {
		pos = new Vector2(x, y);
	}

	public void setPos(Vector2 v) {
		pos = new Vector2(v);
	}

	public void setType(int t) {
		type = t;
	}

	public Vector2 getPos() {
		return pos;
	}

	public int getType() {
		return type;
	}
}
