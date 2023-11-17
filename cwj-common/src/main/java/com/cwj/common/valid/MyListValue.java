package com.cwj.common.valid;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {ListValueConstraintValidator.class})
public @interface MyListValue {
    String message() default "{com.cwj.common.valid.MyListValue}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    int[] vals() default { };
}
