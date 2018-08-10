package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.Envelope2D;

@Route("")
public class DemoView extends VerticalLayout {

    public DemoView() {
        setSizeFull();
        var leaflet = new LeafletMap();
        leaflet.addLayer(new TileLayer(new URLTemplate("https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}"))
                .setCustomAttribute("accessToken", "pk.eyJ1IjoicGV0dGVyaG9sbXN0cm9tIiwiYSI6ImNqa2NhaTZraDA0OGwzdnV2dHptb242MGkifQ.jy-vjyUjYFwy-JQPB68vyg")
                .setCustomAttribute("id", "mapbox.streets")
                .setMaxZoomLevel(18)
                .setAttribution("Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a>"));
        leaflet.setSizeFull();
        add(leaflet);

        var limitBounds = new Checkbox("Limit Bounds");
        limitBounds.addValueChangeListener(event -> {
            if (event.getValue()) {
                leaflet.setMaxBounds(new Envelope2D(new DirectPosition2D(21.1117959, 60.0158351),
                        new DirectPosition2D(22.6224160, 60.5059471)));
            } else {
                leaflet.setMaxBounds(null);
            }
        });

        var attributionControl = new Checkbox("Attribution Control", leaflet.isAttributionControlVisible());
        attributionControl.addValueChangeListener(event -> leaflet.setAttributionControlVisible(event.getValue()));

        var zoomControl = new Checkbox("Zoom Control", leaflet.isZoomControlVisible());
        zoomControl.addValueChangeListener(event -> leaflet.setZoomControlVisible(event.getValue()));

        var resetCenter = new Button("Reset Center", event -> leaflet.setCenter(null));
        var changeCenter = new Button("Change Center", event -> leaflet.setCenter(new DirectPosition2D(22.3010355, 60.3067342)));
        var zoomIn = new Button("Zoom in", event -> leaflet.zoomIn());
        var zoomOut = new Button("Zoom out", event -> leaflet.zoomOut());

        add(new HorizontalLayout(attributionControl, zoomControl, limitBounds, resetCenter, changeCenter, zoomIn, zoomOut));

        var centerCoordinates = new Span("Center: N/A");
        var zoomLevel = new Span("Zoom: N/A");
        var bounds = new Span("Bounds: N/A");
        add(centerCoordinates, zoomLevel, bounds);

        // Test the properties (should have been updated when the event is fired)
        leaflet.addMoveEventListener(event -> {
            centerCoordinates.setText("Center: " + leaflet.getCenter());
            zoomLevel.setText("Zoom: " + leaflet.getZoom());
            bounds.setText("Bounds: " + leaflet.getBounds());
        });

        // Test the events (should contain the same information as the updated properties)
        leaflet.addClickEventListener(event -> Notification.show("The map was clicked at " + event.getPosition(),
                1000, Notification.Position.BOTTOM_END));
        leaflet.addMoveEventListener(event -> System.out.println("The map was moved, center is " + event.getCenter()
                + ", bounds are " + event.getBounds() + ", zoom is " + event.getZoom() + ", fromClient is " + event.isFromClient()));
    }
}
