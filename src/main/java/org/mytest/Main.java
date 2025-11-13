package org.mytest;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Greeter().greet("Pratham"));
        if (args.length > 0) System.out.println(args[0]);
    }
}
