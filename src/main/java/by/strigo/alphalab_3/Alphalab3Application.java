package by.strigo.alphalab_3;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Alphalab3Application {


    @SneakyThrows
    public static void main(String[] args) {

        MultiThreadFileWriter thread1 = new MultiThreadFileWriter("thread1.txt");
        MultiThreadFileWriter thread2 = new MultiThreadFileWriter("thread2.txt");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
