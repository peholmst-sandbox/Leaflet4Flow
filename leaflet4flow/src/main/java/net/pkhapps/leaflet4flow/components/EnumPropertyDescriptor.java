package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.dom.Element;

import java.util.Objects;

/**
 * {@link PropertyDescriptor}-implementation that works with {@link Enum}s and serializes them to strings.
 *
 * @param <E> the enum type.
 */
class EnumPropertyDescriptor<E extends Enum<E>> implements PropertyDescriptor<E, E> {

    private final String propertyName;
    private final Class<E> enumClass;
    private final E defaultValue;

    EnumPropertyDescriptor(String propertyName, Class<E> enumClass, E defaultValue) {
        this.propertyName = Objects.requireNonNull(propertyName, "propertyName must not be null");
        this.enumClass = Objects.requireNonNull(enumClass, "enumClass must not be null");
        this.defaultValue = Objects.requireNonNull(defaultValue, "defaultValue must not be null");
    }

    @Override
    public void set(Element element, E value) {
        if (value == null) {
            element.setProperty(propertyName, defaultValue.toString());
        } else {
            element.setProperty(propertyName, value.toString());
        }
    }

    @Override
    public E get(Element element) {
        var elementValue = element.getProperty(propertyName);
        return elementValue == null ? defaultValue : Enum.valueOf(enumClass, elementValue);
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
