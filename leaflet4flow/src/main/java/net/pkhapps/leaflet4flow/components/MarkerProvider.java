package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.shared.Registration;
import org.opengis.geometry.Envelope;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Interface for providing {@link Marker}s to a {@link LeafletMap} in a lazy-loading fashion.
 */
public interface MarkerProvider extends Serializable {

    /**
     * Returns a new marker view for the given bounds.
     *
     * @param bounds the bounds of the marker view, never {@code null}.
     * @return a new marker view, never {@code null}.
     */
    @Nonnull
    MarkerView getMarkerView(@Nonnull Envelope bounds);

    /**
     * Interface representing a marker view, which can be thought of as a subset of all the available markers, based
     * on geographical bounds. This makes it possible to support large amounts of markers without adding them all
     * to the map and keeping them all in memory.
     */
    interface MarkerView {

        /**
         * Returns the bounds of this marker view.
         *
         * @return the bounds, never {@code null}.
         */
        @Nonnull
        Envelope getBounds();

        /**
         * Adds a listener to be notified whenever markers are added to this view, either by creating or
         * changing their positions so that they move inside the {@link #getBounds() bounds} of this view.
         *
         * @param listener the listener to add, never {@code null}.
         * @return a registration handle for the listener.
         */
        @Nonnull
        Registration addMarkersAddedToViewListener(@Nonnull SerializableConsumer<Iterable<Marker>> listener);

        /**
         * Adds a listener to be notified whenever markers are removed from this view, either by deleting
         * or changing their positions so that they move outside the {@link #getBounds() bounds} of this view.
         *
         * @param listener the listener to add, never {@code null}.
         * @return a registration handle for the listener.
         */
        @Nonnull
        Registration addMarkersRemovedFromViewListener(@Nonnull SerializableConsumer<Iterable<Marker>> listener);

        /**
         * Adds a listener to be notified whenever markers are updated somehow but remain in the view.
         *
         * @param listener the listener to add, never {@code null}.
         * @return a registration handle for the listener.
         */
        @Nonnull
        Registration addMarkersUpdatedInViewListener(@Nonnull SerializableConsumer<Iterable<Marker>> listener);

        /**
         * Returns the markers currently visible in this view. The zoom level is provided so that the view
         * implementation can choose to combine many close markers into a single one if needed.
         *
         * @param zoomLevel the current zoom level of the map.
         * @return a stream of markers, never {@code null}.
         */
        @Nonnull
        Stream<Marker> getMarkersInView(int zoomLevel);
    }
}
