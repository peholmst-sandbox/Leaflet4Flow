package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.dom.Element;
import elemental.json.JsonObject;
import org.opengis.geometry.Envelope;

import javax.annotation.Nonnull;

/**
 * {@link PropertyDescriptor}-implementation that works with {@link Envelope}s and serializes them to JSON using a
 * {@link EnvelopeConverter}. This converter does not support default values so if there is no value, null is returned
 * and if the value is set to null, the property is removed from the element.
 */
class EnvelopePropertyDescriptor extends AbstractPropertyDescriptor<Envelope, Envelope> {

    private final EnvelopeConverter converter = new EnvelopeConverter();

    EnvelopePropertyDescriptor(@Nonnull String propertyName) {
        super(propertyName);
    }

    @Override
    public void set(Element element, Envelope value) {
        if (value == null) {
            element.removeProperty(getPropertyName());
        } else {
            element.setPropertyJson(getPropertyName(), converter.toJson(value));
        }
    }

    @Override
    public Envelope get(Element element) {
        var propertyValue = element.getPropertyRaw(getPropertyName());
        if (propertyValue instanceof JsonObject) {
            return converter.fromJson((JsonObject) propertyValue);
        }
        return null;
    }
}
