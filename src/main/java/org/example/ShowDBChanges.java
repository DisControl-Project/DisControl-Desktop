package org.example;

import com.google.firebase.database.*;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ShowDBChanges implements Runnable {
    int oldX = 0;
    int oldY = 0;
    int newX;
    int newY;
    int tamanoTexto = 0;
    Robot robot = new Robot();

    public ShowDBChanges() throws AWTException {

    }

    public void move(int x, int y){
        robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x + x, MouseInfo.getPointerInfo().getLocation().y + y);
    }


    public void clikL(boolean clickL){
        if (clickL){
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }


    }
    public void clickR(boolean clickR){
        if (clickR) {
            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        }
    }
    public void write(String s){

        char caracter = s.charAt(s.length() - 1);
        int ascii = caracter;

        System.out.println(caracter);
        System.out.println(ascii);

        if (s.length() < tamanoTexto) {
            robot.keyPress(KeyEvent.VK_BACK_SPACE);
            robot.keyRelease(KeyEvent.VK_BACK_SPACE);

        } else if (Character.isUpperCase(caracter)){
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(ascii));
            robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(ascii));
            robot.keyRelease(KeyEvent.VK_SHIFT);

        } else {
            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(ascii));
            robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(ascii));

        }

        tamanoTexto = s.length();

    }
    public void borrar(){
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
    }
    public void intro(){
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
    public void flechasDireccion(String direccion){

        if (direccion.equals("up")){

            robot.keyPress(KeyEvent.VK_UP);
            robot.keyRelease(KeyEvent.VK_UP);

        } else if (direccion.equals("left")){

            robot.keyPress(KeyEvent.VK_LEFT);
            robot.keyRelease(KeyEvent.VK_LEFT);

        }else if (direccion.equals("right")){

            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.keyRelease(KeyEvent.VK_RIGHT);

        } else {

            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);

        }
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

                clikL(dataSnapshot.child("usuario").child("clicks").child("clickL").getValue().toString().equals("true"));

                clickR(dataSnapshot.child("usuario").child("clicks").child("clickR").getValue().toString().equals("true"));

                move(oldX+newX, oldY+newY);

                oldX = newX;
                oldY = newY;

                if (dataSnapshot.child("usuario").child("texto").child("borrar").getValue().toString().equals("true")){
                    borrar();
                }
                if (dataSnapshot.child("usuario").child("texto").child("intro").getValue().toString().equals("true")){
                    intro();
                }

                if(dataSnapshot.child("usuario").child("clicks").child("up").getValue().toString().equals("true")){

                    flechasDireccion("up");

                } else if(dataSnapshot.child("usuario").child("clicks").child("left").getValue().toString().equals("true")){

                    flechasDireccion("left");

                }else if(dataSnapshot.child("usuario").child("clicks").child("down").getValue().toString().equals("true")){

                    flechasDireccion("down");

                }else if(dataSnapshot.child("usuario").child("clicks").child("right").getValue().toString().equals("true")){

                    flechasDireccion("right");

                }

                write(dataSnapshot.child("usuario").child("texto").child("texto").getValue().toString());




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