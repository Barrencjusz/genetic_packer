package genetic.presenter.javafx.view.controls;

import org.springframework.stereotype.Component;

@Component
public class MousePositions {

    private double mouseNewX;

    private double mouseNewY;

    private double mouseOldX;

    private double mouseOldY;

    public double getMouseNewX() {
        return mouseNewX;
    }

    public void setMouseNewX(double mouseNewX) {
        this.mouseNewX = mouseNewX;
    }

    public double getMouseNewY() {
        return mouseNewY;
    }

    public void setMouseNewY(double mouseNewY) {
        this.mouseNewY = mouseNewY;
    }

    public double getMouseOldX() {
        return mouseOldX;
    }

    public void setMouseOldX(double mouseOldX) {
        this.mouseOldX = mouseOldX;
    }

    public double getMouseOldY() {
        return mouseOldY;
    }

    public void setMouseOldY(double mouseOldY) {
        this.mouseOldY = mouseOldY;
    }
}
