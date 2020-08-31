package com.company.discount;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryDiscountsDao implements IDiscountsDao{

    private List<Discounter> prioritisedDiscounters = new ArrayList<>();

    @Override
    public void addDiscount(Discounter discounter) {
        prioritisedDiscounters.add(discounter);
    }

    @Override
    public void updatePriority(Discounter discounter, int priority) {
        prioritisedDiscounters.remove(discounter);
        discounter.setPriority(priority);
        addDiscount(discounter);
    }

    @Override
    public Collection<Discounter> getDiscounts() {
        return prioritisedDiscounters;
    }
}