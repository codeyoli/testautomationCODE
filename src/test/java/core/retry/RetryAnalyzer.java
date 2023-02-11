package core.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int currentRetryCount = 0;
    private int maxRetryCount = 1;

    @Override
    public boolean retry(ITestResult result) {

        if(result.isSuccess())
            return false;

        if (currentRetryCount < maxRetryCount) {
            currentRetryCount++;
            return true;
        }else{
            System.out.println("Ending test retry..");
            return false;
        }
    }
}//end::class