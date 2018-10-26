package com.cloud.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月25日
 */

public class ValidateUtil
{

    public static List<String> validateObject(Object validateObject){
        
        List<String> resultList = new ArrayList<String>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); 
        Validator validator = factory.getValidator(); 
        Set<ConstraintViolation<Object>> violations = validator.validate(validateObject);
        violations.stream().forEach(violation -> {
              String fieldName = violation.getPropertyPath().toString();
              String msg = violation.getMessage();
              resultList.add(fieldName + msg);
        });

        return resultList;
    }

}
