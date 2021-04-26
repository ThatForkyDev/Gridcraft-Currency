package net.gridcraft.core.database;

public final class DatabaseCredentials {
    private final String host;
    private final int port;
    private final String dbName;
    private final String dbUser;
    private final String dbPass;

    public DatabaseCredentials(String host, int port, String dbName, String dbUser, String dbPass) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    public DatabaseCredentials(String host, String dbName, String dbUser, String dbPass) {
        this(host, -1, dbName, dbUser, dbPass);
    }

    public final String getHost() {
        return host;
    }

    public final int getPort() {
        return port;
    }

    public final String getName() {
        return dbName;
    }

    public final String getUser() {
        return dbUser;
    }

    public final String getPass() {
        return dbPass;
    }
}