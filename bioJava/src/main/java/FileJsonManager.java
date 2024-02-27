package main.java;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

public class FileJsonManager {

    /**
     * This method refactor the ent (pdb) file generate by Biojava. This method allow us to considerate only record ATOM
     * @param filePDB pdb file generate
     * @param currentPDB the protein that we are analyze
     * @param unitNumber this represents the unit or region
     * @param cuttedPDBfilesPath this is the location were refactored file were stored
     */
    public  void pdbReaderRefactor(File filePDB,PDB currentPDB, int unitNumber, String cuttedPDBfilesPath) {
        String line;
        int start = currentPDB.getStart(),end = currentPDB.getEnd();
        boolean endReached = false;
        PrintWriter writer;
        try{
            if(unitNumber != -1)  writer = new PrintWriter(Files.newBufferedWriter(Paths.get(cuttedPDBfilesPath+"/"+currentPDB.getRepeatsdbId()+"_"+unitNumber+".pdb")));//writing the reduced file in another file
            else  writer = new PrintWriter(Files.newBufferedWriter(Paths.get(cuttedPDBfilesPath+"/"+currentPDB.getRepeatsdbId()+".pdb")));//writing the reduced file in another file

            try (BufferedReader pdbReader = new BufferedReader(new FileReader(filePDB))) { //reading pdb file
                while ((line = pdbReader.readLine()) != null) {
                    if (line.startsWith("ATOM")) {
                        int startTemp = Integer.parseInt(line.substring(22, 26).trim()); //obtaining the start of this current line
                        String chain = String.valueOf(line.charAt(21));                 //obtaining the chain of this current line
                        if (startTemp >= start && startTemp <= end && (chain.compareTo(currentPDB.getPdbChain()) == 0)){
                            if(startTemp == end) endReached = true;
                            if((endReached) && startTemp != end)break;
                            writer.println(line);
                        } //if the atom number is between the range we are going to write this line
                    }
                }
                writer.close();
            } catch (Exception e) { System.out.println("Something went wrong during reducing pdb file: " + e); }
        }catch (Exception e) { System.out.println("Something went wrong during writing reduced pdb file: " + e); }
    }

    /**
     * This function is used to get all the pdb in the json file
     * @param repeatsDBFile json file containing PDBs and corresponding data
     * @return list of all the PDBs in the json file
     */
    public ArrayList<PDB> getPDBListJSON(File repeatsDBFile){
        ArrayList<PDB> pdbList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        Optional<PDB> lastPdbRead = Optional.empty();
        Optional<PDB> appPdb = Optional.empty();
        int appStart = 0;
        int appEnd = 0;

        try {
            FileReader reader = new FileReader(repeatsDBFile);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;//pdb corrente
                if(lastPdbRead.isEmpty()){
                    lastPdbRead = Optional.of(createPdbRecord(jsonObject));
                    appPdb = lastPdbRead;
                }

                if(jsonObject.get("repeatsdb_id").toString().compareTo(lastPdbRead.get().getRepeatsdbId())==0 ){
                    appStart = Integer.parseInt(jsonObject.get("start").toString());
                    appEnd = Integer.parseInt(jsonObject.get("end").toString());
                    if(appStart<appPdb.get().getStart()) appPdb.get().setStart(appStart);
                    if(appEnd>appPdb.get().getEnd()) appPdb.get().setEnd(appEnd);
                }else{
                    pdbList.add(appPdb.get());
                    appPdb = Optional.of(createPdbRecord(jsonObject) );
                }
                lastPdbRead = Optional.of(createPdbRecord(jsonObject));
            }
        } catch (IOException | ParseException e) {  System.out.println("Something went wrong: " + e);  }
        return pdbList;
    }

    /**
     * this method is used to get all protein unut from the dataset
     * @param jsonDataset is the JSON dataset by RepeatsDB.org
     * @return a list af all protein unit in the dataset
     */
    public ArrayList<PDB> getUnitList(File jsonDataset){
        ArrayList<PDB> pdbUnitList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(jsonDataset);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) {
                pdbUnitList.add(createPdbRecord((JSONObject) obj));
            }
        } catch (IOException | ParseException e) {  System.out.println("Something went wrong: " + e);  }
        return pdbUnitList;
    }

    /**
     * this is a support method used to build an object that represent on eprotein of the dataset
     * @param pdb record from Json dataset
     * @return an object that represent the protein
     */
    private PDB createPdbRecord(JSONObject pdb){
        return new PDB(
                Integer.parseInt(pdb.get("start").toString()),
                Integer.parseInt(pdb.get("end").toString()),
                pdb.get("type").toString(),
                pdb.get("class").toString(),
                pdb.get("topology").toString(),
                pdb.get("fold").toString(),
                pdb.get("clan").toString(),
                pdb.get("class_topology").toString(),
                pdb.get("class_topology_fold").toString(),
                pdb.get("class_topology_fold_clan").toString(),
                pdb.get("pdb_id").toString(),
                pdb.get("pdb_chain").toString(),
                pdb.get("repeatsdb_id").toString(),
                pdb.get("origin").toString(),
                pdb.get("reviewed").toString(),
                new String[1],
                pdb.get("region_id").toString(),
                pdb.get("region_units_num").toString(),
                pdb.get("region_average_unit_length").toString()
        );

    }

}