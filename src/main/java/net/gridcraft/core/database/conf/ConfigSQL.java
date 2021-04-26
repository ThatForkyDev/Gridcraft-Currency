package net.gridcraft.core.database.conf;

import lombok.Getter;
import net.gridcraft.core.utils.json.annotation.ConfigName;
import net.gridcraft.core.utils.json.annotation.Jsonable;

@Getter
@Jsonable
@ConfigName("sql.json")
public class ConfigSQL {
    private String host = "127.0.0.1";
    private int port = 3306;
    private String databaseName = "gridcraft";
    private String user = "user";
    private String password = "passw0rd";
    private int minIdle = 30;
    private int maxConnections = 100;
}
