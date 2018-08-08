package net.pkhapps.leaflet4flow.components;

import org.opengis.geometry.Envelope;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

/**
 * TODO Implement me!
 */
public interface MarkerProvider {

    @Nonnull
    Stream<Marker> getMarkersWithin(@Nonnull Envelope envelope);

}
