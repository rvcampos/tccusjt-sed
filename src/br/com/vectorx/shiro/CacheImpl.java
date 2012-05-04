package br.com.vectorx.shiro;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/**
 * Cache de chaves
 */
public class CacheImpl<K, V> implements Cache<K, V> {

    private Map<K, V> cache = new ConcurrentHashMap<K, V>();

    @Override
    public void clear() throws CacheException {
        this.cache.clear();
    }

    @Override
    public V get(K key) throws CacheException {
        return this.cache.get(key);
    }

    @Override
    public Set<K> keys() {
        return this.cache.keySet();
    }

    @Override
    public V put(K key, V value) throws CacheException {
        this.cache.put(key, value);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        return this.cache.remove(key);
    }

    @Override
    public int size() {
        return this.cache.size();
    }

    @Override
    public Collection<V> values() {
        return this.cache.values();
    }

}
