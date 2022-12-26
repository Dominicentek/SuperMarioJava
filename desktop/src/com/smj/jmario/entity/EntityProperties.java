package com.smj.jmario.entity;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class EntityProperties {
    public boolean drawInBG = false;
    public Texture texture = new Texture(1, 1, Pixmap.Format.RGBA8888);
    public boolean immuneToFluid = false;
    public EntityProperties setDrawInBG(boolean drawInBG) {
        this.drawInBG = drawInBG;
        return this;
    }
    public EntityProperties setTexture(Texture texture) {
        this.texture = texture;
        return this;
    }
    public EntityProperties setImmuneToFluid(boolean immuneToFluid) {
        this.immuneToFluid = immuneToFluid;
        return this;
    }
    public EntityProperties copy() {
        return new EntityProperties().setDrawInBG(drawInBG).setImmuneToFluid(immuneToFluid).setTexture(texture);
    }
}
