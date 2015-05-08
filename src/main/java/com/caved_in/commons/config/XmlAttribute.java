package com.caved_in.commons.config;

import com.caved_in.commons.item.Attributes;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.util.UUID;

@Root(name = "item-attribute")
public class XmlAttribute {
    @Attribute(name = "name")
    private String name;

    @Attribute(name = "type")
    private Attributes.AttributeType type;

    @Attribute(name = "operation")
    private Attributes.Operation operation;

    @Attribute(name = "amount")
    private double amount;

    @Attribute(name = "id")
    private String uid;

    private UUID id;

    public XmlAttribute(Attributes.Attribute attibute) {
        this.name = attibute.getName();
        this.type = attibute.getAttributeType();
        this.operation = attibute.getOperation();
        this.amount = attibute.getAmount();

        this.id = attibute.getUUID();
        this.uid = id.toString();
    }

    public XmlAttribute(@Attribute(name = "name") String name, @Attribute(name = "type") Attributes.AttributeType type, @Attribute(name = "operation") Attributes.Operation operation, @Attribute(name = "amount") double amount, @Attribute(name = "id") String uid) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.uid = uid;
        this.id = UUID.fromString(uid);
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
