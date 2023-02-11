package core.retry;

import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestAnnotationMutator implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation testAnnotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod)
    {
        testAnnotation.setRetryAnalyzer(core.retry.RetryAnalyzer.class);
    }
}
