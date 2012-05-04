package br.com.vectorx.util;

import java.util.Map.Entry;

/**
 * Utilitario para guardar 2 valores {@link java.util.Map.Entry}
 */
public class EntryWrapper<K, V> implements Entry<K, V> {

    private K key;
    private V value;

    /**
     */
    public EntryWrapper (K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     */
    public void setKey(K key) {
        this.key=key;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return this.value;
    }

}
