package dominikschmidtlein.trace.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by domin_2o9sb4z on 2017-01-09.
 */
public abstract class GraphIterator<T> implements Iterator<T>{

    protected Set<T> visitedElements;
    protected Queue<T> nextElements;

    GraphIterator(T element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        visitedElements = new HashSet<>();
        nextElements = new LinkedList<>();
        nextElements.add(element);
    }

    @Override
    public boolean hasNext() {
        return !nextElements.isEmpty();
    }

    @Override
    public T next() {
        T element = nextElements.remove();
        visitedElements.add(element);
        return element;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
