package telran.people;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.IntStream;

public class CreateRandomCompany {
	private static String N_EMPLOYEES = "1000";
	private static String MIN_SALARY = "10000";
	private static String MAX_SALARY = "30000";
	private static String DEPARTMENTS = "QA,Development,Management,Sales";

	public static void main(String[] args) throws IOException {
		File compFile = new File("company.data");
		compFile.delete();
		try {
			generateProperties();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CompanyImpl randCompany = null;
		try {
			randCompany = generateCompany((CompanyImpl) CompanyImpl.createCompany("company.data"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			randCompany.save("company.data");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static CompanyImpl generateCompany(CompanyImpl company) throws Exception {
		Properties props = new Properties();
		int nEmp, minSal, maxSal;
		String[] deps;
		try {
			props.load(new FileInputStream("generation.properties"));
		} finally {
        	nEmp = getProp(props, "N_Employees");
        	minSal = getProp(props, "MinSalary");
        	maxSal = getProp(props, "MaxSalary");
        	deps = props.getProperty("Departments").split(",");
        }
		
		IntStream.range(1, nEmp + 1).boxed().sorted().forEach(i -> {
			try {
				company.addEmployee(new Employee(i, "name" + i, deps[generateRandNumber(0, deps.length - 1)], generateRandNumber(minSal, maxSal)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} );
		
		
		
		return company;
	}

	private static int getProp(Properties props, String prop) {
		return Integer.parseInt(props.getProperty(prop));
	}

	private static void generateProperties() throws IOException, FileNotFoundException {
		Properties props = new Properties();
		props.put("N_Employees", N_EMPLOYEES);
		props.put("MinSalary", MIN_SALARY);
		props.put("MaxSalary", MAX_SALARY);
		props.put("Departments", DEPARTMENTS);
		String path = "generation.properties";
		try (FileOutputStream out = new FileOutputStream(path);) {
			props.store(out, "");
		}
	}
	
	private static int generateRandNumber(int min, int max) {
		return (int) Math.floor(Math.random()*(max-min+1)+min);
	}

}