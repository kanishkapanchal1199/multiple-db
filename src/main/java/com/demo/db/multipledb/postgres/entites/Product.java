package com.demo.db.multipledb.postgres.entites;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="entities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String description;

    private boolean live;

    private double price;

}
