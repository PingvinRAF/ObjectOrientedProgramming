package rs.pingvin.d11.view.layout.config;

import javafx.scene.text.Font;

import java.text.DecimalFormat;

public class Config {

    public static final Font HEADING_FONT = Font.loadFont("file:resources/fonts/Roboto-Medium.ttf", 18);
    public static final Font REGULAR_FONT = Font.loadFont("file:resources/fonts/Roboto-Regular.ttf", 12);
    public static final Font MEDIUM_FONT  = Font.loadFont("file:resources/fonts/Roboto-Regular.ttf", 15);

    public static final String FORMAT(double number) {return new DecimalFormat("##.##").format(number);}

}
