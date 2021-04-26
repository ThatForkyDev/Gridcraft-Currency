package net.gridcraft.core.command;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public abstract class BaseCommand<T extends CommandSender> extends BukkitCommand {
    private final Map<String, CommandData> dataMap = Maps.newHashMap();
    private static CommandMap commandMap = null;
    private final String command;
    private String description = "No description.";
    private String[] aliases = new String[0];;
    private String permission;

    public BaseCommand(Class<? extends BaseCommand> command) {
        super(command.getAnnotation(CommandName.class).value());

        this.command = command.getAnnotation(CommandName.class).value();

        if (command.isAnnotationPresent(CommandDescription.class)) {
            this.description = command.getAnnotation(CommandDescription.class).value();
        }

        if (command.isAnnotationPresent(CommandAliases.class)) {
            this.aliases = command.getAnnotation(CommandAliases.class).value();
        }

        if (command.isAnnotationPresent(CommandPermission.class)) {
            this.permission = command.getAnnotation(CommandPermission.class).value();
        }

        register();
    }

    @Override
    public final boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (strings.length == 0) {
            if (permission != null && !permission.equals("null") && commandSender instanceof Player && !commandSender.hasPermission(permission)) {
                commandSender.sendMessage(ChatColor.RED + "No permission");
                return true;
            }

            onExecute((T) commandSender, strings);
            return true;
        }

        for (String str : dataMap.keySet()) {
            String[] wantedArguments;
            if (str.contains(" ")) wantedArguments = str.split(" ");
            else wantedArguments = new String[] {str};

            if (!isMatch(wantedArguments, strings))
                continue;

            CommandData data = this.dataMap.getOrDefault(str, null);
            if (data == null) {
                return true;
            }

            CommandArgument argument = new CommandArgument((T) commandSender, getLabel(), strings, this);

            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;

                String permission = data.getCommand().permission();
                if (!permission.equals("null") && !player.hasPermission(permission)) {
                    commandSender.sendMessage(ChatColor.RED + "No permission");
                    return true;
                }
            }

            try {
                data.getMethod().invoke(data.getOwningClass(), argument);
                return true;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        if (permission != null && !permission.equals("null") && commandSender instanceof Player && !commandSender.hasPermission(permission)) {
            commandSender.sendMessage(ChatColor.RED + "No permission");
            return true;
        }

        onExecute((T) commandSender, strings);
        return true;
    }

    private boolean isMatch(String[] wanted, String[] args) {
        if (args.length < wanted.length)
            return false;

        for (int i = 0; i < wanted.length; i++) {
            if (!wanted[i].equalsIgnoreCase(args[i]))
                return false;
        }

        return true;
    }

    protected abstract void onExecute(T commandSender, String[] strings);

    protected void registerForeign(Object... objects) {
        for (Object object : objects) {
            for (Method method : object.getClass().getMethods()) {
                if (!method.isAnnotationPresent(ForeignCommand.class))
                    continue;

                ForeignCommand foreignCommand = method.getAnnotation(ForeignCommand.class);
                CommandData data = new CommandData(object, method, foreignCommand);

                dataMap.put(foreignCommand.value().toLowerCase(), data);

                System.out.println("Registered Foreign Command : " + foreignCommand.value());

                if (foreignCommand.aliases().length > 0) {
                    for (String relative : foreignCommand.aliases()) {
                        dataMap.put(relative.toLowerCase(), data);
                    }
                }
            }
        }
    }

    public String getCommand() {
        return command;
    }

    public Map<String, CommandData> getDataMap() {
        return dataMap;
    }

    protected void register() {
        System.out.println("Registering command: " + command);

        if (commandMap != null) {
            setAliases(Arrays.asList(aliases));
            setDescription(description);
            commandMap.register(command, this);
            return;
        }

        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(Bukkit.getServer());

            setAliases(Arrays.asList(aliases));
            setDescription(description);

            commandMap.register(command, this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean unregisterCommand() {
        try {
            final Field f = commandMap.getClass().getDeclaredField("knownCommands");
            f.setAccessible(true);
            Map<String, Command> cmds = (Map<String, Command>) f.get(commandMap);
            cmds.remove(getCommand());
            f.set(commandMap, cmds);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
