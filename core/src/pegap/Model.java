package pegap;

import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

import pegap.Input;

class Model {
	public static int WORLD_SIZE = 0;

	public int level;
	public int turn;

	private List<Tile> map;

	private void mapFromFile(int l) {
		int i;
		int x;
		int y;
		int height;
		int width;
		int[][] types;
		String fname;
		String fcontents;
		String[] vals;
		FileHandle fin;

		fname = new String("lvl/" + String.format("%04d", l) + ".txt");

		Gdx.app.debug("Model:mapFromFile", "Loading map " + fname);
		fin = Gdx.files.internal(fname);
		fcontents = fin.readString();
		vals = fcontents.trim().split("\\s+");

		width = Integer.parseInt(vals[0]);
		height = Integer.parseInt(vals[1]);
		WORLD_SIZE = Math.max(height, width);

		types = new int[width][height];

		for(i = 2; i < vals.length; i++) {
			x = (i - 2) % width;
			y = (height - 1) - ((i - 2) / width);
			types[x][y] = Integer.parseInt(vals[i]);
		}

		if(map == null) map = new ArrayList<Tile>();

		for(x = 0; x < width; x++) {
			for(y = 0; y < height; y++) {
				if(types[x][y] == -1) continue;
				map.add(new Tile(x, y, types[x][y]));
			}
		}
	}

	Model() {
		this(1);
	}

	Model(int l) {
		mapFromFile(l);
		level = l;
		turn = 0;
	}

	public void update(Input in) {
	}

	public List<Tile> getMap() {
		return map;
	}
}
