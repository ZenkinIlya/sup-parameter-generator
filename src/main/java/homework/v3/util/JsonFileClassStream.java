package homework.v3.util;

import homework.v3.entity.JsonFileClass;

import java.io.*;

public class JsonFileClassStream {
    public static JsonFileClass reader(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
        return (JsonFileClass) objectInputStream.readObject();
    }

    public static void writer(String fileName, JsonFileClass jsonFileClass) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        objectOutputStream.writeObject(jsonFileClass);
    }
}
