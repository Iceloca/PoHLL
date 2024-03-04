package iceloca.serverchecker.service;

import java.io.*;
import java.net.*;

import iceloca.serverchecker.model.Server;
import iceloca.serverchecker.repository.ServerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ServerService {

    private final ServerRepository repository;
    public List<Server> findAllServers() {
        return repository.findAll();
    }

    public Server saveServer(Server server) {
        return repository.save(server);
    }

    public Server findByIp(String ip) {
        return repository.findByIp(ip);
    }

    public Server updateServer(Server server) {
        return repository.save(server);
    }

    @Transactional
    public void deleteServer(String ip) {
        repository.deleteByIp(ip);
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
        server.setIsUp(checkServer(ip));
        return saveServer(server);
    }


}