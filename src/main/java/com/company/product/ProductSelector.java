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

    public enum Field {
        Name, Type, Brand, Unit
    }

    private Map<Field, Predicate<String>> criterionMatchers;

    private ProductSelector(){
        criterionMatchers = new HashMap<>();
        for(Field criterion: Field.values()){
            criterionMatchers.put(criterion, (field)-> true);
        }
    }

    public boolean matches(ProductType type){
        return criterionMatchers.get(Field.Name).test(type.getName()) &&
                criterionMatchers.get(Field.Type).test(type.getType()) &&
                criterionMatchers.get(Field.Brand).test(type.getBrand()) &&
                criterionMatchers.get(Field.Unit).test(type.getUnit());
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private ProductSelector selector;

        Builder(){
            this.selector = new ProductSelector();
        }

        public Builder equals(Field field, String string){
            selector.criterionMatchers.put(field, (value)-> value.equals(string));
            return this;
        }

        public Builder startsWith(Field field, String string){
            selector.criterionMatchers.put(field, (value)-> value.startsWith(string));
            return this;
        }

        public ProductSelector get(){
            return selector;
        }

    }
}