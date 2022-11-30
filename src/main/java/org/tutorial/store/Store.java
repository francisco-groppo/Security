package org.tutorial.store;

import lombok.Getter;
import lombok.Setter;
import org.tutorial.product.Product;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nameOfStore;
    private String typeOfStore;

    @OneToMany
    private List<Product> products;
}
