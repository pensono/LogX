package org.logx;

import org.junit.Assert;

import static org.junit.Assert.*;

/**
 * Created by Ethan Shea on 5/30/2017.
 */
public class LoggerTest {
    @org.junit.Test
    public void testLog() throws Exception {
        Logger logger = new Logger();
        logger.log("Test message", "data1", "Wow", "data2", 4);

        Assert.assertEquals(logger.getMessages().get(0).getData("data1").get(), "Wow");
        Assert.assertEquals(logger.getMessages().get(0).getData("data2").get(), 4);
    }

    @org.junit.Test
    public void testBegin() throws Exception {
        Logger logger = new Logger();
        logger.begin("Test 1", "parent1", 5.0);
        logger.log("Test message", "data1", "Wow", "data2", 4);

        Assert.assertEquals(logger.getMessages().get(1).getData("parent1").get(), 5.0);
        Assert.assertEquals(logger.getMessages().get(1).getData("data1").get(), "Wow");
        Assert.assertEquals(logger.getMessages().get(1).getData("data2").get(), 4);
    }
} 