package main.java;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AasParser {
    /**
     * This method is used to write the aas file after parsing the pdb edges file
     * @param pdbEdgesFile is the file containing the pdb's list of edges
     * @param start is the start of this current pdb
     * @return the content of the aas formatted file
     *
     */
    public ArrayList<String> readerEdges(File pdbEdgesFile,int start){

        ArrayList<String>  result = new ArrayList<>();
        StringBuilder aminoAcidList = new StringBuilder();
        StringBuilder bondList= new StringBuilder();
        String lastBond="";
        boolean cont = true ;
        try (BufferedReader pdbEdgesReader = new BufferedReader(new FileReader(pdbEdgesFile))) {
            String line;
            while ((line = pdbEdgesReader.readLine()) != null) {
                if(cont) cont=false;
                else {
                    String[] lineItems = line.split(":");
                    aminoAcidList.append(this.parser(lineItems[3].substring(0, 3))).append(this.parser(lineItems[7].substring(0, 3)));
                    if (lastBond.compareTo("(" + ((Integer.parseInt(lineItems[1]) - start) + 1) + "," + ((Integer.parseInt(lineItems[5]) - start) + 1) + ");")!=0) {
                        bondList.append("(").append((Integer.parseInt(lineItems[1]) - start) + 1).append(",").append((Integer.parseInt(lineItems[5]) - start) + 1).append(");");
                        lastBond= "(" + ((Integer.parseInt(lineItems[1]) - start) + 1) + "," + ((Integer.parseInt(lineItems[5]) - start) + 1) + ");";//save last bond to avoid duplicate
                    }
                }
            }
        } catch (Exception e) { System.out.println("Something went wrong during aas generation: "+e); }
        result.add(aminoAcidList.toString());
        result.add(bondList.toString());
        return result;
    }

    /**
     * This method is used to convert the amino acid 3-letter code
     * into the IUPAC amino acid code
     * @param s is the amino acid 3-letter code
     * @return the corresponding IUPAC amino acid code
     */
    private String parser(String s){
        return switch (s) {
            case "ALA" -> "A";
            case "CYS" -> "C";
            case "ASP" -> "D";
            case "GLU" -> "E";
            case "PHE" -> "F";
            case "GLY" -> "G";
            case "HIS" -> "H";
            case "ILE" -> "I";
            case "LYS" -> "K";
            case "LEU" -> "L";
            case "MET" -> "M";
            case "ASN" -> "N";
            case "PRO" -> "P";
            case "GLN" -> "Q";
            case "ARG" -> "R";
            case "SER" -> "S";
            case "THR" -> "T";
            case "VAL" -> "V";
            case "TRP" -> "W";
            case "TYR" -> "Y";
            default -> "";
        };
    }

    /**
     * This method is used to generate the aas file
     * @param aminoAcidList is the amino acid list we are going to write in the aas file
     * @param bondList is the bond list we are going to write in the aas file
     * @param path is the path we are going to store the aas file
     * @param pdb is the pdb name
     */
    public void buildAASFile(String aminoAcidList,String bondList,String path,String pdb){
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(path+"/aas/"+pdb+".txt")))) {
            writer.println(aminoAcidList);
            writer.printf(bondList);
        } catch (IOException e) { throw new RuntimeException(e); }
    }
}