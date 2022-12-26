package com.smj.jmario.entity;

import com.smj.jmario.level.Level;

import java.util.ArrayList;
import java.util.Iterator;

public final class EntityManager implements Iterable<Entity> {
    public final ArrayList<Entity> entities = new ArrayList<>();
    private Level parent;
    public EntityManager(Level parent) {
        this.parent = parent;
    }
    public void loadEntity(Entity entity) {
        entities.add(entity);
        entity.onLoad(parent);
    }
    public void unloadEntity(int index) {
        entities.get(index).onUnload(parent);
        entities.remove(index);
    }
    public void unloadEntity(Entity entity) {
        entities.remove(entity);
        entity.onUnload(parent);
    }
    public int loadedEntityAmount() {
        return entities.size();
    }
    public void unloadAll() {
        for (Entity entity : entities) {
            entity.onUnload(parent);
        }
        entities.clear();
    }
    public Entity[] array() {
        Entity[] entities = new Entity[this.entities.size()];
        this.entities.toArray(entities);
        return entities;
    }
    public Iterator<Entity> iterator() {
        return entities.iterator();
    }
}