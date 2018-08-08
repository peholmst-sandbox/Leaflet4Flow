package net.pkhapps.leaflet4flow.components;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import org.geotools.geometry.Envelope2D;
import org.opengis.geometry.Envelope;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * TODO Document me
 */
public class EnvelopeConverter {

    private final DirectPositionConverter directPositionConverter = new DirectPositionConverter();

    /**
     * @param envelope
     * @return
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
     * @param jsonObject
     * @return
     */
    @Nonnull
    public Envelope fromJson(@Nonnull JsonObject jsonObject) {
        Objects.requireNonNull(jsonObject);
        var southWest = jsonObject.getObject("_southWest");
        var northEast = jsonObject.getObject("_northEast");
        return new Envelope2D(directPositionConverter.fromJson(southWest), directPositionConverter.fromJson(northEast));
    }
}
