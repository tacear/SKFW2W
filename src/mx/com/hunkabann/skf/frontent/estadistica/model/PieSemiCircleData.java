package mx.com.hunkabann.skf.frontent.estadistica.model;

import org.zkoss.chart.model.DefaultPieModel;
import org.zkoss.zul.PieModel;

public class PieSemiCircleData {
	
	private static final PieModel model;
    static {
        model = (PieModel) new DefaultPieModel();
        model.setValue("Chrome", 73.86);
        model.setValue("Edge", 11.97);
        model.setValue("Firefox", 5.52);
        model.setValue("Safari", 2.98);
        model.setValue("Internet Explorer", 1.90);
        model.setValue("Other", 3.77);
    }
    
    public static PieModel getPieModel() {
        return model;
    }

}
