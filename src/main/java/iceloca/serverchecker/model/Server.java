package iceloca.serverchecker.model;


import jakarta.persistence.*;
import lombok.*;

@Data
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
}