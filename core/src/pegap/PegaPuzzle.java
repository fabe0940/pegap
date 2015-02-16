package pegap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import pegap.Tile;

public class PegaPuzzle implements ApplicationListener {
	private Stage S;
	private Group G;

	@Override
	public void create() {
		S = new Stage(new FitViewport(800, 600));
		G = new Group();

		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				Tile t = new Tile();
				t.setPos((float) i, (float) j);
				G.addActor(t);
			}
		}

		S.addActor(G);
	}

	@Override
	public void dispose() {
		S.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		S.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
