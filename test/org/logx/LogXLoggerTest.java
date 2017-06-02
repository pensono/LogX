package org.logx;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Ethan Shea on 5/30/2017.
 */
public class LogXLoggerTest {
    @Test
    public void testLog() throws Exception {
        LogXLogger logger = new LogXLogger();
        logger.audit("Test message", "data1", "Wow", "data2", 4);

        Assert.assertEquals(getMessageData(logger, 0, "data1"), "Wow");
        Assert.assertEquals(getMessageData(logger, 0, "data2"), 4);
    }

    @Test
    public void testRichTypedParameters() throws Exception {
        LogXLogger logger = new LogXLogger();

        logger.audit("Test message", new FirstName("John"), new LastName("Smith"));

        Assert.assertEquals(getMessageData(logger, 0, "FirstName"), new FirstName("John"));
        Assert.assertEquals(getMessageData(logger, 0, "LastName"), new LastName("Smith"));
    }

    @Test
    public void testLogAndRichTyped(){
        LogXLogger logger = new LogXLogger();

        logger.audit("Test message", new FirstName("John"), "Favorite Integer", 137, new LastName("Smith"));

        Assert.assertEquals(getMessageData(logger, 0, "FirstName"), new FirstName("John"));
        Assert.assertEquals(getMessageData(logger, 0, "LastName"), new LastName("Smith"));
        Assert.assertEquals(getMessageData(logger, 0, "Favorite Integer"), 137);
    }

    @Test
    public void testBegin() throws Exception {
        LogXLogger logger = new LogXLogger();
        logger.begin("Test 1", "parent1", 5.0);
        logger.audit("Test message", "data1", "Wow", "data2", 4);

        Assert.assertEquals(getMessageData(logger, 1, "parent1"), 5.0);
        Assert.assertEquals(getMessageData(logger, 1, "data1"), "Wow");
        Assert.assertEquals(getMessageData(logger, 1, "data2"), 4);
    }

    private Object getMessageData(LogXLogger logger, int index, String data) {
        return logger.getMessages().get(index).getData(data).get();
    }

}