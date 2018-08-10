package net.pkhapps.leaflet4flow.components;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.Envelope2D;
import org.opengis.geometry.Envelope;

import javax.annotation.Nonnull;

/**
 * Utility methods for working with {@link org.opengis.geometry.Envelope}s.
 */
final class EnvelopeUtils {

    private EnvelopeUtils() {
    }

    /**
     * Returns the width of the given envelope.
     */
    public static double getWidth(@Nonnull Envelope envelope) {
        return envelope.getSpan(DirectPositionConverter.LNG_X_ORDINATE);
    }

    /**
     * Returns the height of the given envelope.
     */
    public static double getHeight(@Nonnull Envelope envelope) {
        return envelope.getSpan(DirectPositionConverter.LAT_Y_ORDINATE);
    }

    /**
     * Creates a new envelope based on the passed in envelope.
     *
     * @param envelope  the envelope to base the new envelope on.
     * @param minXDelta the change of the minimum X (longitude) coordinate.
     * @param minYDelta the change of the minimum Y (latitude) coordinate.
     * @param maxXDelta the change of the maximum X (longitude) coordinate.
     * @param maxYDelta the change of the maximum Y (latitude) coordinate.
     * @return a new envelope.
     */
    public static Envelope2D resize(@Nonnull Envelope envelope, double minXDelta, double minYDelta, double maxXDelta,
                                    double maxYDelta) {
        var min = envelope.getLowerCorner();
        var max = envelope.getUpperCorner();

        var newMin = new DirectPosition2D(
                min.getOrdinate(DirectPositionConverter.LNG_X_ORDINATE) + minXDelta,
                min.getOrdinate(DirectPositionConverter.LAT_Y_ORDINATE) + minYDelta);
        var newMax = new DirectPosition2D(
                max.getOrdinate(DirectPositionConverter.LNG_X_ORDINATE) + maxXDelta,
                max.getOrdinate(DirectPositionConverter.LAT_Y_ORDINATE) + maxYDelta);

        return new Envelope2D(newMin, newMax);
    }

}
