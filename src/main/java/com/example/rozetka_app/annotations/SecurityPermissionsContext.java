package com.example.rozetka_app.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use the annotation for security needs. Remember to have parameter, named entityId,
 * in order to verify whether the current user has the permission
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SecurityPermissionsContext {
    String permission();
    Class<?> className();
}
