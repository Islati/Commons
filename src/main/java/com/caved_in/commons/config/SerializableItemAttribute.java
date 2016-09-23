package com.caved_in.commons.config;

import com.caved_in.commons.item.Attributes;
import com.caved_in.commons.yml.ConfigMode;
import com.caved_in.commons.yml.SerializeOptions;
import com.caved_in.commons.yml.Skip;
import com.caved_in.commons.yml.YamlConfig;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.util.UUID;

@SerializeOptions(
        configMode = ConfigMode.DEFAULT
)
public class SerializableItemAttribute extends YamlConfig {
    private String name;

    private Attributes.AttributeType type;
    private Attributes.Operation operation;
    private double amount;
    private String uid;

    @Skip
    private UUID id;

    public SerializableItemAttribute(Attributes.Attribute attibute) {
        this.name = attibute.getName();
        this.type = attibute.getAttributeType();
        this.operation = attibute.getOperation();
        this.amount = attibute.getAmount();

        this.id = attibute.getUUID();
        this.uid = id.toString();
    }
    public String getName() {
        return name;
    }

    public Attributes.AttributeType getType() {
        return type;
    }

    public Attributes.Operation getOperation() {
        return operation;
    }

    public double getAmount() {
        return amount;
    }

    public UUID getId() {
        return id;
    }

    public Attributes.Attribute build() {
        return Attributes.Attribute.newBuilder().type(getType()).operation(getOperation()).amount(getAmount()).name(getName()).uuid(getId()).build();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Attributes.AttributeType type) {
        this.type = type;
    }

    public void setOperation(Attributes.Operation operation) {
        this.operation = operation;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUid(UUID id) {
        this.id = id;
        this.uid = id.toString();
    }
}
