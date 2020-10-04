import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileNameCSV = "data.csv";
        String fileNameXML = "data.xml";
        String fileNameCSVJson = "dataCSV.json";
        String fileNameXMLJson = "dataXML.json";
//////////////////// CSV - JSON /////////////////////////////////////
        System.out.println("Задача №1. Сериализация из CSV в JSON");
        List<Employee> listCSV = null;
        listCSV = parseCSV(columnMapping, fileNameCSV, listCSV);
        listToJson(listCSV, fileNameCSVJson);
/////////////////// XML - JSON //////////////////////////////////////
        System.out.println("Задача №2. Сереализация из XML в JSON");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(fileNameXML);
        List<Employee> listXML = parseXML(doc, fileNameXML);
        listToJson(listXML, fileNameXMLJson);
////////////////// JSON - JAVAObject ///////////////////////////////
        System.out.println("Задача №3. Десереализация из JSON в Java-Object");
        JSONParser parser = new JSONParser();
        List<Employee> employeeList = new ArrayList<>();
        try {
            Object obj = parser.parse(new FileReader(fileNameXMLJson));
            JSONArray jsonArray = (JSONArray) obj;
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            jsonToList(jsonArray, gson, employeeList);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void jsonToList(JSONArray jsonArray, Gson gson, List<Employee> employeeList) {
        for (Object object : jsonArray) {
            String jsonText = object.toString();
            Employee employee = gson.fromJson(jsonText, Employee.class);
            employeeList.add(employee);
            System.out.println(employee);
        }
    }

    public static List<Employee> parseXML(Document doc, String fileNameXML) {
        List<Employee> list = new ArrayList<>();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Employee employee = new Employee();
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                employee.setId(Long.parseLong(getTagValue("id", element)));
                employee.setFirstName(getTagValue("firstName", element));
                employee.setLastName(getTagValue("lastName", element));
                employee.setCountry(getTagValue("country", element));
                employee.setAge(Integer.parseInt(getTagValue("age", element)));
                list.add(employee);
            }
        }
        return list;
    }

    public static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag)
                .item(0)
                .getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    public static void listToJson(List<Employee> list, String fileName) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder
                .setPrettyPrinting()
                .create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);
        System.out.println(json);
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(gson.toJson(list));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName,
                                          List<Employee> list) {
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName));) {
            ColumnPositionMappingStrategy<Employee> strategy =
                    new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            list = csv.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}

