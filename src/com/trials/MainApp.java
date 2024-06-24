package com.trials;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class MainApp {

	public static void main(String[] args) {
		
		List<Employee> employees = Arrays.asList(
	            new Employee(1, "Aditi", 30, 100000, "F", "HR", "Mumbai"),
	            new Employee(2, "Rahul", 65, 130000, "M", "Tech", "Bangalore"),
	            new Employee(3, "Vishal", 34, 110000, "M", "Tech", "Mumbai"),
	            new Employee(4, "Lakshmi", 45, 150000, "F", "HR", "Bangalore"),
	            new Employee(5, "Zameer", 30, 110700, "M", "Tech", "Mumbai")
	        );
       
		
		System.out.println("Employee details whose Age is greater than 28");
		long noOfEmployees= employees.stream()
							.filter( e -> e.getAge() > 28)
							.count();
		
		
		System.out.println("There are "+ noOfEmployees +" employee/s");
							employees.stream()
									 .filter( e -> e.getAge() > 28)
									 .forEach( System.out::println);
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("Maximum age of all Employees");
		Optional<Integer> maxAge =	employees.stream()
											 .map(Employee::getAge)
											 .max(Integer::compare);
		
		maxAge.ifPresent(age-> System.out.println("The maximum age is "+ age));
		
		Optional<Integer> maxAge1 =	employees.stream()
											 .map(Employee::getAge)
											 .max(Comparator.naturalOrder());
		
		maxAge1.ifPresent(age-> System.out.println("The maximum age the other way is "+ age));
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
	System.out.println("Average age of allFemail Employees");
	Double avgFemaleAge = employees.stream()
								   .filter(e-> "F".equals(e.getGender()))
								   .mapToInt(Employee::getAge)
								   .average()
								   .orElse(0.0);
	
	System.out.println("Average age of Female Employees " + avgFemaleAge);	
	
	Double avgMemaleAge = employees.stream()
		     					   .filter(e-> "M".equals(e.getGender()))
		     					   .mapToInt(Employee::getAge)
		     					   .average()
		     					   .orElse(0.0);
	
	
	System.out.println("Average age of Male Employees " + avgMemaleAge);	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	System.out.println("Youngest Employee");
	Optional<Integer> youngestEmployee= employees.stream()
												 .map(Employee::getAge)
												 .min(Integer::compare);
	
	
	youngestEmployee.ifPresent( y -> System.out.println("Method1: The youngest Employee's age is  " + y)
			                  );
	
	Employee employee = employees.stream()
			                     .min(Comparator.comparingInt(Employee::getAge))
			                     .orElse(null);
	
	System.out.println("Method2: The youngest Employee's age is  " + employee);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	System.out.println(" Number of Employees in each department");
	
	Map<String, Long>   noOfEmpinDept = employees.stream()
			                             		 .collect( Collectors.groupingBy( Employee::getDeptName,
			                             				    		               Collectors.counting()
			                             				    		            )
			                             				  );
	
	noOfEmpinDept.forEach((depName,count)-> System.out.println("Department Name: "+depName+ ", Number of Employees: " + count ));
	
   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	System.out.println(" Highest Number of Employees in department ");
	// firstly . the map is created to store the dept names and number of employees in it 
	Map<String,Long> noOfEmployees1 =  employees.stream()
			                                   .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.counting()));
	
	// Extracting the max value from each entry set of the map created above 
	Optional<Map.Entry<String,Long>> bigDept = noOfEmployees1.entrySet()
	            							   .stream()
	            							   .max(Map.Entry.comparingByValue());
	
	// .get() to extract value from optional and getkey() to extract value from Map
	            							   
	System.out.println("The biggest department is: " + bigDept.get().getKey());
	System.out.println(" Number of employee is: " + bigDept.get().getValue());
	
   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	System.out.println("To find if there any Employee from HR dept ");

	boolean emp = employees.stream() 
						   .anyMatch(e->"HR".equals(e.getDeptName()));
																	  
	System.out.println("Are there any employee in HR Department: " + (emp ? "Yes": "No"));
	
                               employees.stream()
			                  .filter(e-> "Tech".equals(e.getDeptName()))
			                  .forEach(System.out::println);
									  
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////								  
									  
    System.out.println("To find the Average Salary of all Departments ");

             Double  avgSal = employees.stream()
                                       .mapToDouble(Employee::getSalary)
                                       .average()
                                       .orElse(0.0);
             System.out.println("The Average Salary of all Departments " + avgSal);
	
	
             Double  avgSal1 = employees.stream()
            		                    .collect(Collectors.averagingDouble(Employee::getSalary));
             
             System.out.println("The Average Salary of all Departments(other way) " + avgSal1);
         	

	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Map<String, Double> avgSalEachDept = employees.stream()
    		 .collect(Collectors.groupingBy(Employee::getDeptName,Collectors.averagingLong(Employee::getSalary)));
	
    avgSalEachDept.forEach((dep , sal) -> System.out.println("Department Name: "+dep+ ", Avg Salary: " + sal));
    
   
 /************************************************************************************************************************************
  * 
  * Parallel streams divide elements into several chunks and process each chunk on a different thread . By default parallelStreams()
  * Creates thread whose count equals the number of processor available
  * limit() and findFirst() are expensive operations cauz they are dependent on ordering of the elements, so parallel streams may not
  * be used in that case for faster performance 
  * 
  ***********************************************************************************************************************************/
    
    Employee highlyPaidEmployee = employees.parallelStream()
    										.max(Comparator.comparingLong(Employee::getSalary))
    										.orElse(null);
    
    System.out.println("The highly paid employee in all deparrt"+ highlyPaidEmployee );											
    													  
    
	}

}
