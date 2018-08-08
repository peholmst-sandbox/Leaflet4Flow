package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class DemoView extends VerticalLayout {

    public DemoView() {
        setSizeFull();
        var leaflet = new Leaflet();
        leaflet.addLayer(new TileLayer(new URLTemplate("https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}"))
                .setCustomAttribute("accessToken", "pk.eyJ1IjoicGV0dGVyaG9sbXN0cm9tIiwiYSI6ImNqa2NhaTZraDA0OGwzdnV2dHptb242MGkifQ.jy-vjyUjYFwy-JQPB68vyg")
                .setCustomAttribute("id", "mapbox.streets")
                .setMaxZoomLevel(18)
                .setAttribution("Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a>"));
        leaflet.setSizeFull();
        add(leaflet);

        var attributionControl = new Checkbox("Attribution Control", leaflet.isAttributionControlVisible());
        attributionControl.addValueChangeListener(event -> leaflet.setAttributionControlVisible(event.getValue()));

        var zoomControl = new Checkbox("Zoom Control", leaflet.isZoomControlVisible());
        zoomControl.addValueChangeListener(event -> leaflet.setZoomControlVisible(event.getValue()));

        // TODO Button for manually setting the center to somewhere else
        var resetCenter = new Button("Reset Center", event -> leaflet.setCenter(null));
        var zoomIn = new Button("Zoom in", event -> leaflet.zoomIn());
        var zoomOut = new Button("Zoom out", event -> leaflet.zoomOut());

        add(new HorizontalLayout(attributionControl, zoomControl, resetCenter, zoomIn, zoomOut));

        var centerCoordinates = new Text("Center: N/A");
        leaflet.addMoveEventListener(event -> centerCoordinates.setText("Center: " + leaflet.getCenter()));

        var zoomLevel = new Text("Zoom: N/A");
        leaflet.addZoomEventListener(event -> zoomLevel.setText("Zoom: " + leaflet.getZoom()));

        add(new HorizontalLayout(centerCoordinates, zoomLevel));

        // TODO Visible bounds, click listener
    }
}
