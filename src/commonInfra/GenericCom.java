/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonInfra;

import java.io.*;

public class GenericCom {
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    public GenericCom() {

    }

    public Object readObject (){
        String errorMessage = Thread.currentThread ().getName () + ": ";
        Object inObject = null;

        try {
            inObject = in.readObject ();
        } catch (Exception ex) {
            if (ex instanceof InvalidClassException) {
                errorMessage = errorMessage.concat("Could not deserialize the object!");
            } else if (ex instanceof IOException) {
                errorMessage = errorMessage.concat("Error reading from the IN channel of the socket!");
            } else if (ex instanceof ClassNotFoundException) {
                errorMessage = errorMessage.concat("Unknown data type!");
            }
            System.out.println(errorMessage);
            ex.printStackTrace ();
            System.exit (1);
        }

        return inObject;
    }

    public void writeObject (Object outObject) {
        String errorMessage = Thread.currentThread ().getName () + ": ";
        try {
            out.writeObject (outObject);
        } catch (Exception ex) {
            if (ex instanceof  InvalidClassException ) {
                errorMessage = errorMessage.concat("Could not serialize object!");
            } else if (ex instanceof NotSerializableException) {
                errorMessage = errorMessage.concat("The object is not serializable!");
            } else if (ex instanceof IOException) {
                errorMessage = errorMessage.concat("Error reading from the OUT channel of the socket!");
            }
            System.out.println(errorMessage);
            ex.printStackTrace ();
            System.exit (1);
        }
    }


    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }
}

