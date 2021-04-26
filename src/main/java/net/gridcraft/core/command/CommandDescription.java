package net.gridcraft.core.command;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandDescription {
    String value();
}
