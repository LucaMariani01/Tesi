package main.java;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.io.PDBFileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BioJavaHandler {
    /**
     * This method is used to generate pdb files using biojava library
     * @param PDBDirectory is the directory where pdb files will be stored
     * @param PDBcuttedDir is the directory where cutted pdb files will be stored
     * @param currentPDB is the pdb that is being analyzed
     * @param unitNumber is the unit or region number of the current pdb
     */
    public static void biojavaGenPdb(String PDBDirectory,String PDBcuttedDir, PDB currentPDB, int unitNumber,File fileNameCsvLabel)throws Exception{
        LabelCsvGenerator.generator(currentPDB,fileNameCsvLabel,unitNumber);
        FileJsonManager fileReader = new FileJsonManager();
        PDBFileReader pdbReader = new PDBFileReader();
        String biojavaDirectory = PDBDirectory+"/path_to_pdb_cache_directory";
        pdbReader.setPath(biojavaDirectory); //setting path where pdb generated with bioJava will be stored

        Structure structure = pdbReader.getStructureById(currentPDB.getPdbId());
        structure.toPDB(); //generating pdb files

        String pdbPath = biojavaDirectory+"/data/structures/divided/pdb/"+
                currentPDB.getRepeatsdbId().substring(1, 3) + "/pdb" + (currentPDB.getPdbId()) + ".ent.gz"; //is the pdb file complete path

        List<String> comando = new ArrayList<>();
        comando.add("gunzip"); //unzipping pdb files
        comando.add(pdbPath);
        ProcessBuilder processBuilder = new ProcessBuilder(comando);
        Process processo = processBuilder.start();
        processo.waitFor();
        pdbPath = biojavaDirectory+"/data/structures/divided/pdb/"+
                currentPDB.getRepeatsdbId().substring(1, 3)+"/pdb"+(currentPDB.getPdbId())+".ent"; //path where we are going to unzip pdb files
        if(unitNumber!=-1) fileReader.pdbReaderRefactor(new File(pdbPath), currentPDB,unitNumber,PDBcuttedDir); //method used to cut pdb file
        else fileReader.pdbReaderRefactor(new File(pdbPath),currentPDB,unitNumber,PDBcuttedDir);
    }
}