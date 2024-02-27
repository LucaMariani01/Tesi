package main.java;
import java.io.FileWriter;
import java.io.IOException;

public class TimeController {

    /**
     * This method is used to write in a tsv file all the time in ms, that each method takes:
     * parsing Json, using ring and calculating aas for each pdb
     * @param jsonTime is the time spent on JsonReader methods
     * @param ringTime is the time spent on Ring methods
     * @param aasTime is the time spent on AasFileGenerator methods
     */
    public static void executionTimeManager(long jsonTime, long ringTime, long aasTime, String fileName){
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(jsonTime + ";" + ringTime + ";" + aasTime + ";"+ "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}