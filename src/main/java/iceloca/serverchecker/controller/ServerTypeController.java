package iceloca.serverchecker.controller;


import iceloca.serverchecker.model.ServerType;
import iceloca.serverchecker.model.dto.ServerDTO;
import iceloca.serverchecker.service.ServerTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/server_types")
@AllArgsConstructor
public class ServerTypeController {
    private final ServerTypeService service;

    @GetMapping
    public List<ServerType> findAllServers() {
        return  service.findAllServerTypes();
    }

    @PostMapping("/save")
    public ServerType saveServer(@RequestBody ServerType serverType) {
        return service.saveServerType(serverType);
    }

    @GetMapping("/{name}")
    public  ServerType findByName(@PathVariable() String name){
        return service.findByName(name);
    }
    @GetMapping("/all/{name}")
    public  List<ServerDTO> findAllByName(@PathVariable() String name){
        return service.findAllByName(name);
    }

    @PutMapping("/update")
    public ServerType updateServer(@RequestBody ServerType serverType){
        return  service.updateServerType(serverType);
    }


    @DeleteMapping("/delete/{name}")
    public void deleteServer(@PathVariable String name){
        service.deleteServerType(name);
    }
}
