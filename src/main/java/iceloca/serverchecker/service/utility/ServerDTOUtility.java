package iceloca.serverchecker.service.utility;

import iceloca.serverchecker.model.Server;
import iceloca.serverchecker.model.ServerType;
import iceloca.serverchecker.model.dto.ServerDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ServerDTOUtility {
    public Server buildServerFromDTO (ServerDTO serverDTO, ServerType serverType){
        return Server.builder()
                .id(serverDTO.getId())
                .serverType(serverType)
                .name(serverDTO.getName())
                .ip(serverDTO.getIp())
                .isUp(serverDTO.getIsUp())
                .build();
    }
    public ServerDTO buildDTOFromServer(Server server){
        String serverTypeName;
        if (server.getServerType() == null)
            serverTypeName = null;
        else
            serverTypeName = server.getServerType().getName();
        return ServerDTO.builder()
                .id(server.getId())
                .isUp(server.getIsUp())
                .name(server.getName())
                .ip(server.getIp())
                .serverType(serverTypeName)
                .build();
    }
}
