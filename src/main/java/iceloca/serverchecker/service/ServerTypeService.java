package iceloca.serverchecker.service;

import iceloca.serverchecker.cache.ServerCache;
import iceloca.serverchecker.cache.ServerTypeCache;
import iceloca.serverchecker.model.Server;
import iceloca.serverchecker.model.ServerType;
import iceloca.serverchecker.model.dto.ServerDTO;
import iceloca.serverchecker.repository.ServerTypeRepository;
import iceloca.serverchecker.service.utility.ServerDTOUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServerTypeService {
    private final ServerTypeRepository serverTypeRepository;
    private final ServerTypeCache serverTypeCache;
    private final ServerCache serverCache;
    public List<ServerType> findAllServerTypes() {
        return serverTypeRepository.findAll();
    }

    public ServerType saveServerType(ServerType serverType) {
        for(Server server : serverType.getServerList())
            serverCache.remove(server.getId());
        serverTypeCache.put(serverType.getId(),serverType);
        return serverTypeRepository.save(serverType);
    }

    public ServerType findByName(String name) {
        ServerType serverType = serverTypeRepository.findByName(name);
        if(serverType != null)
            serverTypeCache.put(serverType.getId(),serverType);
        return serverType;
    }
    public List<ServerDTO> findAllByName(String name) {
        return serverTypeRepository.findAllByName(name).stream()
                .map(ServerDTOUtility::buildDTOFromServer)
                .toList();
    }

    public ServerType updateServerType(ServerType serverType) {
        return saveServerType(serverType);
    }


    @Transactional
    public void deleteServerType(Long id) {
        Optional<ServerType> serverType = serverTypeRepository.findById(id);
        if(serverType.isEmpty())
            return;
        for(Server server : serverType.get().getServerList()) {
            serverCache.remove(server.getId());
            server.setServerType(null);
        }
        serverCache.remove(serverType.get().getId());
        serverTypeRepository.deleteById(id);
    }

}
