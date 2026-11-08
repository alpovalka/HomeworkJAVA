package org.Zadanie8;

import org.Zadanie8.model.ValidationError;
import org.Zadanie8.model.ValidationResult;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Validator {
  private Validator() {
  }

  public static ValidationResult validate(Object object) {
    Objects.requireNonNull(object, "object must not be null");

    List<ValidationError> errors = new ArrayList<>();

    Class<?> current = object.getClass();
    while (current != null && current != Object.class) {
      for (Field field : current.getDeclaredFields()) {
        if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
          continue;
        }
        Object value = readValue(field, object);
        checkField(field, value, errors);
      }
      current = current.getSuperclass();
    }

    return new ValidationResult(errors);
  }

  private static Object readValue(Field field, Object target) {
    if (!field.trySetAccessible()) {
      throw new ValidationConfigurationException("Cannot access field " + field.getDeclaringClass().getName() + "." + field.getName());
    }
    try {
      return field.get(target);
    } catch (IllegalAccessException e) {
      throw new ValidationConfigurationException("Cannot read field " + field.getDeclaringClass().getName() + "." + field.getName(), e);
    }
  }

  private static void checkField(Field field, Object value, List<ValidationError> errors) {
    
  }
}
