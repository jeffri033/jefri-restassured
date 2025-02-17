package testng;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import example.StaticProvider;

public class TestNG {
   
    String name = "AfterOffice12";
    
    @BeforeClass
    public void setUpClass(){
        System.out.println("Ini untuk setup class");

        // scenario login
        // set API, set credential
        // set URL website
    }


    @Test
    public void checkoutBarang(){
        // checkout barang

        /*
         * login
         * checkout
         */
    }

    @BeforeMethod
    public void setUp(){
        System.out.println("Before method");
    }

    @Test
    public void scenarioTest1(){
        Assert.assertEquals(name, "AfterOffice12");
        System.out.println("scenario 1");
    }

    @Test
    public void scenarioTest2(){
        Assert.assertEquals(name, "AfterOffice12");
        System.out.println("scenario 2");
    }

    @Test
    public void scenarioTest3(){
        Assert.assertEquals(name, "AfterOffice12");
        System.out.println("scenario 3");
    }

    @Test(dataProvider = "dataproviderPositive", dataProviderClass = StaticProvider.class)
    public void dataTestScenario(String name, int age){
        System.out.println("Nama : " + name + " Umur : " + age);
    }

    @AfterMethod
    public void afterUp(){
        System.out.println("After method");
    }

    @AfterClass
    public void setUpAfterClass(){
        System.out.println("ini adalah setup after class");
    }

    String sentence;

    @Test
    public void dependMethod(){
        /*
         * Pertama kita define sentence terlebih dahulu ini akan menjadi inisiasi untuk method yang memiliki dependency kesini
         */

         sentence = "Ini adalah class advance";
    }

    @Test(dependsOnMethods = {"dependMethod"})
    public void dependScenarioTest(){
        /*
         * Kemudian kita akan printout hasilnya
         */

         System.out.println("Hasil dari get value dependency: " + sentence);
    }
}
