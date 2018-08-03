package net.pkhapps.leaflet4flow.components;

import elemental.json.JsonObject;

/**
 * TODO Document me
 *
 * @param <L>
 */
public abstract class GridLayer<L extends GridLayer<L>> extends Layer<L> {

    private static final String JSON_PROPERTY_TILE_SIZE = "tileSize";

    private int tileSize = 256;

    public int getTileSize() {
        return tileSize;
    }

    public L setTileSize(int tileSize) {
        this.tileSize = tileSize;
        return self();
    }

    @Override
    protected JsonObject toJson() {
        var object = super.toJson();
        object.put(JSON_PROPERTY_TILE_SIZE, tileSize);
        return object;
    }
}
