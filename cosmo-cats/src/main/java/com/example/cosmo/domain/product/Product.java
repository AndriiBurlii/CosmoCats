package com.example.cosmo.domain.product;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
  private UUID id;
  private String name;
  private String description;
  private BigDecimal price;
  private UUID categoryId;

  public Product() {}
  public Product(UUID id, String name, String description, BigDecimal price, UUID categoryId) {
    this.id = id; this.name = name; this.description = description; this.price = price; this.categoryId = categoryId;
  }
  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) { this.price = price; }
  public UUID getCategoryId() { return categoryId; }
  public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }
}
