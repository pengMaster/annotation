package king.bird.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * <pre>
 * author : Wp
 * e-mail : 18141924293@163.com
 * time   : 2018/09/10
 * desc   : 注解标签
 * version: 1.0
 *</pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    public String source() default "nothing";
}