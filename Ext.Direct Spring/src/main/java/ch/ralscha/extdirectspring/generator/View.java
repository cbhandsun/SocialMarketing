package ch.ralscha.extdirectspring.generator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hongtao
 *
 */
/**
 * 注解 目的根据model生成前端显示需要的 table的columns和form fields
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface  View {
	boolean genColumns() default false;
	boolean genFields() default false;
}
