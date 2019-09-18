package com.ddd.demo.command.domain.vo.command;

import com.ddd.demo.common.exception.BillException;
import org.junit.Test;

import java.math.BigDecimal;

public class CreditLimitApplyCommandTest {

    @Test(expected = BillException.class)
    public void shouldThrowExeceptionWhenAmountAreLargerThan100() throws Exception {
        //given
        CreditLimitApplyCommand creditLimitApplyCommand = new CreditLimitApplyCommand();
        creditLimitApplyCommand.setCreditLimit(BigDecimal.valueOf(101));


        //when
        creditLimitApplyCommand.validate();

    }

    @Test
    public void shouldNotThrowExceptionWhenAmountAreLargerThan0AndSmallerThan100() throws Exception {
        //given
        CreditLimitApplyCommand creditLimitApplyCommand = new CreditLimitApplyCommand();
        creditLimitApplyCommand.setCreditLimit(BigDecimal.valueOf(98));

        //when
        creditLimitApplyCommand.validate();

        //then
    }
}