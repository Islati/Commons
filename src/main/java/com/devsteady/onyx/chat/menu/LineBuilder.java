package com.devsteady.onyx.chat.menu;

import com.devsteady.onyx.Onyx;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class LineBuilder {

    public static LineBuilder of(String text) {
        return new LineBuilder(text);
    }

    BaseComponent base;
    protected ChatMenu container;
    protected List<String> listenerKeys = new ArrayList<>();

    /**
     * Construct a new empty LineBuilder
     */
    public LineBuilder() {
        this("");
    }

    /**
     * Construct a new LineBuilder with the specified text
     *
     * @param text
     */
    public LineBuilder(String text) {
        base = new TextComponent(text);
    }

    /**
     * Append text
     *
     * @param text {@link String} to append
     * @return the LineBuilder
     */
    public LineBuilder append(String... text) {
        for (String s : text) {
            base.addExtra(s);
        }
        return this;
    }

    /**
     * Append {@link BaseComponent}s
     *
     * @param components {@link BaseComponent}s to append
     * @return the LineBuilder
     */
    public LineBuilder append(BaseComponent... components) {
        for (BaseComponent component : components) {
            base.addExtra(component);
        }
        return this;
    }

    /**
     * Append a {@link ChatMenuComponent} (for example a {@link ChatMenuComponentCheckbox})
     *
     * @param component {@link ChatMenuComponent} to append
     * @return the LineBuilder
     */
    public LineBuilder append(ChatMenuComponent component) {
        component.appendTo(this);
        return this;
    }

    /**
     * Append {@link BaseComponent}s with a {@link ChatMenuListener}
     *
     * @param listener   {@link ChatMenuListener}
     * @param components {@link BaseComponent}s to append
     * @return
     */
    public LineBuilder append(ChatMenuListener listener, BaseComponent... components) {
        for (BaseComponent component : components) {
            String key = Onyx.getInstance().getChatMenuListener().registerListener(listener);
            //todo implement hover event listeners.
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/chatmenu " + key));
            listenerKeys.add(key);

            base.addExtra(component);
        }
        return this;
    }

    protected LineBuilder withContainer(ChatMenu container) {
        this.container = container;
        return this;
    }

    /**
     * Internal method to access the {@link ChatMenu}
     */
    public ChatMenu getContainer() {
        return this.container;
    }

    /**
     * Builds the line
     *
     * @return a {@link BaseComponent}
     */
    public BaseComponent build() {
        return this.base;
    }

}