import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class EmployeeTest {
    List<Employee> list = new ArrayList<>();

    @BeforeEach
    public void addEmployee() {
        Employee emp = new Employee(1, "Inav", "Petrov", "RU", 25);
        list.add(emp);
        emp = new Employee(2, "John", "Smith", "USA", 23);
        list.add(emp);
    }

    @Test
    public void getFirstName() {
        Assert.assertEquals("John", list.get(1).getFirstName());
    }

    @Test
    public void firstNameBeginStartSwith() {
        MatcherAssert.assertThat(list.get(1).getFirstName(), CoreMatchers.startsWith("J"));
    }

    @Test
    public void getFirstNameMatcher() {
        MatcherAssert.assertThat("Inav", CoreMatchers.equalTo(list.get(0).firstName));

    }

    @Test
    public void containsStringInLastname() {

        MatcherAssert.assertThat(list.get(1).lastName, CoreMatchers.containsString("i"));
    }

    @Test
    public void endSwithInLastname() {

        MatcherAssert.assertThat(list.get(0).lastName, CoreMatchers.endsWith("ov"));
    }

}


