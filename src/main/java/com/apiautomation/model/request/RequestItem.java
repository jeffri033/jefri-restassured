package com.apiautomation.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestItem {
    /*
     * {\n" + //
                          "  \"title\": \"Le minerale\",\n" + //
                          "  \"description\": \"The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula.\",\n" + //
                          "  \"category\": \"food\",\n" + //
                          "  \"price\": 10000,\n" + //
                          "  \"discountPercentage\": 5,\n" + //
                          "  \"rating\": 5,\n" + //
                          "  \"stock\": 15,\n" + //
                          "  \"tags\": [\n" + //
                          "    \"beauty\",\n" + //
                          "    \"mascara\"\n" + //
                          "  ],\n" + //
                          "  \"dimensions\": {\n" + //
                          "    \"width\": 23.17,\n" + //
                          "    \"height\": 14.43,\n" + //
                          "    \"depth\": 28.01\n" + //
                          "  }\n" + //
                          "}
     */

     @JsonProperty("title")
     public String title;
 
     @JsonProperty("description")
     public String description;
 
     @JsonProperty("category")
     public String category;
 
     @JsonProperty("price")
     public int price;
 
     @JsonProperty("stock")
     public int stock;
 
     @JsonProperty("tags")
     public String[] tags;
 
     @JsonProperty("discountPercentage")
     public float discountPercentage;
 
     @JsonProperty("dimensions")
     public Dimensions dimensions;
 
     @JsonProperty("rating")
     public float rating;
 
 
     public static class Dimensions {
         @JsonProperty("width")
         public float width;
 
         @JsonProperty("height")
         public float height;
 
         @JsonProperty("depth")
         public float depth;
         
     }
}
