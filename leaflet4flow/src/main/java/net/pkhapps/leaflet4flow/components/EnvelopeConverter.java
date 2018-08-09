package net.pkhapps.leaflet4flow.components;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import org.geotools.geometry.Envelope2D;
import org.opengis.geometry.Envelope;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Converter that converts between {@link Envelope} and a JSON-format that Leaflet understands. It is assumed that
 * the {@link Envelope#getLowerCorner() lower corner} is the south-west corner and the
 * {@link Envelope#getUpperCorner() upper corner} is the north-east corner. The {@link DirectPositionConverter} is
 * used to convert the corner positions.
 */
class EnvelopeConverter {

    private final DirectPositionConverter directPositionConverter = new DirectPositionConverter();

    /**
     * Converts the given {@link Envelope} to a JSON array {@code [[lower lat, lower lng], [upper lat, upper lng]]}.
     */
    @Nonnull
    public JsonArray toJson(@Nonnull Envelope envelope) {
        Objects.requireNonNull(envelope);
        if (envelope.getDimension() < 2) {
            throw new IllegalArgumentException("Envelope must have at least two dimensions");
        }
        var json = Json.createArray();
        json.set(0, directPositionConverter.toJsonArray(envelope.getLowerCorner()));
        json.set(1, directPositionConverter.toJsonArray(envelope.getUpperCorner()));
        return json;
    }

    /**
     * Converts the given JSON object to an {@link Envelope2D}.
     */
    @Nonnull
    public Envelope2D fromJson(@Nonnull JsonObject jsonObject) {
        Objects.requireNonNull(jsonObject);
        var southWest = jsonObject.getObject("_southWest");
        var northEast = jsonObject.getObject("_northEast");
        return new Envelope2D(directPositionConverter.fromJson(southWest), directPositionConverter.fromJson(northEast));
    }
}
