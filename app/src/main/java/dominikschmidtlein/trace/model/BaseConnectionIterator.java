package dominikschmidtlein.trace.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by domin_2o9sb4z on 2017-01-08.
 */
public class BaseConnectionIterator implements Iterator<Connection> {

    private Set<Connection> visitedConnections;
    private Queue<Connection> nextConnections;

    BaseConnectionIterator(Connection connection) {
        visitedConnections = new HashSet<>();
        nextConnections = new LinkedList<>();
        nextConnections.add(connection);
    }

    @Override
    public boolean hasNext() {
        return !nextConnections.isEmpty();
    }

    @Override
    public Connection next() {
        Connection connection = nextConnections.remove();
        visitedConnections.add(connection);
        Set<Connection> newConnections = new HashSet<>();
        for (TracePoint tracePoint : connection.getPoints()) {
            for (Connection newConnection : tracePoint.getConnections()) {
                if (newConnection.getSubConnections().isEmpty()) {
                    newConnections.add(newConnection);
                }
            }
        }
        newConnections.removeAll(visitedConnections);
        newConnections.removeAll(nextConnections);
        nextConnections.addAll(newConnections);
        return connection;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
