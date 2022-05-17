package employee;

public abstract class Employee {

    //private default protected public
    //default/protected -> пакеты
    private String name;
    private int age;
    protected int salary;
    private String profession;

    public static final String nameCompany = "CowsAndDogs";

    private static int count = 0; //классу, а не объекту от класса


    public Employee(String name, int age, int salary, String profession) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.profession = profession;
        count++;
    }

    public static int getCount() {
        return count;
    }

    public abstract int getBonus();


    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", profession='" + profession + '\'' +
                '}';
    }

}
