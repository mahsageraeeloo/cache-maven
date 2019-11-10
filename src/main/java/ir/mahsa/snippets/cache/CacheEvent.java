package ir.mahsa.snippets.cache;

public class CacheEvent<K> {
    public enum CacheEventType {
        CACHE_HIT,
        CACHE_MISS,
        CACHE_EVICT,
        CACHE_PUT;
    }

    private final CacheEventType type;
    private final K key;

    public CacheEvent(CacheEventType type, K key) {
        this.type = type;
        this.key = key;
    }

    public CacheEventType getType() {
        return type;
    }

    public K getKey() {
        return key;
    }
}
