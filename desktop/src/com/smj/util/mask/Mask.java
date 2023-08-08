package com.smj.util.mask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.smj.Main;
import com.smj.game.Game;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;

public class Mask extends ArrayList<Circle> {
    private static ShapeRenderer renderer = new ShapeRenderer();
    public boolean extraDark = false;
    public void render() {
        if (size() == 0) return;
        this.sort((Circle a, Circle b) -> {
            return (int)((a.a - b.a) * 255);
        });
        renderer.begin();
        renderer.setColor(0, 0, 0, 1);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.rect(0, 0, Main.WIDTH, Main.HEIGHT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFuncSeparate(GL20.GL_ZERO, GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA);
        for (Circle circle : this) {
            if (extraDark && circle.a < 1) continue;
            renderer.setColor(0, 0, 0, 1 - circle.a);
            renderer.circle(circle.x - (float)Game.cameraX, circle.y - (float)Game.cameraY, circle.r * (extraDark ? 2f / 3f : 1f));
        }
        Gdx.gl.glDisable(GL20.GL_BLEND);
        renderer.end();
    }
    static {
        renderer.setAutoShapeType(true);
    }
    public void setProjectionMatrix(Matrix4 matrix) {
        renderer.setProjectionMatrix(matrix);
    }
    public void setTransformMatrix(Matrix4 matrix) {
        renderer.setTransformMatrix(matrix);
    }
}

