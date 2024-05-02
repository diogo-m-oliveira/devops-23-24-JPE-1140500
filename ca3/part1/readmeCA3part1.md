# DevOps Technical Report | Class Assignment 3 - Part 1

### Part 1 - Virtualization with Vagrant

### Introduction

The purpose of this part of the assignment is to practice with VirtualBox using the same projects of the previous assignments but now inside a VirtualBox with Ubuntu.

### Requirements
1. Create a new VirtualBox VM with Ubuntu.
2. Clone your individual repository inside the VM.
3. Build and execute the spring boot tutorial basic project and the Gradle basic demo project.
4. For web projects you should access the web applications from the browser in your host machine.
5. For projects with a simple chat application, you should execute the server inside the VM and the client in your host machine.

### Analysis
1. Create a virtual machine.
2. Clone the repository.
3. Build and execute the projects.
4. Access the web applications from the browser in the host machine.
5. Execute the server inside the VM and the client in the host machine.

### Implementation

1. Install a new VM with Ubuntu.

2. Clone your own repository inside the VM.
   * For git clone you should generate a token.

```bash
sudo apt install git
git config --global credential.helper store
git config --global credential.helper 'cache --timeout=3600'
git clone repo.git
```

3. Install JDK

```bash
sudo apt update
sudo apt install default-jdk
```
4. Set JAVA_HOME

```bash
nano ~/.bashrc
```
    * Add at the end of the file:
```bash
export JAVA_HOME=/usr/lib/jvm/default-sava
```
    * Save and exit the file. Then, run the script.
```bash
source ~/.bashrc
```
### For CA1: Spring boot tutorial basic project

5. Build and execute spring boot tutorial basic project.

```bash
cd ca1/basic
````
    * Install Maven
```bash
sudo apt install maven
```
    * Execute the project
```bash
mvn spring-boot:run
```

6. Open the web browser in:
    * 192.161.56.5:8080

### For CA2-Part2: Practice with Gradle

7. Install Gradle

```bash
sudo apt remove gradle
wget htpps://services.gradle.org/distributions/gradle-8.6-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-8.6-bin.zip
```
    * Set the environment variables
```bash
echo "export GRADLE_HOME=/opt/gradle/gradle-8.6" >> ~/.bashrc
echo "export PATH=$PATH:$GRADLE_HOME/bin" >> ~/.bashrc
source ~/.bashrc
gradle -v
cd ca2/part1
```
    * Run the server inside the VM
```bash
gradle runServer
```
    * Run the client in the host machine
* Change the gradle.build file
```gradle
task runClient(type:JavaExec, dependsOn: classes){
    group = "DevOps"
    description = "Launches a chat client that connects to a server on localhost:59001 "
  
    classpath = sourceSets.main.runtimeClasspath

   drun

    args '162.168.56.5', '59001'
}
```
    * Execute the client
```bash
gradle runClient
```

### Why is required to run the server inside the VM and the client in the host machine?

In order to isolate the server from the client side, we need to run the server inside the VM and the client in the host machine. This way, we can test the communication between the server and the client without any interference from the host machine.

### For CA2-Part2: Gradle basic demo project

8. Install Java 17
    
```bash
cd ../../ca2/part2
sudo apt install openjdk-17-jdk
echo  "export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64" >> ~/.bashrc
source ~/.bashrc
```
    * Execute the project
```bash
gradle run
``` 

### Conclusion
In this technical report, the primary objective was to familiarize ourselves with the setup and execution of projects from previous assignments within this virtual environment.

We began by creating a new VirtualBox VM with Ubuntu and then proceeded to clone our individual repositories inside the VM. Following this, we installed the necessary dependencies such as JDK, Maven, and Gradle, ensuring our environment was properly configured for project execution.

For the Spring Boot tutorial basic project (CA1), we utilized Maven to build and execute the project, subsequently accessing the web application from the browser in the host machine.

Moving on to CA2-Part1, we installed Gradle and ran the server inside the VM, while executing the client on the host machine. This separation of server and client allowed us to test communication between them without interference from the host environment.

The requirement to run the server inside the VM and the client in the host machine stems from the necessity to isolate these components, ensuring independent testing and avoiding interference from the host environment.

Lastly, for CA2-Part2, we installed Java 17 and executed the Gradle basic demo project.

Overall, this exercise provided valuable hands-on experience with virtualization, enhancing our understanding of DevOps practices and tooling.









