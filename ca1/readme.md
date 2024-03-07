DevOps Technical Report | Class Assignment 1
============================================

Part 1 - With git commands, add a new feature to a React.js and Spring Data REST Application using only the master branch
---------------------------------------------------------------------------------------------------------------
In the firs part of this report, we will walk through the steps to add a new feature to 
an existing React.js and Spring Data REST application, using git commands 
only in the main branch (master).
The feature we will add is the ability to record the years of experience 
of employees in the company. We will follow a step-by-step approach to 
analyze, design, and implement this feature.

### Requirements
1. Copy the code of the Tutorial React.js and Spring Data REST Application 
into a new folder named CA1.
2. Commit the changes (and push them).
3. We should use tags to mark the versions of the application. You should use a
pattern like: major.minor.revision (e.g., 1.1.0).
Tag the initial version as **v1.1.0.** Push it to the server.
4. Let's develop a new feature to add a new field to the application. In this case, lets
add a new field to record the years of the employee in the company (e.g., jobYears).
   * You should add support for the new field.
   * You should also add unit tests for testing the creation of Employees 
   and the validation of their attributes (for instance, no null/empty values).
   For the new field, only integer values should be allowed.
   * You should debug the server and client parts of the solution. 
   * When the new feature is completed (and tested) the code should be 
   committed and pushed and a new tag should be created (e.g, **v1.2.0**).
5. At the end of the assignment mark your repository with the tag **ca1-part1**.
6. Use issues in gitHub to track the main tasks.

### Analysis
Before diving into implementation, let's analyze the requirements and identify
the key components and tasks involved:

1. **Initial Setup**: We need to copy the existing codebase into a new folder 
named "CA1" and commit the changes.
2. **Version Tagging**: Tag the initial version as v1.1.0.
3. **New Feature Development**:
   * Add support for a new field named "jobYears" to the Employee entity.
   * Implement unit tests for creating employees and validating their 
   attributes, including the new field.
   * Debug both server and client parts of the solution.
   * Commit and tag the new feature as v1.2.0.
4. **Final Tagging**: Mark the repository with the tag "ca1-part1" at the 
end of the assignment.

### Design
Based on the analysis, we can outline the design for implementing the new feature:

1. **Modify Entity**: Add a new field named "jobYears" to the Employee entity.
2. **Update API**: Modify the REST API to support the new field.
3. **Implement Unit Tests**: Write unit tests to ensure proper creation and 
validation of employees, including the new field.
4. **Debugging**: Identify and fix any issues in both server and client components.
5. **Version Control**: Commit changes and create version tags for tracking.

### Implementation
For previous tasks, not related to this assignment, we have already created
a local directory with the code of the Tutorial React.js and Spring Data REST
Application (C:\Users\user\Documents\TutorialReactSpringApp).

From the analysis, the steps to implement the new feature are as follows:

1. **Initial Setup**:
   * Using the repository created for this purpose,
   copy the code to a new folder named "CA1".
   * Commit the changes and push them to the server.
```bash 
   mkdir ca1
   cp -r TutorialReactSpringApp/* ca1/
   cd ca1
   git init
   git add .
   git commit -m "first commit"
  ```

2. **Version Tagging**:
   * Tag the initial version as v1.1.0.
```bash
   git tag v1.1.0
   git push origin v1.1.0
  ```
   
3. **New Feature Development**:
* Modify the Employee entity to include a new field named "jobYears".
```java
   @Entity // <1>
   public class Employee {

      private @Id @GeneratedValue Long id; // <2>
      private String firstName;
      private String lastName;
      private String description;
   
      private int jobYears; // New field jobYears added
   
      public Employee(String firstName, String lastName, String description, int jobYears, String email){
        setFirstName(firstName);
        setLastName(lastName);
        setDescription(description);
   
        setJobYears(jobYears); // New method added to set the employee's jobYears
      }

      @Override
      public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
            Objects.equals(firstName, employee.firstName) &&
            Objects.equals(lastName, employee.lastName) &&
            Objects.equals(description, employee.description) &&
   
            jobYears == employee.jobYears;
      }
   
      // getters and setters
   
      public int getJobYears() {
        return jobYears;
      }

      public void setJobYears(int jobYears) {
        if (jobYears < 0)
           throw new IllegalArgumentException("Invalid input");
        this.jobYears = jobYears;
      }
   
      @Override
      public String toString() {
         return "Employee{" + "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", description='" + description + '\'' +
            ", jobYears='" + jobYears + '\'' +
            '}';
      }
   }
```
   
