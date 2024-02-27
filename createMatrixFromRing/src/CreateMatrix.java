import main.java.PDB;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class CreateMatrix {

    public void createAll(PDB currentPDB, int start, String outputPath, int end, String ringResultPath , int unitNumber){
        String edgesFileName ;

        if(unitNumber != -1 ) edgesFileName = currentPDB.getRepeatsdbId()+"_"+unitNumber;
        else edgesFileName = currentPDB.getRepeatsdbId();

        int [][] matrix =  this.buildMatrix(new File(ringResultPath+"/"+edgesFileName+".pdb_ringEdges"),start,end);
        this.buildMatrixFile(matrix,outputPath,edgesFileName);
    }

    private void buildMatrixFile(int[][] matrix, String outputPath, String edgesFileName) {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(outputPath+"/aas/"+edgesFileName+".txt")))) {
            for (int[] row : matrix){
                String[] stringRow= Arrays.stream(row).mapToObj(String::valueOf).toArray(String[]::new);
                writer.write(Arrays.toString(stringRow));
            }
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    private int [][] buildMatrix(File file, int start,int end) {
        int matrixSize = (end -start) + 1;
        int [][] matrix = new int[matrixSize][matrixSize];
        boolean cont = true;

        Arrays.stream(matrix).forEach(row -> Arrays.fill(row,0));
        int index = start;
        for (int x=1 ;  x< matrixSize;x++){
            matrix[0][x] = index;
            matrix[x][0] = index;
            index++;
        }

        try (BufferedReader pdbEdgesReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = pdbEdgesReader.readLine()) != null) {
                if(cont) cont=false;
                else {
                    String[] lineItems = line.split(":");
                    int x = this.findPosition(matrix,Integer.parseInt(lineItems[1]));
                    int y = this.findPosition(matrix,Integer.parseInt(lineItems[5]));
                    matrix[x][y] = 1;
                    matrix[y][x] = 1;
                }
            }
        } catch (Exception e) { System.out.println("Something went wrong during aas generation: "+e); }
       return matrix;
    }

    private int findPosition( int [][] matrix, int val){
        for (int x=1 ;  x< matrix[0].length ;x++){
            if (matrix[0][x] == val) return x;
        }
        return -1;
    }
}
