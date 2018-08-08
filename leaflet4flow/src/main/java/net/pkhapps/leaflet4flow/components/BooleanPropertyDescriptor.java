package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.dom.Element;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Implementation of {@link com.vaadin.flow.component.PropertyDescriptor} that does not remove the default value
 * from the DOM (unlike {@link com.vaadin.flow.component.PropertyDescriptors#propertyWithDefault(String, Boolean)}).
 */
class BooleanPropertyDescriptor extends AbstractPropertyDescriptor<Boolean, Boolean> {

    private final boolean defaultValue;

    BooleanPropertyDescriptor(String propertyName, boolean defaultValue) {
        super(propertyName);
        this.defaultValue = defaultValue;
    }

    @Override
    public void set(Element element, @Nonnull Boolean value) {
        element.setProperty(getPropertyName(), Objects.requireNonNull(value));
    }

    @Override
    public Boolean get(Element element) {
        return element.getProperty(getPropertyName(), defaultValue);
    }
}
