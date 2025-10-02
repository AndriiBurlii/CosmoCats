package com.example.cosmo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class CosmicWordCheckValidator implements ConstraintValidator<CosmicWordCheck, String> {
  private static final Set<String> TERMS = Set.of(
      "star","galaxy","comet","meteor","nebula","orbit","asteroid","cosmos","quasar","nova"
  );
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) return true;
    final String lower = value.toLowerCase();
    return TERMS.stream().anyMatch(lower::contains);
  }
}
