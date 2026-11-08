package org.Zadanie8;

import org.Zadanie8.annotation.Email;
import org.Zadanie8.annotation.NotNull;
import org.Zadanie8.annotation.Size;
import org.Zadanie8.annotation.Range;
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
    checkNotNull(field, value, errors);
    checkSize(field, value, errors);
    checkRange(field, value, errors);
    checkEmail(field, value, errors);
  }

  private static void checkNotNull(Field field, Object value, List<ValidationError> errors) {
    NotNull annotation = field.getAnnotation(NotNull.class);
    if (annotation != null) {
      if (value == null) {
        errors.add(new ValidationError(field.getName(), "NotNull", annotation.message()));
      }
    }
  }

  private static void checkSize(Field field, Object value, List<ValidationError> errors) {
    Size annotation = field.getAnnotation(Size.class);
    if (annotation == null) {
      return;
    }
    int min = annotation.min();
    int max = annotation.max();
    if (min < 0 || min > max) {
      throw new ValidationConfigurationException("Invalid @Size bounds on field " + field.getName());
    }
    if (!CharSequence.class.isAssignableFrom(field.getType())) {
      throw new ValidationConfigurationException("@Size is not applicable to field " + field.getName() + " of type " + field.getType().getSimpleName());
    }
    if (value == null) {
      return;
    }
    if (((CharSequence) value).length() > max || ((CharSequence) value).length() < min) {
      errors.add(new ValidationError(field.getName(), "Size", annotation.message()));
    }
  }

  private static void checkRange(Field field, Object value, List<ValidationError> errors) {
    Range annotation = field.getAnnotation(Range.class);
    if (annotation == null) {
      return;
    }
    double min = annotation.min();
    double max = annotation.max();
    if (Double.isNaN(min) || Double.isNaN(max)) {
      throw new ValidationConfigurationException("Invalid @Range bounds on field " + field.getName());
    }
    if (Double.isInfinite(min) || Double.isInfinite(max)) {
      throw new ValidationConfigurationException("Invalid @Range bounds on field " + field.getName());
    }
    if (min > max) {
      throw new ValidationConfigurationException("Invalid @Range bounds on field " + field.getName());
    }
    if (!isNumericType(field.getType())) {
      throw new ValidationConfigurationException("@Range is not applicable to field " + field.getName() + " of type " + field.getType().getSimpleName());
    }
    if (value == null) {
      return;
    }
    double number = ((Number) value).doubleValue();
    if (Double.isNaN(number) || number < min || number > max) {
      errors.add(new ValidationError(field.getName(), "Range", annotation.message()));
    }
  }

  private static boolean isNumericType(Class<?> type) {
    return Number.class.isAssignableFrom(type) || type == int.class || type == long.class || type == double.class || type == short.class || type == byte.class || type == float.class;
  }

  private static void checkEmail(Field field, Object value, List<ValidationError> errors) {
    Email annotation = field.getAnnotation(Email.class);
    if (annotation == null) {
      return;
    }
    if (!CharSequence.class.isAssignableFrom(field.getType())) {
      throw new ValidationConfigurationException("@Email is not applicable to field " + field.getName() + " of type " + field.getType().getSimpleName());
    }
    if (value == null) {
      return;
    }
    if (!isValidEmail(value.toString())) {
      errors.add(new ValidationError(field.getName(), "Email", annotation.message()));
    }
  }

  private static boolean isValidEmail(String email) {
    if (email.isBlank()) {
      return false;
    }
    for (char c : email.toCharArray()) {
      if (Character.isWhitespace(c)) {
        return false;
      }
    }
    int at = email.indexOf('@');
    if (at <= 0) {
      return false;
    }
    String domain = email.substring(at + 1);
    return !domain.isEmpty() && domain.contains(".");
  }
}
