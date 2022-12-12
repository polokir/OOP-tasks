package org.example;

public class Main {
    public static void main(String[] args) {
        InfoClass infoClass = new InfoClass("org.example.classes.Human");
        infoClass.showClassInfo();
        infoClass = new InfoClass("org.example.classes.Lecturer");
        infoClass.showClassInfo();
        infoClass = new InfoClass("org.example.classes.Teacher");
        infoClass.showClassInfo();
    }
}
