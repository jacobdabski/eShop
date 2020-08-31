package com.company.discount;

import com.company.basket.IBasketDiscounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountsService {

    @Autowired
    private IDiscountsDao discountsDao;

    /**
     * Returns all discounts in priority order
     * @return
     */
    public List<Discounter> getDiscounts(){
        return discountsDao.getDiscounts().stream()
                .sorted(Comparator.comparing(Discounter::getPriority))
                .collect(Collectors.toList());
    }

    public IBasketDiscounter getDiscounter(){
        return (products, discounts)-> getDiscounts().forEach((discounter)-> discounter.apply(products, discounts));
    }

    public void addDiscount(Discounter discounter){
        discountsDao.addDiscount(discounter);
    }

}
