package telran.people;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CompanyDepartmentAppl {

	public static void main(String[] args) {
		try {
			Company company = CompanyImpl.createCompany(RandomCompanyAppl.EMPLOYEES_DATA_FILE);
			String department = getDepartmentWithMaxAvgSalary(company);
			company.getEmployeesDepartment(department)
			.forEach(System.out::println);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static String getDepartmentWithMaxAvgSalary(Company company) {
		Stream<Employee> employeesStream = //getting stream from iterable
				StreamSupport.stream(company.getAllEmployees().spliterator(), false);
		Map<String, Double> departmentsSalary = //grouping for map (key - department, value - avg salary
				employeesStream.collect(Collectors.groupingBy(Employee::getDepartment,
				Collectors.averagingInt(Employee::getSalary)));
		String department = //getting department name with maximal avg salary
				departmentsSalary.entrySet().stream()
				.collect(Collectors.maxBy((e1, e2)->Double.compare(e1.getValue(), e2.getValue())))
				.get().getKey();
		return department ;
				
				
	}

}
