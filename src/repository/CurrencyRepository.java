package repository;


import model.Course;
import model.Currency;

import java.io.*;
import java.util.*;

public class CurrencyRepository {

    private Map<Currency, Course> courseMap = new HashMap<>();
    String path = "src/repository/test.txt";


    public CurrencyRepository(Map<Currency, Course> courseMap) {
        this.courseMap = courseMap;
        setCourseMap();
    }

    public void setCourseMap() {
        Course courseEUR = new Course(1);
        Course courseUSD = new Course(1.08);
        Currency currencyEUR = new Currency("Euro", "EUR");
        Currency currencyUSD = new Currency("American Dollar", "USD");
        courseMap.put(currencyEUR, courseEUR);
        courseMap.put(currencyUSD , courseUSD);

    }
    public Map<Currency,Course> getMupCourse(){
        return courseMap;
    }




    public void removeCourse(String code){



    }




    public void addCurrency(String code,String name, Double value) {

        Course course = new Course(value);
        Currency currency = new Currency(name, code);
        courseMap.put(currency, course);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))) {
            if (currency.getCode().contains(code)) {
                bufferedWriter.write(currency.getCode());
                bufferedWriter.write(String.valueOf(course));
            }
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void showCurrencyHistoryByDate(String date) {
//        String path = "src/repository/test.txt";
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(path))
//
//
    }



