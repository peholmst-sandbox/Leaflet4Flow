package net.pkhapps.leaflet4flow.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class DemoView extends VerticalLayout {

    public DemoView() {
        setSizeFull();
        Leaflet leaflet = new Leaflet();
        leaflet.addLayer(new TileLayer(new URLTemplate("https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}"))
                .setCustomAttribute("accessToken", "pk.eyJ1IjoicGV0dGVyaG9sbXN0cm9tIiwiYSI6ImNqa2NhaTZraDA0OGwzdnV2dHptb242MGkifQ.jy-vjyUjYFwy-JQPB68vyg")
                .setCustomAttribute("id", "mapbox.streets")
                .setMaxZoomLevel(18)
                .setAttribution("Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a>"));
        leaflet.setSizeFull();
        add(leaflet);
    }
}
