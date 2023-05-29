module platform.spring {
    requires java.base;
    requires cglib;
    requires hutool.all;
    requires java.xml;
    requires junit;
    requires net.bytebuddy;

    exports com.sunny.maven.springframework.example;
}