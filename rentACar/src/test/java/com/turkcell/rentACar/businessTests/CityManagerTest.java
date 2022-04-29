package com.turkcell.rentACar.businessTests;

import static org.junit.Assert.*;

import com.turkcell.rentACar.business.abstracts.CacheService;
import com.turkcell.rentACar.business.concrates.CityManager;
import com.turkcell.rentACar.business.requests.create.CreateCityRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCityRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperManager;
import com.turkcell.rentACar.dataAccess.abstracts.CityDao;
import com.turkcell.rentACar.entities.concrates.City;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class CityManagerTest {

    //Basic Tests

    @Mock
    private CityDao cityDao;

    @Mock
    private CacheService cacheService;

    @InjectMocks
    private CityManager cityManager;

    @Before
    public void setUp() throws Exception {
        cityDao = mock(CityDao.class);
        cacheService = mock(CacheService.class);
        cityManager= new CityManager(cityDao, new ModelMapperManager(new ModelMapper()), cacheService);
    }

    @Test
    public void whenGetAllCityCalled_itShouldReturnAllCities(){
        cityManager.getAll();
        verify(cityDao).findAll();
    }

    @Test
    public void whenAddCityCalledWithValidRequest_itShouldSaveCity() throws Exception {
        City city = new City();
        city.setCityId(1);
        city.setCityName("TestCity");

        CreateCityRequest createCityRequest = new CreateCityRequest("TestCity");

        cityManager.add(createCityRequest);

        ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(City.class);

        verify(cityDao).save(cityArgumentCaptor.capture());

        City capturedCity = cityArgumentCaptor.getValue();
        capturedCity.setCityId(1);
        assertEquals(capturedCity, city);
    }


    @Test
    public void whenDeleteCityCalledWithValidRequest_itShouldReturnValidResult() throws Exception {
        DeleteCityRequest deleteCityRequest = new DeleteCityRequest(1);
        given(cityDao.existsById(deleteCityRequest.getCityId())).willReturn(true);

        boolean actual = cityManager.delete(deleteCityRequest).isSuccess();

        assertTrue(actual);
    }

    @Test
    public void whenDeleteCityCalledWithInvalidRequest_itShouldThrowBusinessException() throws Exception {
        DeleteCityRequest deleteCityRequest = new DeleteCityRequest(50);
        given(cityDao.existsById(deleteCityRequest.getCityId())).willReturn(false);

        assertThrows(BusinessException.class, () -> {
            cityManager.delete(deleteCityRequest);
        });
    }

}