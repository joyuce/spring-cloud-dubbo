package com.j.openproject.annotation;

import java.lang.annotation.*;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Joyuce
 * @Type RestPathController
 * @Desc rest路由注解
 * @date 2019年11月22日
 * @Version V1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping
public @interface RestPathController {

    @AliasFor("path")
    String[] value() default {};

    @AliasFor("value")
    String[] path() default {};

}
