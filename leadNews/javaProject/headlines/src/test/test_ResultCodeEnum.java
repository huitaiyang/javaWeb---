package test;

import common.ResultCodeEnum;
import org.junit.Test;

public class test_ResultCodeEnum {
    ResultCodeEnum resultCodeEnum = ResultCodeEnum.SUCCESS;
    @Test
    public void testEnum(){

        System.out.println(resultCodeEnum.getCode());;
    }
}
