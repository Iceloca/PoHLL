package iceloca.serverchecker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "server_types")
public class ServerType {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serverType", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Server> serverList;
}
