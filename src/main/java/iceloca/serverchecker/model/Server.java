package iceloca.serverchecker.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "servers")
public class Server {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(unique = true)
    private String ip;
    private Boolean isUp;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "server_type_id")

    private ServerType serverType;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "servers_watchlists",
            joinColumns = @JoinColumn(name = "server_id"),
            inverseJoinColumns = @JoinColumn(name = "watchlist_id"))
    @JsonBackReference
    private Set<Watchlist> watchlistSet;
}