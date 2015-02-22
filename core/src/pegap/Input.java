package pegap;

import java.util.*;
import com.badlogic.gdx.math.Vector2;

class Input {
	public List<Vector2> clicks;

	Input() {
		clicks = new ArrayList<Vector2>();
	}

	public void addClick(float x, float y) {
		Vector2 v;

		v = new Vector2(x, y);
		addClick(v);
	}

	public void addClick(Vector2 v) {
		clicks.add(v);
	}

	public Vector2 getClick() {
		Vector2 res;

		if(clicks.size() > 0) {
			res = clicks.remove(clicks.size() - 1);
		} else {
			res = null;
		}

		return res;
	}
}
