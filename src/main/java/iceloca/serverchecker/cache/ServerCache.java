package iceloca.serverchecker.cache;

import iceloca.serverchecker.model.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerCache extends InMemoryCache<Long, Server> {
    public ServerCache(@Value("${cache.maxSize}") final Integer maxSize) {
        super(maxSize);
    }
}
