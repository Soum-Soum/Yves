package file;

import data.DATACONTAINER;
import home.Area;
import home.Beam;
import home.Montant;
import home.Window;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import view.obj.SaveView;
import view.obj.ViewArea;
import view.obj.ViewBeam;
import view.obj.ViewWindow;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileManager {

    public FileManager(){}

    public void writeSaveFile(String path, ObservableList<ViewArea> areas, ObservableList<ViewWindow> windows, ObservableList<ViewBeam> beams) throws IOException {
        JSONArray areaJsonArray = new JSONArray();
        JSONArray windowJsonArray = new JSONArray();
        JSONArray beamJsonArray = new JSONArray();
        JSONArray metadataArray = new JSONArray();

        JSONObject metadataJsonObject = new JSONObject();
        metadataJsonObject.put("MONTANTDIST", DATACONTAINER.MONTANTDIST);
        metadataJsonObject.put("MONTANTWIDTH", DATACONTAINER.MONTANTWIDTH);
        metadataJsonObject.put("TRAVERSEWIDTH", DATACONTAINER.TRAVERSEWIDTH);
        metadataJsonObject.put("OBJ_TYPE", "METADATA");
        metadataArray.add(metadataJsonObject);
        for (ViewArea area : areas){
            JSONObject jsonArea = fillCommonVariables(area.name, area.type, area.x, area.y, area.theta, area.thetaDeg, area.thetaPercent, area.height, area.wight, area.isOnRightSide, "AREA");
            jsonArea.put("FAITAGEVALUE",area.faitageValue.getValue());
            areaJsonArray.add(jsonArea);
        }
        for (ViewWindow window : windows){
            JSONObject jsonWindow = fillCommonVariables(window.name, window.type, window.x, window.y, window.theta, window.thetaDeg, window.thetaPercent, window.height,
                    window.wight, window.isOnRightSide, "WINDOWS");
            windowJsonArray.add(jsonWindow);
        }
        for (ViewBeam beam: beams){
            JSONObject jsonBeam = new JSONObject();
            jsonBeam.put("X",beam.x.getValue());
            jsonBeam.put("OBJ_TYPE", "BEAM");
            jsonBeam.put("HEIGHT",beam.height.getValue());
            jsonBeam.put("WIDTH",beam.wight.getValue());
            jsonBeam.put("DEPENDANCE", beam.dependance.getValue());
            beamJsonArray.add(jsonBeam);
        }
        JSONObject finalObj = new JSONObject();
        finalObj.put("AREA", areaJsonArray);
        finalObj.put("WINDOWS", windowJsonArray);
        finalObj.put("BEAMS", beamJsonArray);
        finalObj.put("METADATA", metadataArray);

        java.io.FileWriter fileWriter = new java.io.FileWriter(path + ".json");
        fileWriter.write(finalObj.toJSONString());
        fileWriter.flush();
    }

    public SaveView readSaveFile(String path) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        LinkedList<ViewArea> areas = new LinkedList<>();
        LinkedList<ViewWindow> windows = new LinkedList<>();
        LinkedList<ViewBeam> beams = new LinkedList<>();
        Double montantDist=0.d;
        Double montantWidth=0.d;
        Double traverseWidth=0.d;
        FileReader reader = new FileReader(path);
        JSONObject saveObj = (JSONObject) jsonParser.parse(reader);
        Iterator saveObjIter = saveObj.values().iterator();
        while (saveObjIter.hasNext()){
            JSONArray array = (JSONArray) saveObjIter.next();
            Iterator arrayIter = array.iterator();
            while (arrayIter.hasNext()){
                JSONObject curentObj = (JSONObject) arrayIter.next();
                switch ((String) curentObj.get("OBJ_TYPE")){
                    case "AREA" :
                        areas.add(new ViewArea(curentObj.get("TYPE").toString(),curentObj.get("HEIGHT").toString(),curentObj.get("WIDTH").toString(),curentObj.get("X").toString(),
                                curentObj.get("Y").toString(),(String) curentObj.get("THETADEG"),curentObj.get("THETAPERCENT").toString(),curentObj.get("FAITAGEVALUE").toString(),
                                curentObj.get("NAME").toString(), Boolean.getBoolean(curentObj.get("ISONRIGHTSIDE").toString())));
                        break;
                    case "BEAM" :
                        beams.add(new ViewBeam(curentObj.get("HEIGHT").toString(),curentObj.get("WIDTH").toString(),curentObj.get("X").toString(),
                                curentObj.get("DEPENDANCE").toString()));
                        break;
                    case "WINDOWS":
                        System.out.println(curentObj.get("ISONRIGHTSIDE").toString());
                        windows.add(new ViewWindow(curentObj.get("TYPE").toString(),curentObj.get("HEIGHT").toString(),curentObj.get("WIDTH").toString(),curentObj.get("X").toString(),
                                curentObj.get("Y").toString(),curentObj.get("THETADEG").toString(),curentObj.get("THETAPERCENT").toString(),
                                curentObj.get("NAME").toString(), Boolean.parseBoolean(curentObj.get("ISONRIGHTSIDE").toString())));
                        System.out.println(windows.get(0).isOnRightSide);
                        break;
                    case "METADATA":
                        montantDist = Double.parseDouble(curentObj.get("MONTANTDIST").toString());
                        montantWidth = Double.parseDouble(curentObj.get("MONTANTWIDTH").toString());
                        traverseWidth = Double.parseDouble(curentObj.get("TRAVERSEWIDTH").toString());
                        break;
                }
            }
        }

        return new SaveView(areas,windows,beams,montantDist,montantWidth,traverseWidth);
    }

    private JSONObject fillCommonVariables(StringProperty name, StringProperty type, StringProperty x, StringProperty y, double theta, StringProperty thetaDeg,
                                           StringProperty thetaPercent, StringProperty height, StringProperty wight, Boolean isOnRightSide, String jsonObjType) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("OBJ_TYPE", jsonObjType);
        jsonObj.put("NAME", name.getValue());
        jsonObj.put("TYPE", type.getValue());
        jsonObj.put("X", x.getValue());
        jsonObj.put("Y", y.getValue());
        jsonObj.put("THETA", theta);
        jsonObj.put("THETADEG", thetaDeg.getValue());
        jsonObj.put("THETAPERCENT", thetaPercent.getValue());
        jsonObj.put("HEIGHT", height.getValue());
        jsonObj.put("WIDTH", wight.getValue());
        jsonObj.put("ISONRIGHTSIDE",isOnRightSide.toString());
        return jsonObj;
    }

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
            file = Paths.get(path+ name + ".tsv");
        }else{
            file = Paths.get(path+ "NOM_PAR_DEFAULT.tsv");
        }
        Files.write(file, lines, Charset.forName("UTF-8"));
    }
}
