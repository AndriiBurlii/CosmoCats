package com.example.cosmo.mapper;

import com.example.cosmo.domain.product.Product;
import com.example.cosmo.dto.ProductCreateRequest;
import com.example.cosmo.dto.ProductDto;
import com.example.cosmo.dto.ProductUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  ProductDto toDto(Product product);

  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  Product fromCreate(ProductCreateRequest req);

  @BeanMapping(ignoreByDefault = true)
  @Mappings({
      @Mapping(target = "name", source = "name"),
      @Mapping(target = "description", source = "description"),
      @Mapping(target = "price", source = "price"),
      @Mapping(target = "categoryId", source = "categoryId")
  })
  void update(@MappingTarget Product target, ProductUpdateRequest req);
}