* Modify the DatabaseLoader component to include the new field 
(set 4 years of experience for the employee Frodo Baggins).
```java
    @Component
    public class DatabaseLoader implements CommandLineRunner { // <2>

        private final EmployeeRepository repository;

        @Autowired // <3>
        public DatabaseLoader(EmployeeRepository repository) {
           this.repository = repository;
        }

        @Override
        public void run(String... strings) throws Exception { // <4>
           this.repository.save(new Employee("Frodo", "Baggins", "ring bearer", 4));
        }
    }
```

* Modify the app.js file to display the new field in the employee list.
```javascript 
   // tag::employee-list[]
   class EmployeeList extends React.Component{
       render() {
          const employees = this.props.employees.map(employee =>
             <Employee key={employee._links.self.href} employee={employee}/>
          );
          return (
             <table>
                <tbody>
                   <tr>
                      <th>First Name</th>
                      <th>Last Name</th>
                      <th>Description</th>
   
                      <th>Job Years</th> // New field added to the table
                   </tr>
                   {employees}
                </tbody>
             </table>
          )
       }
   }
   // end::employee-list[]
   
   // tag::employee[]
   class Employee extends React.Component{
      render() {
         return (
            <tr>
               <td>{this.props.employee.firstName}</td>
               <td>{this.props.employee.lastName}</td>
               <td>{this.props.employee.description}</td>
    
               <td>{this.props.employee.jobYears}</td> // New field added to the table
            </tr>
         )
      }
   }
   // end::employee[]
```

* Implement unit tests for creating employees and validating their attributes.
```java
   public class EmployeeTest {

    /**
     * Test that a valid employee object can be created
     */
    @Test
    public void getValidEmployeeObject() {
        // Arrange
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = 1;

        // Act
        Employee employee = new Employee(firstName, lastName, description, jobYears);

        // Assert
        assertNotNull(employee);
    }

    /**
     * Test that an invalid employee object with an empty first name throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithEmptyFirstName_thenThrowsException() {
        // Arrange
        String firstName = "";
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = 1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test that an invalid employee object with an empty last name throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithEmptyLastName_thenThrowsException() {
        // Arrange
        String firstName = "Frodo";
        String lastName = "";
        String description = "ring bearer";
        int jobYears = 1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test that an invalid employee object with an empty description throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithEmptyDescription_thenThrowsException() {
        // Arrange
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "";
        int jobYears = 1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test that an invalid employee object with a negative job years throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithNegativeJobYears_thenThrowsException() {
        // Arrange
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = -1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test that an invalid employee object with a null first name throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithNullFirstName_thenThrowsException() {
        // Arrange
        String firstName = null;
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = 1;


        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test that an invalid employee object with a null last name throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithNullLastName_thenThrowsException() {
        // Arrange
        String firstName = "Frodo";
        String lastName = null;
        String description = "ring bearer";
        int jobYears = 1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test that an invalid employee object with a null description throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithNullDescription_thenThrowsException() {
        // Arrange
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = null;
        int jobYears = 1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }
}
```

* After passing the tests, we can run the application from the ca1/basic/ directory
and debug the server and client parts of the solution.
```bash
    cd ca1/basic
    ./mvnw spring-boot:run
```
![img.png](img.png)

4. **Commit and tagging**:
* Commit the code changes with the tests and tag the new feature as v1.2.0.
```bash
  git add .
  git commit -m "#1 & #2 - Added jobYears field to Employee constructor and unit tests for constructor with validations"
  git tag v1.2.0
  git push origin v1.2.0
```

5. **Final Tagging**:
* Mark the repository with the tag "ca1-part1" at the end of the assignment.
```bash
  git tag ca1-part1
  git push origin ca1-part1
```

Part 2 - With git commands, add a new feature to a React.js and Spring Data REST Application using branches
---------------------------------------------------------------------------------------------------------------
In the second part of this report, we will walk through the steps to add a 
new feature to an existing React.js and Spring Data REST application,
using git commands and branches to manage the development process.
We will be adding an email field to the Employee entity.
We will follow a step-by-step approach to analyze, design, and implement
this feature.

### Requirements
1. You should use the master branch to ”publish” the ”stable” versions of the Tutorial
   React.js and Spring Data REST Application.
2. You should develop new features in branches named after the feature. Create a
   branch named ”email-field” to add a new email field to the application. 
   * You should create a branch called email-field.
   * You should add support for the new field.
   * You should also add unit tests for testing the creation of Employees
     and the validation of their attributes (for instance, no null/empty values).
     For the new field, only integer values should be allowed.
   * You should debug the server and client parts of the solution.
   * When the new feature is completed (and tested) the code should be
     merged with the master and a new tag should be created (e.g, **v1.3.0**).
3. You should also create branches for fixing bugs (e.g., ”fix-invalid-email”).
   * Create a branch called fix-invalid-email. The server should only accept Employees with
     a valid email (e.g., an email must have the ”@” sign).
   * You should debug the server and client parts of the solution.
   * When the fix is completed (and tested) the code should be merged into master and a
     new tag should be created (with a change in the minor number, e.g., v1.3.0 -> **v1.3.1**)
