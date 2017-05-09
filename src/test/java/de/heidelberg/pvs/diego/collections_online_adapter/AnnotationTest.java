package de.heidelberg.pvs.diego.collections_online_adapter;

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
