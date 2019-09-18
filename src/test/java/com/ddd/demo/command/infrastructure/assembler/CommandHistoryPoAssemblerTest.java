package com.ddd.demo.command.infrastructure.assembler;

import static org.junit.Assert.*;

import com.ddd.demo.command.application.api.response.CommandHistoryResponseDTO;
import com.ddd.demo.command.infrastructure.persistence.CommandHistoryPo;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class CommandHistoryPoAssemblerTest {

    private CommandHistoryPoAssembler commandHistoryPoAssembler;

    @Before
    public void setUp(){
        commandHistoryPoAssembler = new CommandHistoryPoAssembler();
    }

    @Test
    public void shouldCommandIdNotNUll(){
        CommandHistoryPo po = new CommandHistoryPo();
        po.setCommandId("111");
        po.setSubmitter("dyc");

        CommandHistoryResponseDTO responseDTO = commandHistoryPoAssembler.assemble(po);

        assertThat(responseDTO.getCommandId(),is("111"));
        assertThat(responseDTO.getCreator(),is("dyc"));
    }

}