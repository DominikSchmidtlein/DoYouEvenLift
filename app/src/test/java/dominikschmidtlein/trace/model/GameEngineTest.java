package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2016-11-28.
 */
public class GameEngineTest {

    GameEngine engine;
    Trace trace;
    TracePoint p12;
    TracePoint p21;
    TracePoint p32;
    TracePoint p43;
    TracePoint p52;
    TracePoint p61;
    TracePoint p72;

    @Before
    public void setUp() {
        trace = new Trace();
        p12 = new TracePoint(100, 200);
        p21 = new TracePoint(200, 100);
        p32 = new TracePoint(300, 200);
        p43 = new TracePoint(400, 300);
        p52 = new TracePoint(500, 200);
        p61 = new TracePoint(600, 100);
        p72 = new TracePoint(700, 200);
        trace.addConnection(p12, p21);
        trace.addConnection(p12, p72);
        trace.addConnection(p21, p43);
        trace.addConnection(p61, p43);
        trace.addConnection(p61, p72);

        engine = new GameEngine(trace);
    }

    @Test
    public void testDown() throws Exception {
        TracePoint temp = new TracePoint(101, 201);
        engine.down(temp);
        assertEquals(engine.getStartPoint(), p12);
        assertEquals(engine.getEndPoint(), temp);

        engine.up();

        temp = new TracePoint(0, 0);
        engine.down(temp);
        assertNull(engine.getStartPoint());
        assertNull(engine.getEndPoint());
    }

    @Test
    public void testMove() {
        engine.move(p21);
        assertNull(engine.getStartPoint());
        assertNull(engine.getEndPoint());

        engine.down(p12);
        engine.move(p21);
        engine.move(new TracePoint(0, 0));
        assertEquals(engine.getStartPoint(), p21);
        assertEquals(engine.getEndPoint(), new TracePoint(0, 0));
    }

    @Test
    public void testUp() {
        engine.down(p12);
        engine.move(p72);
        engine.move(p61);
        engine.move(p43);
        engine.move(p21);
        engine.move(p12);
        engine.up();
        // receive win event
    }
}