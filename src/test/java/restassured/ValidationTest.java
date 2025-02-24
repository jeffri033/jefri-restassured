package restassured;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiautomation.model.ResponseItem;
import com.apiautomation.model.ResponseObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ValidationTest {
    /*
     * Scenario 1:
     * 1. Hit API create products
     * 2. Then validate response
     * - id is not empty
     * - title, price, discountPercentage, stock, category
     */

    ResponseItem responseItem;
    ResponseObject responseObject;
    

    @Test
    public void createProduct(){
         String json = "{\n" + //
                          "  \"id\": 1,\n" + //
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
                          "}";

        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "products")
                            .pathParam("method", "add")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .post("{path}/{method}");


        System.out.println("add product" + response.asPrettyString());
        /*
         * Response :
         * {
            "id": 195,
            "title": "Le minerale",
            "price": 10.99,
            "discountPercentage": 3.17,
            "stock": 15,
            "rating": 5,
            "description": "Minuman segar dan menyehatkan",
            "category": "food"
        }
         */

        JsonPath addJsonPath = response.jsonPath();

        //Cara Pertama
        String title = addJsonPath.get("title");
        int price = addJsonPath.get("price");
        int discount = addJsonPath.get("discountPercentage");
        int stock = addJsonPath.get("stock");
        String category = addJsonPath.get("category");
        
        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(title,"Le minerale");
        Assert.assertEquals(price,10000);
        Assert.assertEquals(discount, 5);
        Assert.assertEquals(stock, 15);
        Assert.assertEquals(category, "food");




        //Cara Kedua
        responseItem = addJsonPath.getObject("", ResponseItem.class);

        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(responseItem.title,"Le minerale");
        Assert.assertEquals(responseItem.price,10000);
        Assert.assertEquals(responseItem.discountPercentage, 5);
        Assert.assertEquals(responseItem.stock, 15);
        Assert.assertEquals(responseItem.category, "food");

    }





// TASK IMPLEMENTATION POJO: ADD NEW OBJECT

    @Test
    public void createObject(){
        String json = "{\r\n" + //
                        "   \"name\": \"Laptop Testing\",\r\n" + //
                        "   \"data\": {\r\n" + //
                        "      \"year\": 2025,\r\n" + //
                        "      \"price\": 7599000,\r\n" + //
                        "      \"CPU model\": \"Intel Core i5\",\r\n" + //
                        "      \"Hard disk size\": \"1 TB\"\r\n" + //
                        "   }\r\n" + //
                        "}\r\n" + //
                        "";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .post("{path}");


        System.out.println("Response API" + response.asPrettyString());

        /*
         * {
                "id": "ff808181932badb60195398158921242",
                "name": "Laptop Testing",
                "createdAt": "2025-02-24T18:06:01.347+00:00",
                "data": {
                    "year": 2025,
                    "price": 7599000,
                    "CPU model": "Intel Core i5",
                    "Hard disk size": "1 TB"
                }
            }
         */


        // VALIDATION RESPONSE
        
        JsonPath jsonPath = response.jsonPath();
        
        responseObject = jsonPath.getObject("", ResponseObject.class);

        Assert.assertEquals(responseObject.name, "Laptop Testing");
        Assert.assertNotNull(responseObject.createdAt);
        Assert.assertNotNull(responseObject.id);
        Assert.assertEquals(responseObject.dataItem.year, 2025);
        Assert.assertEquals(responseObject.dataItem.price, 7599000);
        Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i5");
        Assert.assertEquals(responseObject.dataItem.hardDiskSize, "1 TB");
    }






// TASK IMPLEMENTATION POJO: GET SINGLE OBJECT

