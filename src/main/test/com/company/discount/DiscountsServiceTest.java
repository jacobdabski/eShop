package com.company.discount;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscountsServiceTest {

    @InjectMocks
    private DiscountsService service;

    @Mock
    private IDiscountsDao dao;

    @BeforeEach
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getDiscountsNone(){
        Mockito.when(dao.getDiscounts()).thenReturn(Collections.emptyList());
        assertTrue(service.getDiscounts().isEmpty());
    }

    @Test
    void getDiscountsOne(){
        Discounter discounter = mockDiscounter(1);
        Mockito.when(dao.getDiscounts()).thenReturn(Collections.singletonList(discounter));
        assertEquals(service.getDiscounts().size(), 1);
    }

    @Test
    void getDiscountsInPriority(){
        List<Discounter> unsorted = new ArrayList<>();
        unsorted.add(mockDiscounter(3));
        unsorted.add(mockDiscounter(1));
        unsorted.add(mockDiscounter(2));
        Mockito.when(dao.getDiscounts()).thenReturn(unsorted);
        List<Discounter> sorted = service.getDiscounts();
        int size = 3;
        assertEquals(size, sorted.size());
        assertTrue(discountersInPriority(sorted), "Discounter list not in priority order");
    }

    private boolean discountersInPriority(List<Discounter> discounters){
        for(int index = 1; index < discounters.size()-1; index++){
            if(discounters.get(index).getPriority() < discounters.get(index-1).getPriority()){
                return false;
            }
        }
        return true;
    }

    private Discounter mockDiscounter(int priority){
        Discounter discounter = Mockito.mock(Discounter.class);
        Mockito.when(discounter.getPriority()).thenReturn(priority);
        return discounter;
    }
}