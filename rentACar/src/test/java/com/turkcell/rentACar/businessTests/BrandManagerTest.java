package com.turkcell.rentACar.businessTests;

import com.turkcell.rentACar.business.abstracts.CacheService;
import com.turkcell.rentACar.business.concrates.BrandManager;
import com.turkcell.rentACar.business.requests.create.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteBrandRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperManager;
import com.turkcell.rentACar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACar.entities.concrates.Brand;
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


public class BrandManagerTest {

    @Mock
    private BrandDao brandDao;

    @Mock
    private CacheService cacheService;

    @InjectMocks
    private BrandManager brandManager;


    @Before
    public void setUp() throws Exception {
        brandDao = mock(BrandDao.class);
        cacheService = mock(CacheService.class);
        brandManager = new BrandManager(brandDao, new ModelMapperManager(new ModelMapper()), cacheService);

    }


    @Test
    public void whenGetAllBrandCalled_itShouldReturnAllBrands(){
        brandManager.getAll();
        verify(brandDao).findAll();
    }

    @Test
    public void whenAddBrandCalledWithValidRequest_itShouldSaveBrand() throws Exception {
        Brand brand = new Brand();
        brand.setBrandId(1);
        brand.setBrandName("TestBrand");

        CreateBrandRequest brandRequest = new CreateBrandRequest("TestBrand");

        brandManager.add(brandRequest);

        ArgumentCaptor<Brand> brandArgumentCaptor = ArgumentCaptor.forClass(Brand.class);

        verify(brandDao).save(brandArgumentCaptor.capture());

        Brand capturedBrand = brandArgumentCaptor.getValue();
        capturedBrand.setBrandId(1);

        assertEquals(capturedBrand, brand);
    }

    @Test
    public void whenDeleteBrandCalledWithValidRequest_itShouldReturnValidResult() throws Exception {
        DeleteBrandRequest deleteBrandRequest = new DeleteBrandRequest(1);
        given(brandDao.existsById(deleteBrandRequest.getBrandId())).willReturn(true);

        boolean actual = brandManager.delete(deleteBrandRequest).isSuccess();

        assertTrue(actual);

    }

    @Test
    public void whenDeleteBrandCalledWithInvalidRequest_itShouldThrowBusinessException() throws Exception {
        DeleteBrandRequest deleteBrandRequest = new DeleteBrandRequest(50);
        given(brandDao.existsById(deleteBrandRequest.getBrandId())).willReturn(false);

        assertThrows(BusinessException.class, () -> {
            brandManager.delete(deleteBrandRequest);
        });
    }

}