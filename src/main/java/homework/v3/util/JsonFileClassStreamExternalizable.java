package homework.v3.util;

import homework.v3.entityExt.JsonFileClassExt;

import java.io.*;

public class JsonFileClassStreamExternalizable {
    public static void write(String fileName, JsonFileClassExt jsonFileClassExt) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        jsonFileClassExt.writeExternal(objectOutputStream);
    }

    public static void read(String fileName, JsonFileClassExt jsonFileClassExt) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
        jsonFileClassExt.readExternal(objectInputStream);
//        return (JsonFileClassExt) objectInputStream.readObject();
    }
}
