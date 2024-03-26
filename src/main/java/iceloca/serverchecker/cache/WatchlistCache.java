package iceloca.serverchecker.cache;

import iceloca.serverchecker.model.Watchlist;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WatchlistCache extends InMemoryCache<Long, Watchlist> {
    public WatchlistCache(@Value("${cache.maxSize}") final Integer maxSize) {
        super(maxSize);
    }
}
