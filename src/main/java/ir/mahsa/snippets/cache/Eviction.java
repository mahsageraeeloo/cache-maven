package ir.mahsa.snippets.cache;

interface Eviction<K> extends ICacheObserver<K> {
    void init(int capacity);

    K chooseCandidate();
}
