package com.greglturnquist.payroll;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

	private final EmployeeRepository employeeRepository;

	public HomeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@RequestMapping(value = "/")
	public String index(Model model) {
		List<Employee> employees = (List<Employee>) employeeRepository.findAll();
		model.addText(employees.toString());
		return "index";
	}
}

