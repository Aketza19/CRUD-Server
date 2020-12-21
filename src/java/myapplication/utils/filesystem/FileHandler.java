/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.utils.filesystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Mikel
 */
public class FileHandler {
    
    /**
     * Creates a file
     *
     * @param filename The name of the file to create.
     */
    public static void createFile(String filename) {
        try {
            File myObj = new File(filename);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    /**
     * Writes a text inside a file.
     *
     * @param filename The name of the file, including the extension. Example:
     * myfile.key
     * @param text The text to write inside the file.
     */
    public static void writeStringToFile(String filename, String text) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(text);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
}
