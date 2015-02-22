package pegap;

import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import pegap.Input;

class Model {
	private static final int WORLD_SIZE = 10;

	private List<Tile> map;
	private Vector2 lastClick;

	Model() {
		int i;
		int j;

		map = new ArrayList<Tile>();

		for(i = 0; i < WORLD_SIZE; i++) {
			for(j = 0; j < WORLD_SIZE; j++) {
				map.add(new Tile(i, j, 1));
			}
		}

		lastClick = null;
	}

	public void update(Input in) {
		Iterator<Vector2> iter;

		for(Tile tile : map) {
			tile.type = 1;
		}

		iter = in.clicks.iterator();
		while(iter.hasNext()) {
			lastClick = iter.next();

			Gdx.app.debug("Model:update", "Processing click (" + lastClick.x + "," + lastClick.y + ")");

			iter.remove();
		}

		for(Tile tile : map) {
			if(lastClick != null && tile.pos.equals(lastClick)) {
				tile.type = 2;
			}
		}
	}

	public List<Tile> getMap() {
		return map;
	}
}
