package iceloca.serverchecker.service;

import java.io.*;
import java.net.*;

import iceloca.serverchecker.model.Server;
import iceloca.serverchecker.repository.ServerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Primary
@AllArgsConstructor
public class ServerServiceImpl implements ServerService{

    private final ServerRepository repository;
    @Override
    public List<Server> findAllServers() {
        return repository.findAll();
    }

    @Override
    public Server saveServer(Server server) {
        return repository.save(server);
    }

    @Override
    public Server findByIp(String ip) {
        return repository.findByIp(ip);
    }

    @Override
    public Server updateServer(Server server) {
        return repository.save(server);
    }

    @Override
    @Transactional
    public void deleteServer(String ip) {
        repository.deleteByIp(ip);
    }

    @Override
    public boolean checkServer(String ip) throws IOException {
        InetAddress address = InetAddress.getByName(ip);
        return address.isReachable(5000);
    }


}