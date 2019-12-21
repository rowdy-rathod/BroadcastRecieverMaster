package com.rowdy_rathod.multi_broadcastreciver.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * an annotation that determines the Broadcast Receiver actions in manifest
 *
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BroadcastReceiverActions {

    String[] value();

}
