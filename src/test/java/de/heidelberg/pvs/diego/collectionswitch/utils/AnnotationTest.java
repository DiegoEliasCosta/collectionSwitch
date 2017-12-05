package de.heidelberg.pvs.diego.collectionswitch.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AnnotationTest {

		//should ignore this test?
		public boolean enabled() default true;


}