4. At the end of the assignment mark your repository with the tag **ca1-part2**.
5. Use issues in gitHub to track the main tasks.

### Analysis
Before diving into implementation, let's analyze the requirements and identify
the key components and tasks involved:

1. **Create a branch for feature development**: Create a branch named "email-field"
to develop and test the new email field feature.
2. **New Feature Development**:
   * Add support for a new field named "email" to the Employee entity.
   * Implement unit tests for creating employees and validating their
     attributes, including the new field.
   * Debug both server and client parts of the solution.
   * Merge and tag the new feature as v1.3.0.
3. **Create a branch for bug fixing**: Create a branch named "fix-invalid-email"
to fix the issue with invalid email addresses.
   * Debug both server and client parts of the solution.
   * Merge and tag the fix as v1.3.1.
4. **Final Tagging**: Mark the repository with the tag "ca1-part2" at the
   end of the assignment.

### Design
Based on the analysis, we can outline the design for implementing the new feature:

1. **Create a branch for feature development**: Create a branch named "email-field"
2. **Modify Entity**: Add a new field named "email" to the Employee entity.
3. **Update API**: Modify the REST API to support the new field.
4. **Implement Unit Tests**: Write unit tests to ensure proper creation and
   validation of employees, including the new field.
5. **Debugging**: Identify and fix any issues in both server and client components.
6. **Version Control**: Merge the feature branch with the master and create
version tags for tracking.
7. **Bug fixing**: Create a branch to fix the issue with invalid email addresses.

### Implementation
From the analysis, the steps to implement the new feature are as follows:

1. **Create a branch for feature development**:
      * Create a branch named "email-field" and switch to it.
```bash
   git checkout -b email-field
```

2. **New Feature Development**:
* Modify the Employee entity to include a new field named "email".
```java
   @Entity // <1>
   public class Employee {

      private @Id @GeneratedValue Long id; // <2>
      private String firstName;
      private String lastName;
      private String description;
      private int jobYears;

      private String email; // New field email added
   
      public Employee(String firstName, String lastName, String description, int jobYears, String email){
        setFirstName(firstName);
        setLastName(lastName);
        setDescription(description);
        setJobYears(jobYears);

        setEmail(email); // New method added to set the employee's email
      }

      @Override
      public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
            Objects.equals(firstName, employee.firstName) &&
            Objects.equals(lastName, employee.lastName) &&
            Objects.equals(description, employee.description) &&
            jobYears == employee.jobYears &&
                
            Objects.equals(email, employee.email);
      }
   
      // getters and setters

      public String getEmail() {
         return email;
      }

      public void setEmail(String email) {
         if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Invalid input");
         
         this.email = email;
      }
   
      @Override
      public String toString() {
         return "Employee{" + "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", description='" + description + '\'' +
            ", jobYears='" + jobYears + '\'' +
            ", email='" + email + '\'' +
            '}';
      }
   }
```

* Modify the DatabaseLoader component to include the new field
  (set email for the employee Frodo Baggins - frodobaggins@hotmail.com).
```java
    @Component
    public class DatabaseLoader implements CommandLineRunner { // <2>

        private final EmployeeRepository repository;

        @Autowired // <3>
        public DatabaseLoader(EmployeeRepository repository) {
           this.repository = repository;
        }

        @Override
        public void run(String... strings) throws Exception { // <4>
           this.repository.save(new Employee("Frodo", "Baggins", "ring bearer", 4, "frodobaggins@hotmail.com"));
        }
    }
```

* Modify the app.js file to display the new field in the employee list.
```javascript 
   // tag::employee-list[]
   class EmployeeList extends React.Component{
       render() {
          const employees = this.props.employees.map(employee =>
             <Employee key={employee._links.self.href} employee={employee}/>
          );
          return (
             <table>
                <tbody>
                   <tr>
                      <th>First Name</th>
                      <th>Last Name</th>
                      <th>Description</th>
                      <th>Job Years</th> 
                      
                      <th> Email </th> // New field added to the table
                   </tr>
                   {employees}
                </tbody>
             </table>
          )
       }
   }
   // end::employee-list[]
   
   // tag::employee[]
   class Employee extends React.Component{
      render() {
         return (
            <tr>
               <td>{this.props.employee.firstName}</td>
               <td>{this.props.employee.lastName}</td>
               <td>{this.props.employee.description}</td>
               <td>{this.props.employee.jobYears}</td>
               
               <td>{this.props.employee.email}</td> // New field added to the table
            </tr>
         )
      }
   }
   // end::employee[]
```

