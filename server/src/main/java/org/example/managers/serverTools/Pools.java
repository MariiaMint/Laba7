package org.example.managers.serverTools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pools {
   static ExecutorService requestProcessingPool = Executors.newFixedThreadPool(20);
    static ExecutorService responseSenderPool = Executors.newCachedThreadPool();

    public static ExecutorService getRequestProcessingPool() {
        return requestProcessingPool;
    }

    public static ExecutorService getResponseSenderPool() {
        return responseSenderPool;
    }
}
