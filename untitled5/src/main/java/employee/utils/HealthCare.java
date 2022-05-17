package employee.utils;

import employee.Employee;

public interface HealthCare {

    String company = "afsasf";

    void getMoneyForDantist();

    void getMoneyForTherapy();

    default void getFullNameOfhealthCare() {
        System.out.println(Employee.nameCompany + getCompany());
    }

    private String getCompany() {
        return company;
    }

}
