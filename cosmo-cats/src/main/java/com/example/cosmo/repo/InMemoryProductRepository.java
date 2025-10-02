package com.example.cosmo.repo;

import com.example.cosmo.domain.product.Product;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository {
  private final Map<java.util.UUID, Product> store = new ConcurrentHashMap<>();
  public java.util.List<Product> findAll() { return new ArrayList<>(store.values()); }
  public java.util.Optional<Product> findById(java.util.UUID id) { return Optional.ofNullable(store.get(id)); }
  public Product save(Product p) { store.put(p.getId(), p); return p; }
  public boolean deleteById(java.util.UUID id) { return store.remove(id) != null; }
}
