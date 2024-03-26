package iceloca.serverchecker.cache;

import iceloca.serverchecker.model.ServerType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerTypeCache extends InMemoryCache<Long, ServerType> {
    public ServerTypeCache(@Value("${cache.maxSize}") final Integer maxSize) {
        super(maxSize);
    }
}
