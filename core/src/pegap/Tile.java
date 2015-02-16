package pegap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tile extends Actor {
	private Vector2 pos;
	private Texture texture;

	Tile() {
		setPos(0.0f, 0.0f);
		setTexture("img/tiles/tile.png");
	}

	Tile(float y, float x, String fname) {
		setPos(x, y);
		setTexture(fname);
	}

	public void setPos(float x, float y) {
		pos = new Vector2(x, y);
	}

	public void setPos(Vector2 v) {
		pos = new Vector2(v);
	}

	public void setTexture(String fname) {
		texture = new Texture(Gdx.files.internal(fname));
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(texture, pos.x, pos.y);
	}
}
