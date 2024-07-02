package com.example.fitplan.Database;

public class HelperClass {
    String name,gender, email, goal;
    int age, height, weight;
    Double bmiValue;

    public HelperClass(String name, String gender, int age, int weight, int height, String user, String goal, Double bmiValue) {
        this.age = age;
        this.gender = gender;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.email = user;
        this.goal = goal;
        this.bmiValue = bmiValue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Double getBmiValue() {
        return bmiValue;
    }

    public void setBmiValue(Double bmiValue) {
        this.bmiValue = bmiValue;
    }
}
