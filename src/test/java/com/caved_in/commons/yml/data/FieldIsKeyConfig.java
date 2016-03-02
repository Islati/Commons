package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.ConfigMode;
import com.caved_in.commons.yml.SerializeOptions;
import com.caved_in.commons.yml.base.Config;

@SerializeOptions(
        configMode = ConfigMode.FIELD_IS_KEY
)
public class FieldIsKeyConfig extends Config {
    public String MOTD = "&dWelcome to the server {player}!";
    public String PLAYER_CONNECT_PROXY = "{player}&e has joined the server!";

    public FieldIsKeyConfig() {
    }
}
