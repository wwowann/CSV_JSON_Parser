import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmployeeTest {

        @Test
        public void getFirsName() {
            Employee emp = new Employee();
            String i = emp.getFirstName();
            String expected = "Inav";
            assertEquals(i, expected);
        }
}

