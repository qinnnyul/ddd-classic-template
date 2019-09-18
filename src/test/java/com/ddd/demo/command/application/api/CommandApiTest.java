package com.ddd.demo.command.application.api;

import com.ddd.demo.domain.vo.BankAccount;
import com.ddd.demo.command.application.api.request.CommandHistoryRequestDTO;
import com.ddd.demo.command.application.api.request.CreditLimitApplyRequestDTO;
import com.ddd.demo.command.application.api.response.CommandHistoryResponseDTO;
import com.ddd.demo.command.application.api.response.CreditLimitApplyCommandResponseDTO;
import com.ddd.demo.command.application.api.response.CreditLimitApplyResponseDTO;
import com.ddd.demo.command.application.service.CommandAppService;
import com.ddd.demo.command.domain.vo.CommandStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommandApiTest {

    @Mock
    private CommandAppService commandAppService;

    private CommandApi commandApi;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        commandApi = new CommandApi(commandAppService);
    }

    @Test
    public void shouldSaveCreditLimitApply() throws Exception {
        //given
        CreditLimitApplyRequestDTO creditLimitApplyRequestDTO = new CreditLimitApplyRequestDTO();
        creditLimitApplyRequestDTO.setCreditLimit(BigDecimal.ONE);
        BankAccount customerInfo = new BankAccount();
        customerInfo.setAccountNo("bank-no-xxxx");
        creditLimitApplyRequestDTO.setCustomerInfo(customerInfo);
        CreditLimitApplyResponseDTO expectedResponseDto = new CreditLimitApplyResponseDTO();
        expectedResponseDto.setCommandStatus(CommandStatus.WAITING_FOR_APPROVE);
        expectedResponseDto.setCommandId("command-id-xxx");
        given(commandAppService.saveCreditLimitApply(creditLimitApplyRequestDTO)).willReturn(expectedResponseDto);

        //when
        CreditLimitApplyResponseDTO creditLimitApplyResponseDTO = commandApi.saveCreditLimitApply(creditLimitApplyRequestDTO);


        //then
        assertNotNull(creditLimitApplyResponseDTO.getCommandId());
        assertThat(creditLimitApplyResponseDTO.getCommandStatus(), is(expectedResponseDto.getCommandStatus()));
        assertThat(creditLimitApplyResponseDTO.getCommandId(), is(expectedResponseDto.getCommandId()));
    }

    @Test
    public void shouldGetCommandDetail() throws IOException {
        //given
        String commandId = "1acba838-53d6-451c-a915-1945d7acc406";
        CreditLimitApplyCommandResponseDTO expectedResponse = new CreditLimitApplyCommandResponseDTO();
        BankAccount bankAccount = new BankAccount();
        expectedResponse.setCommandId(commandId);
        expectedResponse.setCommandStatus(CommandStatus.WAITING_FOR_APPROVE.toString());
        expectedResponse.setCreditLimit(BigDecimal.valueOf(123232L));
        expectedResponse.setCustomerInfo(bankAccount);
        given(commandAppService.getCreditApplyCommandDetail(commandId)).willReturn(expectedResponse);



        //when
        CreditLimitApplyCommandResponseDTO creditLimitApplyCommandResponseDTO = commandApi.getCreditApplyCommandDetail(commandId);

        //then
        assertNotNull(creditLimitApplyCommandResponseDTO.getCommandId());
        assertThat(creditLimitApplyCommandResponseDTO.getCommandStatus(), is(CommandStatus.WAITING_FOR_APPROVE.toString()));
        assertThat(creditLimitApplyCommandResponseDTO.getCreditLimit(), is(BigDecimal.valueOf(123232L)));
        assertNotNull(creditLimitApplyCommandResponseDTO.getCustomerInfo());
        assertNotNull(String.valueOf(creditLimitApplyCommandResponseDTO.getSubmitDate()), is("2019-07-17"));


    }

    @Test
    public void shouldFindCommandHistories() throws Exception {

        //given
        CommandHistoryRequestDTO commandHistoryRequestDTO = new CommandHistoryRequestDTO();

        List<CommandHistoryResponseDTO> mockQueryResult = new ArrayList<CommandHistoryResponseDTO>();
        CommandHistoryResponseDTO item1 = new CommandHistoryResponseDTO();
        mockQueryResult.add(item1);
        given(commandAppService.findCommandHistories(commandHistoryRequestDTO)).willReturn(mockQueryResult);
        CommandApi commandApi = new CommandApi(commandAppService);

        //when

        List<CommandHistoryResponseDTO> result = commandApi.findCommandHistories("777", commandHistoryRequestDTO);

        //then
        assertThat(result.size(), is(1));
    }
}
