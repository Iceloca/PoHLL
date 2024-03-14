package iceloca.serverchecker.controller;


import iceloca.serverchecker.model.Watchlist;

import iceloca.serverchecker.model.dto.ServerDTO;
import iceloca.serverchecker.model.dto.WatchlistDTO;
import iceloca.serverchecker.service.WatchlistService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlists")
@AllArgsConstructor
public class WatchlistController {
    WatchlistService watchlistService;

    @GetMapping
    public List<Watchlist> findAllServers() {
        return  watchlistService.findAllWatchlists();
    }

    @PostMapping("/save")
    public Watchlist saveWatchlist(@RequestBody WatchlistDTO watchlistDTO) {
        return watchlistService.saveWatchlist(watchlistDTO);
    }

    @GetMapping("/{name}")
    public  Watchlist findByName(@PathVariable() String name){
        return watchlistService.findByName(name);
    }
    @GetMapping("/all/{name}")
    public  List<ServerDTO> findAllByName(@PathVariable() String name){
        return watchlistService.findAllByName(name);
    }

    @PutMapping("/update")
    public Watchlist updateWatchlist(@RequestBody WatchlistDTO watchlistDTO){
        return  watchlistService.updateWatchlist(watchlistDTO);
    }
    @PutMapping("/add_server")
    public Watchlist addServerToWatchlist(@RequestParam String name, String ip){
        return  watchlistService.addServerToWatchlist(name, ip);
    }
    @DeleteMapping("/remove_server")
    public Watchlist removeServerFromWatchlist(@RequestParam String name,@RequestParam String ip){
        return  watchlistService.removeServerFromWatchlist(name, ip);
    }
    @DeleteMapping("/delete/{name}")
    public void deleteWatchlist(@PathVariable String name){
        watchlistService.deleteWatchlist(name);
    }
}
