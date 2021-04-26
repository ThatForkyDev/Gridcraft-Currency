package net.gridcraft.currency.command;

import net.gridcraft.core.command.BaseCommand;
import net.gridcraft.core.command.CommandDescription;
import net.gridcraft.core.command.CommandName;
import net.gridcraft.core.command.CommandPermission;
import net.gridcraft.core.database.BaseDatabase;
import net.gridcraft.core.utils.TextUtil;
import net.gridcraft.currency.account.CurrencyAccount;
import net.gridcraft.currency.account.CurrencyDAO;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@CommandName("balance")
@CommandDescription("Check your balance")
@CommandPermission("command.balance.use")
public class BalanceCommand extends BaseCommand<CommandSender> {
    public BalanceCommand() {
        super(BalanceCommand.class);

        registerForeign();
    }

    @Override
    protected void onExecute(CommandSender commandSender, String[] args) {
        if (args.length != 1) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                UUID uuid = player.getUniqueId();

                Optional<CurrencyAccount> account = CurrencyAccount.getIfPresent(uuid);
                if (account.isPresent()) {
                    player.sendMessage(ChatColor.GREEN + "Balance: $" + TextUtil.decimalFormat(account.get().getBalance()));
                } else {
                    System.err.println("Account isn't present.");
                }
            }
        } else {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

            if (Objects.isNull(target)) {
                commandSender.sendMessage(ChatColor.RED + "Invalid target.");
                return;
            }

            UUID uid = target.getUniqueId();
            Optional<CurrencyAccount> account = CurrencyAccount.getIfPresent(uid);
            double balance;

            if (account.isPresent())
                balance = CurrencyDAO.getAccount(BaseDatabase.getInstance().getConnection(), uid).getBalance();
            else
                balance = CurrencyDAO.getAccount(BaseDatabase.getInstance().getConnection(), uid).getBalance();

            commandSender.sendMessage(ChatColor.GREEN + "Balance of " + target.getName() + " is: $" + TextUtil.decimalFormat(balance));
        }
    }
}
