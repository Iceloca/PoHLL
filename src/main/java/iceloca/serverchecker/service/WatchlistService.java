package iceloca.serverchecker.service;

import iceloca.serverchecker.model.Server;
import iceloca.serverchecker.model.Watchlist;
import iceloca.serverchecker.model.dto.ServerDTO;
import iceloca.serverchecker.model.dto.WatchlistDTO;
import iceloca.serverchecker.repository.ServerRepository;
import iceloca.serverchecker.repository.WatchlistRepository;
import iceloca.serverchecker.service.utility.ServerDTOUtility;
import iceloca.serverchecker.service.utility.WatchlistDTOUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class WatchlistService {
    private final WatchlistRepository watchlistRepository;
    private final ServerRepository serverRepository;

    public List<Watchlist> findAllWatchlists() {return watchlistRepository.findAll();}
    public Watchlist saveWatchlist(WatchlistDTO watchlistDTO) {
        return watchlistRepository.save(WatchlistDTOUtility.buildWatchlistFromDTO(watchlistDTO));
    }
    @Transactional
    public void deleteWatchlist(Long id) {
        Optional<Watchlist>  watchlist= watchlistRepository.findById(id);
        if (watchlist.isEmpty())
            return;
        for(Server server : watchlist.get().getServerSet())
            server.getWatchlistSet().remove(watchlist.get());
        watchlistRepository.deleteById(id);
    }
    public  Watchlist findById(Long id) {return  watchlistRepository.findById(id).orElse(null);}

    public Watchlist addServerToWatchlist(Long id, String ip){
        return watchlistRepository.save(editServerSet(id, ip, Boolean.TRUE));
    }
    public Watchlist removeServerFromWatchlist(Long id, String ip){
        return watchlistRepository.save(editServerSet(id,ip,Boolean.FALSE));
    }


    public Watchlist editServerSet(Long id, String ip, Boolean add){
        Watchlist watchlist = findById(id);
        Server server = serverRepository.findByIp(ip);
        if(watchlist == null || server == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No such watchlist or server");
        Set<Server> serverSet = watchlist.getServerSet();
        if (serverSet == null)
            serverSet = new HashSet<>();
        Set<Watchlist> watchlistSet = server.getWatchlistSet();
        if(Boolean.TRUE.equals(add)) {
            if(watchlistSet == null)
                watchlistSet = new HashSet<>();
            watchlistSet.add(watchlist);
            serverSet.add(server);
        }
        else if(watchlistSet != null) {
            watchlistSet.remove(watchlist);
            serverSet.remove(server);
        }
        server.setWatchlistSet(watchlistSet);
        serverRepository.save(server);
        watchlist.setServerSet(serverSet);
        return watchlist;
    }
    public Watchlist updateWatchlist (WatchlistDTO watchlistDTO){
        Watchlist watchlist = WatchlistDTOUtility.buildWatchlistFromDTO(watchlistDTO);
        if (watchlist.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Optional<Watchlist> oldWatchlist = watchlistRepository.findById(watchlist.getId());
        if(oldWatchlist.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        watchlist.setServerSet(oldWatchlist.get().getServerSet());
        return watchlistRepository.save(watchlist);
    }
    public Watchlist updateWatchlistStatus (String name){
        Watchlist watchlist = watchlistRepository.findByName(name);
        if (watchlist == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        RestTemplate restTemplate = new RestTemplate();
        for(Server server : watchlist.getServerSet()){

            URI url = UriComponentsBuilder.fromUriString("http://localhost:8080/api/servers/update_status")
                    .queryParam("ip",server.getIp())
                    .build().toUri();

            restTemplate.put(url,null);
        }
        return watchlistRepository.findByName(name);
    }
    public List<ServerDTO> findAllById(Long id) {
        return findById(id).getServerSet()
                .stream()
                .map(ServerDTOUtility::buildDTOFromServer)
                .toList();
    }

}
