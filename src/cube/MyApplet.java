package cube;

import java.applet.Applet;

public class MyApplet extends Applet {

	public static void main(String[] args) {
		MyApplet app = new MyApplet();
		Model model = new Model();
		app.add(model.getContentPane());
//		model.setVisible(true);

	}

}
