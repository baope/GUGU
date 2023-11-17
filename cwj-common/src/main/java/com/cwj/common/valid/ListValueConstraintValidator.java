package com.cwj.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;



public class ListValueConstraintValidator implements ConstraintValidator<MyListValue,Integer> {

    private Set<Integer> set = new HashSet<>();

    public void initialize(ListValue constraintAnnotation) {
        /**
         * 设置的合法的信息
         */
        int valid[] = constraintAnnotation.vals();
        for(int i : valid){
            set.add(i);
        }
    }

    /**
     * value 代表获取的值
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }

}
