package dominikschmidtlein.trace;

/**
 * Created by dominik on 19/01/17.
 */

public class Log {
    private static final boolean unit = true;
    private static final boolean verbose = false;
    private static final boolean debug = true;
    private static final boolean info = true;
    private static final boolean wtf = true;
    private static final boolean error = true;
    private static final boolean warn = true;

    public static void v(String tag, String msg) {
        if (verbose) {
            if (unit) {
                System.out.println(tag + "(verbose): " + msg);
            } else {
                android.util.Log.v(tag, msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (debug) {
            if (unit) {
                System.out.println(tag + "(debug): " + msg);
            } else {
                android.util.Log.d(tag, msg);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (info) {
            if (unit) {
                System.out.println(tag + "(info): " + msg);
            } else {
                android.util.Log.i(tag, msg);
            }
        }
    }

    public static void wtf(String tag, String msg) {
        if (wtf) {
            if (unit) {
                System.out.println(tag + "(WTF): " + msg);
            } else {
                android.util.Log.wtf(tag, msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (error) {
            if (unit) {
                System.out.println(tag + "(ERROR): " + msg);
            } else {
                android.util.Log.e(tag, msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (warn) {
            if (unit) {
                System.out.println(tag + "(WARN): " + msg);
            } else {
                android.util.Log.w(tag, msg);
            }
        }
    }
}
