package net.pkhapps.leaflet4flow.components;


import java.util.Objects;

public class URLTemplate {

    private final String template;

    public URLTemplate(String template) {
        this.template = Objects.requireNonNull(template, "template must not be null");
        // TODO Validate the template
    }

    public String getTemplate() {
        return template;
    }
}
