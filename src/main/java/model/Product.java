package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class Product {
    private int Id;
    private String name;
    private Category category;
    private double price;
    private int quantity;
}
