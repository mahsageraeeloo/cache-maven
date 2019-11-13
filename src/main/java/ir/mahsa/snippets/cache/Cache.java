package ir.mahsa.snippets.cache;

import java.util.Hashtable;
import java.util.Observable;
import java.util.function.Function;

public class Cache<K, V> extends Observable {
    private Hashtable<K, V> cachedData;
    /* HashTable is roughly a synchronized version of HashSet with limitation of null values for key and value*/
    private int capacity;
    private Function<K, V> cacheable;
    private Hashtable<K, Object> lockMap;
    private Eviction<K> eviction;
//    private
    //strategy

    public Cache(int capacity, Function<K, V> cacheable, EvictionPolicy evictionPolicy) {
        this.cacheable = cacheable;
        assert capacity > 0; // if entered capacity was <= 0 it will throw an exception
        this.capacity = capacity;
        cachedData = new Hashtable<>(capacity);
        lockMap = new Hashtable<>();
        eviction = evictionPolicy.getEviction();
        eviction.init(capacity);
        this.addObserver(eviction);
//        cachedData = new HashMap<K, V>(capacity);
    }

    public V readCachedData(K key) {
        printCache();
        /*
        We had a problem here: we have read (1) and read(2). The cache is full.
        The key values are different so there is no limitation to run this block.
        Both threads can run ensureRoomForCachePut(), both will see the full capacity they both
        remove the candidate key but we will have only one free space not two and
         */
        synchronized (lockMap.computeIfAbsent(key, k -> new Object())) { // ????
            System.out.println("read key = " + key);
            V oRead = cachedData.get(key);
            if (oRead == null) {
                System.out.println("Cache missed for " + key + " ...");
                notify(CacheEvent.CacheEventType.CACHE_MISS, key);
                oRead = cacheable.apply(key);
                System.out.println(" checking room for " + key + " ...");
                ensureRoomForCachePut();
                cachedData.put(key, oRead);
                notify(CacheEvent.CacheEventType.CACHE_PUT, key);
            } else {
                notify(CacheEvent.CacheEventType.CACHE_HIT, key);
            }
            return oRead;
        }
    }

    private void notify(CacheEvent.CacheEventType cacheEventType, K key) {
        setChanged();
        notifyObservers(new CacheEvent<>(cacheEventType, key));
    }

    private synchronized void ensureRoomForCachePut() {
        /*
        This block should be synchronized: after running several threads it contained more than
        capacity elements
         */
        if (cachedData.size() == capacity) {
            K key = eviction.chooseCandidate();
            System.out.println("candidate " + key + "...");
//            K key = cachedData.keySet().stream().findFirst().get();
            cachedData.remove(key);
            System.out.println("We removed " + key + "...");
            notify(CacheEvent.CacheEventType.CACHE_EVICT, key);
            /*isPresent() in case of there is no entry in the map, but here we are sure that there is */
        }
    }

    public void printCache() {
        System.out.println("cachedData = " + cachedData);
    }
}
