package view.obj;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class ViewArea {

    public StringProperty type, height, wight, thetaDeg, thetaPercent, faitageValue, name, x, y;

    public ViewArea(String type, String height, String wight, String x, String y, String thetaDeg, String thetaPercent, String faitageValue, String name) {
        this.height = new SimpleStringProperty(height);
        this.wight = new SimpleStringProperty(wight);
        this.thetaDeg = new SimpleStringProperty(thetaDeg);
        this.thetaPercent = new SimpleStringProperty(thetaPercent);
        this.faitageValue = new SimpleStringProperty(faitageValue);
        this.name = new SimpleStringProperty(name);
        this.x = new SimpleStringProperty(x);
        this.y = new SimpleStringProperty(y);
        this.type = new SimpleStringProperty(type);

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
