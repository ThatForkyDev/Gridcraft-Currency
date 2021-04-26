package net.gridcraft.currency.command;

import net.gridcraft.core.command.BaseCommand;
import net.gridcraft.core.command.CommandDescription;
import net.gridcraft.core.command.CommandName;
import net.gridcraft.core.command.CommandPermission;
import net.gridcraft.core.utils.TextUtil;
import net.gridcraft.currency.CurrencyPlugin;
import net.gridcraft.currency.account.CurrencyAccount;
import net.gridcraft.currency.account.CurrencyDAO;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@CommandName("balance")
@CommandDescription("Check your balance")
@CommandPermission("command.balance.use")
public class BalanceCommand extends BaseCommand<CommandSender> {
    public BalanceCommand(CurrencyPlugin plugin) {
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
            //SHOW OWN BALANCE
        } else {
            //SHOW OTHER BALANCE
        }
    }
}
