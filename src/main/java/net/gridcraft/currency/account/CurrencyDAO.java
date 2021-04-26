package net.gridcraft.currency.account;

import net.gridcraft.core.database.Query;
import net.gridcraft.core.utils.UUIDUtil;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.UUID;

public class CurrencyDAO {
    @Nullable
    @Query(select = "balance", from = "account_currency", where = "uuid", type = Query.Type.SELECT)
    public static CurrencyAccount getAccount(Connection connection, UUID uuid) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT `balance` FROM `account_currency` WHERE `uuid`=UNHEX(?);")) {
            statement.setString(1, UUIDUtil.strip(uuid));
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new CurrencyAccount(uuid, result.getDouble("balance"));
                }

                return addAccount(connection, uuid, 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    @Query(insert = "uuid, balance", into = "account_currency", type = Query.Type.INSERT)
    public static CurrencyAccount addAccount(Connection connection, UUID uuid, double amount) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `account_currency` (`uuid`, `balance`) VALUES (UNHEX(?), ?);", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, UUIDUtil.strip(uuid));
            statement.setDouble(2, amount);
            statement.executeUpdate();
            try (ResultSet result = statement.getGeneratedKeys()) {
                if (result.next()) {
                    return new CurrencyAccount(uuid, result.getDouble("balance"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Query(set = "balance", from = "account_currency", where = "uuid", type = Query.Type.UPDATE)
    public static boolean setBalance(Connection connection, UUID uuid, double amount) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE `account_currency` SET `balance`=? WHERE `uuid`=UNHEX(?);")) {
            statement.setDouble(1, amount);
            statement.setString(2, UUIDUtil.strip(uuid));
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Query(set = "balance", from = "account_currency", where = "uuid", type = Query.Type.UPDATE)
    public static boolean giveBalance(Connection connection, UUID uuid, double amount) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE `account_currency` SET `balance`=`balance`+? WHERE `uuid`=UNHEX(?);")) {
            statement.setDouble(1, amount);
            statement.setString(2, UUIDUtil.strip(uuid));
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Query(set = "balance", from = "account_currency", where = "uuid", type = Query.Type.UPDATE)
    public static boolean takeBalance(Connection connection, UUID uuid, double amount) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE `account_currency` SET `balance`=`balance`-? WHERE `uuid`=UNHEX(?);")) {
            statement.setDouble(1, amount);
            statement.setString(2, UUIDUtil.strip(uuid));
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
