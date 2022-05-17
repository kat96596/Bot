package employee;

import employee.Employee;
import employee.EngineerEmployee;
import employee.SalesmanEmployee;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        //name, age, salary, profession

        Object object = new Object();

        //1
        EngineerEmployee employee1 = new EngineerEmployee("Slava", 25, 50000, "QA Engineer");
        System.out.println(employee1.getBonus());
        System.out.println(Employee.getCount());

        //2
        SalesmanEmployee employee2 = new SalesmanEmployee("Leonid", 27, 70000, "Programmer");
        System.out.println(employee2.getBonus(3000));
        System.out.println(employee2.getBonus());
        System.out.println(Employee.getCount());

        System.out.println(employee2);

        String s1 = "sag";
        String s2 = new String("sag");
        System.out.println(s1.equals(s2));



    }

    //ООП
    //Классы и объекты (классы/объекты, Object(toString, equals, hash))
    //Полиморфизм (переопределения, перегрузка, обобщения)
    //Инкапсуляцию (модификаторы доступа, final, static)
    //Наследование (extends, implements)
    //Абстракция (интерфейсы, абстрактные классы, классы)

    //OkHTTP + get/post
    //Jackson

    //JDBC + SQL
    //TelegramAPI

}
