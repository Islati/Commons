package com.caved_in.commons.chat.menu;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * Checkbox MenuComponent
 * States are either <code>true</code> or <code>false</code> (checked/unchecked) returned by {@link #isChecked()}
 * By default is rendered either as <i>[✔]</i> or <i>[✖]</i>
 */
public class ChatMenuComponentCheckbox extends ChatMenuComponent {

    public static final String DEFAULT_FORMAT = " [%s] ";
    public static final String YES = "✔";
    public static final String NO = "✖";
    public static final String EMPTY = " ";

    private boolean checked;
    private String format = DEFAULT_FORMAT;
    private String stringChecked = YES;
    private String stringUnchecked = NO;

    private ValueListener<Boolean> valueListener;

    /**
     * Construct a new Checkbox
     */
    public ChatMenuComponentCheckbox() {
        this(false);
    }

    /**
     * Construct a new Checkbox
     *
     * @param checked if <code>true</code> the checkbox will be checked
     */
    public ChatMenuComponentCheckbox(boolean checked) {
        this.checked = checked;
        updateText();
    }

    /**
     * Changes the default format (<i> [%s] </i>)
     *
     * @param format the new format, must contain a variable (e.g. <i>%s</i>
     * @return the Checkbox
     */
    public ChatMenuComponentCheckbox withFormat(@Nonnull String format) {
        this.format = format;
        updateText();
        return this;
    }

    /**
     * Changes the default <b>checked</b> string (<i>✔</i>)
     *
     * @param stringChecked the new string
     * @return the Checkbox
     */
    public ChatMenuComponentCheckbox withCheckedString(@Nonnull String stringChecked) {
        this.stringChecked = stringChecked;
        updateText();
        return this;
    }

    /**
     * Changes the default <b>unchecked</b> string (<i>✖</i>)
     *
     * @param stringUnchecked the new string
     * @return the Checkbox
     */
    public ChatMenuComponentCheckbox withUncheckedString(@Nonnull String stringUnchecked) {
        this.stringUnchecked = stringUnchecked;
        updateText();
        return this;
    }

    /**
     * @return <code>true</code> if the checkbox is checked, <code>false</code> otherwise
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Change the <i>checked</i>-state of the checkbox
     *
     * @param checked <code>true</code> if the checkbox should be set checked, <code>false</code> otherwise
     * @return the Checkbox
     */
    public ChatMenuComponentCheckbox setChecked(boolean checked) {
        this.checked = checked;
        updateText();
        return this;
    }

    /**
     * Add a {@link ValueListener} to be called when the value updates
     *
     * @param listener {@link ValueListener} to add
     * @return the Checkbox
     */
    public ChatMenuComponentCheckbox onChange(final ValueListener<Boolean> listener) {
        this.valueListener = listener;
        return this;
    }

    @Override
    public String render() {
        String formatted = this.format;

        formatted = String.format(formatted, isChecked() ? stringChecked : stringUnchecked);

        return formatted;
    }

    public TextComponent build() {
        return this.component;
    }

    public ChatMenuComponent appendTo(final LineBuilder builder) {
        builder.append(new ChatMenuListener() {
            @Override
            public void onClick(Player player) {
                boolean wasChecked = isChecked();
                setChecked(!wasChecked);
                if (valueListener != null) {
                    valueListener.onChange(player, wasChecked, isChecked());
                }
                if (builder.getContainer() != null) {
                    builder.getContainer().refreshContent();
                }
            }
        }, build());
        return this;
    }
}