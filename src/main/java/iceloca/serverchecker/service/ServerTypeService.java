package iceloca.serverchecker.service;

import iceloca.serverchecker.model.Server;
import iceloca.serverchecker.model.ServerType;
import iceloca.serverchecker.model.dto.ServerDTO;
import iceloca.serverchecker.repository.ServerTypeRepository;
import iceloca.serverchecker.service.utility.ServerDTOUtility;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ServerTypeService {
    private final ServerTypeRepository serverTypeRepository;
    public List<ServerType> findAllServerTypes() {
        return serverTypeRepository.findAll();
    }

    public ServerType saveServerType(ServerType serverType) {
        return serverTypeRepository.save(serverType);
    }

    public ServerType findByName(String name) {
        return serverTypeRepository.findByName(name);
    }
    public List<ServerDTO> findAllByName(String name) {
        return serverTypeRepository.findAllByName(name).stream()
                .map(ServerDTOUtility::buildDTOFromServer)
                .toList();
    }

    public ServerType updateServerType(ServerType serverType) {
        return serverTypeRepository.save(serverType);
    }

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "serverList")
    @Transactional
    public void deleteServerType(String name) {
        ServerType serverType = serverTypeRepository.findByName(name);
        for(Server server : serverType.getServerList())
            server.setServerType(null);
        serverTypeRepository.deleteByName(name);
    }

}
