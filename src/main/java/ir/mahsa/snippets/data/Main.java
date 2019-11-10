package ir.mahsa.snippets.data;

import ir.mahsa.snippets.cache.Cache;
import ir.mahsa.snippets.cache.EvictionPolicy;

public class Main {
    public static void main(String[] args) {
        SampleDataProvider sampleDataProvider = new SampleDataProvider();

//        Function
        Cache<Long, String> c = new Cache<>(10,
                sampleDataProvider::readData, EvictionPolicy.LFU);
        // Functional Interface, Lambda, Method reference
//        Cache<Long, String> c = new Cache<>(10, aLong -> sampleDataProvider.readData(aLong));
        /* //Anonymous Cache<Long, String> c = new Cache<>(10, new Function<Long, String>() {
            @Override
            public String apply(Long aLong) {
                return sampleDataProvider.readData(aLong);
            }
        });*/
//        Cache<Long, String> c = new Cache<>(2, key -> sampleDataProvider.readData(key));
        long key;
        String s;

        key = 456987;
        s = c.readCachedData(key);
        System.out.println("s = " + s);

        key = 96145;
        s = c.readCachedData(key);
        System.out.println("s = " + s);

        key = 456987;
        s = c.readCachedData(key);
        System.out.println("s = " + s);

        key = 74839;
        s = c.readCachedData(key);
        System.out.println("s = " + s);

        c.printCache();
    }
}
