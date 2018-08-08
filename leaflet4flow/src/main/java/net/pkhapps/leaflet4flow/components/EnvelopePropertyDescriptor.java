package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.dom.Element;
import org.opengis.geometry.Envelope;

import javax.annotation.Nonnull;
import java.util.Objects;

class EnvelopePropertyDescriptor implements PropertyDescriptor<Envelope, Envelope> {

    private final String propertyName;
    private final Envelope defaultValue;

    EnvelopePropertyDescriptor(@Nonnull String propertyName, Envelope defaultValue) {
        this.propertyName = Objects.requireNonNull(propertyName);
        this.defaultValue = defaultValue;
    }

    @Override
    public void set(Element element, Envelope value) {

    }

    @Override
    public Envelope get(Element element) {
        return null;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
