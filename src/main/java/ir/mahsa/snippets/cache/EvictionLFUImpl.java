package ir.mahsa.snippets.cache;

import java.util.Hashtable;

class EvictionLFUImpl<K> implements Eviction<K> {
    private Hashtable<K, Integer> data;

    @Override
    public void init(int capacity) {
        data = new Hashtable<>(capacity);
    }

    @Override
    public K chooseCandidate() {
        int min = Integer.MAX_VALUE;
        K candidateKey = null;
        for (K key : data.keySet()) {
            Integer value = data.get(key);
            if (value < min) {
                min = value;
                candidateKey = key;
            }
        }
        return candidateKey;
    }

    @Override
    public void update(CacheEvent<K> cacheEvent) {
        switch (cacheEvent.getType()) {
            case CACHE_PUT:
                data.put(cacheEvent.getKey(), 1);
                break;
            case CACHE_HIT:
                Integer hitFrequency = data.get(cacheEvent.getKey());
                hitFrequency++;
                /*null pointer exception : cache is hitted for key but before
                run gets here it was deleted
                ?????you should think about this part also
                */
                data.replace(cacheEvent.getKey(), hitFrequency);
                break;
            case CACHE_EVICT:
                data.remove(cacheEvent.getKey());
                break;
        }
        System.out.println("cacheEvent = " + cacheEvent.getType() + " Key " + cacheEvent.getKey());
    }
    public void printPolicyData()
    {
        System.out.println("data = " + data);
    }
}
