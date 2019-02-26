import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;


public class RotatingRectangleMain {
	static Shape shape = new Rectangle(50 ,50); //можно вставить произвольную фигуру
	static AffineTransform transform;
	static double degAngle = 0, radAngle;
	
	private final static Color 
	BACKGROUND_COLOR = new Color(0xFFFFFF),
	SHAPE_COLOR = new Color(0x000000);
	
	public static void main(String[] args) {		
		JFrame frame = new JFrame(); //основное окно
		frame.setSize(300, 400);
		
		final JPanel panel = new JPanel() { //основная панель
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
				g.setColor(BACKGROUND_COLOR);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(SHAPE_COLOR);
				
				Graphics2D g2d = (Graphics2D)g;
				radAngle = degAngle * Math.PI / 180; //поворот в радианах
				transform = AffineTransform.getRotateInstance(radAngle, getWidth() / 2, getHeight() / 2);
				
				//сглаживание
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g2d.setTransform(transform);
				
				//перенос фигуры чтобы её центр совпадал с центром окна
				//(вращение также относительно центра окна)
				g2d.translate(getWidth() / 2 - shape.getBounds2D().getCenterX(), getHeight() / 2  - shape.getBounds2D().getCenterY());
				g2d.draw(shape);
			}
		};
		
		Timer timer = new Timer(5, new ActionListener() {
			//описание действия таймера 
			//можно отдельно добавить другие действия через timer.addActionListener(*действие*)
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(degAngle < 360)
					degAngle += 5;
				else
					degAngle = 5;
				
				//заново рисуем панель с изменённой фигурой
				panel.revalidate();
				panel.repaint();
			}
		});
		timer.start();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //завершение программы при закрытии окна
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
