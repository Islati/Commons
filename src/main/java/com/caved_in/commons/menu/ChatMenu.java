package com.caved_in.commons.menu;

import com.caved_in.commons.Commons;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nonnegative;
import java.util.ArrayList;
import java.util.List;

public class ChatMenu {

    private List<BaseComponent> lines = new ArrayList<>();
    private List<String> listenerKeys = new ArrayList<>();

    private List<LineCallback> callbackLines = new ArrayList<>();

    private List<HumanEntity> viewers = new ArrayList<>();

    /**
     * Construct a new ChatMenu
     */
    public ChatMenu() {
    }

    /**
     * Add lines
     *
     * @param builders Array of {@link LineBuilder}
     * @return the ChatMenu
     */
    public ChatMenu withLine(LineBuilder... builders) {
        for (LineBuilder builder : builders) {
            listenerKeys.addAll(builder.listenerKeys);
            builder.withContainer(this);
            this.lines.add(builder.build());
        }
        return this;
    }

    public ChatMenu withLine(String text, ChatMenuListener onClick) {
        return withLine(new LineBuilder().append(onClick, new TextComponent(text)));
    }

    /**
     * Add a line at the specified index
     *
     * @param index   Position of the line
     * @param builder {@link LineBuilder} to add
     * @return the ChatMenu
     */
    public ChatMenu withLine(@Nonnegative int index, LineBuilder builder) {
        listenerKeys.addAll(builder.listenerKeys);
        builder.withContainer(this);
        if (lines.size() <= index) {
            this.lines.add(index, builder.build());
        } else {
            this.lines.set(index, builder.build());
        }
        return this;
    }

    /**
     * Add lines
     *
     * @param lines Array of {@link String} lines to add
     * @return the ChatMenu
     */
    public ChatMenu withLine(String... lines) {
        for (String line : lines) {
            this.lines.add(new TextComponent(line));
        }
        return this;
    }

    public ChatMenu withLine(TextComponent... textComponents) {
        for (TextComponent comp : textComponents) {
            this.lines.add(comp);
        }
        return this;
    }

    /**
     * Add a line using a {@link LineCallback}
     * The callback will be called when {@link #refreshContent()} is called
     *
     * @param callback {@link LineCallback}
     * @return the ChatMenu
     */
    public ChatMenu withLine(LineCallback callback) {
        callbackLines.add(callback);
        return this;
    }

    /**
     * Shows the menu to the viewers
     *
     * @param viewers Array of {@link HumanEntity}
     * @return the ChatMenu
     */
    public ChatMenu show(HumanEntity... viewers) {
        BaseComponent[] components = build();
        for (HumanEntity viewer : viewers) {
            if (viewer instanceof Player) {
                for (BaseComponent component : components) {
                    ((Player) viewer).spigot().sendMessage(component);
                }
                if (!this.viewers.contains(viewer)) {
                    this.viewers.add(viewer);
                }
            }
        }
        return this;
    }

    /**
     * Refresh the menu
     * Will call all {@link LineCallback}s registered with {@link #withLine(LineCallback)}
     *
     * @return the ChatMenu
     */
    public ChatMenu refreshContent() {
        for (LineCallback callback : callbackLines) {
            int index = callback.getIndex();
            LineBuilder builder = callback.getLine();

            withLine(index, builder);
        }

        for (HumanEntity viewer : this.viewers) {
            show(viewer);
        }
        return this;
    }

    /**
     * Builds the menu
     *
     * @return Array of {@link BaseComponent}
     */
    public BaseComponent[] build() {
        return this.lines.toArray(new BaseComponent[this.lines.size()]);
    }

    public void dispose() {
        for (String key : this.listenerKeys) {
            Commons.getInstance().getChatMenuListener().unregisterListener(key);
        }
    }
}