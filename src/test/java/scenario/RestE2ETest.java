package scenario;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiautomation.model.ResponseItem;
import com.apiautomation.model.ResponseObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class RestE2ETest {

    ResponseItem responseItem;
    ResponseObject responseObject;


    /*
     * SCENARIO
     
     * 1. Hit add products (verify response)
     * 2. Hit get Products (verify response)
     * 3. Hit update product (verify response)
     */

    @Test
    public void scenarioE2ETest(){
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
        // Add product
        RestAssured.baseURI = "https://dummyjson.com";


        Response response = given()
                            .log()
                            .all()
                            .pathParam("path", "products")
                            .pathParam("method", "add")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .post("{path}/{method}");

        System.out.println("add product" + response.asPrettyString());

        JsonPath addJsonPath = response.jsonPath();

        responseItem = addJsonPath.getObject("", ResponseItem.class);

        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(responseItem.title,"Le minerale");
        Assert.assertEquals(responseItem.price,10000);
        Assert.assertEquals(responseItem.discountPercentage, 5);
        Assert.assertEquals(responseItem.stock, 15);
        Assert.assertEquals(responseItem.category, "food");


        String idObject = responseItem.id;

        //Get Product
        Response response2 = given()
                                .pathParam("path", "products")
                                .pathParam("idProduct", idObject)
                                .log()
                                .all()
                            .when()
                                .get("{path}/{idProduct}");
        System.out.println("response2" + response2.asPrettyString());

        //validation POJO

        //Update Product
        Response responseUpdate = given()
                            .log()
                            .all()
                            .pathParam("path", "products")
                            .pathParam("idProduct", idObject)
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .put("{path}/{idProduct}");
        System.out.println("update product" + responseUpdate.asPrettyString());

        //Validation POJO

    }





      /*
     * TASK E2E TEST
     
       1. Create new object (hit API add_object)
       2. Verify new object is added (hit API single_object)
       3. Delete product (hit API delete_object)
       4. Verify new object is deleted (hit API single_object) 
     */

    @Test
    public void taskE2ETest(){
        // 1. ADD OBJECT
        
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

        Response responseAdd = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .post("{path}");


        System.out.println("Response add object" + responseAdd.asPrettyString());

   


        // VALIDATION RESPONSE ADD OBJECT
        
        JsonPath jsonPath = responseAdd.jsonPath();
        
        responseObject = jsonPath.getObject("", ResponseObject.class);
        
        Assert.assertEquals(responseObject.name, "Laptop Testing");
        Assert.assertNotNull(responseObject.createdAt);
        Assert.assertNotNull(responseObject.id);
        Assert.assertEquals(responseObject.dataItem.year, 2025);
        Assert.assertEquals(responseObject.dataItem.price, 7599000);
        Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i5");
        Assert.assertEquals(responseObject.dataItem.hardDiskSize, "1 TB");

        // get object id
        String idObject = responseObject.id;



        // 2. GET SINGLE OBJECT

        Response responseGet = given()
        .pathParam("path", "objects")
        .pathParam("idObject", idObject)
        .log()
        .all()
        .when()
            .get("{path}/{idObject}");

        System.out.println("response get single object" + responseGet.asPrettyString());


        
        // VALIDATION RESPONSE GET SINGLE OBJECT
            
        JsonPath jsonPathGet = responseGet.jsonPath();
                
        responseObject = jsonPathGet.getObject("", ResponseObject.class);

        Assert.assertEquals(responseObject.name, "Laptop Testing");
        Assert.assertNotNull(responseObject.id);
        Assert.assertEquals(responseObject.dataItem.year, 2025);
        Assert.assertEquals(responseObject.dataItem.price, 7599000);
        Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i5");
        Assert.assertEquals(responseObject.dataItem.hardDiskSize, "1 TB");



        // 3. DELETE OBJECT
        Response responseDelete = requestSpecification
        .log()
        .all()
        .pathParam("path", "objects")
        .pathParam("idObject", idObject)
        .contentType("application/json")
        .when()
            .delete("{path}/{idObject}");
            
        System.out.println("Delete Object" + responseDelete.asPrettyString());


         // VALIDATION RESPONSE DELETE OBJECT
            
        JsonPath jsonPathDelete = responseDelete.jsonPath();
                
        responseObject = jsonPathDelete.getObject("", ResponseObject.class);
    
        Assert.assertNotNull(responseObject.message);



        // 4. VERIFY OBJECT IS DELETED
        Response responseVerifyDeleted = given()
        .pathParam("path", "objects")
        .pathParam("idObject", idObject)
        .log()
        .all()
        .when()
            .get("{path}/{idObject}");

        System.out.println("response verify deleted" + responseVerifyDeleted.asPrettyString());


        
        // VALIDATION RESPONSE GET DELETED OBJECT      
        JsonPath jsonPathVerifyDeleted = responseVerifyDeleted.jsonPath();
                
        responseObject = jsonPathVerifyDeleted.getObject("", ResponseObject.class);

        Assert.assertNotNull(responseObject.error);
    }
}
