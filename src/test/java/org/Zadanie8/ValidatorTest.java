package org.Zadanie8;

import org.Zadanie8.annotation.Email;
import org.Zadanie8.annotation.NotNull;
import org.Zadanie8.annotation.Range;
import org.Zadanie8.annotation.Size;
import org.Zadanie8.model.ValidationError;
import org.Zadanie8.model.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

  static class User {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Email
    private String email;

    @Range(min = 0, max = 150)
    private Integer age;

    User(String name, String email, Integer age) {
      this.name = name;
      this.email = email;
      this.age = age;
    }
  }

  static class SizeHolder {
    @Size(min = 2, max = 5)
    private String value;

    SizeHolder(String value) {
      this.value = value;
    }
  }

  static class RangeHolder {
    @Range(min = 0, max = 10)
    private int value;

    RangeHolder(int value) {
      this.value = value;
    }
  }

  static class Person {
    @NotNull
    private static String constant = null;

    @NotNull
    private String id;

    Person(String id) {
      this.id = id;
    }
  }

  static class Student extends Person {
    Student(String id) {
      super(id);
    }
  }

  static class BadBounds {
    @Size(min = 5, max = 2)
    private String value;
  }

  static class SizeOnInteger {
    @Size
    private Integer value;
  }

  private static Set<String> keys(ValidationResult result) {
    return result.getErrors().stream()
      .map(e -> e.fieldName() + ":" + e.annotationName())
      .collect(Collectors.toSet());
  }

  @Test
  void validObjectHasNoErrors() {
    ValidationResult result = Validator.validate(new User("Alena", "a@b.ru", 20));

    assertTrue(result.isValid());
    assertTrue(result.getErrors().isEmpty());
  }

  @Test
  void notNullDetectsViolation() {
    assertEquals(Set.of("name:NotNull"), keys(Validator.validate(new User(null, "a@b.ru", 20))));
  }

  @Test
  void emailDetectsViolation() {
    assertEquals(Set.of("email:Email"), keys(Validator.validate(new User("Alena", "invalid-email", 20))));
  }

  @ParameterizedTest
  @ValueSource(strings = {"ab", "abcde"})
  void sizeBoundariesPass(String value) {
    assertTrue(Validator.validate(new SizeHolder(value)).isValid());
  }

  @ParameterizedTest
  @ValueSource(strings = {"a", "abcdef"})
  void sizeOutOfRangeFails(String value) {
    assertEquals(Set.of("value:Size"), keys(Validator.validate(new SizeHolder(value))));
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 10})
  void rangeBoundariesPass(int value) {
    assertTrue(Validator.validate(new RangeHolder(value)).isValid());
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 11})
  void rangeOutOfBoundsFails(int value) {
    assertEquals(Set.of("value:Range"), keys(Validator.validate(new RangeHolder(value))));
  }

  @Test
  void collectsAllViolationsInOneCall() {
    ValidationResult result = Validator.validate(new User("A", "invalid-email", 200));

    assertEquals(Set.of("name:Size", "email:Email", "age:Range"), keys(result));
  }

  @Test
  void validateNullThrowsNpe() {
    assertThrows(NullPointerException.class, () -> Validator.validate(null));
  }

  @Test
  void invalidBoundsThrowConfigurationException() {
    assertThrows(ValidationConfigurationException.class, () -> Validator.validate(new BadBounds()));
  }

  @Test
  void unsupportedTypeThrowsConfigurationException() {
    assertThrows(ValidationConfigurationException.class, () -> Validator.validate(new SizeOnInteger()));
  }

  @Test
  void validatesPrivateInheritedFieldsAndIgnoresStatic() {
    assertEquals(Set.of("id:NotNull"), keys(Validator.validate(new Student(null))));
  }

  @Test
  void errorsListIsUnmodifiable() {
    ValidationResult result = Validator.validate(new User(null, "a@b.ru", 20));

    assertThrows(UnsupportedOperationException.class,
      () -> result.getErrors().add(new ValidationError("x", "y", "z")));
  }
}