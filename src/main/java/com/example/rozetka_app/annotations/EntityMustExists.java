package com.example.rozetka_app.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use the annotation to check whether the entity with the specified key (entityId) exists
 * @author ValValeria
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EntityMustExists {
    Class<?> classType();
}
