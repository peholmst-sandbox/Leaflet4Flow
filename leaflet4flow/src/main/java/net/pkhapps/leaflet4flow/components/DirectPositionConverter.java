package net.pkhapps.leaflet4flow.components;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import org.geotools.geometry.DirectPosition2D;
import org.opengis.geometry.DirectPosition;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * TODO Document me
 */
public class DirectPositionConverter {

    /**
     * @param directPosition
     * @return
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
     * @param directPosition
     * @return
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
     * @param jsonObject
     * @return
     */
    @Nonnull
    public DirectPosition2D fromJson(@Nonnull JsonObject jsonObject) {
        Objects.requireNonNull(jsonObject);
        var x = jsonObject.getNumber("lng");
        var y = jsonObject.getNumber("lat");
        return new DirectPosition2D(x, y);
    }
}
