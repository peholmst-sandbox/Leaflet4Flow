package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.PropertyDescriptor;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Base class for custom {@link PropertyDescriptor}s used by Leaflet4Flow.
 *
 * @param <S> he type used when setting the property value.
 * @param <G> the type used when getting the property value.
 */
abstract class AbstractPropertyDescriptor<S, G> implements PropertyDescriptor<S, G> {

    private final String propertyName;

    protected AbstractPropertyDescriptor(@Nonnull String propertyName) {
        this.propertyName = Objects.requireNonNull(propertyName);
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
