package com.example.sortingandbubblescroll;

public class Employee implements Comparable<Employee>{

    private String dishName;
    private String dishDescription;
    private int dishID;

    public Employee(int dishID, String dishName, String dishDescription) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.dishDescription = dishDescription;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }


    @Override
    public int compareTo(Employee employee) {
        return this.dishID - employee.getDishID();
    }
}
