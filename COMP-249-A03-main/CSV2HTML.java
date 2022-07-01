
// Assignment 3
// COMP 249 William Zicha Student ID: 40127016

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;

public class CSV2HTML {

	public static void ConvertCSVtoHTML(Scanner sc, PrintWriter pw, String file) throws CSVAttributeMissing {
		// Print HTML Head Section
		pw.println("<html>");
		pw.println("<style>");
		pw.println("table {font-family: arial, sans-serif;border-collapse: collapse;}");
		pw.println("td, th {border: 1px solid #000000;text-align: left;padding: 8px;}");
		pw.println("tr:nth-child(even) {background-color: #dddddd;}");
		pw.println("span{font-size: small}");
		pw.println("</style>");

		pw.println("<body>");

		pw.println("<table>");

		String title = sc.nextLine();
		String[] titleList = title.split(",");

		// Print title
		pw.println("<caption>" + titleList[0] + "</caption>");

		// Save attributes
		String attributes = sc.nextLine();
		String[] attributeList = attributes.split(",");

		// Check for missing attributes
		// throw exception if missing
		for (int i = 0; i < attributeList.length; i++) {
			if (attributeList[i] == "")
				throw new CSVAttributeMissing();
		}

		pw.println("<tr>");

		// Print attributes
		for (int i = 0; i < attributeList.length; i++) {
			pw.println("<td>" + attributeList[i] + "</td>");
		}

		pw.println("</tr>");

		// While loop handles saving printing of data and note lines
		String note;
		int line = 3, missingIndex = 0;

		while (sc.hasNextLine()) {
			// Save Note (in savedata)
			// Saving and Printing Data
			try {
				// Saving Data
				String data = sc.nextLine();
				String[] dataList = data.split(",");

				if (sc.hasNextLine() == false) {
					// Write note
					pw.println("</table>");
					pw.println("<span>" + dataList[0] + "</span>");
					break;
				}

				// Check for missing data and throw exception if missing
				for (int i = 0; i < dataList.length; i++) {
					if (dataList[i] == "") {
						missingIndex = i;
						throw new CSVDataMissing();
					}
				}

				pw.println("<tr>");

				// Write data
				for (int i = 0; i < dataList.length; i++) {
					pw.println("<td>" + dataList[i] + "</td>");
				}

				pw.println("</tr>");

			} catch (CSVDataMissing e) {
				// TODO: handle exception
				appendToLogs("WARNING: In file " + file + ".csv line " + Integer.toString(line)
						+ " is not converted to HTML: missing data: " + attributeList[missingIndex]);
			}
			line++;
		}
		pw.println("</body>");
		pw.println("</html>");
	}

	public static void appendToLogs(String aString) {
		// Opening Exceptions file
		PrintWriter logs = null;

		try {
			logs = new PrintWriter(new FileOutputStream("C:\\A03Output\\Exceptions.log"), true);
			logs.println(aString);
		} catch (FileNotFoundException e) {
			System.err.println("Could not open Exceptions file for logging.");
			System.err.println("This program will now terminate ...");
			System.exit(0);
		}
	}

	public static void main(String args[]) {
		Scanner sc = null;
		PrintWriter pw = null;
		Scanner keyboard = new Scanner(System.in);
		int fileCounter = 1;

		// Welcome Message
		System.out.println("================================");
		System.out.println("Welcome to my CSV2HTML Program!");
		System.out.println("================================");
		System.out.println();

		
		System.out.println("Please enter the amount of files you wish to convert");
		int fileNb = keyboard.nextInt();
		keyboard.nextLine();

		
		String filepath = "C:\\A03Output\\";
		File outputFolder = new File(filepath);

		// For loop to repeat file creation for every file

		for (int i = 0; i < fileNb; i++) {

			// Ask for file names

			System.out.println("Please enter the name of file #" + Integer.toString(fileCounter));
			System.out.println("Do NOT include the file extension!");
			String filename = keyboard.nextLine();
			fileCounter++;

			// Opening Input files
			try {
				sc = new Scanner(new FileInputStream("C:\\A03Input\\" + filename + ".csv"));
			} catch (FileNotFoundException e) {
				appendToLogs("Could not open file " + filename + " for reading.");
				appendToLogs(
						"Please check that the file exists and is readable. This program will terminate after closing any opened files.");
				sc.close();
				System.exit(0);
			}

			// Opening Output files
			try {
				pw = new PrintWriter(new FileOutputStream("C:\\A03Output\\" + filename + ".html"));
			} catch (FileNotFoundException e) {
				appendToLogs("Could not open file " + filename + " for reading.");
				appendToLogs(
						"Please check that the file exists and is readable. This program will TERMINATE after closing any opened files.");
				sc.close();
				System.exit(0);
			}

			// Requirement 4 - Call ConvertCSVtoHTML method
			try {
				ConvertCSVtoHTML(sc, pw, filename);
				pw.close();
			} catch (CSVAttributeMissing e) {
				// TODO: handle exception
				appendToLogs("ERROR: In file " + filename + ".csv. Missing attribute. File is NOT converted to HTML.");
			}
		}
		// Requirement 5 - Display HTML files in output dir
		System.out.println();
		System.out.println("Please enter name of file to open");
		String lastfilename = keyboard.next();
		keyboard.nextLine();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("C:\\A03Output\\" + lastfilename + ".html"));

			String brline = br.readLine();
			while (brline != null) {
				System.out.println(brline);
				brline = br.readLine();
			}
			br.close();

		} catch (FileNotFoundException e) {
			try {
				System.out.println("Error! That file name is invalid, try again.");
				lastfilename = keyboard.next();
				keyboard.nextLine();

				br = new BufferedReader(new FileReader("C:\\A03Output\\" + lastfilename + ".html"));

				String brline = br.readLine();
				while (brline != null) {
					System.out.println(brline);
					brline = br.readLine();
				}
				br.close();

			} catch (FileNotFoundException f) {
				System.out.println("Error! That file name is also invalid.");
				System.out.println("Program terminating.");
				System.exit(0);
			} catch (IOException g) {
				System.out.println("Error: An error has occured while reading from this file.");
				System.out.println("Program terminating.");
				System.exit(0);
			}
		} catch (IOException e) {
			System.out.println("Error: An error has occured while reading from this file.");
			System.out.println("Program terminating.");
			System.exit(0);
		}
	}
}
