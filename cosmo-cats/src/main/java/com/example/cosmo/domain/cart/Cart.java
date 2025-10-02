package com.example.cosmo.domain.cart;

import java.util.UUID;

public class Cart {
  private UUID id;
  private UUID userId;
  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public UUID getUserId() { return userId; }
  public void setUserId(UUID userId) { this.userId = userId; }
}
