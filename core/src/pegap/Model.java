package pegap;

import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import pegap.Input;

class Model {
	public static final int WORLD_SIZE = 8;

	private List<Tile> map;
	private Vector2 lastClick;

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

		lastClick = null;
	}

	public void update(Input in) {
		Iterator<Vector2> iter;

		for(Tile tile : map) {
			tile.type = tile.type % 10 != 2 ? tile.type : tile.type - 1;
		}

		iter = in.clicks.iterator();
		while(iter.hasNext()) {
			lastClick = iter.next();

			Gdx.app.debug("Model:update", "Processing click (" + lastClick.x + "," + lastClick.y + ")");

			iter.remove();
		}

		for(Tile tile : map) {
			if(lastClick != null && tile.pos.equals(lastClick)) {
				tile.type = tile.type + 1;
			}
		}
	}

	public List<Tile> getMap() {
		return map;
	}
}
