package employee;

import employee.utils.HealthCare;

public class ProgrammerEmployee extends Employee implements HealthCare {

    private int writtenPrograms;

    public ProgrammerEmployee(String name, int age, int salary, String profession) {
        super(name, age, salary, profession);
    }

    @Override
    public int getBonus() {
        return writtenPrograms*400 + salary * 2;
    }

    public int getWrittenPrograms() {
        return writtenPrograms;
    }

    public void setWrittenPrograms(int writtenPrograms) {
        this.writtenPrograms = writtenPrograms;
    }

    @Override
    public void getMoneyForDantist() {

    }

    @Override
    public void getMoneyForTherapy() {

    }
}
