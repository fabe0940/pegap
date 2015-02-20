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

		for(int i = 0; i < Model.WORLD_SIZE; i++) {
			for(int j = 0; j < Model.WORLD_SIZE; j++) {
				map.add(new Tile((float) i, (float) j, 1));
			}
		}
	}

	public void update(Input in) {
	}

	public List<Tile> getMap() {
		return map;
	}
}
