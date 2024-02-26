package iceloca.serverchecker.service;

import iceloca.serverchecker.model.Server;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

@Service
public interface ServerService {
    List<Server> FindAllServers();
    Server  saveServer(Server server);
    Server findByIp(String ip);
    Server updateServer(Server server);
    void deleteServer(String ip);
    boolean checkServer(String ip) throws IOException;
}