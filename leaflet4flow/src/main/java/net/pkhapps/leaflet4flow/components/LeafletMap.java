package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.shared.Registration;
import elemental.json.JsonObject;
import org.geotools.geometry.DirectPosition2D;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.Envelope;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Stream;

/**
 * TODO Document me!
 */
@Tag("leaflet-element")
@HtmlImport("bower_components/leaflet-element/leaflet-element.html")
public class LeafletMap extends Component implements HasSize {

    private static final String EVENT_CLICK = "map-click";
    private static final String EVENT_MOVE = "map-move";

    private final List<Layer<?>> layers = new ArrayList<>();

    private MarkerProvider markerProvider;
    private transient MarkerProvider.MarkerView markers;

    private static final PropertyDescriptor<Boolean, Boolean> attributionControlVisible
            = new BooleanPropertyDescriptor("attributionControlVisible", true);
    private static final PropertyDescriptor<Boolean, Boolean> zoomControlVisible
            = new BooleanPropertyDescriptor("zoomControlVisible", true);
    private static final PropertyDescriptor<Boolean, Boolean> boxZoomEnabled
            = new BooleanPropertyDescriptor("boxZoomEnabled", true);
    private static final PropertyDescriptor<Boolean, Boolean> doubleClickZoomEnabled
            = new BooleanPropertyDescriptor("doubleClickZoomEnabled", true);
    private static final PropertyDescriptor<ZoomMode, ZoomMode> doubleClickZoomMode
            = new EnumPropertyDescriptor<>("doubleClickZoomMode", ZoomMode.class, ZoomMode.POINTER);
    private static final PropertyDescriptor<Boolean, Boolean> scrollWheelZoomEnabled
            = new BooleanPropertyDescriptor("scrollWheelZoomEnabled", true);
    private static final PropertyDescriptor<ZoomMode, ZoomMode> scrollWheelZoomMode
            = new EnumPropertyDescriptor<>("scrollWheelZoomMode", ZoomMode.class, ZoomMode.POINTER);
    private static final PropertyDescriptor<Boolean, Boolean> draggingEnabled
            = new BooleanPropertyDescriptor("draggingEnabled", true);
    private static final PropertyDescriptor<DirectPosition, DirectPosition> center
            = new DirectPositionPropertyDescriptor("center",
            new DirectPosition2D(22.2995748, 60.4524144)); // Vaadin HQ coordinates ;-)
    private static final PropertyDescriptor<Integer, Integer> zoom
            = PropertyDescriptors.propertyWithDefault("zoom", 10);
    private static final PropertyDescriptor<Envelope, Envelope> bounds
            = new EnvelopePropertyDescriptor("bounds");
    private static final PropertyDescriptor<Envelope, Envelope> maxBounds
            = new EnvelopePropertyDescriptor("maxBounds");

    // TODO Improve layer API. This is not good.

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
     * Returns the marker provider of the map.
     *
     * @return the marker provider or {@code null} if none has been set.
     */
    public MarkerProvider getMarkerProvider() {
        return markerProvider;
    }

    /**
     * Sets the marker provider of the map.
     *
     * @param markerProvider the marker provider or {@code null} to remove all markers.
     */
    public void setMarkerProvider(MarkerProvider markerProvider) {
        this.markerProvider = markerProvider;
    }

    private void updateMarkers(@Nonnull Envelope bounds) {
        // TODO continue here

        // If there is no marker view from before, create a new one, get the markers and add them to the map
        // If there is a marker view from before, compare it to the new one. Add any markers missing from the old one,
        // remove any markers that are missing from the new one.
        MarkerProvider.MarkerView markerView = markerProvider.getMarkerView(bounds);

        markerView.addMarkersAddedToViewListener(this::addMarkersToMap);
        markerView.addMarkersRemovedFromViewListener(this::removeMarkersFromMap);
        markerView.addMarkersUpdatedInViewListener(this::updateMarkersOnMap);

    }

    private void addMarkersToMap(Iterable marker) {
        // TODO Implement me!
    }

    private void removeMarkersFromMap(Iterable<Marker> markers) {
        // TODO Implement me!
    }

    private void updateMarkersOnMap(Iterable<Marker> markers) {
        // TODO Implement me!
    }

