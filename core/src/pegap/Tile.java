package pegap;

import com.badlogic.gdx.math.Vector2;

public class Tile {
	public Vector2 pos;
	public int type;

	Tile() {
		this(new Vector2(0f, 0f), 1);
	}

	Tile(int x, int y, int t) {
		this(new Vector2((float) x, (float) y), t);
	}

	Tile(float x, float y, int t) {
		this(new Vector2(x, y), t);
	}

	Tile(Vector2 v, int t) {
		pos = v;
		type = t;
	}
}
