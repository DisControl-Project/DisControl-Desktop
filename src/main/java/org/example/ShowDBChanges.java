package org.example;

import com.google.firebase.database.*;

import java.awt.*;
import java.io.IOException;

public class ShowDBChanges implements Runnable {
    int oldX = 0;
    int oldY = 0;
    int newX;
    int newY;
    Robot robot = new Robot();

    public ShowDBChanges() throws AWTException {
    }

    public void move(int x, int y){
        robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x - x, MouseInfo.getPointerInfo().getLocation().y - y);
    }
   // cuando se hace tap oldX y oldY sea igual a newX y newY
    public void run() {
        FireBaseService fbs = null;
        try {
            fbs = new FireBaseService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatabaseReference ref = fbs.getDb()
                .getReference("/");
        ref.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
//                Object document = dataSnapshot.getValue();

                System.out.println("---");
                newX = (int) Double.parseDouble(dataSnapshot.child("usuario").child("pos").child("x_pos").getValue().toString());
                newY = (int) Double.parseDouble(dataSnapshot.child("usuario").child("pos").child("y_pos").getValue().toString());

                move(oldX-newX, oldY-newY);

                oldX = newX;
                oldY = newY;

                System.out.println("New X:" + newX);
                System.out.println("New Y:" + newY);
                System.out.println("Old X:" + oldX);
                System.out.println("Old Y:" + oldY);



//                System.out.println("--------------------------------");
//                System.out.println("Posición X: " + dataSnapshot.child("usuario").child("pos").child("x_pos").getValue());
//                System.out.println("Posición Y: " + dataSnapshot.child("usuario").child("pos").child("y_pos").getValue());
//                System.out.println("Click R: " + dataSnapshot.child("usuario").child("clicks").child("clickR").getValue());
//                System.out.println("Click L: " + dataSnapshot.child("usuario").child("clicks").child("clickL").getValue());
//                System.out.println("--------------------------------\n");

//                System.out.println(document);
            }


            public void onCancelled(DatabaseError error) {
                System.out.print("Error: " + error.getMessage());
            }
        });

    }
}