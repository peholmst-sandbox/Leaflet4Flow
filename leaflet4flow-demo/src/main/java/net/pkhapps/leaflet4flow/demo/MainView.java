package net.pkhapps.leaflet4flow.demo;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import net.pkhapps.leaflet4flow.components.LeafletMap;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        setSizeFull();
        LeafletMap leafletMap = new LeafletMap();
        leafletMap.setSizeFull();
        add(leafletMap);
    }
}
