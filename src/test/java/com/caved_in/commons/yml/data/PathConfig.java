package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.Comment;
import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.base.Config;

public class PathConfig extends Config {
    @Comment("test")
    @Path("config-with-dash")
    public Boolean Test = true;
}
