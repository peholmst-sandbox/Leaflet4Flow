package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.HtmlImport;
import org.geotools.geometry.DirectPosition2D;
import org.opengis.geometry.DirectPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * TODO Document me!
 */
@Tag("leaflet-element")
@HtmlImport("bower_components/leaflet-element/leaflet-element.html")
public class Leaflet extends Component implements HasSize {

    private final List<Layer<?>> layers = new ArrayList<>();

    private static final PropertyDescriptor<Boolean, Boolean> attributionControlVisible
            = PropertyDescriptors.propertyWithDefault("attributionControlVisible", true);
    private static final PropertyDescriptor<Boolean, Boolean> zoomControlVisible
            = PropertyDescriptors.propertyWithDefault("zoomControlVisible", true);
    private static final PropertyDescriptor<Boolean, Boolean> boxZoomEnabled
            = PropertyDescriptors.propertyWithDefault("boxZoomEnabled", true);
    private static final PropertyDescriptor<Boolean, Boolean> doubleClickZoomEnabled
            = PropertyDescriptors.propertyWithDefault("doubleClickZoomEnabled", true);
    private static final PropertyDescriptor<ZoomMode, ZoomMode> doubleClickZoomMode
            = new EnumPropertyDescriptor<>("doubleClickZoomMode", ZoomMode.class, ZoomMode.POINTER);
    private static final PropertyDescriptor<Boolean, Boolean> scrollWheelZoomEnabled
            = PropertyDescriptors.propertyWithDefault("scrollWheelZoomEnabled", true);
    private static final PropertyDescriptor<ZoomMode, ZoomMode> scrollWheelZoomMode
            = new EnumPropertyDescriptor<>("scrollWheelZoomMode", ZoomMode.class, ZoomMode.POINTER);
    private static final PropertyDescriptor<Boolean, Boolean> draggingEnabled
            = PropertyDescriptors.propertyWithDefault("draggingEnabled", true);
    private static final PropertyDescriptor<DirectPosition, DirectPosition> center
            = new DirectPositionPropertyDescriptor("center",
            new DirectPosition2D(22.2995748, 60.4524144)); // Vaadin HQ coordinates ;-)
    private static final PropertyDescriptor<Integer, Integer> zoom
            = PropertyDescriptors.propertyWithDefault("zoom", 10);
    // TODO maxBounds and other properties

    public <L extends Layer<L>> void addLayer(L layer) {
        Objects.requireNonNull(layer, "layer must not be null");
        // TODO Either make Layer
        // immutable (using the builder pattern for example) or make sure the setter methods update the UI after the layer has been added
        layers.add(layer.clone());
        layer.addToElement(getElement());
    }

    public Stream<Layer<?>> getLayers() {
        return layers.stream();
    }

    /**
     * Returns whether the attribution control is visible on the map (default is true).
     */
    public boolean isAttributionControlVisible() {
        return attributionControlVisible.get(this);
    }

    /**
     * Shows or hides the attribution control on the map.
     *
     * @param attributionControlVisible true to show the attribution control, false to hide it.
     */
    public void setAttributionControlVisible(boolean attributionControlVisible) {
        this.attributionControlVisible.set(this, attributionControlVisible);
    }

    /**
     * Returns whether the zoom control is visible on the map (default is true).
     */
    public boolean isZoomControlVisible() {
        return zoomControlVisible.get(this);
    }

    /**
     * Shows or hides the zoom control on the map.
     *
     * @param zoomControlVisible true to show the zoom control, false to hide it.
     */
    public void setZoomControlVisible(boolean zoomControlVisible) {
        this.zoomControlVisible.set(this, zoomControlVisible);
    }

    /**
     * Returns whether the map can be zoomed to a rectangular area specified by dragging the mouse while pressing the
     * shift key (default is true).
     */
    public boolean isBoxZoomEnabled() {
        return boxZoomEnabled.get(this);
    }

    /**
     * Enables or disables {@link #isBoxZoomEnabled() box zoom}.
     *
     * @param boxZoomEnabled true to enable box zoom, false to disable it.
     */
    public void setBoxZoomEnabled(boolean boxZoomEnabled) {
        this.boxZoomEnabled.set(this, boxZoomEnabled);
    }

