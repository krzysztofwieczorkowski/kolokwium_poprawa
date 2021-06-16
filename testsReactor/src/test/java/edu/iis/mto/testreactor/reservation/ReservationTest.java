package edu.iis.mto.testreactor.reservation;

import edu.iis.mto.testreactor.money.Money;
import edu.iis.mto.testreactor.offer.Discount;
import edu.iis.mto.testreactor.offer.DiscountPolicy;
import edu.iis.mto.testreactor.offer.Offer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    Reservation res;

    @Mock
    Money dummyMoney;
    @Mock
    ClientData dummyClientData;
    @Mock
    DiscountPolicy dummyDiscountPolicy;
    Product product1;
    Product product2;
    Product product3;
    @Mock
    Discount dummyDiscount;


    @BeforeEach
    void setUp() {
        Product product1 = new Product(new Id("prod1"),dummyMoney,"Product1",ProductType.FOOD);
        Product product2 = new Product(new Id("prod2"),dummyMoney,"Product2",ProductType.STANDARD);
        Product product3 = new Product(new Id("prod3"),dummyMoney,"Product3",ProductType.DRUG);


    }

    @Test
    void ifNoItemsGivenReturnEmptyOffer(){
        Reservation res = new Reservation(Id.generate(), Reservation.ReservationStatus.OPENED, dummyClientData, new Date(System.currentTimeMillis()));
        assertTrue(res.calculateOffer(dummyDiscountPolicy).sameAs(new Offer(new ArrayList<>(),new ArrayList<>()),0));
    }
    @Test
    void ifNoDicsountPolicyGivenThrowsException(){
        Reservation res = new Reservation(Id.generate(), Reservation.ReservationStatus.OPENED, dummyClientData, new Date(System.currentTimeMillis()));
        Product product1 = new Product(new Id("prod1"),dummyMoney,"Product1",ProductType.FOOD);
        res.add(product1,1);
        assertThrows(NullPointerException.class,() -> res.calculateOffer(null));
    }
    @Test
    void ifAddedTwoSameArticlesThereIsOneProductWithTwoQuantity(){
        Reservation res = new Reservation(Id.generate(), Reservation.ReservationStatus.OPENED, dummyClientData, new Date(System.currentTimeMillis()));
        dummyMoney = new Money(500);
        Product product1 = new Product(new Id("prod1"),dummyMoney,"Product1",ProductType.FOOD);
        DiscountPolicy dummyDiscountPolicy = new DiscountPolicy() {
            @Override
            public Discount applyDiscount(Product product, int quantity, Money regularCost) {
                return new Discount("dummyCause",dummyMoney);
            }
        };
        res.add(product1,1);
        res.add(product1,1);
        assertTrue(res.calculateOffer(dummyDiscountPolicy).getAvailabeItems().size()==1);

    }
    @Test
    void ifAddedProductsWhileClosedOfferThrowsDomainError(){
        Reservation res = new Reservation(Id.generate(), Reservation.ReservationStatus.OPENED, dummyClientData, new Date(System.currentTimeMillis()));
        dummyMoney = new Money(500);
        Product product1 = new Product(new Id("prod1"),dummyMoney,"Product1",ProductType.FOOD);
        DiscountPolicy dummyDiscountPolicy = new DiscountPolicy() {
            @Override
            public Discount applyDiscount(Product product, int quantity, Money regularCost) {
                return new Discount("dummyCause",dummyMoney);
            }
        };
        res.close();
        assertThrows(DomainOperationException.class, ()-> res.add(product1,1));
    }
    @Test
    void ifAddingNotAvailableProductThrowsDomainError(){
        // brak mmetody ustawiającej ProductStatus
    }
    @Test
    void ifClosingClosedReservationThrowsError(){
        Reservation res = new Reservation(Id.generate(), Reservation.ReservationStatus.OPENED, dummyClientData, new Date(System.currentTimeMillis()));
        res.close();
        assertThrows(DomainOperationException.class, ()-> res.close());
    }
    @Test
    void doesClosingReservationMethodCloseReservation(){
        Reservation res = new Reservation(Id.generate(), Reservation.ReservationStatus.OPENED, dummyClientData, new Date(System.currentTimeMillis()));
        res.close();
        assertTrue(res.isClosed());
    }

}