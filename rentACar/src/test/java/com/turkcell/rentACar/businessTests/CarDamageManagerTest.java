package com.turkcell.rentACar.businessTests;

import static org.junit.Assert.*;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.concrates.CarDamageManager;
import com.turkcell.rentACar.business.requests.create.CreateCarDamageRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperManager;
import com.turkcell.rentACar.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentACar.entities.concrates.CarDamage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class CarDamageManagerTest {
    //Basic Tests


    @Mock
    private CarDamageDao carDamageDao;

    @Mock
    private CarService carService;
    @InjectMocks
    private CarDamageManager carDamageManager;

    @Before
    public void setUp() throws Exception {
        carDamageDao = mock(CarDamageDao.class);
        carService = mock(CarService.class);
        carDamageManager = new CarDamageManager(carDamageDao, new ModelMapperManager(new ModelMapper()), carService);
    }


    @Test
    public void whenGetAllCarDamageCalled_itShouldReturnAllCarDamages(){
        carDamageManager.getAll();
        verify(carDamageDao).findAll();
    }

    @Test
    public void whenAddCarDamageCalledWithValidRequest_itShouldSaveCarDamage() throws Exception{
        CarDamage carDamage = new CarDamage(1, "TestDamage" , null );

        CreateCarDamageRequest createCarDamageRequest = new CreateCarDamageRequest();
        createCarDamageRequest.setDamageDescription("TestDamage");

        carDamageManager.add(createCarDamageRequest);

        ArgumentCaptor<CarDamage> carDamageArgumentCaptor = ArgumentCaptor.forClass(CarDamage.class);

        verify(carDamageDao).save(carDamageArgumentCaptor.capture());

        CarDamage capturedCarDamage = carDamageArgumentCaptor.getValue();
        capturedCarDamage.setCarDamageId(1);
        capturedCarDamage.setCar(null);

        assertEquals(capturedCarDamage, carDamage);

    }


    @Test
    public void whenDeleteCarDamageCalledWithValidRequest_itShouldReturnValidResult() throws Exception {
        given(carDamageDao.existsById(1)).willReturn(true);

        boolean actual = carDamageManager.delete(1).isSuccess();
        assertTrue(actual);
    }

    @Test
    public void whenDeleteCarDamageCalledWithValidRequest_itShouldThrowBusinessException() throws Exception {
        given(carDamageDao.existsById(50)).willReturn(false);

        assertThrows(BusinessException.class, () -> {
            carDamageManager.delete(50);
        });

    }


}