    private Optional<Envelope> getMarkerViewBounds() {
        var mapBounds = getBounds();
        if (mapBounds == null) {
            return Optional.empty();
        }
        var height = EnvelopeUtils.getHeight(mapBounds);
        var width = EnvelopeUtils.getWidth(mapBounds);

        return Optional.of(EnvelopeUtils.resize(mapBounds, -width, -height, width, height));
    }

    /**
     * Returns whether the attribution control is visible on the map (default is true).
     */
    public boolean isAttributionControlVisible() {
        return attributionControlVisible.get(this);
    }

    /**
     * Shows or hides the attribution control on the map. Changing this property after the component has been added
     * to the DOM will cause the map to be recreated on the client side.
     *
     * @param attributionControlVisible true to show the attribution control, false to hide it.
     */
    public void setAttributionControlVisible(boolean attributionControlVisible) {
        LeafletMap.attributionControlVisible.set(this, attributionControlVisible);
    }

    /**
     * Returns whether the zoom control is visible on the map (default is true).
     */
    public boolean isZoomControlVisible() {
        return zoomControlVisible.get(this);
    }

    /**
     * Shows or hides the zoom control on the map. Changing this property after the component has been added
     * to the DOM will cause the map to be recreated on the client side.
     *
     * @param zoomControlVisible true to show the zoom control, false to hide it.
     */
    public void setZoomControlVisible(boolean zoomControlVisible) {
        LeafletMap.zoomControlVisible.set(this, zoomControlVisible);
    }

    /**
     * Returns whether the map can be zoomed to a rectangular area specified by dragging the mouse while pressing the
     * shift key (default is true).
     */
    public boolean isBoxZoomEnabled() {
        return boxZoomEnabled.get(this);
    }

