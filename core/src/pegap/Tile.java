package pegap;

import com.badlogic.gdx.math.Vector2;

public class Tile {
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_NORMAL_LIGHT = 1;
	public static final int TYPE_NORMAL_DARK = 11;

	public Vector2 pos;
	public int type;

	Tile() {
		this(new Vector2(0f, 0f), TYPE_NORMAL);
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
