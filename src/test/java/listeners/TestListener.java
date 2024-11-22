package listeners;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

import java.lang.reflect.Method;
import java.util.Date;


@NoArgsConstructor
@Log4j2
public class TestListener implements TestExecutionListener, AfterTestExecutionCallback {
    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        log.info(testIdentifier.getDisplayName());
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        log.info(testIdentifier.getDisplayName());
    }

    @Override
    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
        log.info(testIdentifier.getDisplayName());
    }
    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        Method testMethod = extensionContext.getRequiredTestMethod();
        boolean testFailed = extensionContext.getExecutionException().isPresent();
        if (testFailed) {
            log.info(testMethod.getName() + " was failed");
        } else {
            log.info(testMethod.getName() + " was successfully executed");
        }
    }
}
