package dominikschmidtlein.trace.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by domin_2o9sb4z on 2017-01-08.
 */
public class ConnectionIterator extends GraphIterator<Connection> {
    protected boolean baseOnly;

    ConnectionIterator(Connection connection, boolean baseOnly) {
        super(connection);
        this.baseOnly = baseOnly;
    }

    @Override
    public Connection next() {
        Connection connection = super.next();
        Set<Connection> newConnections = new HashSet<>();
        for (TracePoint tracePoint : connection.getPoints()) {
            for (Connection newConnection : tracePoint.getConnections()) {
                if (newConnection.isBaseConnection() || !baseOnly) {
                    newConnections.add(newConnection);
                }
            }
        }
        newConnections.removeAll(visitedElements);
        newConnections.removeAll(nextElements);
        nextElements.addAll(newConnections);
        return connection;
    }
}
