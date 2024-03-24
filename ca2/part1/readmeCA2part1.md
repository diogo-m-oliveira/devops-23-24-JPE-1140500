DevOps Technical Report | Class Assignment 2 - Part 1
=====================================================

Table of Contents
-----------------
1. [Introduction](#Introduction)
2. [Requirements](#Requirements)
3. [Analysis](#Analysis)
4. [Implementation](#Implementation)
5. [Conclusion](#Conclusion)

### Introduction

In this assignment, we will go through the process of executing a simple demo application
supported by Gradle, and experimenting with adding tasks to automate different processes.
The application is a simple chat application that allows users to send
and receive messages. 

### Requirements
1. Copy the code of the demo Gradle application into a new folder CA2/part1 in the repository.
2. Read the instructions in the application readme file and execute the application.
3. Add a new task to execute the server side of the application.
4. Add a simple unit test and upgrade the gradle script so it can run.
5. Add a new task to make backups of the source code.
6. Add a new task to make an archive of the source code.
7. Tag the repository at the end of the assignment as **ca2-part1**.

### Analysis
Before diving into implementation, let's analyze the requirements and identify
the key components and tasks involved:

1. **Initial Setup**: Copy the code of the demo Gradle application into a new folder CA2/part1 in the repository.
2. **Execution**: Read the instructions in the application readme file and execute the application.
3. **Gradle task creation**: Add tasks to the Gradle script to automate different processes.
    - **Task 1**: Execute the server side of the application.
    - **Task 2**: Make backups of the source code.
    - **Task 3**: Create an archive of the source code.
4. **Unit Testing**: Add a simple unit test and upgrade the gradle script so it can run.
5. **Final Tagging**: Tag the repository at the end of the assignment as **ca2-part1**.

### Implementation
1. **Initial Setup**: Copy the code of the demo Gradle application into a new folder CA2/part1 in the repository.
    - Create a new folder `CA2/part1` in the repository.
    - Copy the code of the demo Gradle application into this folder.
    ```bash
    # Create a new folder 
    mkdir CA2/part1
    
    # Clones the repository without its full history
    git clone --depth 1 https://bitbucket.org/pssmatos/gradle_basic_demo.git
    # Copys the contents of the cloned repository to the new folder, excluding the .git 
    rsync gradle_basic_demo/* CA2/part1 --exclude .git
    # Removes the cloned repository
    rm -rf gradle_basic_demo
    ```

2. **Execution**: Read the instructions in the application readme file and execute the application.
    - Follow the instructions in the `readme.md` file to execute the application.
    ```bash
    # Change directory to the CA2/part1 folder
    cd CA2/part1
    
    # Build the application (./gradlew command calls the Gradle wrapper, which downloads the necessary version of Gradle. 
    # The command `gradle build` could also be used if Gradle is installed on the system.)
    ./gradlew build
    
    # Run the server
    java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp 59001
    
    # Run a client
    ./gradlew runClient  
    ```

3. **Gradle task creation**: Add tasks to the Gradle script to automate different processes.
   - **Task 1**: Execute the server side of the application.
     - Add a new task `runServer` to the `build.gradle` file.
      ```gradle
      task runServer(type: JavaExec, dependsOn: classes) {
        group = "DevOps"
        description = "Launches a chat server that listens on port 59001"
        
        classpath = sourceSets.main.runtimeClasspath
        
        mainClass = 'basic_demo.ChatServerApp'
        
        args '59001'
      }
       ```
       - Run the task `runServer` from the command line
       ```bash
       ./gradlew runServer
       ```
       - Commit the changes to the repository, linking them to issue #11.
       ```bash
       git add build.gradle
       git commit -m "#11 [UPDATE]: Updated build.gradle file with a new task 'runServer' to run the server automatically on port 59001"
       git push
       ```
     
   - **Task 2**: Make backups of the source code.
     - Add a new task `backup` to the `build.gradle` file.
     ```gradle
     task backup(type: Copy) {
         from 'src'
         into 'backup'
     }
     ```
      - Run the task `backup` from the command line
     ```bash
     ./gradlew backup
     ```
     - Commit the changes to the repository, linking them to issue #13.
     ```bash
     git add build.gradle
     git commit -m "#13 [UPDATE]: Added task 'backup' to build.gradle file"
     git push
     ```
     
   - **Task 3**: Create an archive of the source code.
     - Add a new task `createZip` to the `build.gradle` file.
     ```gradle
     task createZip(type: Zip) {
        from 'src'
        archiveFileName = 'src.zip'
        destinationDirectory = file('zipFile')
     }
     ```
     - Run the task `createZip` from the command line
     ```bash
     ./gradlew createZip
     ```
     - Commit the changes to the repository, linking them to issue #14.
     ```bash
     git add build.gradle
     git commit -m "#14 [UPDATE]: Added task 'createZip' to build.gradle file"
     git push
     ```

4. **Unit Testing**: Add a simple unit test and upgrade the gradle script so it can run.
    - Create a new test file `AppTest.java` in the `src/test/java/basic_demo` folder.
    ```bash
    mkdir -p src/test/java/basic_demo
    touch src/test/java/basic_demo/AppTest.java
    ```    
    - Add a simple test to the `AppTest.java` file.
    ```java
    package basic_demo;

    import org.junit.Test;
    import static org.junit.Assert.*;

    public class AppTest {
        @Test
        public void testAppHasAGreeting() {
            App classUnderTest = new App();
            assertNotNull("app should have a greeting", classUnderTest.getGreeting());
        }
    }
    ```
    - Update the `dependencies` in the `build.gradle` file to include the test task.
    ```gradle
    dependencies {
        (...)
        testImplementation group: 'junit', name: 'junit', version: '4.12'
    }
    ```
    - Run the test from the command line
    ```bash
    ./gradlew test
    ```
    - Commit the changes to the repository, linking them to issue #12.
    ```bash
    git add build.gradle
    git commit -m "#12 [UPDATE]: Updated build.gradle file with a new dependency for test implementation with jUnit v4.2"
    
    git add src/test/java/basic_demo/AppTest.java
    git commit -m "#12 [NEW]: Created the AppTest class for unit testing, and a new test."
   
    git push
    ```

5. **Final Tagging**: Tag the repository at the end of the assignment as **ca2-part1**.
    - Create a new tag `ca2-part1` in the repository.
    ```bash
    git tag ca2-part1
    ```
    - Push the tag to the remote repository.
    ```bash
    git push origin ca2-part1
    ```

### Conclusion
In this assignment, we had the opportunity to create our first Gradle tasks to automate processes. 
Updating the Gradle's build file to add tasks to run the server side of the application, make backups 
of the source code, and create an archive of the source code, gave us a better understanding 
of how Gradle tasks work.
We also added a simple unit test and upgraded the Gradle script dependencies to run the test. 
Finally, we tagged the repository at the end of the assignment as **ca2-part1**.