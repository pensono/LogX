package org.logx;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by Ethan Shea on 5/30/2017.
 */
public class LogXLoggerTest {
    @Test
    public void testLog() throws Exception {
        doTest(logger -> {
                    logger.audit("Test message", "data1", "Wow", "data2", 4);
                },
                repo -> {
                    messageDataEquals(repo, 0, "data1", "Wow");
                    messageDataEquals(repo, 0, "data2", 4);
                });
    }

    @Test
    public void testRichTypedParameters() throws Exception {
        doTest(logger -> {
                    logger.audit("Test message", new FirstName("John"), new LastName("Smith"));
                },
                repo -> {
                    messageDataEquals(repo, 0, "FirstName", new FirstName("John"));
                    messageDataEquals(repo, 0, "LastName", new LastName("Smith"));
                });
    }

    @Test
    public void testPlainAndRichTyped(){
        doTest(logger -> {
                    logger.audit("Test message", new FirstName("John"), "Favorite Integer", 137, new LastName("Smith"));
                },
                repo -> {
                    messageDataEquals(repo, 0, "FirstName", new FirstName("John"));
                    messageDataEquals(repo, 0, "LastName", new LastName("Smith"));
                    messageDataEquals(repo, 0, "Favorite Integer", 137);
                });
    }

    @Test
    public void parentAndChildDataIncluded() throws Exception {
        doTest(logger -> {
                    logger.begin("Test 1", "parent1", 5.0);
                    logger.audit("Test message", "data1", "Wow", "data2", 4);
                    logger.end("Done");
                },
                repo -> {
                    messageDataEquals(repo, 1, "parent1", 5.0);
                    messageDataEquals(repo, 1, "data1", "Wow");
                    messageDataEquals(repo, 1, "data2", 4);
                });
    }

    @Test
    public void childDataOverridesParentData() throws Exception {
        doTest(logger -> {
                    logger.begin("Test 1", "data1", 5.0);
                    logger.audit("Test message", "data1", "Wow", "data2", 4);
                },
                repo -> {
                    messageDataEquals(repo, 1, "data1", "Wow");
                    messageDataEquals(repo, 1, "data2", 4);
                });
    }

    private void doTest(Consumer<LogXLogger> doTest, Consumer<LogXRepository> check){
        LogXRepository repository = new BasicRepository();
        LogXLogger logger = new LogXLogger(repository);

        doTest.accept(logger);
        check.accept(repository);
    }

    private void messageDataEquals(LogXRepository repo, int index, String key, Object expected) {
        Assert.assertEquals(repo.allMessages().get(index).getData(key).get(), expected);
    }

}