package ir.mahsa.snippets.cache;

import java.util.Observable;
import java.util.Observer;

public interface ICacheObserver<K> extends Observer {
    @Override
    default void update(Observable o, Object arg) {
        update((CacheEvent<K>) arg);
    }

    void update(CacheEvent<K> cacheEvent);
}
