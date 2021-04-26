package net.gridcraft.currency.command;

import net.gridcraft.core.command.BaseCommand;
import net.gridcraft.core.command.CommandData;
import net.gridcraft.core.command.CommandDescription;
import net.gridcraft.core.command.CommandName;
import net.gridcraft.core.command.CommandPermission;
import net.gridcraft.core.command.ForeignCommand;
import net.gridcraft.currency.command.sub.CurrencyGiveCommand;
import net.gridcraft.currency.command.sub.CurrencySetCommand;
import net.gridcraft.currency.command.sub.CurrencyTakeCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandName("currency")
@CommandDescription("Base currency command")
@CommandPermission("command.currency.use")
public class CurrencyCommand extends BaseCommand<CommandSender> {
    public CurrencyCommand() {
        super(CurrencyCommand.class);

        registerForeign(
                new CurrencyGiveCommand(),
                new CurrencySetCommand(),
                new CurrencyTakeCommand()
        );
    }

    @Override
    protected void onExecute(CommandSender sender, String[] args) {
        boolean canUse = false;

        for (CommandData data : getDataMap().values()) {
            ForeignCommand command = data.getCommand();

            if (command.permission() == null || sender.hasPermission(command.permission())) {
                if (!canUse)
                    sender.sendMessage(ChatColor.RED + "/" + getCommand());

                sender.sendMessage(ChatColor.YELLOW + "  " + data.getCommand().usage() + " - " + data.getCommand().description());
                canUse = true;
            }
        }

        if (!canUse)
            sender.sendMessage(ChatColor.RED + "No permission.");
    }
}
