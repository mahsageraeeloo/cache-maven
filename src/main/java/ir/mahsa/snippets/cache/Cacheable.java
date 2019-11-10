package ir.mahsa.snippets.cache;

@FunctionalInterface
public interface Cacheable {
    Object readCacheableData(Long key);
}
