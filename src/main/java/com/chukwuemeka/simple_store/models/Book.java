package com.chukwuemeka.simple_store.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Book extends BaseEntity {

    private String title;

    private String author;

//    @ManyToOne
//    @JoinColumn(name = "book_adder_id",
//    referencedColumnName = "id")
//    private User bookAdder;

    private double price;

    private boolean available;

    private Integer quantity;

}
