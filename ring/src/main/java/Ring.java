package main.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Ring {
    /**
     * This method is used to execute the softare "ring"
     * @param currentPDB is the proteind that we are analyzing
     * @param outputPath is the path where ring result files will be stored
     * @param pdbCuttedDir is the dire that contain refactored pdb given in input to ring
     * @param unitNumber represent the unit or region of the protein
     */
    public static void ringManager(PDB currentPDB, String outputPath, String pdbCuttedDir, int unitNumber) {

        ArrayList<String> comando = new ArrayList<>();
        comando.add("ring");
        comando.add("-i");
        if(unitNumber != -1) comando.add(pdbCuttedDir+"/"+currentPDB.getRepeatsdbId()+"_"+unitNumber+".pdb");
        else comando.add(pdbCuttedDir+"/"+currentPDB.getRepeatsdbId()+".pdb");
        comando.add("--out_dir");
        comando.add(outputPath);

        ProcessBuilder processBuilder = new ProcessBuilder(comando);
        try {
            Process processo1 = processBuilder.start();
            processo1.waitFor(); // Attendere il completamento del processo (se necessario)
        } catch (Exception e) {
            System.out.println("RING "+e);
        }

        File myFile;
        if(unitNumber != -1) myFile = new File(outputPath+"/"+currentPDB.getRepeatsdbId()+"_"+unitNumber+".pdb_ringNodes");
        else myFile = new File(outputPath+"/"+currentPDB.getRepeatsdbId()+".pdb_ringNodes");
        myFile.delete();
    }
}