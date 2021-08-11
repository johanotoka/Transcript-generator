/**
 *@author Steeve Johan Otoka Eyota
 *@version 1.0
 *@since 2020-12-01
 */

import java.util.ArrayList;

public class TranscriptDemo {

	public static void main(String[] args) {
		
		Transcript tr01 = new Transcript("src/input.txt", "output.txt");
		
		ArrayList<Student> arr01 = tr01.buildStudentArray();
		
		tr01.printTranscript(arr01);
		
		Transcript tr02 = new Transcript("src/wrongInput.txt", "wrongOutput.txt");
		
		ArrayList<Student> arr02 = tr02.buildStudentArray();
		
		tr02.printTranscript(arr02);
	}

}
