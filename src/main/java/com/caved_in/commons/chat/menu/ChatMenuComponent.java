package com.caved_in.commons.chat.menu;

import net.md_5.bungee.api.chat.TextComponent;

/**
 * Core class for MenuComponents
 */
public abstract class ChatMenuComponent {

    TextComponent component = new TextComponent();

    public ChatMenuComponent() {
    }

    protected void updateText() {
        component.setText(render());
    }

    /**
     * Renders the Component
     */
    public String render() {
        return "";
    }

    /**
     * Append the component to a {@link LineBuilder}
     */
    public abstract ChatMenuComponent appendTo(LineBuilder builder);

}