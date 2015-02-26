package pegap;

import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import pegap.Player;
import pegap.Tile;

public class Enemy {
	public boolean moved;
	public Vector2 pos;

	Enemy() {
		this(new Vector2(0f, 0f));
	}

	Enemy(int x, int y) {
		this(new Vector2((float) x, (float) y));
	}

	Enemy(float x, float y) {
		this(new Vector2(x, y));
	}

	Enemy(Vector2 v) {
		moved = false;
		pos = v;
	}

	public void update(Map<Vector2, Tile> terrain, Map<Vector2, Enemy> world, Player p) {
		int x;
		int y;
		Enemy e;
		Vector2 goal;
		Vector2 move;
		Queue<Vector2> moves;
		Tile t;

		goal = new Vector2(p.pos.x - pos.x, p.pos.y - pos.y);
		moves = new LinkedList<Vector2>();

		if(Math.abs(goal.x) > Math.abs(goal.y)) {
				x = (int) (goal.x > 0 ? pos.x + 1 : pos.x - 1);
				y = (int) (pos.y);
				moves.add(new Vector2(x, y));

				x = (int) (pos.x);
				y = (int) (goal.y > 0 ? pos.y + 1 : pos.y - 1);
				moves.add(new Vector2(x, y));
		} else {
				x = (int) (pos.x);
				y = (int) (goal.y > 0 ? pos.y + 1 : pos.y - 1);
				moves.add(new Vector2(x, y));

				x = (int) (goal.x > 0 ? pos.x + 1 : pos.x - 1);
				y = (int) (pos.y);
				moves.add(new Vector2(x, y));
		}

		while((move = moves.poll()) != null) {
			if((t = terrain.get(move)) == null) continue;
			if((e = world.get(move)) != null) continue;
			if(t.type == Tile.TYPE_NORMAL || t.type == Tile.TYPE_EXIT) {
				pos = move;
				moved = true;
				break;
			}
		}
	}
}
