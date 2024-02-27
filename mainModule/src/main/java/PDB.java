package main.java;

/**
 * This class represent a record of JSON protein dataset
 */
public class PDB {
    private  int start;
    private  int end;
    private  String type;
    private  String classPdb;
    private  String topology;
    private  String fold;
    private  String clan;
    private  String classTopology;
    private  String classTopologyFold;
    private  String classTopologyFoldClan;
    private  String pdbId;
    private  String pdbChain;
    private  String repeatsdbId;
    private  String origin;
    private  String reviewed;
    private  String[] annotator;
    private  String regionId;
    private  String regionUnitsNum;
    private  String regionAverageUnitLength;

    // Costruttore
    public PDB(int start, int end, String type, String classPdb, String topology, String fold, String clan,
               String classTopology, String classTopologyFold, String classTopologyFoldClan,
               String pdbId, String pdbChain, String repeatsdbId, String origin, String reviewed,
               String[] annotator, String regionId, String regionUnitsNum, String regionAverageUnitLength) {
        this.start = start;
        this.end = end;
        this.type = type;
        this.classPdb = classPdb;
        this.topology = topology;
        this.fold = fold;
        this.clan = clan;
        this.classTopology = classTopology;
        this.classTopologyFold = classTopologyFold;
        this.classTopologyFoldClan = classTopologyFoldClan;
        this.pdbId = pdbId;
        this.pdbChain = pdbChain;
        this.repeatsdbId = repeatsdbId;
        this.origin = origin;
        this.reviewed = reviewed;
        this.annotator = annotator;
        this.regionId = regionId;
        this.regionUnitsNum = regionUnitsNum;
        this.regionAverageUnitLength = regionAverageUnitLength;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassPdb() {
        return classPdb;
    }

    public void setClassPdb(String classPdb) {
        this.classPdb = classPdb;
    }

    public String getTopology() {
        return topology;
    }

    public void setTopology(String topology) {
        this.topology = topology;
    }

    public String getFold() {
        return fold;
    }

    public void setFold(String fold) {
        this.fold = fold;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public String getClassTopology() {
        return classTopology;
    }

    public void setClassTopology(String classTopology) {
        this.classTopology = classTopology;
    }

    public String getClassTopologyFold() {
        return classTopologyFold;
    }

    public void setClassTopologyFold(String classTopologyFold) {
        this.classTopologyFold = classTopologyFold;
    }

    public String getClassTopologyFoldClan() {
        return classTopologyFoldClan;
    }

    public void setClassTopologyFoldClan(String classTopologyFoldClan) {
        this.classTopologyFoldClan = classTopologyFoldClan;
    }

    public String getPdbId() {
        return pdbId;
    }

    public void setPdbId(String pdbId) {
        this.pdbId = pdbId;
    }

    public String getPdbChain() {
        return pdbChain;
    }

    public void setPdbChain(String pdbChain) {
        this.pdbChain = pdbChain;
    }

    public String getRepeatsdbId() {
        return repeatsdbId;
    }

    public void setRepeatsdbId(String repeatsdbId) {
        this.repeatsdbId = repeatsdbId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String isReviewed() {
        return reviewed;
    }

    public void setReviewed(String reviewed) {
        this.reviewed = reviewed;
    }

    public String[] getAnnotator() {
        return annotator;
    }

    public void setAnnotator(String[] annotator) {
        this.annotator = annotator;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionUnitsNum() {
        return regionUnitsNum;
    }

    public void setRegionUnitsNum(String regionUnitsNum) {
        this.regionUnitsNum = regionUnitsNum;
    }

    public String getRegionAverageUnitLength() {
        return regionAverageUnitLength;
    }

    public void setRegionAverageUnitLength(String regionAverageUnitLength) {
        this.regionAverageUnitLength = regionAverageUnitLength;
    }
}
