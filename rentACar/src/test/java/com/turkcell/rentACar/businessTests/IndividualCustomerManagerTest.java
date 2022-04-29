package com.turkcell.rentACar.businessTests;

import com.turkcell.rentACar.business.concrates.IndividualCustomerManager;
import com.turkcell.rentACar.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperManager;
import com.turkcell.rentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACar.entities.concrates.IndividualCustomer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class IndividualCustomerManagerTest {

    @Mock
    private IndividualCustomerDao individualCustomerDao;

    @InjectMocks
    private IndividualCustomerManager individualCustomerManager;

    @Before
    public void setUp() throws Exception {
        individualCustomerDao = mock(IndividualCustomerDao.class);
        individualCustomerManager = new IndividualCustomerManager(individualCustomerDao, new ModelMapperManager(new ModelMapper()));
    }

    @Test
    public void whenGetAllIndividualCustomerCalled_itShouldReturnAllIndividualCustomers(){
        individualCustomerManager.getAll();
        verify(individualCustomerDao).findAll();
    }

    @Test
    public void whenAddIndividualCustomerCalledWithValidRequest_itShouldSaveIndividualCustomer() throws Exception {
        IndividualCustomer individualCustomer = new IndividualCustomer();
        individualCustomer.setUserId(1);
        individualCustomer.setFirstName("TestName");
        individualCustomer.setLastName("TestName");
        individualCustomer.setEmail("test@mail.com");
        individualCustomer.setNationalIdentity("123456879");
        individualCustomer.setPassword("testPassword");
        individualCustomer.setRegisteredDate(LocalDate.now());

        CreateIndividualCustomerRequest createIndividualCustomerRequest = new CreateIndividualCustomerRequest();
        createIndividualCustomerRequest.setEmail("test@mail.com");
        createIndividualCustomerRequest.setFirstName("TestName");
        createIndividualCustomerRequest.setLastName("TestName");
        createIndividualCustomerRequest.setPassword("testPassword");
        createIndividualCustomerRequest.setNationalIdentity("123456879");

        individualCustomerManager.add(createIndividualCustomerRequest);

        ArgumentCaptor<IndividualCustomer> individualCustomerArgumentCaptor = ArgumentCaptor.forClass(IndividualCustomer.class);

        verify(individualCustomerDao).save(individualCustomerArgumentCaptor.capture());

        IndividualCustomer capturedIndividualCustomer = individualCustomerArgumentCaptor.getValue();
        capturedIndividualCustomer.setUserId(1);
        capturedIndividualCustomer.setRegisteredDate(LocalDate.now());

        assertEquals(capturedIndividualCustomer, individualCustomer);



    }

    @Test
    public void whenDeleteIndividualCustomerCalledWithValidRequest_itShouldReturnValidResult() throws Exception {
        given(individualCustomerDao.existsById(1)).willReturn(true);

        boolean actual = individualCustomerManager.delete(1).isSuccess();

        assertTrue(actual);
    }

    @Test
    public void whenDeleteIndividualCustomerCalledWithInvalidRequest_itShouldThrowBusinessException() throws Exception {
        given(individualCustomerDao.existsById(50)).willReturn(false);

        assertThrows(BusinessException.class, () -> {
            individualCustomerManager.delete(50);
        } );
    }




    }