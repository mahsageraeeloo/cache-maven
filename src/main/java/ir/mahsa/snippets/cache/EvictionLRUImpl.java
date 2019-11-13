package ir.mahsa.snippets.cache;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class EvictionLRUImpl<K> implements Eviction<K> {
    private Queue<K> queue;

    @Override
    public void init(int capacity) {
        queue = new ArrayBlockingQueue<K>(capacity, true);
        /* About fair parameter:
         By default, this ordering is not guaranteed.
         However, a queue constructed with fairness set to true grants threads access in FIFO order.
         Fairness generally decreases throughput but reduces variability and avoids starvation.
        */
    }

    @Override
    public K chooseCandidate() {
        return queue.peek();
    }

    @Override
    public void update(CacheEvent<K> cacheEvent) {
        K key = cacheEvent.getKey();
        switch (cacheEvent.getType()) {
            case CACHE_HIT:
                queue.remove(key);
                queue.add(key);
                break;
            case CACHE_EVICT:
                queue.poll();
                break;
            case CACHE_PUT:
                queue.add(key);
                break;
            case CACHE_MISS:

        }

    }
}
