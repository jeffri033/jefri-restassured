package restassured;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredImpl {
    public static void main(String[] args) {
        getAllProducts();
        getSingleProduct();
        getProductsByIds();
        //searchProduct();
        addProduct();

        updateProduct();
        updatePartialProduct();
        deleteProduct();
    }


    // branch submission-json-validation
    // implementation POJO

    public static String auth(){

        String token;
        String json = "{\n" + //
                        "  \"username\": \"emilys\",\n" + //
                        "  \"password\": \"emilyspass\",\n" + //
                        "  \"expiresInMins\": 30\n" + //
                        "}";

        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

       Response response = requestSpecification
                               .log()
                               .all()
                               .body(json)
                               .contentType("application/json")
                               .pathParam("path", "auth")
                               .pathParam("section", "login")
                           .when()
                               .post("{path}/{section}");

        JsonPath jsonPath = response.jsonPath();

        token = jsonPath.get("accessToken");

        return token;
    }



    public static void getAllProducts(){
        //Define baseURI
       /*
        *  "https://api.restful-api.dev/objects"
        baseURI = https://api.restful-api.dev
        path = objects
        */

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification.log().all().get("objects");


        Response response2 = requestSpecification
                                .log()
                                .all()
                            .when()
                                .get("objects");

        
        System.out.println("GET ALL PRODUCTS");
        System.out.println("Hasilnya adalah " + response2.asPrettyString());
        System.out.println("Hasilnya adalah " + response.asPrettyString());
    }


    public static void getSingleProduct(){
        /*
         * 'https://api.restful-api.dev/objects/1'
         */

        String token;

        token = auth();


         RestAssured.baseURI = "https://api.restful-api.dev";
         RequestSpecification requestSpecification = RestAssured
                                                     .given();

        Response response = requestSpecification
                                .log()
                                .all()
                                .pathParam("idProduct", 1)
                                .pathParam("path", "objects")
                                .header("Authorization", "Berear " + token)
                            .when()
                                .get("{path}/{idProduct}");

 
        System.out.println("GET SINGLE PRODUCT");
        System.out.println("single Product " + response.asPrettyString()); 
    }




    public static void getProductsByIds(){
        /*
         * 'https://api.restful-api.dev/objects?id=3&id=5&id=10'
         */

       


         RestAssured.baseURI = "https://api.restful-api.dev";
         RequestSpecification requestSpecification = RestAssured
                                                     .given();

        Response response = requestSpecification
                                .log()
                                .all()
                                .pathParam("idProduct", "3&id=5&id=10")            
                                .pathParam("path", "objects")    
                            .when()                    
                               .get("{path}?id={idProduct}");

 
        System.out.println("GET PRODUCTs by IDs");
        System.out.println("List Products " + response.asPrettyString()); 
    }


    public static void addProduct(){

        String json = "{\r\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\r\n" + //
                        "   \"data\": {\r\n" + //
                        "      \"year\": 2019,\r\n" + //
                        "      \"price\": 1849.99,\r\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\r\n" + //
                        "      \"Hard disk size\": \"1 TB\"\r\n" + //
                        "   }\r\n" + //
                        "}";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            //.pathParam("method", "add")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                //.post("{path}/{method}");
                                .post("{path}");
        System.out.println("add product" + response.asPrettyString());
    }




    public static void updateProduct(){
        String json = "{\r\n" + //
                        "   \"name\": \"TEST EDIT PRODUK\",\r\n" + //
                        "   \"data\": {\r\n" + //
                        "      \"year\": 2024,\r\n" + //
                        "      \"price\": 5000.99,\r\n" + //
                        "      \"CPU model\": \"Intel Core i3\",\r\n" + //
                        "      \"Hard disk size\": \"10 TB\",\r\n" + //
                        "      \"color\": \"gold\"\r\n" + //
                        "   }\r\n" + //
                        "}";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            .pathParam("idProduct", "ff808181932badb601951390de5442f0")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .put("{path}/{idProduct}");
        System.out.println("update product" + response.asPrettyString());
    }



    public static void updatePartialProduct(){
        String json = "{\r\n" + //
                        "   \"name\": \"Apple MacBook Pro 16 (Updated Name)\"\r\n" + //
                        "}";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            .pathParam("idProduct", "ff808181932badb601951390de5442f0")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .patch("{path}/{idProduct}");
        System.out.println("update partial product" + response.asPrettyString());
    }



    public static void deleteProduct(){
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            .pathParam("idProduct", "ff808181932badb60195138e043b42e4")
                            .contentType("application/json")
                            .when()
                                .delete("{path}/{idProduct}");
        System.out.println("delete product" + response.asPrettyString());
    }
}
