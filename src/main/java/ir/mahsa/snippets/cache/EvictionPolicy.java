package ir.mahsa.snippets.cache;

import java.util.function.Supplier;

public enum EvictionPolicy {
    LFU(EvictionLFUImpl::new)
    ;

    private Supplier<Eviction<?>> evictionSupplier;

    EvictionPolicy(Supplier<Eviction<?>> evictionSupplier) {
        this.evictionSupplier = evictionSupplier;
    }

    public <K> Eviction<K> getEviction() {
        return (Eviction<K>) evictionSupplier.get();
    }
}
