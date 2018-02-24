package de.heidelberg.pvs.diego.collectionswitch.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import static java.lang.annotation.ElementType.FIELD;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface Switch {
	
	String name();

}
