package com.ddd.demo.command.application.api.assembler;

import com.ddd.demo.command.application.api.response.CreditLimitApplyCommandResponseDTO;
import com.ddd.demo.command.domain.vo.command.CreditLimitApplyCommand;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CreditLimitApplyDTOMapperTest {

    @Test
    public void shouldMapToResponseDTO() throws Exception {
        //given
        CreditLimitApplyDTOMapper mapper = CreditLimitApplyDTOMapper.INSTANCE;
        CreditLimitApplyCommand creditCommand = new CreditLimitApplyCommand();
        creditCommand.setCreditLimit(BigDecimal.ONE);


        //when
        CreditLimitApplyCommandResponseDTO creditLimitApplyCommandResponseDTO = mapper.mapToResponseDTO(creditCommand);


        //then
        assertThat(creditLimitApplyCommandResponseDTO.getCreditLimit(), is(BigDecimal.ONE));
    }
}