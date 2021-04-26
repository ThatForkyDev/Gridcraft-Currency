package net.gridcraft.core.command;

import org.bukkit.command.CommandSender;

import java.util.Arrays;

public final class CommandArgument {
    private final CommandSender sender;
    private final String label;
    private final String[] args;
    private BaseCommand<? extends CommandSender> baseCommand;

    public CommandArgument(CommandSender sender, String label, String[] args, BaseCommand<? extends CommandSender> baseCommand) {
        this.sender = sender;
        this.label = label;
        this.args = args;
        this.baseCommand = baseCommand;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String getLabel() {
        return label;
    }

    public String[] getArgs() {
        return args;
    }

    public BaseCommand getBaseCommand() {
        return baseCommand;
    }

    @Override
    public String toString() {
        return "sender = [" + sender.getName() + "], label = [" + label + "], args = [" + Arrays.toString(args) + "]";
    }
}
