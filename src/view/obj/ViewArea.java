package view.obj;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.Utilies;

import java.util.Objects;

public class ViewArea {

    public StringProperty type, height, wight, thetaDeg, thetaPercent, faitageValue, name, x, y;
    public boolean isOnRightSide;
    public double theta;

    public ViewArea(String type, String height, String wight, String x, String y, String thetaDeg, String thetaPercent, String faitageValue, String name, boolean isOnRightSide) {
        this.height = new SimpleStringProperty(height);
        this.wight = new SimpleStringProperty(wight);
        this.thetaDeg = new SimpleStringProperty(thetaDeg);
        this.thetaPercent = new SimpleStringProperty(thetaPercent);
        this.faitageValue = new SimpleStringProperty(faitageValue);
        this.name = new SimpleStringProperty(name);
        this.x = new SimpleStringProperty(x);
        this.y = new SimpleStringProperty(y);
        this.type = new SimpleStringProperty(type);
        this.isOnRightSide=isOnRightSide;
        if (!thetaDeg.equals("")){
            theta = Double.parseDouble(thetaDeg)*((Math.PI*2)/360);
        }else {
            if (!type.equals("RECTANGLE")){
                double radValue = Math.atan(Double.parseDouble(thetaPercent)/100);
                thetaDeg=(String.valueOf(Utilies.round3(radValue*(360/(Math.PI*2)))));
                theta = Double.parseDouble(thetaDeg)*((Math.PI*2)/360);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewArea viewArea = (ViewArea) o;
        return Objects.equals(height, viewArea.height) &&
                Objects.equals(wight, viewArea.wight) &&
                Objects.equals(thetaDeg, viewArea.thetaDeg) &&
                Objects.equals(thetaPercent, viewArea.thetaPercent) &&
                Objects.equals(faitageValue, viewArea.faitageValue) &&
                Objects.equals(name, viewArea.name) &&
                Objects.equals(x, viewArea.x) &&
                Objects.equals(y, viewArea.y) &&
                Objects.equals(type,viewArea.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, height, wight, thetaDeg, thetaPercent, faitageValue, name, x, y);
    }
}
