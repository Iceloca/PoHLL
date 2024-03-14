package iceloca.serverchecker.service.utility;


import iceloca.serverchecker.model.Watchlist;
import iceloca.serverchecker.model.dto.WatchlistDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WatchlistDTOUtility {
    public Watchlist buildWatchlistFromDTO (WatchlistDTO watchlistDTO){
        return Watchlist.builder()
                .id(watchlistDTO.getId())
                .name(watchlistDTO.getName())
                .build();
    }
}
