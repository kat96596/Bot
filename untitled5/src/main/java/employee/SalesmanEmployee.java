package employee;

public class SalesmanEmployee extends Employee {

    private int sales;

    public SalesmanEmployee(String name, int age, int salary, String profession) {
        super(name, age, salary, profession);
    }

    @Override
    public int getBonus() {
        return (int) (salary * 2);
    }

    public int getBonus(int extraBonus) {
        return 5000+extraBonus;
    }

}
