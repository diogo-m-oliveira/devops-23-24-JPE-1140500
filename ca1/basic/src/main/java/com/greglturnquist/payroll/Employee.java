/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.payroll;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Entity // <1>
public class Employee {

	private @Id @GeneratedValue Long id; // <2>

	private String firstName;
	private String lastName;
	private String description;
	private int jobYears;

	@NotNull
	private String email;

	private Employee() {}

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

	@Override
	public int hashCode() {

		return Objects.hash(id, firstName, lastName, description, jobYears, email);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName == null || firstName.isEmpty())
			throw new IllegalArgumentException("Invalid input");
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (lastName == null || lastName.isEmpty())
			throw new IllegalArgumentException("Invalid input");
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null || description.isEmpty())
			throw new IllegalArgumentException("Invalid input");
		this.description = description;
	}

	public int getJobYears() {
		return jobYears;
	}

	public void setJobYears(int jobYears) {
		if (jobYears < 0)
			throw new IllegalArgumentException("Invalid input");
		this.jobYears = jobYears;
	}

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
		return "Employee{" +
			"id=" + id +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", description='" + description + '\'' +
			", jobYears='" + jobYears + '\'' +
			", email='" + email + '\'' +
			'}';
	}
}
// end::code[]
