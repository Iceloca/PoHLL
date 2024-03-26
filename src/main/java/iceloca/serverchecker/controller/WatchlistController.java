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

    @GetMapping("/{id}")
    public  Watchlist findByName(@PathVariable() Long id){
        return watchlistService.findById(id);
    }
    @GetMapping("/all/{id}")
    public  List<ServerDTO> findAllByName(@PathVariable() Long id){
        return watchlistService.findAllById(id);
    }

    @PutMapping("/update")
    public Watchlist updateWatchlist(@RequestBody WatchlistDTO watchlistDTO){
        return  watchlistService.updateWatchlist(watchlistDTO);
    }
    @PutMapping("/add_server")
    public Watchlist addServerToWatchlist(@RequestParam Long id, String ip){
        return  watchlistService.addServerToWatchlist(id, ip);
    }
    @DeleteMapping("/remove_server")
    public Watchlist removeServerFromWatchlist(@RequestParam Long id,@RequestParam String ip){
        return  watchlistService.removeServerFromWatchlist(id, ip);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteWatchlist(@PathVariable Long id){
        watchlistService.deleteWatchlist(id);
    }
}
