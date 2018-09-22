package view.obj;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class ViewBeam {

    public StringProperty height, wight, dependance, x;

    public ViewBeam(String height, String wight, String x, String dependance) {
        this.height = new SimpleStringProperty(height);
        this.wight = new SimpleStringProperty(wight);
        this.dependance = new SimpleStringProperty(dependance);
        this.x = new SimpleStringProperty(x);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewBeam viewBeam = (ViewBeam) o;
        return Objects.equals(height, viewBeam.height) &&
                Objects.equals(wight, viewBeam.wight) &&
                Objects.equals(dependance, viewBeam.dependance) &&
                Objects.equals(x, viewBeam.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, wight, dependance, x);
    }
}
