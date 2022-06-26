package com.yubraj.microservice.grpc.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yubraj.microservice.grpc.jModels.JPerson;
import com.yubraj.microservice.models.Person;
import org.springframework.util.StopWatch;

import java.io.IOException;

public class PerformanceTest {

    public static void main(String[] args) throws IOException {

        JPerson person = new JPerson();
        person.setFirst_name("Yubraj");
        person.setLast_name("Pokharel");
        person.setAge(30);

        ObjectMapper mapper = new ObjectMapper();

        Runnable runnableJava = () -> {
            try {
                byte[] bytes = mapper.writeValueAsBytes(person);
                JPerson person1 = mapper.readValue(bytes, JPerson.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };


        Person per = Person.newBuilder()
                .setFirstName("Yubraj")
                .setLastName("Pokharel")
                .setAge(30)
                .build();

        Runnable runnableProto = () -> {
            try {
                byte[] bytes = per.toByteArray();
                Person person1 = Person.parseFrom(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };


        for (int i = 0; i < 5; i++) {
            runPerformaceTest(runnableJava, "JSON");
            runPerformaceTest(runnableProto, "PROTO");
        }


    }

    public static void runPerformaceTest(Runnable runnable, String method) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < 10000000; i++) {
            runnable.run();
        }

        stopWatch.stop();
        System.out.println("Total time ["+method+"] :  " + stopWatch.getTotalTimeMillis());
    }
}
