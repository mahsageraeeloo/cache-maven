package ir.mahsa.snippets.data;

import ir.mahsa.snippets.cache.Cache;
import ir.mahsa.snippets.cache.EvictionPolicy;

import java.util.Random;

public class CheckCacheThreadSafety {

    public static void main(String[] args) {
        SampleDataProvider dataProvider = new SampleDataProvider();
        Cache<Long, String> cache = new Cache<>(5, dataProvider::readData, EvictionPolicy.LFU);

        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            new Thread(() -> {
                for (int j = 0; j < 20; j++) {
                    cache.readCachedData(new Long((long) r.nextInt(10)));
                }
            }).start();
        }
    }
}
