package com.example.example4;

public class Employee {
    String name;
    String address;
    String email;
    String pnumber;

    public Employee(){}

    public Employee(String name, String address, String email, String pnumber){
        this.name = name;
        this.address = address;
        this.email = email;
        this.pnumber = pnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPnumber() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }
}
