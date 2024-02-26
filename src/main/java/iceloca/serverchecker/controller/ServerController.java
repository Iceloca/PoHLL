package iceloca.serverchecker.controller;

import iceloca.serverchecker.model.Server;
import iceloca.serverchecker.service.ServerService;



import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/servers")
@AllArgsConstructor
public class ServerController {
    private final ServerService service ;
    @GetMapping
    public List<Server> FindAllServers() {
        return  service.FindAllServers();
    }

    @PostMapping("save_server")
    public Server saveServer(@RequestBody Server server) {
        return service.saveServer(server);
    }

    @GetMapping("/{ip}")
    public  Server findByIp(@PathVariable() String ip){
        return service.findByIp(ip);
    }

    @GetMapping("/check/{ip}")
    public  boolean checkIp(@PathVariable() String ip){
        try {
            return service.checkServer(ip);
        }catch (IOException exception){
            return false;
        }
    }

    @PutMapping("update_server")
    public Server updateServer(@RequestBody Server server){
        return  service.updateServer(server);
    }

    @DeleteMapping("delete_server/{ip}")
    public void deleteServer(@PathVariable String ip){
        service.deleteServer(ip);
    }
}
