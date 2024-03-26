package iceloca.serverchecker.repository;


import iceloca.serverchecker.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Server findByIp(String  ip);
}
