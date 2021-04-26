package net.gridcraft.core.command;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandAliases {
    String[] value() default {};
}
