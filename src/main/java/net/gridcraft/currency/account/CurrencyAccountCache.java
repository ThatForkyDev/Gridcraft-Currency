package net.gridcraft.currency.account;

import com.google.common.cache.CacheLoader;
import net.gridcraft.core.database.BaseDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class CurrencyAccountCache extends CacheLoader<UUID, CurrencyAccount> {
    @Override
    public CurrencyAccount load(UUID uuid) {
        try (Connection connection = BaseDatabase.getInstance().getConnection()) {
            return CurrencyDAO.getAccount(connection, uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
