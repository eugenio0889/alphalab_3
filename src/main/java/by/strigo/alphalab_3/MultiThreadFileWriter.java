package by.strigo.alphalab_3;

import lombok.SneakyThrows;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadFileWriter extends Thread {

    private static volatile Writer resultWriter;
    private static volatile AtomicInteger currentPrimeNumber = new AtomicInteger(2);
    private final Writer threadWriter;

    static {
        try {
            resultWriter = new FileWriter(new File("Result.txt"), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public MultiThreadFileWriter(String threadFileName) {
        super();
        this.threadWriter = new FileWriter(threadFileName, true);
    }

    @Override
    public void run() {
        writePrimeNumbers();
    }

    @SneakyThrows
    public void writePrimeNumbers() {
        try {
            while (currentPrimeNumber.get() < 1_000) {
                synchronized (currentPrimeNumber) {
                    resultWriter.write(currentPrimeNumber + " ");
                    resultWriter.flush();

                    threadWriter.write(currentPrimeNumber + " ");
                    threadWriter.flush();

                    int nextPrimeNumber = generateNextPrimeNumber(currentPrimeNumber.get());
                    System.out.println("Сейчас пишет " + Thread.currentThread().getName() + " значение " + currentPrimeNumber);
                    currentPrimeNumber.set(nextPrimeNumber);
                }
            }
        } finally {
            resultWriter.close();
            threadWriter.close();
        }
    }

    private int generateNextPrimeNumber(int currentPrimeNumber) {
        int nextPrime = 0;

        for (int i = currentPrimeNumber + 1; i < 1_000_000; i++) {
            if (isPrime(i)) {
                nextPrime = i;
                break;
            }
        }

        return nextPrime;
    }

    private static boolean isPrime(int number) {

        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
