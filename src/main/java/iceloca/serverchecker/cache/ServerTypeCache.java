package iceloca.serverchecker.cache;

import iceloca.serverchecker.model.ServerType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ServerTypeCache{
    private static Map<Long, ServerType> cacheMap ;
    private final Integer maxSize;

    protected ServerTypeCache(@Value("${cache.maxSize}") final Integer size)
    {
        this.maxSize = size;
        cacheMap = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(
                    final Map.Entry eldest) {
                return size() > ServerTypeCache.this.maxSize;
            }
        };
    }
    public ServerType get(final Long key) {
        return cacheMap.get(key);
    }
    public void put(final Long key, final ServerType value) {
        cacheMap.put(key, value);
    }
    public void remove(final Long key) {
        cacheMap.remove(key);
    }
}
