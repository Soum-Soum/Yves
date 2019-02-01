package data;

public class DATACONTAINER {

    public static double MONTANTWITH = 5.;
    public static double MONTANTDIST = 60.;
    public static double TRAVERSEWIDTH = 20.;
    public static int MONTANTCOUNT = 0;

    public static void SETDATACONTAINERVALUES(double montantWidth, double montantDist, double traverseWidth){
        DATACONTAINER.MONTANTWITH=montantWidth;
        DATACONTAINER.MONTANTDIST=montantDist;
        DATACONTAINER.TRAVERSEWIDTH=traverseWidth;
    }

}
