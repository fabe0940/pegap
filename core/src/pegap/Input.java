package pegap;

import java.util.*;
import com.badlogic.gdx.math.Vector2;

class Input {
	public boolean scrollNorth;
	public boolean scrollSouth;
	public boolean scrollWest;
	public boolean scrollEast;
	public boolean move;
	public boolean moveNorthwest;
	public boolean moveNortheast;
	public boolean moveSouthwest;
	public boolean moveSoutheast;

	Input() {
		scrollNorth = false;
		scrollSouth = false;
		scrollWest = false;
		scrollEast = false;
		move = false;
		moveNorthwest = false;
		moveNortheast = false;
		moveSouthwest = false;
		moveSoutheast = false;
	}
}
