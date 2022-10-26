package telran.people;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CompanyImpl implements Company {

	private HashMap<Long, Employee> employees = new HashMap<>();
	private TreeMap<Integer, List<Employee>> employeesSalary = new TreeMap<>();
	private HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
	private static final long serialVersionUID = 1L;

	private CompanyImpl() {

	}

	public static Company createCompany(String fileName) throws Exception {
		File companyFile = new File(fileName);
		if (!companyFile.exists()) {
			return new CompanyImpl();
		}

		CompanyImpl company;
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(companyFile))) {
			company = (CompanyImpl) input.readObject();
			return company;
		}
	}

	@Override
	public Iterable<Employee> getAllEmployees() {
		return employees.values();
	}

	@Override
	public void addEmployee(Employee empl) throws Exception {
		if (employees.putIfAbsent(empl.getId(), empl) != null) {
		throw new Exception("Employee already exists");	
		}
		employeesSalary.computeIfAbsent(empl.getSalary(), c -> new LinkedList<Employee>()).add(empl);
		employeesDepartment.computeIfAbsent(empl.getDepartment(), c -> new LinkedList<Employee>()).add(empl);
	}

	@Override
	public void save(String filePath) throws Exception {
		try (ObjectOutputStream output = new ObjectOutputStream(
				new FileOutputStream(filePath))){
			output.writeObject(this);			
		}
	}

	@Override
	public Iterable<Employee> getEmployeesDepartment(String department) {
		return employeesDepartment.get(department);
	}

	@Override
	public Iterable<Employee> getEmployeesSalary(int salaryFrom, int salaryTo) {
		return employeesSalary.subMap(salaryFrom, salaryTo).values().stream().flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	public Employee getEmployee(long id) {
		return employees.get(id);
	}

}
