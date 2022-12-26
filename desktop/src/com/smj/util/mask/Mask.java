package com.smj.util.mask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.smj.Main;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;

public class Mask extends ArrayList<Circle> {
    private static ShapeRenderer renderer = new ShapeRenderer();
    public void render() {
        if (size() == 0) return;
        renderer.begin();
        renderer.setColor(0, 0, 0, 1);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.rect(0, 0, Main.WIDTH, Main.HEIGHT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFuncSeparate(GL20.GL_ZERO, GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA);
        renderer.setColor(0, 0, 0, 0);
        for (Circle circle : this) {
            renderer.circle(circle.x, circle.y, circle.r);
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

