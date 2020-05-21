package org.example;
import com.google.firebase.database.*;

import java.awt.*;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws AWTException {
        Thread t = new Thread(new ShowDBChanges());

        t.start();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
