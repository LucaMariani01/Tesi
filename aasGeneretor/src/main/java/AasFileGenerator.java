package main.java;

import java.io.File;
import java.util.ArrayList;

public class AasFileGenerator {
    /**
     * this method call all methods to build aas files
     * @param currentPDB is the protein that we are analyzing
     * @param start is the start of this protein structure
     * @param outputPath is the path were aas file will be stored
     * @param bondList is the eventual list of bond given in input by the user
     * @param ringResultPath is the path were protein bond's file are stored
     * @param unitNumber is the number that represent the protein
     */
    public static void aasFileGeneratorMain(PDB currentPDB, int start, String outputPath, ArrayList<String> bondList, String ringResultPath , int unitNumber) {

        AasParser aasParser = new AasParser();
        ArrayList<String> result;
        String edgesFileName ;

        if(unitNumber != -1 ) edgesFileName = currentPDB.getRepeatsdbId()+"_"+unitNumber;
        else edgesFileName = currentPDB.getRepeatsdbId();

        result = aasParser.readerEdges(new File(ringResultPath+"/"+edgesFileName+".pdb_ringEdges"),start,bondList);
        aasParser.buildAASFile(result.get(0),result.get(1),outputPath,edgesFileName);
    }
}