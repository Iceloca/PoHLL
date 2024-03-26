package iceloca.serverchecker.repository;

import iceloca.serverchecker.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository<Watchlist,Long> {

}
