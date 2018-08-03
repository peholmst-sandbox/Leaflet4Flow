package net.pkhapps.leaflet4flow.demo;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import net.pkhapps.leaflet4flow.components.Leaflet;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        setSizeFull();
        Leaflet leaflet = new Leaflet();
        leaflet.setSizeFull();
        add(leaflet);
    }
}
