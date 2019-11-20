package homework.v3;

import homework.v3.entity.*;
import homework.v3.entityExt.JsonFileClassExt;
import homework.v3.util.JsonFileClassStream;
import homework.v3.util.JsonFileClassStreamExternalizable;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Задание
 * 1) Составить файл с JSON-объектом, который "разложен" в пакете homework.v3.entity.
 * Определить какой элемент является корневым
 * Дать имя файлу homework.parameters.json
 * 2) Заполнить его значениями (как пример "parameters.v1.json")
 * 3) Считать получившийся homework.parameters.json в память
 * 4) Сериализовать его с помощью встроенного механиза сериализации в файл с именем homework.parameters.ser
 * 5) Сериализовать его с использованием интерфейса Externalizable в файл с именем homework.parameters.exter
 * 6) Считать данные из файла homework.parameters.ser в память и сохранить в формате JSON в файл с именем homework.result.ser.parameters.json
 * 7) Считать данные из файла homework.parameters.exter в память и сохранить в формате JSON в файл с именем homework.result.exter.parameters.json
 * 8) Убедиться, что файлы homework.result.ser.parameters.json и  homework.result.exter.parameters.json
 * совпадают с homework.parameters.json
 * */
public class HomeWork {

    private static JsonFileClass read(String url) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        final JsonFileClass jsonFileClass = mapper.readValue(new File(url), JsonFileClass.class);
        return jsonFileClass;
    }

    private static final String JSON = "homework.parameters.json";
    private static final String SER = "homework.parameters.ser";
    private static final String EXTER = "homework.parameters.exter";
    private static final String SER_JSON = "homework.result.ser.parameters.json";
    private static final String EXTER_JSON = "homework.result.exter.parameters.json";

    private static List<Path> createPathList(){
        List<Path> pathList = new ArrayList<>();
        Path path = new Path();
        path.setCode("1234");
        path.setValue("5000");
        pathList.add(path);
        return pathList;
    }

    private static List<Bundle> createBundleList(){
        List<Bundle> bundleList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.setPath(createPathList());
        bundle.setValues(Arrays.asList("2","3"));
        bundleList.add(bundle);
        return bundleList;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        JsonFileClass jsonFileClass = new JsonFileClass();
        jsonFileClass.setVersion("HomeWork version");

        List<JsonParameters> listJsonParameters = new ArrayList<>();
        JsonParameters jsonParameters = new JsonParameters();

        jsonParameters.setBundle(createBundleList());
        jsonParameters.setDescription("HomeDescription");
        jsonParameters.setList(true);
        jsonParameters.setName("HomeName");
        jsonParameters.setRoles(Arrays.asList("role1","role2"));
        jsonParameters.setType("HomeType");

        listJsonParameters.add(jsonParameters);

        jsonFileClass.setParameters(listJsonParameters);

        //запись без сериализации
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Записываем данные в файл " + JSON);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON), jsonFileClass);
        System.out.println("Данные записаны на диск в файл " + JSON);

        //считывание не серилизованного файла
        System.out.println("Считываем данные из не сериализованного файла " + JSON);
        JsonFileClass readNoSerFile = mapper.readValue(new File(JSON), JsonFileClass.class);
//        System.out.println(readNoSerFile);

        //4) Сериализовать его с помощью встроенного механиза сериализации в файл с именем homework.parameters.ser
        System.out.println("4) Сериализация файла (Serializable) JSON в " + SER);
        JsonFileClassStream.writer(SER, readNoSerFile);
        System.out.println("Выполнена сериализация файла " + SER);

        //6) Считать данные из файла homework.parameters.ser в память и сохранить в формате JSON
        // в файл с именем homework.result.ser.parameters.json
        System.out.println("6) Десериализация (Serializable) SER в память");
        JsonFileClass ser = JsonFileClassStream.reader(SER);
//        System.out.println(ser);
        System.out.println("Выполнена десериализация файла " + SER);
        System.out.println("Записываем SER в файл " + SER_JSON);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(SER_JSON), ser);
        System.out.println("Данные записаны на диск в файл " + SER_JSON);

        //5) Сериализовать его с использованием интерфейса Externalizable в файл с именем homework.parameters.exter
        System.out.println("5) Сериализация файла (Externalizable) JSON в " + EXTER);
        JsonFileClassExt serObjectExt = mapper.readValue(new File(JSON), JsonFileClassExt.class);
//        System.out.println(serObjectExt);
        JsonFileClassStreamExternalizable.write(EXTER, serObjectExt);
        System.out.println("Выполнена сериализация (Externalizable) файла " + EXTER);

        //7) Считать данные из файла homework.parameters.exter в память и сохранить в формате JSON
        // в файл с именем homework.result.exter.parameters.json
        System.out.println("7) Десериализация (Externalizable) EXTER в память");
        JsonFileClassExt deserObjectExt = new JsonFileClassExt();
        JsonFileClassStreamExternalizable.read(EXTER, deserObjectExt);
//        System.out.println(deserObjectExt);
        System.out.println("Выполнена десериализация (Externalizable) файла " + EXTER);
        System.out.println("Записываем EXTER в файл " + EXTER_JSON);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(EXTER_JSON), deserObjectExt);
        System.out.println("Данные записаны на диск в файл " + EXTER_JSON);
    }
}
