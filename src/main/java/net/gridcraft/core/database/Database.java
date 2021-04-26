package net.gridcraft.core.database;

import java.sql.Connection;

public interface Database {
    DatabaseCredentials getCredentials();

    Connection getConnection();
}