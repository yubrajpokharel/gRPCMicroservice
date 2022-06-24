package com.yubraj.microservice.demo;

import com.yubraj.microservice.models.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersonDemo {
    public static void main(String[] args) throws IOException {

        Person person = Person.newBuilder()
                .setFirstName("Yubraj")
                .setLastName("Pokharel")
                .setAge(30)
                .build();

        Path path = Paths.get("person.txt");
        Files.write(path, person.toByteArray());

        Person person1 = Person.parseFrom(Files.readAllBytes(path));

        System.out.println(person1);

    }
}
