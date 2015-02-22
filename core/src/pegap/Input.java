package pegap;

import java.util.*;
import com.badlogic.gdx.math.Vector2;

class Input {
	public boolean scrollUp;
	public boolean scrollDown;
	public boolean scrollLeft;
	public boolean scrollRight;
	public List<Vector2> clicks;

	Input() {
		scrollUp = false;
		scrollDown = false;
		scrollLeft = false;
		scrollRight = false;
		clicks = new ArrayList<Vector2>();
	}
}
