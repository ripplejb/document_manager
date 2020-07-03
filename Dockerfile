FROM openjdk:14-alpine
COPY build/libs/document_manager-*-all.jar document_manager.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "document_manager.jar"]