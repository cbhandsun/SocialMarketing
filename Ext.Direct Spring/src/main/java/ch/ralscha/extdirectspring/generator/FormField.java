package ch.ralscha.extdirectspring.generator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface FormField {
	String name() default "";
	String fieldLabel() default "";
	boolean allowBlank() default false;
	String vtype() default "";
	String vtypeText() default "";
	int columnWidth() default 100;
}
