package view.obj;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class ViewWindows {

    public StringProperty type,height, wight, thetaDeg, thetaPercent, name, x, y;

    public ViewWindows(String type, String height, String wight, String x, String y, String thetaDeg, String thetaPercent, String name) {
        this.type = new SimpleStringProperty(type);
        this.height = new SimpleStringProperty(height);
        this.wight = new SimpleStringProperty(wight);
        this.thetaDeg = new SimpleStringProperty(thetaDeg);
        this.thetaPercent = new SimpleStringProperty(thetaPercent);
        this.name = new SimpleStringProperty(name);
        this.x = new SimpleStringProperty(x);
        this.y = new SimpleStringProperty(y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewWindows that = (ViewWindows) o;
        return Objects.equals(height, that.height) &&
                Objects.equals(wight, that.wight) &&
                Objects.equals(thetaDeg, that.thetaDeg) &&
                Objects.equals(thetaPercent, that.thetaPercent) &&
                Objects.equals(name, that.name) &&
                Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, wight, thetaDeg, thetaPercent, name, x, y);
    }
}
