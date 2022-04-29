package com.turkcell.rentACar.businessTests;

import com.turkcell.rentACar.business.abstracts.CacheService;
import com.turkcell.rentACar.business.concrates.AdditionalServiceManager;
import com.turkcell.rentACar.business.requests.create.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteAdditionalServiceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperManager;
import com.turkcell.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACar.entities.concrates.AdditionalService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class AdditionalServiceManagerTest {
    //Basic Tests


    @Mock
    private AdditionalServiceDao additionalServiceDao;

    @Mock
    private CacheService cacheService;

    @InjectMocks
    private AdditionalServiceManager additionalServiceManager;

    @Before
    public void setUp() throws Exception {
        additionalServiceDao = mock(AdditionalServiceDao.class);
        cacheService = mock(CacheService.class);
        additionalServiceManager = new AdditionalServiceManager(additionalServiceDao, new ModelMapperManager(new ModelMapper()), cacheService);

    }

    @Test
    public void whenGetAllAdditionalServiceCalled_itShouldReturnAllAdditionalServices(){
        additionalServiceManager.getAll();
        verify(additionalServiceDao).findAll();
    }

    @Test
    public void whenAddAdditionalServiceCalledWithValidRequest_itShouldSaveAdditionalService() throws Exception{
        AdditionalService additionalService = new AdditionalService(1, "TestService",50);

        CreateAdditionalServiceRequest additionalServiceRequest = new CreateAdditionalServiceRequest("TestService", 50);

        given(additionalServiceDao.existsByAdditionalServiceName(additionalServiceRequest.getAdditionalServiceName())).willReturn(false);

        additionalServiceManager.add(additionalServiceRequest);

        ArgumentCaptor<AdditionalService> additionalServiceArgumentCaptor = ArgumentCaptor.forClass(AdditionalService.class);

        verify(additionalServiceDao).save(additionalServiceArgumentCaptor.capture());

        AdditionalService capturedAdditionalService = additionalServiceArgumentCaptor.getValue();
        capturedAdditionalService.setAdditionalServiceId(1);

        assertEquals(capturedAdditionalService, additionalService);

    }

    @Test
    public void whenDeleteAdditionalServiceCalledWithValidRequest_itShouldReturnValidResult() throws Exception{
        DeleteAdditionalServiceRequest deleteAdditionalServiceRequest = new DeleteAdditionalServiceRequest(1);
        given(additionalServiceDao.existsById(deleteAdditionalServiceRequest.getAdditionalServiceId())).willReturn(true);

        boolean actual = additionalServiceManager.delete(deleteAdditionalServiceRequest).isSuccess();

        assertTrue(actual);
    }

    @Test
    public void whenDeleteAdditionalServiceCalledWithInvalidRequest_itShouldThrowBusinessException() throws Exception{
        DeleteAdditionalServiceRequest deleteAdditionalServiceRequest = new DeleteAdditionalServiceRequest(50);
        given(additionalServiceDao.existsById(deleteAdditionalServiceRequest.getAdditionalServiceId())).willReturn(false);

        assertThrows(BusinessException.class, () -> {
            additionalServiceManager.delete(deleteAdditionalServiceRequest);
        });
    }



}