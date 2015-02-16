package pegap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tile extends Actor {
	private Texture texture;

	Tile() {
		setTexture("img/tiles/tile.png");
	}

	Tile(String fname) {
		setTexture(fname);
	}

	public void setTexture(String fname) {
		texture = new Texture(Gdx.files.internal(fname));
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(texture, 0, 0);
	}
}
