package pegap;

import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import pegap.Input;
import pegap.Player;
import pegap.Enemy;

class Model {
	public static final int MAX_LEVEL = 6;
	public static int WORLD_SIZE = 0;

	public int level;
	public int turn;
	public Map<Vector2, Tile> terrain;
	public Map<Vector2, Enemy> world;
	public Player p;

	private void loadFromFile(int l) {
		int i;
		int x;
		int y;
		int type;
		int height;
		int width;
		int offset;
		int enemies;
		String fname;
		String fcontents;
		String[] vals;
		FileHandle fin;
		Map<Vector2, Tile>res;

		fname = new String("lvl/" + String.format("%04d", l) + ".txt");

		Gdx.app.debug("Model:loadFromFile", "Loading map " + fname);
		fin = Gdx.files.internal(fname);
		fcontents = fin.readString();
		vals = fcontents.trim().split("\\s+");

		width = Integer.parseInt(vals[0]);
		height = Integer.parseInt(vals[1]);
		WORLD_SIZE = Math.max(height, width);

		x = (Integer.parseInt(vals[2]) - 1);
		y = (Integer.parseInt(vals[3]) - 1);
		p = new Player(x, y);

		enemies = Integer.parseInt(vals[4]);

		world = new HashMap<Vector2, Enemy>();
		for(offset = 5; offset < 5 + (enemies * 2); offset += 2) {
			x = (Integer.parseInt(vals[offset + 0]) - 1);
			y = (Integer.parseInt(vals[offset + 1]) - 1);

			world.put(new Vector2(x, y), new Enemy(x, y));
		}

		terrain = new HashMap<Vector2, Tile>();
		for(i = offset; i < vals.length; i++) {
			type = Integer.parseInt(vals[i]);
			x = (i - offset) % width;
			y = (height - 1) - ((i - offset) / width);

			if(type == Tile.TYPE_NONE) continue;

			terrain.put(new Vector2(x, y), new Tile(x, y, type));
		}
	}

	private List<Enemy> shuffleEnemies(Collection<Enemy> c) {
		Object[] arr;
		List<Enemy> L;

		arr = c.toArray();
		L = new ArrayList<Enemy>();

		for(Object e : arr) {
			L.add((Enemy) e);
		}

		Collections.shuffle(L);

		return L;
	}


	Model() {
		this(1);
	}

	Model(int l) {
		level = l;
		turn = 0;

		loadFromFile(l);
	}

	public int update(Input in) {
		boolean update;
		int x;
		int y;
		int res;
		int tries;
		Enemy target;
		Vector2 move;
		Vector2 pos;
		Tile dest;

		res = 0;
		update = false;
		move = new Vector2(-1, -1);

		if(in.move == true) {
			if(in.moveNorthwest == true) {
				move = new Vector2(p.pos.x, p.pos.y + 1);
				in.moveNorthwest = false;
			}

			if(in.moveNortheast == true) {
				move = new Vector2(p.pos.x + 1, p.pos.y);
				in.moveNortheast = false;
			}

			if(in.moveSouthwest == true) {
				move = new Vector2(p.pos.x - 1, p.pos.y);
				in.moveSouthwest = false;
			}

			if(in.moveSoutheast == true) {
				move = new Vector2(p.pos.x, p.pos.y - 1);
				in.moveSoutheast = false;
			}

			if(in.moveNone == true) {
				move = new Vector2(p.pos.x, p.pos.y);
				in.moveNone = false;
			}

			if((target = world.get(move)) != null) {
				x = (int) (p.pos.x + (2 * (move.x - p.pos.x)));
				y = (int) (p.pos.y + (2 * (move.y - p.pos.y)));
				move = new Vector2(x, y);
			}

			if((dest = terrain.get(move)) != null) {
				switch(dest.type) {
					case Tile.TYPE_EXIT:
						res = 1;
					case Tile.TYPE_NORMAL:
						update = true;
						turn++;
						p.pos = move;
						in.resetOffset = true;

						if(target != null) {
							world.remove(target.pos);
							turn--;
							update = false;
							target = null;
						}

						break;
				}
			}

			if(res == 0 && update == true) {
				tries = 0;
				while(update == true && tries++ < 10 * world.size()) {
					for(Enemy e : shuffleEnemies(world.values())) {
						if(e.moved == true) continue;

						pos = e.pos;

						e.update(terrain, world, p);

						e = world.remove(pos);
						world.put(e.pos, e);

						if(e.pos.equals(p.pos)) {
							Gdx.app.log("Model:update", "you lose!");
							Gdx.app.exit();
						}

						break;
					}

					for(Enemy e : world.values()) {
						update = update && e.moved;
					}
					update = !update;
				}

				for(Enemy e : world.values()) {
					e.moved = false;
				}
			}

			in.move = false;
		}

		return res;
	}
}
