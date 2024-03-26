package iceloca.serverchecker.cache;

import iceloca.serverchecker.model.Watchlist;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class WatchlistCache {
    private static Map<Long, Watchlist> cacheMap ;
    private final Integer maxSize;

    protected WatchlistCache(@Value("${cache.maxSize}") final Integer size)
    {
        this.maxSize = size;
        cacheMap = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(
                    final Map.Entry eldest) {
                return size() > WatchlistCache.this.maxSize;
            }
        };
    }
    public Watchlist get(final Long key) {
        return cacheMap.get(key);
    }
    public void put(final Long key, final Watchlist value) {
        cacheMap.put(key, value);
    }
    public void remove(final Long key) {
        cacheMap.remove(key);
    }
}
