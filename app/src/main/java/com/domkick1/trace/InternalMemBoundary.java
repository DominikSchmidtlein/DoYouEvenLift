package com.domkick1.trace;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by domin_2o9sb4z on 2016-03-15.
 */
public class InternalMemBoundary {

    private final Context context;

    public InternalMemBoundary(Context context) {
        this.context = context;
    }

    public String read(String filename) {
        try {
            byte[] buf = new byte[(int) new File(context.getFilesDir(), filename).length()];
            FileInputStream inputStream = context.openFileInput(filename);
            inputStream.read(buf);
            inputStream.close();
            return new String(buf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException ee) {
            ee.printStackTrace();
            return null;
        }
    }

    public void write(String filename, String jsonString) {
        try {
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }

}
