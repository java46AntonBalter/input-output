package telran.people;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.TreeMap;

public class RestoreCompanyFromFile {

	public static void main(String[] args) {
		CompanyImpl company = null;
		try {
			company = restoreCompanyFromPath("company.data");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TreeMap<Double, String> AverageSalaryMap = new TreeMap<>();
		AverageSalaryMap.put(getAverage(company, "QA"), "QA");
		AverageSalaryMap.put(getAverage(company, "Development"), "Development");
		AverageSalaryMap.put(getAverage(company, "Management"), "Management");
		AverageSalaryMap.put(getAverage(company, "Sales"), "Sales");
		
		ArrayList<Employee>  highestSalaryDep = (ArrayList<Employee>) company.getEmployeesDepartment(AverageSalaryMap.lastEntry().getValue());
		highestSalaryDep.stream().distinct().sorted((o1, o2) -> (int) (o1.getId() - o2.getId())).forEach(t -> System.out.println(t.toString())); 
		
	}

	private static Double getAverage(CompanyImpl company, String department) {
		ArrayList<Employee> qaEmployeesList = (ArrayList<Employee>) company.getEmployeesDepartment(department);
		return qaEmployeesList.stream().mapToInt(e -> e.getSalary()).summaryStatistics().getAverage();
	}

	private static CompanyImpl restoreCompanyFromPath(String path) throws IOException, ClassNotFoundException, FileNotFoundException {
		File companyFile = new File(path);
		if (!companyFile.exists()) {
			throw new FileNotFoundException("File not found");
		}

		CompanyImpl company;
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(companyFile))) {
			company = (CompanyImpl) input.readObject();
			return company;
		}
	}

}
