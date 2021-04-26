package net.gridcraft.currency;

import net.gridcraft.core.database.BaseDatabase;
import net.gridcraft.currency.command.BalanceCommand;
import net.gridcraft.currency.command.CurrencyCommand;
import net.gridcraft.currency.listener.ConnectionListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CurrencyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        initialiseSQL();

        new BalanceCommand();
        new CurrencyCommand();

        Bukkit.getPluginManager().registerEvents(new ConnectionListeners(), this);
    }

    private void initialiseSQL() {
        BaseDatabase.getInstance().init(getDataFolder());
        BaseDatabase.createTables(CurrencyPlugin.class, "create_tables");
    }
}
