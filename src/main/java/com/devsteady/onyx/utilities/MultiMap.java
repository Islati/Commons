package com.devsteady.onyx.utilities;

import java.io.Serializable;
import java.util.*;

public class MultiMap<A, B, C> implements Cloneable, Iterable<A>, Serializable {

    private static final long serialVersionUID = -4128351615302325785L;

    private MultiMapType type;
    private String name = null;
    private HashMap<A, ArrayList<Object>> map = null;

    public MultiMap() {
        type = MultiMapType.NORMAL;
        this.map = new HashMap<A, ArrayList<Object>>();
    }

    public MultiMap(String name) {
        this();
        this.name = (name != null ? name : "MultiMap");
    }

    public MultiMap(int size) {
        type = MultiMapType.NORMAL;
        this.map = new HashMap<A, ArrayList<Object>>(size);
    }

    public MultiMap(String name, int size) {
        this(size);
        this.name = (name != null ? name : "MultiMap");
    }

    public void put(A key, final B firstValue, final C secondValue) {
        map.put(key, new ArrayList<Object>(2) {
            private static final long serialVersionUID = -8841921592042474055L;

            {
                add(firstValue);
                add(secondValue);
            }
        });
    }

    public Set<A> keySet() {
        return map.keySet();
    }

    public Collection<ArrayList<Object>> valueSet() {
        return map.values();
    }

    public void remove(A key) {
        map.remove(key);
    }

    public void remove(int index) {
        remove(getKey(index));
    }

    public void clear() {
        map.clear();
    }

    public int size() {
        return map.size();
    }

    public int hashCode() {
        return (map.hashCode() * map.values().hashCode() + map.keySet().hashCode() * valueSet().hashCode());
    }

    public int getIndex(A key) {
        return new ArrayList<A>(this.keySet()).indexOf(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean containsKey(A key) {
        return map.containsKey(key);
    }

    public boolean containsValue(A key, Object value) {
        return map.get(key).contains(value);
    }

    public boolean containsValue(Object value) {
        for (A key : keySet()) {
            if (containsValue(key, value)) {
                return true;
            }
        }
        return false;
    }

    public MultiMapType getType() {
        return type;
    }

    public A getKey(int index) {
        return new ArrayList<A>(this.keySet()).get(index);
    }

    @SuppressWarnings("unchecked")
    public B getFirstValue(A key) {
        return (B) map.get(key).get(0);
    }

    @SuppressWarnings("unchecked")
    public C getSecondValue(A key) {
        return (C) map.get(key).get(1);
    }

    public MultiMapResource<A, B, C> getResource(A key) {
        return new MultiMapResource<>(key, this);
    }

    public MultiMapResource<A, B, C> getResource(int index) {
        return getResource(getKey(index));
    }

    public void setFirstValue(A key, B value) {
        map.get(key).set(0, value);
    }

    public void setSecondValue(A key, C value) {
        map.get(key).set(1, value);
    }

    @Override
    public String toString() {
        return String.format("(MultiMap[%s]{type=%s, hashCode=%s, size=%s, keys=%s)})",
                String.valueOf(this.name),
                String.valueOf(this.getType()),
                String.valueOf(this.hashCode()),
                String.valueOf(this.size()),
                String.valueOf(this.keySet().toString()));
    }

    @Override
    public MultiMap<A, B, C> clone() {
        MultiMap<A, B, C> cloneMap = new MultiMap<A, B, C>(this.size());

        cloneMap.c(c());
        cloneMap.a(a() + "-" + this.getType().toString());
        cloneMap.b(MultiMapType.CLONE);

        return cloneMap;
    }

    @Override
    public MultiMapIterator<A, B, C> iterator() {
        return new MultiMapIterator<A, B, C>(this);
    }

    @Override
    public boolean equals(Object o) {
        return o.equals(this);
    }


    String a() {
        return this.name;
    }

    void a(String a) {
        this.name = a;
    }

    void b(MultiMapType z) {
        this.type = z;
    }

    HashMap<A, ArrayList<Object>> c() {
        return this.map;
    }

    @SuppressWarnings("unchecked")
    void c(HashMap<A, ArrayList<Object>> c) {
        for (A cc : c.keySet()) {
            this.put(cc, (B) c.get(cc).get(0), (C) c.get(cc).get(1));
        }
    }


    public class MultiMapIterator<A, B, C> implements Iterator<A> {

        private MultiMap<A, B, C> mapBase = null;
        private A currentlyObject = null;
        private int index = 0, lastIndex;

        public MultiMapIterator(MultiMap<A, B, C> mapBase) {
            this.mapBase = mapBase.clone();
        }

        @Override
        public boolean hasNext() {
            return (index < mapBase.size());
        }

        @Override
        public A next() {
            A object = mapBase.getKey(index);
            currentlyObject = object;

            lastIndex = index;
            index++;

            return object;
        }

        public A getCurrently() {
            return currentlyObject;
        }

        public MultiMapResource<A, B, C> getResource() {
            return mapBase.getResource(currentlyObject);
        }

        @Override
        public void remove() {
            mapBase.remove(lastIndex);
        }

    }

    public enum MultiMapType {

        NORMAL,
        CLONE

    }


    public static class MultiMapResource<A, B, C> {

        private A key = null;
        private B firstValue = null;
        private C secondValue = null;

        private int index;

        public MultiMapResource(A key, MultiMap<A, B, C> map) {
            this.key = key;
            this.firstValue = map.getFirstValue(key);
            this.secondValue = map.getSecondValue(key);

            this.index = map.getIndex(key);
        }


        public A getKey() {
            return key;
        }

        public B getFirstValue() {
            return firstValue;
        }

        public C getSecondValue() {
            return secondValue;
        }

        public int getIndex() {
            return index;
        }

    }

}