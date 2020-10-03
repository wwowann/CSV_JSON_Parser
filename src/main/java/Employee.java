public class Employee {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" + "id='" + id + '\'' + ", firstName='" + firstName +
                '\'' + ", lastName='" + lastName +
                '\'' + ", country='" + country + '\'' +
                ", age='" + age + '\'' + '}';
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setfirtName(String firstName) {
        this.firstName = firstName;
    }

    public String setfirtName() {
        return firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String setLastName() {
        return lastName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String setCountry() {
        return country;
    }

    public void setAge(int age){
        this.age = age;
    }

    public int setAge(){
        return age;
    }

}
