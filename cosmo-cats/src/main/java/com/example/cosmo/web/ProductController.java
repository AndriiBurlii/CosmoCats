package com.example.cosmo.web;

import com.example.cosmo.domain.product.Product;
import com.example.cosmo.dto.*;
import com.example.cosmo.mapper.ProductMapper;
import com.example.cosmo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService service;
  private final ProductMapper mapper;

  public ProductController(ProductService service, ProductMapper mapper) {
    this.service = service; this.mapper = mapper;
  }

  @GetMapping
  public List<ProductDto> list() {
    return service.getAll().stream().map(mapper::toDto).toList();
  }

  @GetMapping("/{id}")
  public ProductDto get(@PathVariable UUID id) {
    return mapper.toDto(service.getById(id));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ProductDto create(@Valid @RequestBody ProductCreateRequest req) {
    Product p = mapper.fromCreate(req);
    return mapper.toDto(service.create(p));
  }

  @PutMapping("/{id}")
  public ProductDto update(@PathVariable UUID id, @Valid @RequestBody ProductUpdateRequest req) {
    Product existing = service.getById(id);
    mapper.update(existing, req);
    return mapper.toDto(service.update(id, existing));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }
}
