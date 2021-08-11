/**
 *@author Steeve Johan Otoka Eyota
 *@version 1.0
 *@since 2020-12-01
 */

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * @author Steeve Johan Otoka Eyota
 *
 */
class TranscriptTest {

	/**
	 * Test method for {@link Assignment2.Transcript#Transcript(java.lang.String, java.lang.String)}.
	 */
//	@Test
//	void testTranscript() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link Assignment2.Transcript#buildStudentArray()}.
	 */
	@Test
	void testBuildStudentArray01() {
		Transcript tr = new Transcript("src/input.txt", "output.txt");
		ArrayList<Student> arr = tr.buildStudentArray();
		
		// Student ID
		assertEquals(arr.get(0).getStudentID(), "1000");
		assertEquals(arr.get(1).getStudentID(), "2000");
		assertEquals(arr.get(2).getStudentID(), "3000");
		assertEquals(arr.get(3).getStudentID(), "4000");
		assertEquals(arr.get(4).getStudentID(), "5000");
		assertEquals(arr.get(5).getStudentID(), "6000");
		assertEquals(arr.get(6).getStudentID(), "7000");
		assertEquals(arr.get(7).getStudentID(), "8000");
		assertEquals(arr.get(8).getStudentID(), "9000");
		
		arr.get(0).setStudentID("1111");
		assertEquals(arr.get(0).getStudentID(), "1111");
		assertNotEquals(arr.get(0).getStudentID(), "1000");
		
		arr.get(4).setStudentID("5555");
		assertEquals(arr.get(4).getStudentID(), "5555");
		assertNotEquals(arr.get(4).getStudentID(), "5000");
		
		arr.get(8).setStudentID("8888");
		assertEquals(arr.get(8).getStudentID(), "8888");
		assertNotEquals(arr.get(8).getStudentID(), "8000");

		// Student Name
		assertEquals(arr.get(0).getName(), "John");
		assertEquals(arr.get(1).getName(), "Jane");
		assertEquals(arr.get(2).getName(), "Pam");
		assertEquals(arr.get(3).getName(), "Jack");
		assertEquals(arr.get(4).getName(), "Emily");
		assertEquals(arr.get(5).getName(), "Jill");
		assertEquals(arr.get(6).getName(), "Jasmine");
		assertEquals(arr.get(7).getName(), "Josh");
		assertEquals(arr.get(8).getName(), "James");
		
		arr.get(0).setName("Jeremiah");
		assertEquals(arr.get(0).getName(), "Jeremiah");
		assertNotEquals(arr.get(0).getName(), "John");
		
		arr.get(2).setName("Juanita");
		assertEquals(arr.get(2).getName(), "Juanita");
		assertNotEquals(arr.get(2).getName(), "Pam");
		
		arr.get(4).setName("Josephine");
		assertEquals(arr.get(4).getName(), "Josephine");
		assertNotEquals(arr.get(4).getName(), "Emily");
		
		// Assessment
		Assessment a1 = Assessment.getInstance('P', 10);
		Assessment a2 = Assessment.getInstance('P', 10);
		Assessment a3 = Assessment.getInstance('P', 30);
		Assessment a4 = Assessment.getInstance('E', 15);
		Assessment a5 = Assessment.getInstance('E', 15);
		Assessment a6 = Assessment.getInstance('E', 20);
		
		assertEquals(arr.get(0).getCourseTaken().get(0).getAssignment().get(0), a1);
		assertEquals(arr.get(0).getCourseTaken().get(0).getAssignment().get(1), a2);
		assertEquals(arr.get(0).getCourseTaken().get(0).getAssignment().get(2), a3);
		assertEquals(arr.get(0).getCourseTaken().get(0).getAssignment().get(3), a4);
		assertEquals(arr.get(0).getCourseTaken().get(0).getAssignment().get(4), a5);
		assertEquals(arr.get(0).getCourseTaken().get(0).getAssignment().get(5), a6);
		
		// Course
		ArrayList<Assessment> assignment = new ArrayList<Assessment>();
		assignment.add(a1);
		assignment.add(a2);
		assignment.add(a3);
		assignment.add(a4);
		assignment.add(a5);
		assignment.add(a6);
		
		Course c1 = new Course("EECS2030", assignment, 3);
		
		assertEquals(arr.get(0).getCourseTaken().get(0), c1);
		
		// Student
		
		
	}
	
	@Test
	void testBuildStudentArray02() {
		Transcript tr = new Transcript("src/wrongInput.txt", "output.txt");
		ArrayList<Student> arr = tr.buildStudentArray();
		
		assertEquals(arr.get(0).getName(), "John");
		assertEquals(arr.get(1).getName(), "Jane");
		assertEquals(arr.get(2).getName(), "Pam");
		assertEquals(arr.get(3).getName(), "Jack");
		assertEquals(arr.get(4).getName(), "Emily");
		assertEquals(arr.get(5).getName(), "Jill");
		assertEquals(arr.get(6).getName(), "Jasmine");
		assertEquals(arr.get(7).getName(), "Josh");
		assertEquals(arr.get(8).getName(), "James");
		
		arr.get(0).setName("Jeremiah");
		assertEquals(arr.get(0).getName(), "Jeremiah");
		assertNotEquals(arr.get(0).getName(), "John");
		
	}

	/**
	 * Test method for {@link Assignment2.Transcript#printTranscript(java.util.ArrayList)}.
	 */
//	@Test
//	void testPrintTranscript() {
//		fail("Not yet implemented");
//	}

}
