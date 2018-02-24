package de.heidelberg.pvs.diego.collectionswitch.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import de.heidelberg.pvs.diego.collectionswitch.context.CollectionTypeEnum;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

@Retention(CLASS)
@Target(value = FIELD)
public @interface ListSwitch_Old {

	CollectionTypeEnum type() default CollectionTypeEnum.ARRAY;
	ContextScope scope() default ContextScope.STATIC;
	
}
