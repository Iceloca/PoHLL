package iceloca.serverchecker.service;

import iceloca.serverchecker.model.ServerType;
import iceloca.serverchecker.model.dto.ServerDTO;
import iceloca.serverchecker.repository.ServerTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ServerTypeService {
    private final ServerTypeRepository repository;
    public List<ServerType> findAllServerTypes() {
        return repository.findAll();
    }

    public ServerType saveServerType(ServerType serverType) {
        return repository.save(serverType);
    }

    public ServerType findByName(String name) {

        return repository.findByName(name);
    }
    public List<ServerDTO> findAllByName(String name) {
        return repository.findAllByName(name).stream()
                .map(ServerDTOUtility::buildDTOFromServer)
                .toList();
    }

    public ServerType updateServerType(ServerType serverType) {
        return repository.save(serverType);
    }

    @Transactional
    public void deleteServerType(String name) {
        repository.deleteByName(name);
    }

}
