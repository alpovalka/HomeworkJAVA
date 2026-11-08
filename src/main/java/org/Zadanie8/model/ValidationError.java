package org.Zadanie8.model;

public record ValidationError(String fieldName, String annotationName, String message) {
}
