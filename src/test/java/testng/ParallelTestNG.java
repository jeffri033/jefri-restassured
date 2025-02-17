package testng;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import example.ExecutionListener;
import example.StaticProvider;

@Listeners(ExecutionListener.class)
public class ParallelTestNG {
   
    String name = "AfterOffice12";
    
    @Test
    public void scenarioTest1(){
        Assert.assertEquals(name, "AfterOffice12");
        System.out.println("scenario 1");
        System.out.println(Thread.currentThread().getId());
    }

    @Test
    public void scenarioTest2(){
        Assert.assertEquals(name, "AfterOffice12");
        System.out.println("scenario 2");
        System.out.println(Thread.currentThread().getId());
    }

    @Test
    public void scenarioTest3(){
        Assert.assertEquals(name, "AfterOffice12");
        System.out.println("scenario 3");
        System.out.println(Thread.currentThread().getId());
    }

    @Test
    public void scenarioTest4(){
        Assert.assertEquals(name, "AfterOffice12");
        System.out.println("scenario 3");
        System.out.println(Thread.currentThread().getId());
    }

    @Test
    public void scenarioTest5(){
        Assert.assertEquals(name, "AfterOffice12");
        System.out.println("scenario 3");
        System.out.println(Thread.currentThread().getId());
    }

    @Test(dataProvider = "dataproviderPositive", dataProviderClass = StaticProvider.class)
    public void dataTestScenario(String name, int age){
        System.out.println("Nama : " + name + " Umur : " + age);
    }

    @Test(retryAnalyzer = RetryTest.class)
    public void testRetry(){
        System.out.println("Execute test ini");
        assert true;
    }


    //Grouping
    @Test(groups = {"group1"}) 
    public void scenarioTestGroup1(){
        System.out.println("Ini adalah scenario test group1");
    }

    @Test(groups = {"group2"}) 
    public void scenarioTestGroup2(){
        System.out.println("Ini adalah scenario test group2");
    }

    //Parameters
    @Parameters({"program"})
    @Test
    public void scenarioTestParams(String program){
        System.out.println("Nama programnya adalah : " + program);
        Assert.assertEquals(program, "Bootcamp API Automation");
    }

}
