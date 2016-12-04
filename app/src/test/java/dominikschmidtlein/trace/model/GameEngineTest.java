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
        assertTrue(engine.down(temp));
        assertEquals(engine.getStartPoint(), p12);
        assertEquals(engine.getEndPoint(), temp);
    }

    @Test
    public void testMove() {

    }

    @Test
    public void testUp() {

    }
}