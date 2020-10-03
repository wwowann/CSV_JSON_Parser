import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
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
import java.util.List;

import static java.lang.Integer.getInteger;

public class Main {
    public static void main(String[] srgs) throws ParserConfigurationException, IOException, SAXException {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileNameCSV = "data.csv";
        String fileNameJson = "data.json";
        String fileNameXML = "data.xml";
        List<Employee> list = parseCSV(columnMapping, fileNameCSV);
        Gson gson = listToJson(list);
        writeString(gson, list, fileNameJson);
//        List<Employee> listXML = parseXML(fileNameXML);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(fileNameXML);

        Node root = doc.getDocumentElement();
//        System.out.println("Корневой элемент: " + root.getNodeName());
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        getEmployee(nodeList);


//            System.out.println("Текущий элемент " + node.getNodeName());
//                System.out.println("тип элемента  " + node.getNodeType());
//                Employee emplList = new Employee
//                        (getInteger(element.getAttribute("id")),
//                        element.getElementsByTagName("firstName")
//                        .item(0).getTextContent(),
//                        element.getElementsByTagName("lastName")
//                                .item(0).getTextContent(),
//                        element.getElementsByTagName("country")
//                                .item(0).getTextContent(),
//                        getInteger(element.getElementsByTagName("age")
//                                .item(0).getTextContent()));
//                System.out.println(emplList);
//                         element.getAttribute("id"));
//                System.out.println("firstname: " + element.getElementsByTagName("firstName")
//                        .item(0).getTextContent());
//                System.out.println("lastname: " + element.getElementsByTagName("lastName")
//                        .item(0).getTextContent());
//                System.out.println("country: " + element.getElementsByTagName("country")
//                        .item(0).getTextContent());
//                System.out.println("age: " +
//                        element.getAttribute("age"));
    }

//создание экземпляра класса Employee
    public static Employee getEmployee(NodeList nodeList) {
        Employee employee = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            employee = new Employee();
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                employee.setId(Long.parseLong(getTagValue("id", element)));
                employee.setfirtName(getTagValue("firstName", element));
                employee.setLastName(getTagValue("lastName", element));
                employee.setCountry(getTagValue("country", element));
                employee.setId(Integer.parseInt(getTagValue("age", element)));
                System.out.println(employee.toString());
            }
           }
        return employee;
    }

        // получение значение элемента по его тегу
        public static String getTagValue (String tag, Element element){
            NodeList nodeList = element.getElementsByTagName(tag)
                    .item(0)
                    .getChildNodes();
            Node node = (Node) nodeList.item(0);
            return node.getNodeValue();
        }

        public static Gson listToJson (List < Employee > list) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder
                    .setPrettyPrinting()
                    .create();
            Type listType = new TypeToken<List<Employee>>() {
            }.getType();
            String json = gson.toJson(list, listType);
            System.out.println(json);
            return gson;
        }

        public static void writeString (Gson gson, List < Employee > list, String fileNameJson){
            try (FileWriter file = new FileWriter(fileNameJson)) {
                file.write(gson.toJson(list));
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static List<Employee> parseCSV (String[]columnMapping, String fileName){
            List<Employee> list = null;
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

