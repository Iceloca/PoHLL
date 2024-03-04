package iceloca.serverchecker.controller;

import iceloca.serverchecker.model.Server;


import iceloca.serverchecker.service.ServerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
@AllArgsConstructor
public class ServerController {
    private final ServerService service ;
    @GetMapping
    public List<Server> findAllServers() {
        return  service.findAllServers();
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
            return service.checkServer(ip);
    }

    @PutMapping("/update_server")
    public Server updateServer(@RequestBody Server server){
        return  service.updateServer(server);
    }

    @PutMapping("/update_server_status")
    public Server updateServerStatusIp(@RequestParam String ip){
        return  service.updateServerStatusIp(ip);
    }

    @DeleteMapping("/delete_server/{ip}")
    public void deleteServer(@PathVariable String ip){
        service.deleteServer(ip);
    }
}
