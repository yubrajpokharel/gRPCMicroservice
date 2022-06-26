package com.yubraj.microservice.grpc.database;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountDatabase {

    public static Map<Integer, Integer> MAP = IntStream.rangeClosed(1, 50)
            .boxed()
            .collect(Collectors.toMap(
                    Function.identity(),
                    v -> v * 10
            ));

    public static int getBalance(int accountId) {
        return MAP.get(accountId);
    }

    public static int  addBalance(int accountId, int amount) {
        return MAP.computeIfPresent(accountId, (k, v) ->  v + amount);
    }

    public static int  deductBalance(int accountId, int amount) {
        return MAP.computeIfPresent(accountId, (k, v) ->  v - amount);
    }

}
