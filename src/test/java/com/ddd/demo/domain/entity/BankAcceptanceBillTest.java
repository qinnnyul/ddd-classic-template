package com.ddd.demo.domain.entity;

import com.ddd.demo.domain.external.CoreSystemService;
import com.ddd.demo.infrastructure.external.CoreSystemServiceImpl;
import com.ddd.demo.common.util.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringContextUtil.class})
public class BankAcceptanceBillTest {

    @Test
    public void shouldTransferBillToDifferentPerson() {
        //given
        PowerMockito.mockStatic(SpringContextUtil.class);

        CoreSystemService coreSystemService = mock(CoreSystemServiceImpl.class);

        when(SpringContextUtil.getBean(CoreSystemService.class)).thenReturn(coreSystemService);

        BankAcceptanceBill bankAcceptanceBill = new BankAcceptanceBill();

        String commandId = "commandId";

        bankAcceptanceBill.transfer(commandId);

        verify(SpringContextUtil.getBean(CoreSystemService.class), times(1)).transfer(commandId);

    }
}
