package com.ivy.example;

import java.lang.String;

class MealFactory {
  public Meal create(String id) {
    if (id == null) {
      throw new IllegalArgumentException("id is null!");
    }
    if ("Calzone".equals(id)) {
      return new com.ivy.example.CalzonePizza();
    }
    if ("Margherita".equals(id)) {
      return new com.ivy.example.MargheritaPizza();
    }
    if ("Tiramisu".equals(id)) {
      return new com.ivy.example.Tiramisu();
    }
    throw new IllegalArgumentException("Unknown id = " + id);
  }
}
