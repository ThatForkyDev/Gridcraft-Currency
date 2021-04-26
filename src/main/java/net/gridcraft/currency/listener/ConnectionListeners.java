package net.gridcraft.currency.listener;

import net.gridcraft.currency.account.CurrencyAccount;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class ConnectionListeners implements Listener {
    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        //Ignore unwanted
        if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            CurrencyAccount currencyAccount = CurrencyAccount.of(event.getUniqueId());

            if (Objects.isNull(currencyAccount)) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "An error occurred.");
                System.err.println("Account is null - " + event.getName());
                return;
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        Optional<CurrencyAccount> account = CurrencyAccount.getIfPresent(uuid);

        if (!account.isPresent())
            return;

        account.get().invalidate();
    }
}
