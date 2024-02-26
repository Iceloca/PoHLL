package iceloca.serverchecker.model;


import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "servers")
public class Server {
    @Id
    @GeneratedValue
    private  Long id;
    private String name;
    @Column(unique = true)
    private String ip;
    private Boolean isUp;
}