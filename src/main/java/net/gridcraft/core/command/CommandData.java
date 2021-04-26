package net.gridcraft.core.command;

import java.lang.reflect.Method;

public final class CommandData {
    private final Object owningClass;
    private final Method method;
    private final ForeignCommand command;

    public CommandData(Object owningClass, Method method, ForeignCommand command) {
        this.owningClass = owningClass;
        this.method = method;
        this.command = command;
    }

    public Object getOwningClass() {
        return owningClass;
    }

    public Method getMethod() {
        return method;
    }

    public ForeignCommand getCommand() {
        return command;
    }
}
