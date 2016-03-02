package com.caved_in.commons.yml.data;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */

import com.caved_in.commons.yml.base.Config;

import java.util.HashMap;
import java.util.Map;

public class UpdateConfig extends Config {
    public Boolean Enabled = false;
    public Map<String, Integer> restriction = new HashMap<String, Integer>() {{
        put("bedwars.restriction.player", 1);
        put("bedwars.restriction.premium", 3);
    }};
}