* Implement unit tests for creating employees and validating their attributes.
```java
   public class EmployeeTest {

    // previous tests    

    /**
    * Test that an invalid employee object with an empty email throws an exception
    */
    @Test
    public void testInvalidEmployeeObjectWithEmptyEmail_thenThrowsException() {
      // Arrange
      String firstName = "Frodo";
      String lastName = "Baggins";
      String description = "ring bearer";
      int jobYears = -1;
      String email = "";

      String expectedMessage = "Invalid input";

      // Act
      Exception exception = assertThrows(IllegalArgumentException.class, () -> {
         new Employee(firstName, lastName, description, jobYears, email);
      });

      // Assert
      assertEquals(expectedMessage, exception.getMessage());
    }

    /**
    * Test that an invalid employee object with a null email throws an exception
    */
    @Test
    public void testInvalidEmployeeObjectWithNullEmail_thenThrowsException() {
      // Arrange
      String firstName = "Frodo";
      String lastName = "Baggins";
      String description = "ring bearer";
      int jobYears = 1;
      String email = null;

      String expectedMessage = "Invalid input";

      // Act
      Exception exception = assertThrows(IllegalArgumentException.class, () -> {
         new Employee(firstName, lastName, description, jobYears, email);
      });

      // Assert
      assertEquals(expectedMessage, exception.getMessage());
    }
}
```

* After passing the tests, we can run the application from the ca1/basic/ directory
  and debug the server and client parts of the solution.
```bash
    cd ca1/basic
    ./mvnw spring-boot:run
```
![img_1.png](img_1.png)

* Commit changes and linking them to an issue. Merge and tagging the new version as v1.3.0
```bash
  # Commit changes in the email-field branch
  git add .
  git commit -m "#4 and #5 - added email feature to Employee and respective tests"
  
  # Switch to master branch and merge the email-field branch
  git checkout master
  git merge email-field
  
  # Add a tag for the new version
  git tag v1.3.0
  git push origin v1.3.0
```

3. **Create a branch for bug fixing**:
   * Create a branch named "fix-invalid-email" and switch to it.
```bash
   git checkout -b fix-invalid-email
```

* Modify the setEmail() method in the Employee entity to validate the email format.
```java
   @Entity // <1>
   public class Employee {

      private @Id @GeneratedValue Long id; // <2>
      private String firstName;
      private String lastName;
      private String description;
      private int jobYears;
      private String email;
   
      public Employee(String firstName, String lastName, String description, int jobYears, String email){
        setFirstName(firstName);
        setLastName(lastName);
        setDescription(description);
        setJobYears(jobYears);
        setEmail(email); 
      }

      @Override
      public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
            Objects.equals(firstName, employee.firstName) &&
            Objects.equals(lastName, employee.lastName) &&
            Objects.equals(description, employee.description) &&
            jobYears == employee.jobYears &&
            Objects.equals(email, employee.email);
      }
   
      // getters and setters

      public String getEmail() {
         return email;
      }

      public void setEmail(String email) {
         if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Invalid input");

         String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
         Pattern pattern = Pattern.compile(emailRegex);
         Matcher matcher = pattern.matcher(email);

         if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid email format");
         }
         
         this.email = email;
      }
   
      @Override
      public String toString() {
         return "Employee{" + "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", description='" + description + '\'' +
            ", jobYears='" + jobYears + '\'' +
            ", email='" + email + '\'' +
            '}';
      }
   }
```

* Implement unit tests for validating the email format.
```java
   public class EmployeeTest {
    
    // previous tests

   /**
    * Test that an invalid employee object with an invalid email throws an exception
    */
   @Test
   public void testInvalidEmployeeObjectWithInvalidEmail_thenThrowsException() {
      // Arrange
      String firstName = "Frodo";
      String lastName = "Baggins";
      String description = "ring bearer";
      int jobYears = 2;
      String email = "frodo.bagginsgmail.com";

      String expectedMessage = "Invalid email format";

      // Act
      Exception exception = assertThrows(IllegalArgumentException.class, () -> {
         new Employee(firstName, lastName, description, jobYears, email);
      });

      // Assert
      assertEquals(expectedMessage, exception.getMessage());
   }
}
```

* Commit changes and linking them to an issue. Merge and tagging the new version as v1.3.1
```bash
  # Commit changes in the fix-invalid-email branch
  git add .
  git commit -m "#6 - fixed invalid email validations and respective tests"
  
  # Switch to master branch and merge the fix-invalid-email branch
  git checkout master
  git merge fix-invalid-email
  
  # Add a tag for the new version
  git tag v1.3.1
  git push origin v1.3.1
```

4. **Final Tagging**:
* Mark the repository with the tag "ca1-part2" at the end of the assignment.
```bash
  git tag ca1-part2
  git push origin ca1-part1
```
