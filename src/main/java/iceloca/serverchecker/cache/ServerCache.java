package iceloca.serverchecker.cache;

import iceloca.serverchecker.model.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ServerCache {
    private static Map<Long, Server> cacheMap ;
    private final Integer maxSize;

    protected ServerCache(@Value("${cache.maxSize}") final Integer size)
    {
        this.maxSize = size;
        cacheMap = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(
                    final Map.Entry eldest) {
                return size() > ServerCache.this.maxSize;
            }
        };
    }
    public Server get(final Long key) {
        return cacheMap.get(key);
    }
    public void put(final Long key, final Server value) {
        cacheMap.put(key, value);
    }
    public void remove(final Long key) {
        cacheMap.remove(key);
    }

}
