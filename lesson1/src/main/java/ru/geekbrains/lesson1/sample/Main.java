package ru.geekbrains.lesson1.sample;

import ru.geekbrains.lesson1.regular.Decorator;
import ru.geekbrains.lesson1.regular.BasicMathOperations;

/**

 https://www.docker.com/products/docker-desktop/
 https://hub.docker.com/
 *
  javac -sourcepath ./java -d out java/ru/geekbrains/lesson1/sample/Main.java
 *
  java -classpath ./out ru.geekbrains.lesson1.sample.Main
 *
  javadoc -d docs -sourcepath ./java -cp ./out -subpackages ru

*/

/**

 FROM bellsoft/liberica-openjdk-alpine:11.0.16
 COPY ./java ./src
 RUN mkdir ./out
 RUN javac -sourcepath ./src -d out src/ru/geekbrains/lesson1/sample/Main.java
 CMD java -classpath ./out ru.geekbrains.lesson1.sample.Main

 >> docker build . -t myhomeworkapp:latest
 >> docker run myhomeworkapp:latest
 >> docker run --rm myhomeworkapp:latest

==============================================================

 FROM bellsoft/liberica-openjdk-alpine:11.0.16 as BuildProject
 WORKDIR /app
 COPY ./java ./src
 RUN mkdir ./out
 RUN javac -sourcepath ./src -d out ./src/ru/geekbrains/lesson1/sample/Main.java

 FROM scratch as OutputFiles
 COPY --from=BuildProject /app/out /bin

 >> docker buildx build --output type=local,dest=. .

 ==============================================================

 FROM bellsoft/liberica-openjdk-alpine:11.0.16
 WORKDIR /app
 COPY ./bin .
 CMD java -classpath . ru.geekbrains.lesson1.sample.Main

 >> docker build . -t runmyhomeworkapp
 >> docker run runmyhomeworkapp

*/

public class Main {

    public static void main(String[] args) {
        int result = BasicMathOperations.add(2, 3);
        System.out.println(Decorator.decorate(result));
        result = BasicMathOperations.sub(2, 3);
        System.out.println(Decorator.decorate(result));
        result = BasicMathOperations.mul(2, 3);
        System.out.println(Decorator.decorate(result));
        result = BasicMathOperations.div(2, 3);
        System.out.println(Decorator.decorate(result));
    }

}
