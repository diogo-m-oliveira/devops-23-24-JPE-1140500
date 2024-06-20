# Use an official Ubuntu base image
FROM openjdk:21-slim

# Set the working directory
WORKDIR /opt/h2-data

# Download the H2 database JAR
ADD https://search.maven.org/remotecontent?filepath=com/h2database/h2/2.2.224/h2-2.2.224.jar /opt/h2-data/h2.jar


# Expose the H2 database port
EXPOSE 8082 9092

# Run H2 database (TCP and Web Console)
CMD ["java", "-cp", "/opt/h2-data/h2.jar", "org.h2.tools.Server", "-web", "-webAllowOthers", "-webPort", "8082", "-tcp", "-tcpAllowOthers", "-tcpPort", "9092", "-ifNotExists"]