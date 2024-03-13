package iceloca.serverchecker.service;

import java.io.*;
import java.net.*;

import iceloca.serverchecker.model.Server;
import iceloca.serverchecker.model.ServerType;
import iceloca.serverchecker.model.dto.ServerDTO;
import iceloca.serverchecker.repository.ServerRepository;
import iceloca.serverchecker.repository.ServerTypeRepository;
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
    public List<ServerDTO> findAllServers() {
        return serverRepository.findAll().stream()
                .map(ServerDTOUtility::buildDTOFromServer)
                .toList();
    }

    public Server saveServer(ServerDTO serverDTO) {
        ServerType serverType = serverTypeRepository.findByName(serverDTO.getServerType());
        if(serverType == null){
            serverType = new ServerType();
            serverType.setName(serverDTO.getServerType());
        }
        Server server = ServerDTOUtility.buildServerFromDTO(serverDTO, serverType);
        return serverRepository.save(server);
    }

    public Server findByIp(String ip) {
        return serverRepository.findByIp(ip);
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
    public void deleteServer(String ip) {
        serverRepository.deleteByIp(ip);
    }

    public boolean checkServer(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(5000);
        }catch (IOException exception){
            return false;
        }
    }

    public Server updateServerStatusIp(String ip) {
        Server server = findByIp(ip);
        if(server == null)
            return null;
        server.setIsUp(checkServer(ip));
        return serverRepository.save(server);
    }


}