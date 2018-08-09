package net.pkhapps.leaflet4flow.components;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import org.geotools.geometry.DirectPosition2D;
import org.opengis.geometry.DirectPosition;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Converter that converts between {@link DirectPosition} and a JSON-format that Leaflet understands. It is assumed
 * that the 0-ordinate of the position is the longitude coordinate and the 1-ordinate is the latitude coordinate.
 * Altitude is ignored completely even though it is supported both by {@link DirectPosition} and Leaflet.
 * The {@link DirectPosition#getCoordinateReferenceSystem() CRS} is also ignored.
 *
 * @see EnvelopeConverter
 */
class DirectPositionConverter {

    /**
     * Converts the given {@code directPosition} to a JSON object.
     */
    @Nonnull
    public JsonObject toJson(@Nonnull DirectPosition directPosition) {
        Objects.requireNonNull(directPosition);
        if (directPosition.getDimension() < 2) {
            throw new IllegalArgumentException("DirectPosition must have at least two dimensions");
        }
        var json = Json.createObject();
        json.put("lng", directPosition.getOrdinate(0));
        json.put("lat", directPosition.getOrdinate(1));
        return json;
    }

    /**
     * Converts the given {@code directPosition} to a JSON array {@code [lat, lng]}.
     */
    @Nonnull
    public JsonArray toJsonArray(@Nonnull DirectPosition directPosition) {
        Objects.requireNonNull(directPosition);
        if (directPosition.getDimension() < 2) {
            throw new IllegalArgumentException("DirectPosition must have at least two dimensions");
        }
        var json = Json.createArray();
        json.set(0, directPosition.getOrdinate(1));
        json.set(1, directPosition.getOrdinate(0));
        return json;
    }

    /**
     * Converts the given JSON object to a {@link DirectPosition2D}.
     */
    @Nonnull
    public DirectPosition2D fromJson(@Nonnull JsonObject jsonObject) {
        Objects.requireNonNull(jsonObject);
        var x = jsonObject.getNumber("lng");
        var y = jsonObject.getNumber("lat");
        return new DirectPosition2D(x, y);
    }
}
