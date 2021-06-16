package edu.iis.mto.testreactor.reservation;

import edu.iis.mto.testreactor.money.Money;
import edu.iis.mto.testreactor.offer.DiscountPolicy;
import edu.iis.mto.testreactor.offer.Offer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Mock
    Money dummyMoney;
    @Mock
    DiscountPolicy dummyDiscountPolicy;
    @Mock
    ClientData dummyClientData;
    @Mock
    Reservation res;

    @BeforeEach
    void setUp() {
        Product product1 = new Product(new Id("prod1"),dummyMoney,"Product1",ProductType.FOOD);
        Product product2 = new Product(new Id("prod2"),dummyMoney,"Product2",ProductType.STANDARD);
        Product product3 = new Product(new Id("prod3"),dummyMoney,"Product3",ProductType.DRUG);
        Reservation res = new Reservation(new Id("res1"), Reservation.ReservationStatus.OPENED, dummyClientData, new Date(System.currentTimeMillis()));

    }

    @Test
    void ifNoItemsGivenReturnEmptyOffer(){
        assertTrue(res.calculateOffer(dummyDiscountPolicy).sameAs(new Offer(new ArrayList<>(),new ArrayList<>()),0));
    }
    @Test
    void ifAddedTwoSameArticlesThereIsOneProductWithTwoQuantity(){

    }
    @Test
    void ifAddedProductsWhileClosedOfferThrowsDomainError(){

    }
    @Test
    void ifAddingNotAvailableProductThrowsDomainError(){

    }
    @Test
    void ifClosingClosedReservationThrowsError(){

    }
    @Test
    void doesClosingReservationMethodCloseReservation(){
        
    }

}