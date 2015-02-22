package pegap;

import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import pegap.Input;

class Model {
	private static final int WORLD_SIZE = 10;

	private List<Tile> map;

	Model() {
		map = new ArrayList<Tile>();
		map.add(new Tile(0, 0, 1));
	}

	public void update(Input in) {
	}

	public List<Tile> getMap() {
		return map;
	}
}
