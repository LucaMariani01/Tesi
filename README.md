# Group Project
Group Project exam for University of Camerino, Computer Science L-31. 
The group is composed by Luca Mariani, Diego Pennesi, Filippo Reucci.

# Ring PDB Analyzer
The focus of this group project is, given a list of PDBs contained in a JSON file, building a set of Arc Annotated Sequence (AAS) files in the given directory.

## RingPdbAnalyzer.jar usage examples

* `> java -jar RingPdbAnalyzer.jar -i path/to/inputJsonFile.json -o path/to/outputAasDirectory`

Produces a list of AAS file contained in the path/to/outputAasDirectory directory, corresponding to the PDBs contained in the inputJsonFile.json file.

* `> java -jar RingPdbAnalyzer.jar -i path/to/inputJsonFile.json -o path/to/outputAasDirectory -b arcType`

Produces a list of AAS file contained in the path/to/outputAasDirectory directory, corresponding to the PDBs contained in the inputJsonFile.json file, but using this command, only the selected types of arc will be analyzed, the arc list is: HBOND, IAC, IONIC, PICATION, PIPISTACK, SSBOND, VDW.

* `> java -jar RingPdbAnalyzer.jar -i path/to/inputJsonFile.json -o path/to/outputAasDirectory -u`

This command analyzes all the unit contained in the protein dataset (JSON file), and not just the regions.

* `> java -jar RingPdbAnalyzer.jar -i path/to/inputJsonFile.json -o path/to/outputAasDirectory -t path/to/executionTimeDirectory`

Produces a list of AAS file contained in the path/to/outputAasDirectory directory, corresponding to the PDBs contained in the inputJsonFile.json file, but using this command, a csv file containing the execution times for each operation per pdb will be created in the path/to/executionTimeDirectory directory.

## Accepted Input file formats 
* PDB JSON format, download link: <https://www.rcsb.org/>

## Result formats
Arc Annotated Sequence (AAS) file, inclueds sequence and bond list, expressed in the format `(i_1,j_1);(i_2,j_2); ... ;(i_m,j_m)` where each index 
`i_k, j_k` is withinthe range `[1,n]` where `n` is the length
of the primary sequence and `i_k < j_k`  for all `k`.

## Results 
In the output directory specified by the user, RingPdbAnalyzer.jar creates four directories:
* *aas*  containing the generated files
* *cutted pdb* containing the refactored .pdb files
* *path_to_pdb_cache_directory* genereted by Biojava
* *Ring result*
* *labels.csv* is the label csv file, each label represents a pdb with three parameters: PDB, UNIPROT, classification
* *execTime.csv* is the file generated using -t command, where execution times are written
  
## Requirements
* *Java 8* or higher
* *RING software* added to the PATH evironment variable
* Internet connection to use biojava library

## Credits

RingPdbAnalyzer group project is developed and tested by:

- Prof. Luca Tesei, Supervisor, University of Camerino
- Prof. Michela Quadrini, Supervisor, University of Camerino
- Filippo Reucci, Student, University of Camerino
- Diego Pennesi, Student, University of Camerino
- Luca Mariani, Student, University of Camerino
