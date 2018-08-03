package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.dom.Element;
import elemental.json.JsonObject;

/**
 * TODO Document me!
 */
public class TileLayer extends GridLayer<TileLayer> {

    private final URLTemplate urlTemplate;

    private int minZoomLevel = 0;
    private int maxZoomLevel = 18;

    public TileLayer(URLTemplate urlTemplate) {
        this.urlTemplate = urlTemplate;
    }

    public URLTemplate getUrlTemplate() {
        return urlTemplate;
    }

    public int getMinZoomLevel() {
        return minZoomLevel;
    }

    public TileLayer setMinZoomLevel(int minZoomLevel) {
        this.minZoomLevel = minZoomLevel;
        return self();
    }

    public int getMaxZoomLevel() {
        return maxZoomLevel;
    }

    public TileLayer setMaxZoomLevel(int maxZoomLevel) {
        this.maxZoomLevel = maxZoomLevel;
        return self();
    }

    @Override
    protected JsonObject toJson() {
        var object = super.toJson();
        // TODO continue here
        return object;
    }

    @Override
    protected void addToElement(Element element) {
        // TODO implement me
    }
}
