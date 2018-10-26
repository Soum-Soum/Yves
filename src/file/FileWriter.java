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
import java.util.List;

public class FileWriter {

    public FileWriter(){}

    public void writeFile(ArrayList<Area> areas, String path) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Area area : areas){
            lines.add(area.name);
            lines.add("CONTOURS\n");
            for (Montant m :  area.outlinesMontants){
                lines.add(m.printMontant());
            }
            lines.add("\nPOUTRES");
            for (Beam b : area.beams){
                lines.add("\n" + b.ref + "\n");
                for(Montant m : b.montants){
                    lines.add(m.printMontant());
                }
            }
            lines.add("\nOUVERTURES");
            for (Window w : area.windows){
                lines.add("\n" + w.ref + "\n");
                for(Montant m : w.montants){
                    lines.add(m.printMontant());
                }
            }
            lines.add("\nAUTRE\n");
            for (Montant m : area.midMontant){
                lines.add(m.printMontant());
            }
        }
        Path file = Paths.get(path+"the-file-name.tsv");
        Files.write(file, lines, Charset.forName("UTF-8"));
    }
}
