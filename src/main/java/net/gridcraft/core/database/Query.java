package net.gridcraft.core.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface Query {
    enum Type {SELECT, INSERT, UPDATE, DELETE}

    String[] select() default "";
    String[] set() default "";
    String[] insert() default "";
    String[] from() default "";
    String into() default "";
    String[] where() default "";

    Type type();
}