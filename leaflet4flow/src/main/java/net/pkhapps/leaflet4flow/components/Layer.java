package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.dom.Element;
import elemental.json.Json;
import elemental.json.JsonObject;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * TODO document me!
 *
 * @param <L>
 */
public abstract class Layer<L extends Layer<L>> implements Serializable, Cloneable {

    private static final String JSON_PROPERTY_ATTRIBUTION = "attribution";

    private String attribution;
    private final Map<String, Object> customAttributes = new HashMap<>();

    public String getAttribution() {
        return attribution;
    }

    public L setAttribution(String attribution) {
        this.attribution = attribution;
        return self();
    }

    public <V> L setCustomAttribute(String attributeName, V attributeValue) {
        Objects.requireNonNull(attributeName, "attributeName must not be null");
        if (attributeValue == null) {
            customAttributes.remove(attributeName);
        } else {
            customAttributes.put(attributeName, attributeValue);
        }
        return self();
    }

    public Map<String, Object> getCustomAttributes() {
        return Collections.unmodifiableMap(customAttributes);
    }

    @SuppressWarnings("unchecked")
    protected final L self() {
        return (L) this;
    }

    protected JsonObject toJson() {
        var object = Json.createObject();
        // Custom attributes first. In case they conflict with "official" attributes, the official
        // values will take precedence.
        customAttributes.forEach((key, value) -> object.put(key, value.toString()));
        object.put(JSON_PROPERTY_ATTRIBUTION, attribution);
        return object;
    }

    protected abstract void addToElement(Element element);

    @Override
    @SuppressWarnings("unchecked")
    public L clone() {
        try {
            return (L) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
