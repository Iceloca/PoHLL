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
import org.springframework.web.server.ResponseStatusException;

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
    public void deleteWatchlist(String name) {watchlistRepository.deleteByName(name);}
    public  Watchlist findByName(String name) {return  watchlistRepository.findByName(name);}
    public Watchlist addServerToWatchlist(String name, String ip){
        return watchlistRepository.save(editServerSet(name, ip, Boolean.TRUE));
    }
    public Watchlist removeServerFromWatchlist(String name, String ip){
        return watchlistRepository.save(editServerSet(name,ip,Boolean.FALSE));
    }
    public Watchlist editServerSet(String name, String ip, Boolean add){
        Watchlist watchlist = findByName(name);
        Server server = serverRepository.findByIp(ip);
        if(watchlist == null || server == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
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
    public List<ServerDTO> findAllByName(String name) {
        return findByName(name).getServerSet()
                .stream()
                .map(ServerDTOUtility::buildDTOFromServer)
                .toList();
    }

}
