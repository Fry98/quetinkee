package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.BouquetDao;
import com.quetinkee.eshop.model.Bouquet;
import com.quetinkee.eshop.model.enums.Size;
import com.quetinkee.eshop.rabbit.CacheRabbit;
import com.quetinkee.eshop.rabbit.SearchRabbit;
import com.quetinkee.eshop.utils.helpers.BouquetEdit;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.Validator;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BouquetServiceTest extends TestCase {
    @Mock
    BouquetDao daoMock;
    @Mock
    SearchRabbit searchRabbitMock;
    @Mock
    CacheRabbit cacheRabbitMock;
    @Mock
    Validator validator;
    @InjectMocks
    BouquetService bouquetService;

    @Test
    public void testCreate_callsSaveWithNewBouquet() {
        // arrange
        Bouquet expectedBouquet = new Bouquet("test expectedBouquet", "description", null, "999", Size.MEDIUM, true);
        // act
        bouquetService.create(new BouquetEdit(expectedBouquet), null);
        // assert
        ArgumentCaptor<Bouquet> bouquetCaptor = ArgumentCaptor.forClass(Bouquet.class);
        verify(daoMock, times(1)).save(bouquetCaptor.capture());
        Bouquet newBouquet = bouquetCaptor.getValue();

        assertEquals(expectedBouquet.getName(),  newBouquet.getName());
        assertEquals(expectedBouquet.getPerex(), newBouquet.getPerex());
        assertEquals(expectedBouquet.getPrice(), newBouquet.getPrice());
        assertEquals(expectedBouquet.getSize(),  newBouquet.getSize());
        assertEquals(expectedBouquet.isActive(), newBouquet.isActive());
    }

    @Test
    public void testUpdate_callsSaveWithUpdatedBouquet() {
        // arrange
        String originalName = "original name";
        Bouquet original = new Bouquet(originalName, "description", null, "999", Size.MEDIUM, true);
        Bouquet bouquetUpdate = new Bouquet("new name", null, null, null, null, null);
        ArgumentCaptor<Bouquet> bouquetCapture = ArgumentCaptor.forClass(Bouquet.class);
        // act
        bouquetService.update(original, bouquetUpdate);
        // assert
        verify(daoMock, times(1)).save(bouquetCapture.capture());
        String expectedName = bouquetUpdate.getName();
        String actualName = bouquetCapture.getValue().getName();

        assertNotEquals(originalName, actualName);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void testDelete_callsDeleteInBouquetDao() {
        // arrange
        Bouquet bouquetToDelete = new Bouquet("test bouquet", "description", null, "999", Size.MEDIUM, true);
        ArgumentCaptor<Bouquet> bouquetCapture = ArgumentCaptor.forClass(Bouquet.class);
        // act
        bouquetService.delete(bouquetToDelete);
        // assert
        verify(daoMock, times(1)).delete(bouquetCapture.capture());
        assertSame(bouquetToDelete, bouquetCapture.getValue());
    }
}
