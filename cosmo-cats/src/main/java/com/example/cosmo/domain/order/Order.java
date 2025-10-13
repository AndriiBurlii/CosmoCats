package com.example.cosmo.domain.order;

import java.util.UUID;

public class Order {
  private UUID id;
  private UUID cartId;
  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public UUID getCartId() { return cartId; }
  public void setCartId(UUID cartId) { this.cartId = cartId; }
}
