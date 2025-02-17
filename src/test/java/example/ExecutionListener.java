package example;

import org.testng.IExecutionListener;

public class ExecutionListener implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        System.err.println("TestNG is commencing execution");
    }

    @Override
    public void onExecutionFinish() {
        System.err.println("TestNG is finished execution");
    }
    
}
