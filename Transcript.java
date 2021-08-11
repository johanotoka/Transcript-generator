import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * This class generates a transcript for each student, whose information is received from
 * the text file.
 * 
 *@author Steeve Johan Otoka Eyota
 *@version 1.0
 *@since 2020-12-01
 */

public class Transcript {
	/**
	 * The list of grades.
	 */
	private ArrayList<Object> grade = new ArrayList<Object>();
	
	/**
	 * The name of the input file of the transcript.
	 */
	private File inputFile;
	
	/**
	 * The name of the output file of the transcript.
	 */
	private String outputFile;

	/**
	 * This the the constructor for Transcript class that initializes its instance
	 * variables and call readFie private method to read the file and construct
	 * this grade.
	 * 
	 * @param inFile  is the name of the input file.
	 * @param outFile is the name of the output file.
	 */
	public Transcript(String inFile, String outFile) {
		inputFile = new File(inFile);
		outputFile = outFile;
		grade = new ArrayList<Object>();
		this.readFile();
	}// end of Transcript constructor

	/**
	 * This method reads a text file and add each line as an entry of grade
	 * ArrayList.
	 * 
	 * @exception It throws FileNotFoundException if the file is not found.
	 */
	private void readFile() {
		Scanner sc = null;
		try {
			sc = new Scanner(inputFile);
			while (sc.hasNextLine()) {
				grade.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	} // end of readFile

	/**
	 * This method creates and returns an ArrayList, 
	 * whose element is an object of class Student. 
	 * The object at each element is created by aggregating 
	 * ALL the information, that is found for one student in 
	 * the grade Arraylist of class Transcript. (i.e. if the 
	 * text file contains information about 9 students, then 
	 * the array list will have 9 elements).
	 * 
	 * @return studentArray is an ArrayList of student.
	 */
	public ArrayList<Student> buildStudentArray() {
		// create the return array
		ArrayList<Student> studentArray = new ArrayList<Student>();
		// access the info of the input file located in the array grade
		for (Object obj : grade) {
			// type cast every object into a string
			String line = (String) obj;
			// split all the information into an array so that we can manipulate them easily
			String[] arrOfInfo = line.split(",");

			// create a new student
			Student s = new Student();
			s.setStudentID(arrOfInfo[2]);
			s.setName(arrOfInfo[arrOfInfo.length - 1]);
			// add course
			s.addCourse(newCourse(arrOfInfo));
			// add grade
			s.addGrade(listOfGrade(arrOfInfo), listOfWeight(arrOfInfo));
			// add student to the list if the list of student does not contain this student
			if (!studentArray.contains(s)) {
				studentArray.add(s);
			}
			// otherwise, add a course and final grade for this student
			else {
				studentArray.get(studentArray.indexOf(s)).addCourse(newCourse(arrOfInfo));
				studentArray.get(studentArray.indexOf(s)).addGrade(listOfGrade(arrOfInfo), listOfWeight(arrOfInfo));
			}
		}
		return studentArray;
	}

	/**
	 * This is the method that prints the transcript to 
	 * the given file (i.e. outputFile attribute).
	 * 
	 * @param studentArray is an ArrayList of students.
	 */
	public void printTranscript(ArrayList<Student> studentArray) {
		// create new file
		try {
			FileWriter myWriter = new FileWriter(outputFile);
			for (Student s : studentArray) {
				myWriter.write(s.getName() + "\t" + s.getStudentID() + "\r\n");
				myWriter.write("--------------------" + "\r\n");
				for (int i = 0; i < s.getCourseTaken().size(); i++) {
					myWriter.write(s.getCourseTaken().get(i).getCode() + "\t" + s.getFinalGrade().get(i) + "\r\n");
				}
				myWriter.write("--------------------" + "\r\n");
				myWriter.write("GPA: " + s.weightedGPA() + "\r\n");
				myWriter.write("" + "\r\n");
			}
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Object> getGrade() {
		return grade;
	}

	public void setGrade(ArrayList<Object> grade) {
		this.grade = grade;
	}

	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = new File(inputFile); 
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	// The following methods are helper methods used by the method buildStudentArray()

	/**
	 * This is the method creates and returns a new course for a student.
	 * 
	 * @param arrOfInfo is an array information for a student.
	 * @return a list of courses.
	 */
	private Course newCourse(String[] arrOfInfo) {
		String code = arrOfInfo[0];
		double credit = Double.parseDouble(arrOfInfo[1]);
		ArrayList<Assessment> assess = arrOfAssessment(arrOfInfo);
		return new Course(code, assess, credit);
	} // end of newCourse

	/**
	 * This method creates and returns a list of Assessments.
	 * 
	 * @param arrOfInfo is an array information for a student.
	 * @return a list of assessments.
	 */
	private ArrayList<Assessment> arrOfAssessment(String[] arrOfInfo) {
		ArrayList<Assessment> listOfAssessment = new ArrayList<Assessment>();
		for (int i = 3; i < arrOfInfo.length - 1; i++) {
			char type = arrOfInfo[i].charAt(0);
			int weight = Integer.parseInt(arrOfInfo[i].substring(1, arrOfInfo[i].indexOf("(")));
			Assessment assess = Assessment.getInstance(type, weight);
			listOfAssessment.add(assess);
		}
		return listOfAssessment;
	} // end of arrOfAssessment

	/**
	 * This method that creates and returns a list of Assessments.
	 * 
	 * @param arrOfInfo is an array information for a student.
	 * @return a list of assessments.
	 */
	private ArrayList<Double> listOfGrade(String[] arrOfInfo) {
		ArrayList<Double> listOfGrade = new ArrayList<Double>();
		for (int i = 3; i < arrOfInfo.length - 1; i++) {
			listOfGrade.add(Double.parseDouble(arrOfInfo[i].substring(arrOfInfo[i].indexOf("(") + 1, arrOfInfo[i].indexOf(")"))));
		}
		return listOfGrade;
	} // end of listOfGrade

	/**
	 * This method creates and returns a list of weight.
	 * 
	 * @param arrOfInfo is an array information for a student.
	 * @return a list of weight.
	 */
	private ArrayList<Integer> listOfWeight(String[] arrOfInfo) {
		ArrayList<Integer> listOfWeight = new ArrayList<Integer>();
		for (int i = 3; i < arrOfInfo.length - 1; i++) {
			listOfWeight.add(Integer.parseInt(arrOfInfo[i].substring(1, arrOfInfo[i].indexOf("("))));
		}
		return listOfWeight;
	} // end of listOfWeight

} // end of Transcript

/**
 * This class generates a Student who has a transcript, whose information is in
 * the text file.
 */

class Student {
	/**
	 * The ID of the student.
	 */
	private String studentID;
	
	/**
	 * The name of the student.
	 */
	private String name;
	
	/**
	 * The list of course taken by the student.
	 */
	private ArrayList<Course> courseTaken;
	
	/**
	 * The list of final grades received by the student for each course taken.
	 */
	private ArrayList<Double> finalGrade;

	/**
	 * This is the no argument constructor for the class Student.
	 * Initializes this student so that it has default values.
	 */
	public Student() {
		this("", "", new ArrayList<Course>());
		this.finalGrade = new ArrayList<Double>();
	}

	/**
	 * This is the overloaded constructor for the class Student.
	 * Initializes this student so that it has the specified studentId, name, courseTaken, and finalGrade.
	 * 
	 * @param studentID is the ID of the student.
	 * @param name is the name of the student.
	 * @param courseTaken is the list of course taken by the student.
	 */
	public Student(String studentID, String name, ArrayList<Course> courseTaken) {
		this.studentID = studentID;
		this.name = name;
		this.courseTaken = courseTaken;
	}

	/**
	 * This method gets the ID of this student.
	 * 
	 * @return the ID of this student.
	 */
	public String getStudentID() {
		return studentID;
	}

	/**
	 * This method sets the ID of the student.
	 * 
	 * @param studentID is the ID of the student.
	 */
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	/**
	 * This method gets the name of this student.
	 * 
	 * @return the name of this student.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method sets the name of the student to the given value.
	 * 
	 * @param name is the name of the student.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method gets the list of courses taken by this student.
	 * 
	 * @return the list of courses taken by this student.
	 */
	public ArrayList<Course> getCourseTaken() {
		ArrayList<Course> arrOfCourse = new ArrayList<Course>();
		for(Course c : this.courseTaken) {
			arrOfCourse.add(new Course(c.getCode(), c.getAssignment(), c.getCredit()));
		}
		return arrOfCourse;
	}

	public void setCourseTaken(ArrayList<Course> courseTaken) {
		this.courseTaken = new ArrayList<Course>();
		for(Course c : courseTaken) {
			this.courseTaken.add(new Course(c.getCode(), c.getAssignment(), c.getCredit()));
		}
	}

	/**
	 * This method gets the list of final grades for each course taken by this student.
	 * 
	 * @return the list of final grades for each course taken by this student.
	 */
	public ArrayList<Double> getFinalGrade() {
		ArrayList<Double> finGrade = new ArrayList<Double>();
		for(Double d : finalGrade) {
			finGrade.add(new Double(d)); 
		}
		return finalGrade;
	}

	/**
	 * This method sets the list of final grades to the given value.
	 * 
	 * @param finalGrade is the list of final grades.
	 */
	public void setFinalGrade(ArrayList<Double> finalGrade) {
		this.finalGrade = new ArrayList<Double>();
		for(Double d : finalGrade) {
			this.finalGrade.add(new Double(d));
		}
	}

	/**
	 * This method overrides the hashCode method from class Object.
	 * This method returns a hash code value for the object. 
	 * This method is supported for the benefit of hash tables 
	 * such as those provided by HashMap.
	 * 
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((studentID == null) ? 0 : studentID.hashCode());
		return result;
	}

	/**
	 * This method overrides the equal method from class Object.
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param obj is the reference object with which to compare.
	 * @return true if this object is the same as the obj argument; false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (studentID == null) {
			if (other.studentID != null)
				return false;
		} else if (!studentID.equals(other.studentID))
			return false;
		return true;
	}

	/**
	 * This method calculates the final grade of the student for each course
	 * taken and adds it to the list of final grades.
	 * 
	 * @param gradeArray is an array of grades received by the student.
	 * @param weightArray is an array of weight of the courses taken by the student.
	 * @throws InvalidTotalException if the total weight
	 *  of the assessments does not add up to 100, or of 
	 *  the total grade of a student is more than 100.
	 */
	public void addGrade(ArrayList<Double> gradeArray, ArrayList<Integer> weightArray) {
		double finGrade = 0;
		int totalWeight = 0;
		try {
			for (int i = 0; i < gradeArray.size(); i++) {
				finGrade += (gradeArray.get(i) * weightArray.get(i)) / 100;
				totalWeight += weightArray.get(i);
			}
			finalGrade.add((double) Math.round(finGrade * 10) / 10);
			if (totalWeight != 100) {
				throw new InvalidTotalException("Exception: The total weight does not add up to 100");
			}
			if(finGrade > 100) {
				throw new InvalidTotalException("Exception: The total garde of the student is more than 100");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This method calculates the GPA of the student.
	 * 
	 * @return the calculated GPA of the student.
	 */
	public double weightedGPA() {
		int gradePoint;
		double attemptedCredit = 0;
		double gpa = 0;

		for (int i = 0; i < finalGrade.size(); i++) {
			if (finalGrade.get(i) >= 90 && finalGrade.get(i) <= 100) {
				gradePoint = 9;
			} else if (finalGrade.get(i) >= 80 && finalGrade.get(i) < 90) {
				gradePoint = 8;
			} else if (finalGrade.get(i) >= 75 && finalGrade.get(i) < 80) {
				gradePoint = 7;
			} else if (finalGrade.get(i) >= 70 && finalGrade.get(i) < 75) {
				gradePoint = 6;
			} else if (finalGrade.get(i) >= 65 && finalGrade.get(i) < 70) {
				gradePoint = 5;
			} else if (finalGrade.get(i) >= 60 && finalGrade.get(i) < 65) {
				gradePoint = 4;
			} else if (finalGrade.get(i) >= 55 && finalGrade.get(i) < 60) {
				gradePoint = 3;
			} else if (finalGrade.get(i) >= 50 && finalGrade.get(i) < 55) {
				gradePoint = 2;
			} else if (finalGrade.get(i) >= 47 && finalGrade.get(i) < 50) {
				gradePoint = 1;
			} else {
				gradePoint = 0;
			}
			attemptedCredit += courseTaken.get(i).getCredit();
			gpa += gradePoint * courseTaken.get(i).getCredit();
		}
		return (double) Math.round(gpa / attemptedCredit * 10) / 10;
	}

	/**
	 * This method adds a course to the course taken by the students.
	 * 
	 * @param course is the course to be added the list of course taken.
	 */
	public void addCourse(Course course) {
		courseTaken.add(course);
	}

} // end of Student

/**
 * This class generates a course taken by a student.
 */

class Course {
	/**
	 * The code of the course.
	 */
	private String code;
	
	/**
	 * The name of the student.
	 */
	private ArrayList<Assessment> assignment;
	
	/**
	 * The list of assignments for each course.
	 */
	private double credit;

	/**
	 * This is the no argument constructor for the class Course.
	 */
	public Course() {
		this("", null, 0.0);
	}

	/**
	 * This is the overloaded constructor for the class course.
	 * Initializes  this course so that it has the specified 
	 * code, assignment, credit.
	 * 
	 *@param code is the code of the course.
	 *@param assignment is the list of assignments of the course.
	 *@param credit is the credit of the course.
	 */
	public Course(String code, ArrayList<Assessment> assignment, double credit) {
		this.code = new String(code);
		this.assignment = new ArrayList<Assessment>();
		for(Assessment a : assignment) {
			this.assignment.add(Assessment.getInstance(a.getType(), a.getWeight()));
		}
		this.credit = credit;
	}

	/**
	 * This is the copy constructor for the class Course.
	 * Initializes this student by copying another student. This 
	 * course will have the same attributes as the other course.
	 * 
	 * @param other is the course to copy.
	 */
	public Course(Course other) {
		this(other.code, other.assignment, other.credit);
	}

	/**
	 * This method gets the code this of course.
	 * 
	 * @return the code of this course.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * This method sets the code of the course to the given value.
	 * 
	 * @param code is the code of the course.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * This method gets the list of assessment of this course.
	 * 
	 * @return the list of assessment of this course.
	 */
	public ArrayList<Assessment> getAssignment() {
		ArrayList<Assessment> assessList = new ArrayList<Assessment>();
		for(Assessment a : this.assignment) {
			assessList.add(Assessment.getInstance(a.getType(), a.getWeight()));
		}
		return assessList;
	}

	/**
	 * This method sets the list of assessment of the course to the given value.
	 * 
	 * @param assignment is the list of assessment of the course.
	 */
	public void setAssignment(ArrayList<Assessment> assignment) {
		this.assignment = new ArrayList<Assessment>();
		for(Assessment a : assignment) {
			this.assignment.add(Assessment.getInstance(a.getType(), a.getWeight()));
		}
	}

	/**
	 * This method gets the credit of this course.
	 * 
	 * @return the credit of this course.
	 */
	public double getCredit() {
		return credit;
	}

	/**
	 * This method sets the credit of the course to the given value.
	 * 
	 * @param credit is the credit of the course.
	 */
	public void setCredit(double credit) {
		this.credit = credit;
	}

	/**
	 * This method overrides the hashCode method from class Object.
	 * This method returns a hash code value for the object. 
	 * This method is supported for the benefit of hash tables 
	 * such as those provided by HashMap.
	 * 
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assignment == null) ? 0 : assignment.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		long temp;
		temp = Double.doubleToLongBits(credit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * This method overrides the equal method from class Object.
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param obj is the reference object with which to compare.
	 * @return true if this object is the same as the obj argument; false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (assignment == null) {
			if (other.assignment != null)
				return false;
		} else if (!assignment.equals(other.assignment))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (Double.doubleToLongBits(credit) != Double.doubleToLongBits(other.credit))
			return false;
		return true;
	}

} // end of COurse

/**
 * This class generates an Assessment.
 * 
 *
 */

class Assessment {
	/**
	 * The type of the assessment.
	 */
	private char type;
	
	/**
	 * The weight of the assessment.
	 */
	private int weight;

	/**
	 * This is the no argument constructor for the class Assessment.
	 * Initializes this assignment to default values.
	 * 
	 */
	private Assessment() {
		this(' ', 0);
	}

	/**
	 * This is the overloaded constructor for the class Assessment.
	 * Initializes this assessment so that it has the specified 
	 * type and weight.
	 * 
	 * @param type is the type of the assessment.
	 * @param weight is the weight of the assessment.
	 */
	private Assessment(char type, int weight) {
		this.type = type;
		this.weight = weight;
	}

	/**
	 * This is the static factory method for the class Assessment
	 * 
	 * @param type the type of the assessment.
	 * @param weight the type of the assessment.
	 * @return an assessment.
	 */
	public static Assessment getInstance(char type, int weight) {
		Assessment assessment = new Assessment(type, weight);
		return assessment;
	}
	
	/**
	 * This method gets the type of this assessment.
	 * 
	 * @return the type of this assessment.
	 */
	public char getType() {
		return type;
	}

	/**
	 * This method sets the type to the given value.
	 * 
	 * @param type is the type of the assessment.
	 */
	public void setType(char type) {
		this.type = type;
	}

	/**
	 * This method gets the weight of this assessment.
	 * 
	 * @return the type of this assessment.
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * This method sets the weight to the given value.
	 * 
	 * @param weight is the weight of the assessment.
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * This method overrides the hashCode method from class Object.
	 * This method returns a hash code value for the object. 
	 * This method is supported for the benefit of hash tables 
	 * such as those provided by HashMap.
	 * 
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + type;
		result = prime * result + weight;
		return result;
	}

	/**
	 * This method overrides the equal method from class Object.
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param obj is the reference object with which to compare.
	 * @return true if this object is the same as the obj argument; false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assessment other = (Assessment) obj;
		if (type != other.type)
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}
	
	

} // end of Assessment

/**
 * This class is a custom exception that extends RuntimeException.
 * 
 *
 */

class InvalidTotalException extends Exception {
	/**
	 * Initializes an exception with no detail message.
	 */
	public InvalidTotalException() {
		super();
	}

	/**
	 * Initializes an exception with a detail message.
	 * 
	 * @param message is the message to be printed with the exception.
	 */
	public InvalidTotalException(String message) {
		super(message);
	}
} // end of InvalidTotalException