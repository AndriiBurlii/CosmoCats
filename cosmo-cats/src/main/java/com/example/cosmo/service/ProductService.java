package com.example.cosmo.service;

import com.example.cosmo.common.NotFoundException;
import com.example.cosmo.domain.product.Product;
import com.example.cosmo.repo.InMemoryProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
  private final InMemoryProductRepository repo = new InMemoryProductRepository();

  public List<Product> getAll() { return repo.findAll(); }
  public Product getById(UUID id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException("Product %s not found".formatted(id)));
  }
  public Product create(Product p) { return repo.save(p); }
  public Product update(UUID id, Product updater) {
    Product existing = getById(id);
    existing.setName(updater.getName());
    existing.setDescription(updater.getDescription());
    existing.setPrice(updater.getPrice());
    existing.setCategoryId(updater.getCategoryId());
    return repo.save(existing);
  }
  public void delete(UUID id) {
    if (!repo.deleteById(id)) throw new NotFoundException("Product %s not found".formatted(id));
  }
}
