package iceloca.serverchecker.controller;

import iceloca.serverchecker.model.Server;


import iceloca.serverchecker.model.dto.ServerDTO;
import iceloca.serverchecker.service.ServerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
@AllArgsConstructor
public class ServerController {
    private final ServerService serverService;
    @GetMapping
    public List<ServerDTO> findAllServers() {
        return  serverService.findAllServers();
    }

    @PostMapping("/save")
    public Server saveServer(@RequestBody ServerDTO server) {
        return serverService.saveServer(server);
    }

    @GetMapping("/{ip}")
    public  Server findByIp(@PathVariable() String ip){
        return serverService.findByIp(ip);
    }

    @GetMapping("/check/{ip}")
    public  boolean checkIp(@PathVariable() String ip){
            return serverService.checkServer(ip);
    }

    @PutMapping("/update")
    public Server updateServer(@RequestBody ServerDTO server){
        return  serverService.updateServer(server);
    }

    @PutMapping("/update_status")
    public Server updateServerStatusIp(@RequestParam String ip){
        return  serverService.updateServerStatusIp(ip);
    }

    @DeleteMapping("/delete/{ip}")
    public void deleteServer(@PathVariable String ip){
        serverService.deleteServer(ip);
    }
}
