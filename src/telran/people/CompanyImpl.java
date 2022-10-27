package telran.people;
import java.util.*;
import java.io.*;
public class CompanyImpl implements Company {

	private HashMap<Long, Employee> employees = new HashMap<>();
	private TreeMap<Integer, List<Employee>> employeesSalary = new TreeMap<>();
	private HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
	private static final long serialVersionUID = 1L;
	private CompanyImpl() {
		
	}
	public static Company createCompany(String fileName) throws Exception {
		File file = new File(fileName);
		
		return file.exists() ? restoreFromFile(file) : new CompanyImpl();
	}

	private static Company restoreFromFile(File file) throws Exception {
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))){
			return (Company) input.readObject();
		}
	}
	@Override
	public Iterable<Employee> getAllEmployees() {
		
		return employees.values();
	}

	@Override
	public void addEmployee(Employee empl) throws Exception {
		long id = empl.getId();
		if (employees.putIfAbsent(id, empl) != null) {
			throw new Exception(String.format("Employee with id %d already exists", id));
		}
		employeesDepartment.computeIfAbsent(empl.getDepartment(),
				v -> new ArrayList<>()).add(empl);
		employeesSalary.computeIfAbsent(empl.getSalary(), v -> new ArrayList<>()).add(empl);

	}

	@Override
	public void save(String filePath) throws Exception {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath))) {
			output.writeObject(this);
		}

	}

	@Override
	public Iterable<Employee> getEmployeesDepartment(String department) {
		
		return employeesDepartment.getOrDefault(department, Collections.emptyList());
	}

	@Override
	public Iterable<Employee> getEmployeesSalary(int salaryFrom, int salaryTo) {
		
		return employeesSalary.subMap(salaryFrom, true, salaryTo, true)
				.values().stream().flatMap(Collection::stream).toList();
	}

	@Override
	public Employee getEmployee(long id) {
		
		return employees.get(id);
	}

}
