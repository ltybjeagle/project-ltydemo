package com.liuty.maven.basicfeature;

import com.liuty.maven.basicfeature.entity.Trader;
import com.liuty.maven.basicfeature.entity.Transaction;

import java.util.Arrays;
import static java.util.Comparator.comparing;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class StreamSecondDemo {

    public static void main(String ...args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        List<Transaction> tr2011 = transactions.stream().filter(tran -> tran.getYear() == 2011)
                .sorted(comparing(Transaction::getValue)).collect(Collectors.toList());
        System.out.println(tr2011);
        List<String> citys = transactions.stream().map(tran -> tran.getTrader().getCity())
                .distinct().collect(Collectors.toList());
        System.out.println(citys);
        String traderStr = transactions.stream().map(transaction -> transaction.getTrader().getName())
                        .distinct().sorted().collect(joining());
        System.out.println(traderStr);
    }
}
