package com.ddd.demo.command.domain.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ddd.demo.command.domain.entity.Command;
import com.ddd.demo.command.domain.vo.CommandType;
import com.ddd.demo.command.domain.vo.command.CreditLimitApplyCommand;
import com.ddd.demo.command.domain.vo.command.TransferApplyCommand;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CommandFactoryTest {

    @Test
    public void shouldConvertTranferApplyCommandToCommand() throws JsonProcessingException {
        //given
        TransferApplyCommand transferApplyCommand = new TransferApplyCommand();
        transferApplyCommand.setBillNo("123");

        //when
        Command command = CommandFactory.build(transferApplyCommand, CommandType.TRANSFER_APPLY);


        //then
        assertThat(command.getType(), is(CommandType.TRANSFER_APPLY));
        assertThat(command.getData(), is("{\"billNo\":\"123\"}"));
    }

    @Test
    public void shouldCommandToCreditLimitCommand() throws Exception {
        //given
        Command command = new Command();
        command.setId("1111");
        command.setType(CommandType.CREDIT_LIMIT_APPLY);
        command.setData("{\"creditLimit\":1}");


        //when
        CreditLimitApplyCommand creditLimitApplyCommand = (CreditLimitApplyCommand) CommandFactory.build(command);

        //then
        assertNotNull(creditLimitApplyCommand);
    }
}