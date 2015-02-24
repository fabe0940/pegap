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
	public List<Tile> map;
	public Player p;

	private List<Tile> mapFromFile(int l) {
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
		List<Tile> res;

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

		res = new ArrayList<Tile>();

		for(x = 0; x < width; x++) {
			for(y = 0; y < height; y++) {
				if(types[x][y] == -1) continue;
				res.add(new Tile(x, y, types[x][y]));
			}
		}

		return res;
	}

	Model() {
		this(1);
	}

	Model(int l) {
		p = new Player();
		level = l;
		turn = 0;

		map = mapFromFile(l);
	}

	public void update(Input in) {
		if(in.moveNorthwest == true) {
			turn++;
			p.pos.y += 1;
			in.moveNorthwest = false;
		}

		if(in.moveNortheast == true) {
			turn++;
			p.pos.x += 1;
			in.moveNortheast = false;
		}

		if(in.moveSouthwest == true) {
			turn++;
			p.pos.x -= 1;
			in.moveSouthwest = false;
		}

		if(in.moveSoutheast == true) {
			turn++;
			p.pos.y -= 1;
			in.moveSoutheast = false;
		}
	}
}
