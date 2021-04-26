package net.gridcraft.currency.command.sub;

import net.gridcraft.core.command.CommandArgument;
import net.gridcraft.core.command.ForeignCommand;
import net.gridcraft.core.database.BaseDatabase;
import net.gridcraft.core.utils.TextUtil;
import net.gridcraft.currency.account.CurrencyAccount;
import net.gridcraft.currency.account.CurrencyDAO;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CurrencySetCommand {
    @ForeignCommand(value = "set", permission = "command.currency.set", description = "Set a players balance", usage = "set <player> <amount>")
    public void set(CommandArgument argument) {
        CommandSender sender = argument.getSender();

        if (argument.getArgs().length == 3) {
            //Would normally have a database for name , given <UUID> <NAME> which you can filter.
            //However in this case we're gonna relay on Spigot because the infrastructure isn't there.
            OfflinePlayer target = Bukkit.getOfflinePlayer(argument.getArgs()[1]);
            if (Objects.isNull(target)) {
                sender.sendMessage(ChatColor.RED + "Unknown target.");
                return;
            }

            UUID uuid = target.getUniqueId();
            if (TextUtil.isDouble(argument.getArgs()[2], true)) {
                double amount = Double.valueOf(argument.getArgs()[2]);

                Optional<CurrencyAccount> account = CurrencyAccount.getIfPresent(uuid);

                if (account.isPresent()) {
                    account.get().setBalance(amount);
                    sender.sendMessage(ChatColor.GREEN + "You have set " + target.getName() + "'s balance to $" + TextUtil.decimalFormat(amount));
                } else {
                    if (CurrencyDAO.setBalance(BaseDatabase.getInstance().getConnection(), uuid, amount)) {
                        sender.sendMessage(ChatColor.GREEN + "You have set " + target.getName() + "'s balance to $" + TextUtil.decimalFormat(amount));
                    } else {
                        sender.sendMessage(ChatColor.RED + "An error occurred! Please try again later.");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid amount.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid usage.");
        }
    }
}
