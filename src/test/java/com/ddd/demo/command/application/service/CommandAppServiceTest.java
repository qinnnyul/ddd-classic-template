package com.ddd.demo.command.application.service;

import com.ddd.demo.domain.vo.BankAccount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ddd.demo.command.application.api.request.CommandHistoryRequestDTO;
import com.ddd.demo.command.application.api.request.CreditLimitApplyRequestDTO;
import com.ddd.demo.command.application.api.request.CommandActionRequestDTO;
import com.ddd.demo.command.application.api.request.IssueApplyRequestDTO;
import com.ddd.demo.command.application.api.response.CommandHistoryResponseDTO;
import com.ddd.demo.command.application.api.response.CreditLimitApplyCommandResponseDTO;
import com.ddd.demo.command.application.api.response.CreditLimitApplyResponseDTO;
import com.ddd.demo.command.application.external.UserServiceFeignClient;
import com.ddd.demo.command.application.repository.CommandQueryRepository;
import com.ddd.demo.command.domain.entity.Command;
import com.ddd.demo.command.domain.external.WorkflowService;
import com.ddd.demo.command.domain.repository.CommandRepository;
import com.ddd.demo.command.domain.vo.CommandStatus;
import com.ddd.demo.command.domain.vo.CommandType;
import com.ddd.demo.common.exception.BillException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommandAppServiceTest {

    private CommandAppService commandAppService;

    @Mock
    private CommandRepository commandRepository;

    @Mock
    private WorkflowService workflowService;

    @Mock
    private CommandQueryRepository commandQueryRepository;

    @Mock
    private UserServiceFeignClient userServiceFeignClient;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        commandAppService = new CommandAppService(commandRepository, workflowService, commandQueryRepository, userServiceFeignClient);
    }

    @Test
    public void shouldChangeCommandStatusWhenItGetsApproved() throws IOException {
        //given
        CommandActionRequestDTO commandActionRequestDTO = new CommandActionRequestDTO();
        commandActionRequestDTO.setAction("同意");
        String mockCommandId = "mockCommandId";
        Command command = new Command();
        command.setStatus(CommandStatus.WAITING_FOR_APPROVE);

        given(commandRepository.findById(mockCommandId)).willReturn(of(command));

        //when
        commandAppService.operate(mockCommandId, commandActionRequestDTO);


        //then
        assertThat(command.getStatus(), is(CommandStatus.BANK_ALREAD_ACCEPT));
    }

    @Test(expected = BillException.class)
    public void shouldThrowExceptionWhenCommandNotFound() throws IOException {
        //given
        CommandActionRequestDTO commandActionRequestDTO = new CommandActionRequestDTO();
        commandActionRequestDTO.setAction("同意");
        String mockCommandId = "mockCommandId";
        Command command = new Command();
        command.setStatus(CommandStatus.WAITING_FOR_APPROVE);

        given(commandRepository.findById(mockCommandId)).willReturn(Optional.empty());

        //when
        commandAppService.operate(mockCommandId, commandActionRequestDTO);
    }

    @Test
    public void shouldSaveCommandOnceItGetsRejected() throws IOException {
        //given
        CommandActionRequestDTO commandActionRequestDTO = new CommandActionRequestDTO();
        commandActionRequestDTO.setAction("拒绝");
        String mockCommandId = "mockCommandId";
        Command command = new Command();
        command.setStatus(CommandStatus.WAITING_FOR_APPROVE);

        given(commandRepository.findById(mockCommandId)).willReturn(Optional.of(command));

        //when
        commandAppService.operate(mockCommandId, commandActionRequestDTO);

        //then
        verify(commandRepository, times(1)).save(command);
    }

    @Test
    public void shouldSaveIssueApplyCommand() throws JsonProcessingException {
        //given
        IssueApplyRequestDTO issueApplyRequestDTO = new IssueApplyRequestDTO();


        String commandId = "XX-Command-ID";
        given(commandRepository.save(any())).willReturn(commandId);


        //when
        commandAppService.issue(issueApplyRequestDTO);


        //then
        verify(commandRepository, times(1)).save(any());

    }

    @Test
    public void shouldTriggerWorkflowAfterIssueApplyCommand() throws JsonProcessingException {
        //given
        IssueApplyRequestDTO issueApplyRequestDTO = new IssueApplyRequestDTO();

        given(workflowService.bindWorkflow(any())).willReturn("审批流ID");


        String commandId = "xx-command-id";
        given(commandRepository.save(any())).willReturn(commandId);


        //when
        commandAppService.issue(issueApplyRequestDTO);


        //then
        verify(workflowService, times(1)).bindWorkflow(any());

    }

    @Test
    public void shouldGetBillNoFromTransferCommandByCommandId() throws IOException {
        //given
        String commandId = "mockCommandId";
        Command command = new Command();
        command.setType(CommandType.TRANSFER_APPLY);
        command.setData("{\"billNo\":\"123\"}");
        command.setStatus(CommandStatus.WAITING_FOR_APPROVE);

        given(commandRepository.getOne(commandId)).willReturn(command);

        //when
        String billNo = commandAppService.getBillNoById(commandId);


        //then
        assertThat(billNo, is("123"));
    }

    @Test
    public void shouldSaveCreditLimitApply() throws Exception {
        //given
        CreditLimitApplyRequestDTO creditLimitApplyRequestDTO = new CreditLimitApplyRequestDTO();
        BankAccount custInfo = new BankAccount();
        custInfo.setAccountNo("bank-no");
        creditLimitApplyRequestDTO.setCreditLimit((BigDecimal.ONE));
        creditLimitApplyRequestDTO.setCustomerInfo(custInfo);
        given(commandRepository.save(any())).willReturn("command-id-xxx");

        //when
        CreditLimitApplyResponseDTO creditLimitApplyResponseDTO = commandAppService.saveCreditLimitApply(creditLimitApplyRequestDTO);


        //then
        assertThat(creditLimitApplyResponseDTO.getCommandId(), is("command-id-xxx"));
        assertThat(creditLimitApplyResponseDTO.getCommandStatus(), is(CommandStatus.WAITING_FOR_APPROVE));
    }

    @Test
    public void shouldGetCreditLimitApplyCommandDetail() throws Exception {
        //given
        String commandId = "1acba838-53d6-451c-a915-1945d7acc406";
        Command exceptedCommand = new Command();
        exceptedCommand.setId("1acba838-53d6-451c-a915-1945d7acc406");
        exceptedCommand.setType(CommandType.CREDIT_LIMIT_APPLY);
        exceptedCommand.setData("{\"creditLimit\":123}");
        exceptedCommand.setStatus(CommandStatus.WAITING_FOR_APPROVE);
        given(commandRepository.getOne(commandId)).willReturn(exceptedCommand);


        //when
        CreditLimitApplyCommandResponseDTO creditLimitApplyCommandResponseDTO = commandAppService.getCreditApplyCommandDetail(commandId);

        //then
        assertNotNull(creditLimitApplyCommandResponseDTO);
        assertThat(creditLimitApplyCommandResponseDTO.getCreditLimit(), is(BigDecimal.valueOf(123)));
        assertThat(creditLimitApplyCommandResponseDTO.getCommandStatus(), is(CommandStatus.WAITING_FOR_APPROVE.toString()));
    }

    @Test(expected = BillException.class)
    public void shouldThrowCommandNotFoundBillExcepiton() {
        CommandHistoryRequestDTO requestDTO = new CommandHistoryRequestDTO();

        List<CommandHistoryResponseDTO> mockQueryResult = new ArrayList<>();
        CommandHistoryResponseDTO commandHistoryResponseDTO = new CommandHistoryResponseDTO();
        mockQueryResult.add(commandHistoryResponseDTO);


        given(commandQueryRepository.findCommandById("000")).willReturn(0);

        //when
        commandAppService.findCommandHistories(requestDTO);
    }

    @Test(expected = BillException.class)
    public void shouldThrowSubmmitterNotFoundBillExcepiton() {
        CommandHistoryRequestDTO requestDTO = new CommandHistoryRequestDTO();
        requestDTO.setSubmitter("dyc");

        List<CommandHistoryResponseDTO> mockQueryResult = new ArrayList<>();
        CommandHistoryResponseDTO commandHistoryResponseDTO = new CommandHistoryResponseDTO();
        mockQueryResult.add(commandHistoryResponseDTO);

        given(commandQueryRepository.findCommandById("777")).willReturn(1);
        given(userServiceFeignClient.findUserIdByName(requestDTO.getSubmitter())).willReturn(null);
        //when
        commandAppService.findCommandHistories(requestDTO);
    }

    @Test
    public void souldFindHistories() {
        CommandHistoryRequestDTO requestDTO = new CommandHistoryRequestDTO();
        requestDTO.setSubmitter("dyc");
        requestDTO.setCommandId("777");

        List<CommandHistoryResponseDTO> mockQueryResult = new ArrayList<>();
        CommandHistoryResponseDTO commandHistoryResponseDTO = new CommandHistoryResponseDTO();
        mockQueryResult.add(commandHistoryResponseDTO);

        given(commandQueryRepository.findCommandById(requestDTO.getCommandId())).willReturn(1);
        given(userServiceFeignClient.findUserIdByName(requestDTO.getSubmitter())).willReturn("111");
        given(commandQueryRepository.findByPage(requestDTO)).willReturn(mockQueryResult);
        //when
        List<CommandHistoryResponseDTO> result = commandAppService.findCommandHistories(requestDTO);
        //then
        assertThat(result.size(), is(1));
    }
}
