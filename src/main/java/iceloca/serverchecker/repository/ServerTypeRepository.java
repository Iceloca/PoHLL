package iceloca.serverchecker.repository;


import iceloca.serverchecker.model.Server;
import iceloca.serverchecker.model.ServerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServerTypeRepository extends JpaRepository<ServerType, Long> {
    @Query(value = "SELECT s FROM Server s WHERE s.serverType.name = ?1")
    List<Server> findAllByName(String name);
    void deleteByName(String name);
    ServerType findByName(String  name);
}
