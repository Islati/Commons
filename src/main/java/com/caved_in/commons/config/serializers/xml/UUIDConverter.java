package com.caved_in.commons.config.serializers.xml;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.util.UUID;

public class UUIDConverter implements Converter<UUID> {
    @Override
    public UUID read(InputNode inputNode) throws Exception {
        return UUID.fromString(inputNode.getValue());
    }

    @Override
    public void write(OutputNode outputNode, UUID uuid) throws Exception {
        String uid = uuid.toString();

        outputNode.setName("uuid");
        outputNode.setValue(uid);
    }
}
