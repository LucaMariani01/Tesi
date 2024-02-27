package main.java;

import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.chem.ChemCompGroupFactory;
import org.biojava.nbio.structure.chem.ReducedChemCompProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import static main.java.BioJavaHandler.biojavaGenPdb;

public class Main {
    public static void main(String[] args) {
        ChemCompGroupFactory.setChemCompProvider(new ReducedChemCompProvider());
        Options options = getOptions();

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("CommandLineExample", options);
            } else {

                if (cmd.hasOption("i") && cmd.hasOption("o")) {
                    String fileJson = cmd.getOptionValue("i");
                    String outputPath = cmd.getOptionValue("o");

                    if(!(fileJson.isEmpty() && outputPath.isEmpty())){

                        File fileNameCsvLabel = new File(outputPath+"/labels.csv");
                        Path fileLabelPath = Paths.get(outputPath, "labels.csv");
                        if (Files.exists(fileLabelPath)) Files.delete(Paths.get(outputPath+"/labels.csv"));
                        if(fileNameCsvLabel.createNewFile()) System.out.println("LABEL CSV FILE CREATED CORRECTLY");

                        try {
                            FileWriter writer = new FileWriter(fileNameCsvLabel, true);
                            writer.write("Id"+";"+"Uniprot"+";"+"Classification\n");
                            writer.close();
                        } catch (IOException e) {throw new RuntimeException(e);}

                        Path biojavaCachesPath = Paths.get(outputPath + "/path_to_pdb_cache_directory");
                        if(Files.exists(biojavaCachesPath)){
                            FileUtils.cleanDirectory(new File(outputPath+"/path_to_pdb_cache_directory"));
                            Files.delete(biojavaCachesPath);
                        }


                        Path pathCuttedPdb = Paths.get(outputPath + "/cuttedPDB");
                        File cuttedPDBdir = new File(pathCuttedPdb.toString());
                        if (Files.exists(pathCuttedPdb)) {
                            FileUtils.cleanDirectory(cuttedPDBdir);
                            Files.delete(pathCuttedPdb);
                        }
                        if(cuttedPDBdir.mkdir()) System.out.println("PDB REFACTORED DIRECTORY CREATED CORRECTLY");

                        //if (cmd.hasOption("b")) bondList = new ArrayList<>( Arrays.asList(cmd.getOptionValues("b") ));

                        Path aasDir = Paths.get( outputPath+"/aas");
                        File aasDirectory = new File(aasDir.toString());
                        if (Files.exists(aasDir)) {
                            FileUtils.cleanDirectory(aasDirectory);
                            Files.delete(aasDir);
                        }
                        if(aasDirectory.mkdir()) System.out.println("AAS DIRECTORY CREATED CORRECTLY");

                        Path ringDir = Paths.get( outputPath+"/ringResult");
                        File ringDirectory = new File(ringDir.toString());
                        if (Files.exists(ringDir)) {
                            FileUtils.cleanDirectory(ringDirectory);
                            Files.delete(ringDir);
                        }
                        if(ringDirectory.mkdir()) System.out.println("RING RESULT DIRECTORY CREATED CORRECTLY");

                        FileJsonManager fileReader = new FileJsonManager();
                        ArrayList<PDB> pdbList;
                        int unitNumberCount ;
                        if(cmd.hasOption("u")){
                            pdbList = fileReader.getUnitList(new File(fileJson));
                            unitNumberCount = 1;
                        }else {
                            pdbList = fileReader.getPDBListJSON(new File(fileJson));
                            unitNumberCount = -1;
                        }
                        Optional<PDB> lastPDBread = Optional.empty();
                        for(PDB singlePDB : pdbList) {
                            if(checkValidPdb(singlePDB)){
                                if(unitNumberCount != -1){
                                    if( lastPDBread.isEmpty() ){
                                        lastPDBread = Optional.of(singlePDB);
                                        System.out.println("Analyze pdb: "+ singlePDB.getRepeatsdbId()+" unit: "+unitNumberCount);
                                    }else {
                                        if(lastPDBread.get().getRepeatsdbId().compareTo(singlePDB.getRepeatsdbId()) == 0 )unitNumberCount++;
                                        else unitNumberCount = 1;
                                        System.out.println("Analyze pdb: "+ singlePDB.getRepeatsdbId()+" unit: "+unitNumberCount);
                                    }
                                }else System.out.println("Analyze pdb: "+ singlePDB.getRepeatsdbId());


                                biojavaGenPdb(outputPath,cuttedPDBdir.toString(),singlePDB, unitNumberCount,fileNameCsvLabel);

                                Ring.ringManager(singlePDB, ringDirectory.toString(),cuttedPDBdir.toString(),unitNumberCount);  //passiamo il pdb corrente, il path di destinazione, e i legami da analizzare
                                AasFileGenerator.aasFileGeneratorMain(singlePDB,singlePDB.getStart(),outputPath,ringDirectory.toString(),unitNumberCount);
                                if(unitNumberCount != -1) lastPDBread = Optional.of(singlePDB);
                            }
                        }
                    }
                } else System.out.println("ERROR: use -h to view the help.");
            }
        } catch (Exception e) {
            System.err.println("ERROR while parsing options: "+e.getMessage());
        }
    }

    private static Options getOptions() {
        Options options = new Options();
        Option helpOption = new Option("h", "help", false, "Show help");
        options.addOption(helpOption);

        Option inputOption = new Option("i","input",true,"Insert the JSON file path to fetch PDB information.");
        options.addOption(inputOption);

        Option outputOption = new Option("o","output",true,"Insert the path where RING and AAS files will be saved.");
        options.addOption(outputOption);


        Option unitOption = new Option("u","unit",false,"This option calculate AAS file for every unit of JSON dataset ");
        options.addOption(unitOption);

        return options;
    }

    private static boolean checkValidPdb(PDB singlePDB){
        String pdb = singlePDB.getRepeatsdbId();
        boolean validPdb = true;
        try {
            Structure structure = StructureIO.getStructure(pdb.substring(0, pdb.length()-1));
        }catch (Exception e){
            System.out.println(e);
            validPdb = false;
        }
        return validPdb;
    }
}
