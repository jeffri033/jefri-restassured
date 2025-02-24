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

    @Test
    public void createObject(){
        String json = "{\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\n" + //
                        "   \"data\": {\n" + //
                        "      \"year\": 2019,\n" + //
                        "      \"price\": 20000,\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\n" + //
                        "      \"Hard disk size\": \"1 TB\"\n" + //
                        "   }\n" + //
                        "}";

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
            "id": "ff808181932badb6019513f5bb1844ba",
            "title": "Apple MacBook Pro 16",
            "createdAt": "2025-02-17T12:50:26.200+00:00",
            "data": {
                "year": 2019,
                "price": 1849.99,
                "CPU model": "Intel Core i9",
                "Hard disk size": "1 TB",
                "object":[{
                    "year1": 2019,
                    "price1": 1849.99,
                    "CPU model1": "Intel Core i9",
                    "Hard disk size1": "1 TB",
                },
                {
                    "year1": 2019,
                    "price1": 1849.99,
                    "CPU model1": "Intel Core i9",
                    "Hard disk size1": "1 TB",
                },
                {
                    "year1": 2019,
                    "price1": 1849.99,
                    "CPU model1": "Intel Core i9",
                    "Hard disk size1": "1 TB",
                }]
            }
        }
            String year1 = jsonPath.getString("data.object.year1");
         */

        JsonPath jsonPath = response.jsonPath();
        
        //Cara Pertama
        String id = jsonPath.getString("id");
        String name = jsonPath.getString("title");
        String createdAt = jsonPath.getString("createdAt");
        int year = jsonPath.getInt("data.year");
        int price = jsonPath.getInt("data.price");
        String cpuModel = jsonPath.getString("data.'CPU model'");
        String harddisk = jsonPath.getString("data.'Hard disk size'");

        Assert.assertEquals(name, "Apple MacBook Pro 16");
        Assert.assertNotNull(createdAt);
        Assert.assertNotNull(id);
        Assert.assertEquals(year, 2019);
        Assert.assertEquals(price, 20000);
        Assert.assertEquals(cpuModel, "Intel Core i9");
        Assert.assertEquals(harddisk, "1 TB");



        // Cara Kedua pakai POJO
        responseObject = jsonPath.getObject("", ResponseObject.class);

        Assert.assertEquals(responseObject.name, "Apple MacBook Pro 16");
        Assert.assertNotNull(responseObject.createdAt);
        Assert.assertNotNull(responseObject.id);
        Assert.assertEquals(responseObject.dataItem.year, 2019);
        Assert.assertEquals(responseObject.dataItem.price, 20000);
        Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i9");
        Assert.assertEquals(responseObject.dataItem.hardDiskSize, "1 TB");
    }



    /*
     * Kekurangan:
     * 1. Ketika terjadi perubahan path, kita perlu trace semua tc
     * 2. Susah maintenance
     */
}
