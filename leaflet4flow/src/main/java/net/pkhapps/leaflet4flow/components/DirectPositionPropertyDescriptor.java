package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.dom.Element;
import elemental.json.Json;
import elemental.json.JsonObject;
import org.geotools.geometry.DirectPosition2D;
import org.opengis.geometry.DirectPosition;

import java.util.Objects;

/**
 * {@link PropertyDescriptor}-implementation that works with {@link DirectPosition}s and serializes them to
 * JSON objects with {@code lng} and {@code lat} properties. This descriptor assumes that longitude is
 * {@link DirectPosition#getOrdinate(int) ordinate 0} and latitude is
 * {@link DirectPosition#getOrdinate(int) ordinate 1}.
 * <p>
 * Altitude is ignored completely even though it is supported
 * both by {@link DirectPosition} and Leaflet. The {@link DirectPosition#getCoordinateReferenceSystem() CRS} is also
 * ignored.
 */
class DirectPositionPropertyDescriptor implements PropertyDescriptor<DirectPosition, DirectPosition> {

    private final String propertyName;
    private final DirectPosition defaultValue;

    DirectPositionPropertyDescriptor(String propertyName, DirectPosition defaultValue) {
        this.propertyName = Objects.requireNonNull(propertyName, "propertyName must not be null");
        this.defaultValue = Objects.requireNonNull(defaultValue, "defaultValue must not be null");
    }

    @Override
    public void set(Element element, DirectPosition value) {
        if (value == null) {
            set(element, defaultValue);
        } else {
            if (value.getDimension() < 2) {
                throw new IllegalArgumentException("DirectPosition must have at least two dimensions");
            }
            JsonObject json = Json.createObject();
            json.put("lng", value.getOrdinate(0));
            json.put("lat", value.getOrdinate(1));
            element.setPropertyJson(propertyName, json);
        }
    }

    @Override
    public DirectPosition get(Element element) {
        var propertyValue = element.getPropertyRaw(propertyName);
        if (propertyValue instanceof JsonObject) {
            var json = (JsonObject) propertyValue;
            return new DirectPosition2D(json.getNumber("lng"), json.getNumber("lat"));
        }
        return defaultValue;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
