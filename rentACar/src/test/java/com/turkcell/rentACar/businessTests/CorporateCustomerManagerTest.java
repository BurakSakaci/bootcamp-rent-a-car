package com.turkcell.rentACar.businessTests;

import static org.junit.Assert.*;

import com.turkcell.rentACar.business.concrates.CorporateCustomerManager;
import com.turkcell.rentACar.business.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperManager;
import com.turkcell.rentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACar.entities.concrates.CorporateCustomer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class CorporateCustomerManagerTest {

    @Mock
    private CorporateCustomerDao corporateCustomerDao;

    @InjectMocks
    private CorporateCustomerManager customerManager;

    @Before
    public void setUp() throws Exception {
        corporateCustomerDao = mock(CorporateCustomerDao.class);
        customerManager = new CorporateCustomerManager(corporateCustomerDao, new ModelMapperManager(new ModelMapper()));
    }

    @Test
    public void whenGetAllCorporateCustomerCalled_itShouldReturnAllCorporateCustomers(){
        customerManager.getAll();
        verify(corporateCustomerDao).findAll();
    }

    @Test
    public void whenAddCorporateCustomerCalledWithValidRequest_itShouldSaveCorporateCustomer() throws Exception {
        CorporateCustomer corporateCustomer = new CorporateCustomer();
        corporateCustomer.setUserId(1);
        corporateCustomer.setEmail("test@mail.com");
        corporateCustomer.setPassword("testPassword");
        corporateCustomer.setCompanyName("testCompany");
        corporateCustomer.setTaxNumber("1234567");

        CreateCorporateCustomerRequest createCorporateCustomerRequest = new CreateCorporateCustomerRequest();
        createCorporateCustomerRequest.setEmail("test@mail.com");
        createCorporateCustomerRequest.setPassword("testPassword");
        createCorporateCustomerRequest.setCompanyName("testCompany");
        createCorporateCustomerRequest.setTaxNumber("1234567");

        customerManager.add(createCorporateCustomerRequest);

        ArgumentCaptor<CorporateCustomer> corporateCustomerArgumentCaptor = ArgumentCaptor.forClass(CorporateCustomer.class);

        verify(corporateCustomerDao).save(corporateCustomerArgumentCaptor.capture());

        CorporateCustomer capturedCorporateCustomer = corporateCustomerArgumentCaptor.getValue();
        capturedCorporateCustomer.setUserId(1);

        assertEquals(capturedCorporateCustomer, corporateCustomer);
    }

    @Test
    public void whenDeleteCorporateCustomerCalledWithValidRequest_itShouldReturnValidResult() throws Exception {
        given(corporateCustomerDao.existsById(1)).willReturn(true);

        boolean actual = customerManager.delete(1).isSuccess();

        assertTrue(actual);
    }

    @Test
    public void whenDeleteCorporateCustomerCalledWithValidRequest_itShouldThrowBusinessException() throws Exception {
        given(corporateCustomerDao.existsById(50)).willReturn(false);

        assertThrows(BusinessException.class, () -> {
            customerManager.delete(50);
        });
    }


}