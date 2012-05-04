package br.com.vectorx.shiro;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cache Manager
 */
public class CacheMgr implements CacheManager, Initializable, Destroyable {

    private static final Logger                LOG    = LoggerFactory.getLogger(CacheMgr.class);

    private Map<String, Cache<Object, Object>> caches = new ConcurrentHashMap<String, Cache<Object, Object>>();

    @SuppressWarnings ("unchecked")
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        synchronized (CacheMgr.class) {
            //nome do caching realm:myRealm.authorizationCache
            //nome do caching session:shiro-activeSessionCache
            CacheMgr.LOG.info("Caching for: " + cacheName);
            if (this.caches.get(cacheName) != null) { return (Cache<K, V>) this.caches.get(cacheName); }
            Cache<Object, Object> cache = new CacheImpl<Object, Object>();
            this.caches.put(cacheName, cache);
            return (Cache<K, V>) cache;
        }
    }

    @Override
    public void destroy() throws Exception {
        // XXX faz sentido usar para caches Cluster ou Arquivos
    }

    @Override
    public void init() throws ShiroException {
        // XXX faz sentido usar para caches Cluster ou Arquivos
    }

}
