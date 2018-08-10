package net.pkhapps.leaflet4flow.components;

import org.opengis.geometry.DirectPosition;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * TODO Document me
 */
public class Marker implements Serializable {

    private final String id;
    private final boolean draggable;
    private final String title;
    private DirectPosition position;
    private Object data;
    private Icon icon;

    /**
     * Creates a new {@code Marker}.
     *
     * @param id        the unique ID of the marker (never {@code null}).
     * @param icon      the icon of the marker (never {@code null}).
     * @param draggable whether the marker can be dragged by the user or not.
     * @param title     the title text for the browser tooltip.
     */
    public Marker(@Nonnull String id, @Nonnull Icon icon, boolean draggable, String title) {
        this.id = Objects.requireNonNull(id);
        this.icon = Objects.requireNonNull(icon);
        this.draggable = draggable;
        this.title = Optional.ofNullable(title).orElse("");
    }

    /**
     * Creates a new {@code Marker} with a randomly generated ID.
     *
     * @param icon      the icon of the marker (never {@code null}).
     * @param draggable whether the marker can be dragged by the user or not.
     * @param title     the title text for the browser tooltip.
     */
    public Marker(@Nonnull Icon icon, boolean draggable, String title) {
        this(UUID.randomUUID().toString(), icon, draggable, title);
    }

    /**
     * Creates a new, undraggable {@code Marker} with a randomly generated ID.
     *
     * @param icon  the icon of the marker (never {@code null}).
     * @param title the title text for the browser tooltip.
     */
    public Marker(Icon icon, String title) {
        this(icon, false, title);
    }

    /**
     * Returns a unique ID that is used to identify this marker. This ID cannot be changed once the marker has been
     * created.
     *
     * @return the ID, never {@code null}.
     */
    @Nonnull
    public String getId() {
        return id;
    }

    /**
     * Returns whether the marker can be dragged by the user or not. This flag cannot be changed once the marker has
     * been created.
     */
    public boolean isDraggable() {
        return draggable;
    }

    /**
     * Returns the text for the browser tooltip that appears when the marker is hovered on. This text cannot be changed
     * once the marker has been created.
     *
     * @return the text, or an empty string if there is no tooltip.
     */
    @Nonnull
    public String getTitle() {
        return title;
    }

    /**
     * Returns the icon that is used to represent the marker on the map.
     *
     * @return the icon, never {@code null}.
     */
    @Nonnull
    public Icon getIcon() {
        return icon;
    }

    /**
     * Sets the icon of the marker.
     *
     * @param icon the new icon, never {@code null}.
     */
    public void setIcon(@Nonnull Icon icon) {
        this.icon = Objects.requireNonNull(icon);
    }

    /**
     * Returns the geographical position of the marker.
     *
     * @return the position, never {@code null}.
     */
    @Nonnull
    public DirectPosition getPosition() {
        return position;
    }

    /**
     * Sets the geographical position of the marker.
     *
     * @param position the new position, never {@code null}.
     */
    public void setPosition(@Nonnull DirectPosition position) {
        this.position = Objects.requireNonNull(position);
    }

    /**
     * Returns any user-defined data associated with the marker.
     *
     * @return the data, or {@code null} if not set.
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets any user-defined data to associate with the marker.
     *
     * @param data the data, may be {@code null}.
     */
    public void setData(Object data) {
        this.data = data;
    }

    // TODO Event when the marker position is changed
}
