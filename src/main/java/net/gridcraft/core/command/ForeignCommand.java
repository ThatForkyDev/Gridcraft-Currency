package net.gridcraft.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignCommand {
    String value();
    String[] aliases() default {};
    String permission() default "null";
    String usage() default "No usage defined.";
    String description() default "No description defined.";
}
