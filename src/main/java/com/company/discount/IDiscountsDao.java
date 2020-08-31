package com.company.discount;

import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IDiscountsDao {

    void addDiscount(Discounter discounter);
    void updatePriority(Discounter discounter, int priority);
    Collection<Discounter> getDiscounts();

}
