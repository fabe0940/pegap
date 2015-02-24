package pegap;

import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import pegap.Input;

class Model {
	public static final int WORLD_SIZE = 8;

	private List<Tile> map;

	Model() {
		int i;
		int j;
		int type;

		map = new ArrayList<Tile>();

		for(i = 0; i < WORLD_SIZE; i++) {
			for(j = 0; j < WORLD_SIZE; j++) {
				type = (i + j) % 2 == 0 ? 1 : 11;
				map.add(new Tile(i, j, type));
			}
		}
	}

	public void update(Input in) {
	}

	public List<Tile> getMap() {
		return map;
	}
}
