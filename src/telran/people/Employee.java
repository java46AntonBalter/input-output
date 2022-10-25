package telran.people;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Employee implements Serializable, Comparator<Employee> {
	static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String department;
	private int salary;

	public Employee(long id, String name, String department, int salary) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.salary = salary;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDepartment() {
		return department;
	}

	public int getSalary() {
		return salary;
	}

	@Override
	public int hashCode() {
		return Objects.hash(department, id, name, salary);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(department, other.department) && id == other.id && Objects.equals(name, other.name)
				&& salary == other.salary;
	}

	public String toString() {
		return new String("Id = " + getId() + ", Name = " + getName() + ", Department = " + getDepartment()
				+ ", Salary = " + getSalary());

	}

	@Override
	public int compare(Employee o1, Employee o2) {
		long id1 = o1.getId();
		long id2 = o2.getId();

		if (id1 > id2) {
			return 1;
		} else if (id1 < id2) {
			return -1;
		}
		return 0;
	}

}