    /**
     * Enables or disables {@link #isBoxZoomEnabled() box zoom}. Changing this property after the component has been
     * added to the DOM will cause the map to be recreated on the client side.
     *
     * @param boxZoomEnabled true to enable box zoom, false to disable it.
     */
    public void setBoxZoomEnabled(boolean boxZoomEnabled) {
        LeafletMap.boxZoomEnabled.set(this, boxZoomEnabled);
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
     * Enables or disables {@link #isDoubleClickZoomEnabled() double click zoom}. Changing this property after the
     * component has been added to the DOM will cause the map to be recreated on the client side.
     *
     * @param doubleClickZoomEnabled true to enable double click zoom, false to disable it.
     * @see #setDoubleClickZoomMode(ZoomMode)
     */
    public void setDoubleClickZoomEnabled(boolean doubleClickZoomEnabled) {
        LeafletMap.doubleClickZoomEnabled.set(this, doubleClickZoomEnabled);
    }

    /**
     * Returns whether double click should zoom to the center of the view or to the location of the mouse pointer
     * (default is the location of the mouse pointer).
     *
     * @see #isDoubleClickZoomEnabled()
     */
    @Nonnull
    public ZoomMode getDoubleClickZoomMode() {
        return doubleClickZoomMode.get(this);
    }

    /**
     * Specifies whether double click should zoom to the center of the view or to the location of the mouse pointer.
     * Changing this property after the component has been added to the DOM will cause the map to be recreated on the
     * client side.
     *
     * @param doubleClickZoomMode the double click zoom mode, {@code null} to reset to the default value.
     * @see #setDoubleClickZoomEnabled(boolean)
     */
    public void setDoubleClickZoomMode(ZoomMode doubleClickZoomMode) {
        LeafletMap.doubleClickZoomMode.set(this, doubleClickZoomMode);
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
     * Enables or disables {@link #isScrollWheelZoomEnabled() scroll wheel zoom}. Changing this property after the
     * component has been added to the DOM will cause the map to be recreated on the client side.
     *
     * @param scrollWheelZoomEnabled true to enable scroll wheel zoom, false to disable it.
     * @see #setScrollWheelZoomMode(ZoomMode)
     */
    public void setScrollWheelZoomEnabled(boolean scrollWheelZoomEnabled) {
        LeafletMap.scrollWheelZoomEnabled.set(this, scrollWheelZoomEnabled);
    }

    /**
     * Returns whether using the mouse wheel should zoom to the center of the view or to the location of the mouse
     * pointer (default is the location of the mouse pointer).
     *
     * @see #isScrollWheelZoomEnabled()
     */
    @Nonnull
    public ZoomMode getScrollWheelZoomMode() {
        return scrollWheelZoomMode.get(this);
    }

    /**
     * Specifies whether using the mouse wheel should zoom to the center of the view or to the location of the mouse
     * pointer. Changing this property after the component has been added to the DOM will cause the map to be recreated
     * on the client side.
     *
     * @param scrollWheelZoomMode the scroll wheel zoom mode, {@code null} to reset to the default value.
     * @see #setScrollWheelZoomEnabled(boolean)
     */
    public void setScrollWheelZoomMode(ZoomMode scrollWheelZoomMode) {
        LeafletMap.scrollWheelZoomMode.set(this, scrollWheelZoomMode);
    }

    /**
     * Returns whether the map can be dragged with the mouse/touch (default is true).
     */
    public boolean isDraggingEnabled() {
        return draggingEnabled.get(this);
    }

    /**
     * Enables or disables {@link #isDraggingEnabled() dragging}. Changing this property after the component has been
     * added to the DOM will cause the map to be recreated on the client side.
     *
     * @param draggingEnabled true to enable dragging, false to disable it.
     */
    public void setDraggingEnabled(boolean draggingEnabled) {
        LeafletMap.draggingEnabled.set(this, draggingEnabled);
    }

    /**
     * Returns the coordinates of the center of the map.
     */
    @Nonnull
    @Synchronize(EVENT_MOVE)
    public DirectPosition getCenter() {
        return center.get(this);
    }

    /**
     * Moves the map so that it is centered over the given coordinates.
     *
     * @param center the new center position, or {@code null} to revert to the default center position.
     */
    public void setCenter(DirectPosition center) {
        // TODO What happens if the max bounds do not allow this operation?
        LeafletMap.center.set(this, center);
    }

    /**
     * Returns the zoom level of the map.
     *
     * @see #getMaxZoom()
     * @see #getMinZoom()
     */
    @Synchronize(EVENT_MOVE)
    public int getZoom() {
        return zoom.get(this);
    }

    /**
     * Sets the zoom level of the map.
     *
     * @param zoom the zoom level.
     * @see #getMaxZoom()
     * @see #getMinZoom()
     */
    public void setZoom(int zoom) {
        // TODO What happens if this is set to something outside the max/min?
        LeafletMap.zoom.set(this, zoom);
    }

    /**
     * Moves the map to the specified {@code center} while changing the zoom level. This is a way of calling
     * {@link #setZoom(int)} and {@link #setCenter(DirectPosition)} in one call.
     *
     * @param center the new center position, never {@code null}.
     * @param zoom   the new zoom level.
     */
    public void setCenterAndZoom(@Nonnull DirectPosition center, int zoom) {
        // TODO Implement me!
        throw new UnsupportedOperationException("This is not implemented yet");
    }

    /**
     * Zooms in to the next zoom level. If the map is already at the highest level, nothing happens.
     */
    public void zoomIn() {
        getElement().callFunction("zoomIn");
    }

    /**
     * Zooms out to the next zoom level. If the map is already at the lowest level, nothing happens.
     */
    public void zoomOut() {
        getElement().callFunction("zoomOut");
    }

    /**
     * Returns the maximum zoom level. This is calculated based on the map layers.
     */
    public int getMaxZoom() {
        return 0; // TODO Implement me!
    }

    /**
     * Returns the minimum zoom level. This is calculated based on the map layers.
     */
    public int getMinZoom() {
        return 0; // TODO Implement me!
    }

    /**
     * Returns the maximum bounds of the map, or {@code null} if there are no bounds (the default).
     */
    public Envelope getMaxBounds() {
        return maxBounds.get(this);
    }

    /**
     * When set, the map restricts the view to the given geographical bounds, bouncing the user back if the user tries
     * to pan outside the view.
     *
     * @param maxBounds the maximum bounds, or {@code null} to remove the bounds.
     */
    public void setMaxBounds(Envelope maxBounds) {
        LeafletMap.maxBounds.set(this, maxBounds);
    }

    /**
     * Returns the geographical bounds currently visible in the map.
     *
     * @return the bounds or {@code null} if this information is not available.
     */
    @Synchronize(EVENT_MOVE)
    public Envelope getBounds() {
        return bounds.get(this);
    }

    /**
     * Registers a listener to be notified when the user clicks on the map.
     *
     * @param clickEventListener the listener, never {@code null}.
     * @return a registration handle for the listener, never {@code null}.
     * @see ClickEvent
     */
    @Nonnull
    public Registration addClickEventListener(@Nonnull ComponentEventListener<ClickEvent> clickEventListener) {
        Objects.requireNonNull(clickEventListener);
        return addListener(ClickEvent.class, clickEventListener);
    }

    /**
     * Registers a listener to be notified when the user has moved the map (i.e. changed the {@link #getCenter() center}
     * either programmatically or by dragging, resizing or zooming).
     *
     * @param moveEventListener the listener, never {@code null}.
     * @return a registration handle for the listener, never {@code null}.
     * @see #setCenter(DirectPosition)
     * @see #setZoom(int)
     * @see MoveEvent
     */
    @Nonnull
    public Registration addMoveEventListener(@Nonnull ComponentEventListener<MoveEvent> moveEventListener) {
        Objects.requireNonNull(moveEventListener);
        return addListener(MoveEvent.class, moveEventListener);
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

    /**
     * Event fired when the map is clicked or tapped. Please note, that at the moment, double clicking the map will
     * result in two click events being fired (see
     * <a href="https://github.com/Leaflet/Leaflet/issues/108">Leaflet issue 108</a>).
     * <p>
     * TODO Fix double click generating two single click events
     * TODO Include info about which marker was clicked, if any
     */
    @DomEvent(EVENT_CLICK)
    public static class ClickEvent extends ComponentEvent<LeafletMap> {

        private final DirectPosition position;

        /**
         * Creates a new event using the given source and indicator whether the
         * event originated from the client side or the server side.
         *
         * @param source     the source component
         * @param fromClient <code>true</code> if the event originated from the client
         * @param position   JSON containing the position of the click event, will be converted using a {@link DirectPositionConverter}.
         */
        public ClickEvent(LeafletMap source, boolean fromClient, @EventData("event.detail.position") JsonObject position) {
            super(source, fromClient);
            this.position = new DirectPositionConverter().fromJson(position);
        }

        /**
         * Returns the geographical position where the map was clicked.
         */
        @Nonnull
        public DirectPosition getPosition() {
            return position;
        }
    }

    /**
     * Event fired when the map is moved in some way (by changing the center coordinates, resizing the map or zooming
     * in or out).
     */
    @DomEvent(EVENT_MOVE)
    public static class MoveEvent extends ComponentEvent<LeafletMap> {

        private final DirectPosition center;
        private final Envelope bounds;
        private final int zoom;

        /**
         * Creates a new event using the given source and indicator whether the
         * event originated from the client side or the server side.
         *
         * @param source     the source component
         * @param fromClient <code>true</code> if the event originated from the client
         * @param center     JSON containing the center coordinates of the map, will be converted using a {@link DirectPositionConverter}.
         * @param bounds     JSON containing the bounds of the map, will be converted using a {@link EnvelopeConverter}.
         * @param zoom       the zoom level of the map.
         */
        public MoveEvent(LeafletMap source, boolean fromClient,
                         @EventData("event.detail.center") JsonObject center,
                         @EventData("event.detail.bounds") JsonObject bounds,
                         @EventData("event.detail.zoom") int zoom) {
            super(source, fromClient);
            this.center = new DirectPositionConverter().fromJson(center);
            this.bounds = new EnvelopeConverter().fromJson(bounds);
            this.zoom = zoom;
            // TODO fromClient will always be true even when the move was originated from the server.
            // This is because the server side move will cause the client side map to change, which will fire a
            // client side event, which in turn is propagated back to the server. This needs to be fixed at some point.
        }

        /**
         * Returns the current center coordinates of the map.
         *
         * @see LeafletMap#getCenter()
         */
        @Nonnull
        public DirectPosition getCenter() {
            return center;
        }

        /**
         * Returns the current bounds of the map.
         *
         * @see LeafletMap#getBounds()
         */
        @Nonnull
        public Envelope getBounds() {
            return bounds;
        }

        /**
         * Returns the current zoom level of the map.
         *
         * @see LeafletMap#getZoom()
         */
        public int getZoom() {
            return zoom;
        }
    }
}
