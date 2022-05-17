package employee;

public class EngineerEmployee extends Employee {

    private int fixedItems;

    public EngineerEmployee(String name, int age, int salary, String profession) {
        super(name, age, salary, profession);
    }

    @Override
    public int getBonus() {
        return (int) (salary * 1.5);
    }
}
