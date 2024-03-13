package iceloca.serverchecker.model.dto;

import lombok.*;


@Getter
@Setter
@Builder
public class ServerDTO {
    private Long id;
    private String name;
    private String ip;
    private Boolean isUp;
    private String serverType;
}
