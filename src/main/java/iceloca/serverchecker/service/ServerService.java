package iceloca.serverchecker.service;

import java.io.*;
import java.net.*;

import iceloca.serverchecker.cache.ServerCache;
import iceloca.serverchecker.cache.ServerTypeCache;
import iceloca.serverchecker.model.Server;
import iceloca.serverchecker.model.ServerType;
import iceloca.serverchecker.model.dto.ServerDTO;
import iceloca.serverchecker.repository.ServerRepository;
import iceloca.serverchecker.repository.ServerTypeRepository;
import iceloca.serverchecker.service.utility.ServerDTOUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;
    private  final ServerTypeRepository serverTypeRepository;
    private final ServerCache serverCache;
    private final ServerTypeCache serverTypeCache;
    public List<ServerDTO> findAllServers() {
        return serverRepository.findAll().stream()
                .map(ServerDTOUtility::buildDTOFromServer)
                .toList();
    }

    public Server saveServer(ServerDTO serverDTO) {
        ServerType serverType = serverTypeRepository.findByName(serverDTO.getServerType());
        if(serverType == null && serverDTO.getServerType() != null){
            serverType = new ServerType();
            serverType.setName(serverDTO.getServerType());
        }
        Server server = ServerDTOUtility.buildServerFromDTO(serverDTO, serverType);
        serverCache.put(server.getId(),server);
        Server oldServer = findById(server.getId());
        if(oldServer != null && oldServer.getServerType() != null   )
            serverTypeCache.remove(server.getServerType().getId());
        return serverRepository.save(server);
    }

    public Server findById(Long id) {
        Server server = serverCache.get(id);
        if(server != null)
            return server;
        server = serverRepository.findById(id).orElse(null);
        if(server != null)
            serverCache.put(server.getId(),server);
        return server;
    }

    public Server updateServer(ServerDTO serverDTO) {
        boolean wrongId = true;
        if (serverDTO.getId() != null)
             wrongId= serverRepository.findById(serverDTO.getId()).isEmpty();
        if(wrongId)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return saveServer(serverDTO);
    }

    @Transactional
    public void deleteServer(Long id) {
        serverCache.remove(id);
        Server oldServer = findById(id);
        if (oldServer != null) {
            ServerType serverType = oldServer.getServerType();
            if (serverType != null)
                serverTypeCache.remove(serverType.getId());
        }
        serverRepository.deleteById(id);
    }

    public boolean checkServer(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(5000);
        }catch (IOException exception){
            return false;
        }
    }

    public Server updateServerStatusIp(Long id) {
        Server server = findById(id);
        if(server == null)
            return null;
        server.setIsUp(checkServer(server.getIp()));
        if (server.getServerType() != null)
            serverTypeCache.remove(server.getServerType().getId());
        serverCache.put(server.getId(),server);
        return serverRepository.save(server);
    }


}