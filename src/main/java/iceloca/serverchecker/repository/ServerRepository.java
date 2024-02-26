package iceloca.serverchecker.repository;


import iceloca.serverchecker.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
    void deleteByIp(String ip);
    Server findByIp(String  ip);
}
