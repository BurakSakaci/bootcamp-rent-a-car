package com.turkcell.rentACar.businessTests;


import com.turkcell.rentACar.business.abstracts.CacheService;
import com.turkcell.rentACar.business.concrates.ColorManager;
import com.turkcell.rentACar.business.requests.create.CreateColorRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteColorRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperManager;
import com.turkcell.rentACar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACar.entities.concrates.Color;
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

public class ColorManagerTest {
    //Basic Tests

    @Mock
    private ColorDao colorDao;

    @Mock
    private CacheService cacheService;

    @InjectMocks
    private ColorManager colorManager;

    @Before
    public void setUp() throws Exception {
        colorDao = mock(ColorDao.class);
        cacheService = mock(CacheService.class);
        colorManager = new ColorManager(colorDao, new ModelMapperManager(new ModelMapper()), cacheService);


    }

    @Test
    public void whenGetAllColorCalled_itShouldReturnAllColors(){
        colorManager.getAll();
        verify(colorDao).findAll();

    }

    @Test
    public void whenAddColorCalledWithValidRequest_itShouldSaveColor() throws Exception{
        Color color = new Color();
        color.setColorId(1);
        color.setColorName("TestColor");

        CreateColorRequest createColorRequest = new CreateColorRequest("TestColor");

        colorManager.add(createColorRequest);

        ArgumentCaptor<Color> colorArgumentCaptor = ArgumentCaptor.forClass(Color.class);

        verify(colorDao).save(colorArgumentCaptor.capture());

        Color capturedColor = colorArgumentCaptor.getValue();
        capturedColor.setColorId(1);

        assertEquals(capturedColor, color);

    }

    @Test
    public void whenDeleteColorCalledWithValidRequest_itShouldReturnValidResult() throws Exception{
        DeleteColorRequest deleteColorRequest = new DeleteColorRequest(1);
        given(colorDao.existsById(deleteColorRequest.getColorId())).willReturn(true);

        boolean actual = colorManager.delete(deleteColorRequest).isSuccess();

        assertTrue(actual);
    }


    @Test
    public void whenDeleteColorCalledWithInvalidRequest_itShouldThrowBusinessException(){
        DeleteColorRequest deleteColorRequest = new DeleteColorRequest(50);
        given(colorDao.existsById(deleteColorRequest.getColorId())).willReturn(false);

        assertThrows(BusinessException.class, () -> {
            colorManager.delete(deleteColorRequest);
        });

    }




}