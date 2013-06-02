/**
 * 
 */
package ch.ralscha.extdirectspring.generator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hongtao
 *注解 目的根据 table的column
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface GridColumn {
	String text() default "";
	String dataIndex() default "";
	GridColumnType xtype() default GridColumnType.AUTO;
	String format() default "";
	String tpl() default "";
	int columnWidth() default 100;
}
