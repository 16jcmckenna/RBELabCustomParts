import eu.mihosoft.vrl.v3d.*;
import javafx.scene.text.Font;
import java.awt.GraphicsEnvironment;

Font font = new Font("Ubuntu",  30);
Font font_2 = new Font("Ubuntu",  10);
def f = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

for ( int i = 0; i < f.length; i++ )
{
 System.out.println(f[i]);
}
double offset=10

def letters= CSG.unionAll(TextExtrude.text((double)1.0,"Jona",font).collect{
	it.rotx(180)
	.toZMin()
})
def title= CSG.unionAll(TextExtrude.text((double)1.0,"2001",font_2).collect{
	it.rotx(180)
	.toZMin()
})

def cube = new Cube(	letters.getMaxX()+offset,// X dimention
			30,// Y dimention
			2//  Z dimention
			).toCSG()
			.toXMin()
			.toYMin()
			.toZMax()
			.movey(-5)

letters=	letters.movex(offset/2)
letters=  letters.movey(3)
title=   title.toXMax().movex(cube.getMaxX()-3)
title =  title.movey(-5)

return CSG.unionAll([letters,title,cube])
