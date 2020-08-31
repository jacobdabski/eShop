package com.company.product;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * A helper class that is used to match products against non specific criteria
 * Criteria (ie fields) are defined as enums and each enum has a corresponding
 * matching function that will be executed when comparing the fields on the ProductType tested.
 * Use ProductSelector.builder to construct the criteria.
 */
public class ProductSelector {

    public enum Criterion {
        Name, Type, Brand, Unit
    }

    private Map<Criterion, Predicate<String>> criterionMatchers;

    private ProductSelector(){
        criterionMatchers = new HashMap<>();
        for(Criterion criterion: Criterion.values()){
            criterionMatchers.put(criterion, (field)-> true);
        }
    }

    public boolean matches(ProductType type){
        return criterionMatchers.get(Criterion.Name).test(type.getName()) &&
                criterionMatchers.get(Criterion.Type).test(type.getType()) &&
                criterionMatchers.get(Criterion.Brand).test(type.getBrand()) &&
                criterionMatchers.get(Criterion.Unit).test(type.getUnit());
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private ProductSelector selector;

        Builder(){
            this.selector = new ProductSelector();
        }

        public Builder equals(Criterion criterion, String string){
            selector.criterionMatchers.put(criterion, (field)-> field.equals(string));
            return this;
        }

        public Builder startsWith(Criterion criterion, String string){
            selector.criterionMatchers.put(criterion, (field)-> field.startsWith(string));
            return this;
        }

        public ProductSelector get(){
            return selector;
        }

    }
}