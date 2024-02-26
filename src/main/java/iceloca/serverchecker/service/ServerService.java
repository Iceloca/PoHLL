package iceloca.serverchecker.service;

import iceloca.serverchecker.model.Server;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServerService {
    List<Server> findAllServers();
    Server  saveServer(Server server);
    Server findByIp(String ip);
    Server updateServer(Server server);
    void deleteServer(String ip);
    boolean checkServer(String ip) ;
    Server updateServerStatusIp(String ip);
}