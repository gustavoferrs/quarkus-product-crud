package org.ferrs.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.ferrs.entities.Product;

@ApplicationScoped
public class ProductRepository implements PanacheRepositoryBase<Product, Long> {
}

