package org.logx;

/**
 * Created by Ethan Shea on 5/31/2017.
 */
public enum LogXLevel implements Comparable<LogXLevel> {
    /**
     * Information which is not relevant to a production environment
     */
    DEBUG(100000),

    /**
     * Information which could be useful for diagnosing problems
     */
    INFO(1000),

    /**
     * Information which must be rocorded
     */
    AUDIT(400),

    /**
     * A problem which should be fixed eventually, but has little business impact
     */
    FIX_EVENTUALLY(300),

    /**
     * A problem which has a business impact and should be dealt with haste
     */
    FIX_SOON(200),

    /**
     * A problem which has a large impact on many business interests and customers.
     */
    FIX_IMMEDIATELY(100),

    /**
     * A problem which threatens the livelihood of the business.
     */
    SERVER_FIRE(0);

    private int numericalLevel;

    LogXLevel(int numericalLevel){
        this.numericalLevel = numericalLevel;
    }
}
