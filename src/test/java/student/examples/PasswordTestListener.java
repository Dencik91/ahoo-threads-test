package student.examples;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;

public class PasswordTestListener implements ITestListener {

    private List<Boolean> results;

    @Override
    public void onStart(ITestContext context) {
        results = new ArrayList<>();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        results.add(true);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        results.add(false);
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("AAAA" + results.size());
    }
}
