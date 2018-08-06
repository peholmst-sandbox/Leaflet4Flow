package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Tag("leaflet-element")
@HtmlImport("bower_components/leaflet-element/leaflet-element.html")
public class Leaflet extends Component implements HasSize {

    private final List<Layer<?>> layers = new ArrayList<>();

    public <L extends Layer<L>> void addLayer(L layer) {
        Objects.requireNonNull(layer, "layer must not be null");
        // TODO Either make Layer
        // immutable (using the builder pattern for example) or make sure the setter methods update the UI after the layer has been added
        layers.add(layer.clone());
        layer.addToElement(getElement());
    }

    public Stream<Layer<?>> getLayers() {
        return layers.stream();
    }
}
