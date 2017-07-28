package net.catchpole.console;

public interface DataUpdate<T> {
    void update(T object);

    void clear();
}