    /**
     * Returns whether the map can be zoomed in by double clicking on it and zoomed out by double clicking while holding
     * shift (default is true).
     *
     * @see #getDoubleClickZoomMode()
     */
    public boolean isDoubleClickZoomEnabled() {
        return doubleClickZoomEnabled.get(this);
    }

    /**
     * Enables or disables {@link #isDoubleClickZoomEnabled() double click zoom}.
     *
     * @param doubleClickZoomEnabled true to enable double click zoom, false to disable it.
     * @see #setDoubleClickZoomMode(ZoomMode)
     */
    public void setDoubleClickZoomEnabled(boolean doubleClickZoomEnabled) {
        this.doubleClickZoomEnabled.set(this, doubleClickZoomEnabled);
    }

    /**
     * Returns whether double click should zoom to the center of the view or to the location of the mouse pointer
     * (default is the location of the mouse pointer).
     *
     * @see #isDoubleClickZoomEnabled()
     */
    public ZoomMode getDoubleClickZoomMode() {
        return doubleClickZoomMode.get(this);
    }

    /**
     * Specifies whether double click should zoom to the center of the view or to the location of the mouse pointer.
     *
     * @param doubleClickZoomMode the double click zoom mode.
     * @see #setDoubleClickZoomEnabled(boolean)
     */
    public void setDoubleClickZoomMode(ZoomMode doubleClickZoomMode) {
        this.doubleClickZoomMode.set(this, doubleClickZoomMode);
    }

    /**
     * Returns whether the map can be zoomed by using the mouse wheel (default is true).
     *
     * @see #getScrollWheelZoomMode()
     */
    public boolean isScrollWheelZoomEnabled() {
        return scrollWheelZoomEnabled.get(this);
    }

    /**
     * Enables or disables {@link #isScrollWheelZoomEnabled() scroll wheel zoom}.
     *
     * @param scrollWheelZoomEnabled true to enable scroll wheel zoom, false to disable it.
     * @see #setScrollWheelZoomMode(ZoomMode)
     */
    public void setScrollWheelZoomEnabled(boolean scrollWheelZoomEnabled) {
        this.scrollWheelZoomEnabled.set(this, scrollWheelZoomEnabled);
    }

    /**
     * Returns whether using the mouse wheel should zoom to the center of the view or to the location of the mouse
     * pointer (default is the location of the mouse pointer).
     *
     * @see #isScrollWheelZoomEnabled()
     */
    public ZoomMode getScrollWheelZoomMode() {
        return scrollWheelZoomMode.get(this);
    }

    /**
     * Specifies whether using the mouse wheel should zoom to the center of the view or to the location of the mouse
     * pointer.
     *
     * @param scrollWheelZoomMode the scroll wheel zoom mode.
     * @see #setScrollWheelZoomEnabled(boolean)
     */
    public void setScrollWheelZoomMode(ZoomMode scrollWheelZoomMode) {
        this.scrollWheelZoomMode.set(this, scrollWheelZoomMode);
    }

    /**
     * @return
     */
    public boolean isDraggingEnabled() {
        return draggingEnabled.get(this);
    }

    /**
     * @param draggingEnabled
     */
    public void setDraggingEnabled(boolean draggingEnabled) {
        this.draggingEnabled.set(this, draggingEnabled);
    }

    /**
     * @return
     */
    public DirectPosition getCenter() {
        return center.get(this);
    }

    /**
     * @param center
     */
    public void setCenter(DirectPosition center) {
        this.center.set(this, center);
    }

    /**
     * @return
     */
    public int getZoom() {
        return zoom.get(this);
    }

    /**
     * @param zoom
     */
    public void setZoom(int zoom) {
        this.zoom.set(this, zoom);
    }

    /**
     * @return
     */
    public int getMaxZoom() {
        return 0; // TODO Implement me!
    }

    /**
     * @return
     */
    public int getMinZoom() {
        return 0; // TODO Implement me!
    }

    /**
     * Enumeration of map zooming behaviours.
     */
    public enum ZoomMode {
        /**
         * The map will zoom to the center of the view, regardless of where the mouse pointer is.
         */
        CENTER,
        /**
         * The map will zoom to the location of the mouse pointer.
         */
        POINTER
    }
}