@Test
public void getSingleObject(){
    RestAssured.baseURI = "https://api.restful-api.dev";

    RequestSpecification requestSpecification = RestAssured
                                                 .given();

    Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("idProduct", "ff808181932badb60195398158921242")
                            .pathParam("path", "objects")
                        .when()
                            .get("{path}/{idProduct}");


    System.out.println("Response Get Single Object " + response.asPrettyString());

    /*         
     {
        "id": "ff808181932badb60195398158921242",
        "name": "Laptop Testing",
        "data": {
            "year": 2025,
            "price": 7599000,
            "CPU model": "Intel Core i5",
            "Hard disk size": "1 TB"
        }
    }        
    */



    // VALIDATION RESPONSE
            
    JsonPath jsonPath = response.jsonPath();
            
    responseObject = jsonPath.getObject("", ResponseObject.class);

    Assert.assertEquals(responseObject.name, "Laptop Testing");
    Assert.assertNotNull(responseObject.id);
    Assert.assertEquals(responseObject.dataItem.year, 2025);
    Assert.assertEquals(responseObject.dataItem.price, 7599000);
    Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i5");
    Assert.assertEquals(responseObject.dataItem.hardDiskSize, "1 TB");
}






// TASK IMPLEMENTATION POJO: UPDATE OBJECT

@Test
public void updateObject(){
    String json = "{\r\n" + //
                "        \"id\": \"ff808181932badb60195398158921242\",\r\n" + //
                "        \"name\": \"Laptop Testing Update\",\r\n" + //
                "        \"data\": {\r\n" + //
                "            \"year\": 2024,\r\n" + //
                "            \"price\": 8599000,\r\n" + //
                "            \"CPU model\": \"Intel Core i7\",\r\n" + //
                "            \"Hard disk size\": \"2 TB\"\r\n" + //
                "        }\r\n" + //
                "    }";

    RestAssured.baseURI = "https://api.restful-api.dev";

    RequestSpecification requestSpecification = RestAssured
                                                 .given();
      
      
    Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            .pathParam("idProduct", "ff808181932badb60195398158921242")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .put("{path}/{idProduct}");

    System.out.println("Update Object" + response.asPrettyString());

    /*         
        {
            "id": "ff808181932badb60195398158921242",
            "name": "Laptop Testing Update",
            "updatedAt": "2025-02-24T18:45:22.010+00:00",
            "data": {
                "year": 2024,
                "price": 8599000,
                "CPU model": "Intel Core i7",
                "Hard disk size": "2 TB"
            }
        }        
    */



    // VALIDATION RESPONSE
            
    JsonPath jsonPath = response.jsonPath();
            
    responseObject = jsonPath.getObject("", ResponseObject.class);

    Assert.assertEquals(responseObject.name, "Laptop Testing Update");
    Assert.assertNotNull(responseObject.id);
    Assert.assertNotNull(responseObject.updatedAt);
    Assert.assertEquals(responseObject.dataItem.year, 2024);
    Assert.assertEquals(responseObject.dataItem.price, 8599000);
    Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i7");
    Assert.assertEquals(responseObject.dataItem.hardDiskSize, "2 TB");
}





// TASK IMPLEMENTATION POJO: DELETE OBJECT

@Test
public void deleteObject(){
    RestAssured.baseURI = "https://api.restful-api.dev";

    RequestSpecification requestSpecification = RestAssured
                                                .given();

    Response response = requestSpecification
                        .log()
                        .all()
                        .pathParam("path", "objects")
                        .pathParam("idProduct", "ff808181932badb60195398158921242")
                        .contentType("application/json")
                        .when()
                            .delete("{path}/{idProduct}");
                            
    System.out.println("Delete Object" + response.asPrettyString());

    /*
        {
            "message": "Object with id = ff808181932badb60195398158921242 has been deleted."
        }  
    */


     // VALIDATION RESPONSE
            
     JsonPath jsonPath = response.jsonPath();
            
     responseObject = jsonPath.getObject("", ResponseObject.class);
 
     Assert.assertNotNull(responseObject.message);
     Assert.assertEquals(responseObject.message, "Object with id = ff808181932badb60195398158921242 has been deleted.");
}


    /*
     * Kekurangan:
     * 1. Ketika terjadi perubahan path, kita perlu trace semua tc
     * 2. Susah maintenance
     */
}
