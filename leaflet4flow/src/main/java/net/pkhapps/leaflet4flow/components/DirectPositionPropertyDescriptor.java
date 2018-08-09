package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.dom.Element;
import elemental.json.JsonObject;
import org.opengis.geometry.DirectPosition;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * {@link PropertyDescriptor}-implementation that works with {@link DirectPosition}s and serializes them to
 * JSON objects using a {@link DirectPositionConverter}. Null values are converted to a default value, that
 * must not be null. The default value is also passed down to the element (i.e. the property is not removed from the DOM
 * when the default value is set).
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
