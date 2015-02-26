package pegap;

import com.badlogic.gdx.math.Vector2;

public class Player {
	public Vector2 pos;

	Player() {
		this(new Vector2(0f, 0f));
	}

	Player(int x, int y) {
		this(new Vector2((float) x, (float) y));
	}

	Player(float x, float y) {
		this(new Vector2(x, y));
	}

	Player(Vector2 v) {
		pos = v;
	}
}
