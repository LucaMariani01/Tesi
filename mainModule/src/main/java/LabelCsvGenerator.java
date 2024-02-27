package main.java;

import org.biojava.nbio.structure.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * this class is used to build te csv file that contain the protein's label
 */
public class LabelCsvGenerator {
    /**
     * this method generate a label by a protein
     * @param currentPDB is the protein that we are analyzing
     * @param csvFileName is the name of csv file thath represent the label
     * @param unitNumber is used to know if we are analyze a unit of protein or a region
     */
    public static void generator(PDB currentPDB, File csvFileName, int unitNumber){

        String pdbId = currentPDB.getPdbId();
        String classification =currentPDB.getClassTopologyFoldClan();
        Structure structure;
        try {
            structure = StructureIO.getStructure(pdbId);
        } catch (IOException | StructureException e) {
            throw new RuntimeException(e);
        }

        Chain targetChain = structure.getChain(currentPDB.getPdbChain().toUpperCase());

        String app = "";

        if (targetChain != null) {
            for (DBRef dbRef : targetChain.getStructure().getDBRefs()){
                if (dbRef.getDatabase().equalsIgnoreCase("UNP")) {
                    app = dbRef.getDbAccession();
                }
            }
            if(app.compareTo("")==0) app = "unknown";
        } else app = "unknown";

        if(unitNumber == -1 ){
            writeLabel(pdbId+currentPDB.getPdbChain(), app, classification, csvFileName);
        }else{
            writeLabel(pdbId+currentPDB.getPdbChain()+"_"+unitNumber, app, classification, csvFileName);
        }
    }

    /**
     * this metod write a record in csv file
     * @param Id represent th protein id
     * @param uniprot represent the protein uniprot
     * @param classification represent the protein classification
     * @param csvFileName is the csv file
     */
    private static void writeLabel(String Id, String uniprot, String classification, File csvFileName){
        try {
            FileWriter writer = new FileWriter(csvFileName, true);
            writer.write(Id+ ";" +uniprot+";" + classification + "\n");
            writer.close();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

}
