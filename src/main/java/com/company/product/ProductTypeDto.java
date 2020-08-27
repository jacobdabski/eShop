package com.company.product;

import javax.validation.constraints.NotNull;

public class ProductTypeDto {
    private String name;
    private String type;
    private String brand;
    private String unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    static class Builder {

        private ProductTypeDto dto;

        Builder(){
            dto = new ProductTypeDto();
        }

        public Builder name(String name){
            dto.setName(name);
            return this;
        }

        public Builder brand(String brand){
            dto.setBrand(brand);
            return this;
        }

        public Builder unit(String unit){
            dto.setUnit(unit);
            return this;
        }

        public Builder type(String type){
            dto.setType(type);
            return this;
        }

        public ProductTypeDto get(){
            return dto;
        }

    }
}
