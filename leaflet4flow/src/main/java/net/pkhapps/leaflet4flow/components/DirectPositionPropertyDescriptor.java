package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.dom.Element;
import elemental.json.JsonObject;
import org.opengis.geometry.DirectPosition;

import javax.annotation.Nonnull;
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
class DirectPositionPropertyDescriptor extends AbstractPropertyDescriptor<DirectPosition, DirectPosition> {

    private final DirectPosition defaultValue;
    private final DirectPositionConverter converter = new DirectPositionConverter();

    DirectPositionPropertyDescriptor(@Nonnull String propertyName, @Nonnull DirectPosition defaultValue) {
        super(propertyName);
        this.defaultValue = Objects.requireNonNull(defaultValue, "defaultValue must not be null");
    }

    @Override
    public void set(Element element, DirectPosition value) {
        if (value == null) {
            set(element, defaultValue);
        } else {
            element.setPropertyJson(getPropertyName(), converter.toJson(value));
        }
    }

    @Override
    public DirectPosition get(Element element) {
        var propertyValue = element.getPropertyRaw(getPropertyName());
        if (propertyValue instanceof JsonObject) {
            return converter.fromJson((JsonObject) propertyValue);
        }
        return defaultValue;
    }
}
