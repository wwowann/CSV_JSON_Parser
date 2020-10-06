import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmployeeTest {

    @Before
    public void addEmployee() {
        List<Employee> list = new ArrayList<>();
        Employee emp = new Employee(1, "Inav", "Petrov", "RU", 25);
        list.add(emp);
        Employee emp1 = new Employee(2, "John", "Smith", "USA", 23);
        list.add(emp1);
    }

    @Test
    public void getFirstName() {
        List<Employee> list = new ArrayList<>();
        Employee emp = new Employee();
        for (Employee e : list) {
            String i = emp.getFirstName();
            String expected = "Inav";
            assertEquals(i, expected);
        }
    }

    @Test
    public void SubstringMatcher() {
        List<Employee> list = new ArrayList<>();
        Employee emp = new Employee();
        for (Employee e : list) {
            MatcherAssert.assertThat("T", CoreMatchers.containsString(e.getFirstName()));

        }
    }

    @Test
    public void getFirstNameMatcher() {
        List<Employee> list = new ArrayList<>();
        Employee emp = new Employee();
        for (Employee e : list) {
            String i = emp.getFirstName();
            String expected = "Inav";
            MatcherAssert.assertThat(i, CoreMatchers.equalTo(expected));
        }
    }

    @Test
    public void idEqualsIndexOf() {
        List<Employee> list = new ArrayList<>();
        Employee emp = new Employee();
        Assert.assertEquals(list.size(), emp.id);
    }
}


