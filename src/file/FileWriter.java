package file;

import home.Area;
import home.Beam;
import home.Montant;
import home.Window;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileWriter {

    public FileWriter(){}

    public void writeFile(ArrayList<Area> areas, String path, String name) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Area area : areas){
            lines.add(area.name);
            lines.add("CONTOURS\n");
            for (Montant m :  area.outlinesMontants.values()){
                lines.add(m.printMontant());
            }
            lines.add("\nPOUTRES");
            for (Beam b : area.beams){
                lines.add("\n" + b.ref + "\n");
                for (Montant m : b.leftMontant){
                    lines.add(m.printMontant());
                }
                for(LinkedList<Montant> l : b.midsMontants){
                    for(Montant m : l){
                        lines.add(m.printMontant());
                    }
                }
                for (Montant m : b.rightMontant){
                    lines.add(m.printMontant());
                }
            }
            lines.add("\nOUVERTURES");
            for (Window w : area.windows){
                lines.add("\n" + w.name + "\n");
                for(LinkedList<Montant> l : w.montantsAfterCut.values()){
                    for(Montant m : l){
                        lines.add(m.printMontant());
                    }
                }
            }
            lines.add("\nAUTRE\n");
            for (Montant m : area.midMontant){
                lines.add(m.printMontant());
            }
        }
        Path file;
        if(!name.equals("") && !name.equals(".tsv")){
            file = Paths.get(path+ name);
        }else{
            file = Paths.get(path+ "nom_de_fichier_par_default.tst");
        }
        Files.write(file, lines, Charset.forName("UTF-8"));
    }
}
