@Grab('com.xlson.groovycsv:groovycsv:1.1')
import static com.xlson.groovycsv.CsvParser.parseCsv
import eu.mihosoft.vrl.v3d.*;
import javafx.scene.text.Font;
import java.awt.GraphicsEnvironment;

Font font = new Font("Arial",  25);
Font font_2 = new Font("Arial",  10);
def f = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

for ( int i = 0; i < f.length; i++ )
{
 System.out.println(f[i]);
}
double offset=15
double minSize = 45.5
// 300x
def Names = ["Everett",  "Nick",    "Leif",    "Ryan",    "TC",      "Zhaoyuan"] as String[];
def course = ["300x SA", "300x SA", "300x SA", "300x SA", "300x TA", "300x TA"] as String[]
//// 200x
//def Names = ["Mia",      "Nikhil",  "Jesse",   "Peter",  "James",    "Matt"] as String[];
//def course = ["200x SA", "200x SA", "200x SA", "200x SA", "200x TA", "200x TA"] as String[]
//// 1001
//def Names = ["Tom",      "Kristen", "Tyler",   "Maria",   "Emily",   "Ethan",   "Alex",    "Alexandra"] as String[];
//def course = ["1001 SA", "1001 SA", "1001 SA", "1001 SA", "1001 SA", "1001 TA", "1001 TA", "1001 TA"] as String[]
// Others
//def Names = ["Max",      "Jess",    "Kevin",   "Alex"] as String[];
//def course = ["Lab Dad", "Lab Mom", "Lab Manager", "Lab Manager" ] as String[]
def index = 0;
def cutout 
def letters
def title
def cube
def nameTag
def Made  = [] as String;
String fileName
for(String name:Names){

cutout = new Cube(45.5, 13.7, 1.5).toCSG()

letters= CSG.unionAll(TextExtrude.text((double)1.0,name,font).collect{
	it.rotx(180)
	.toZMin()
})
title= CSG.unionAll(TextExtrude.text((double)1.0,course[index],font_2).collect{
	it.rotx(180)
	.toZMin()
})

if((letters.getMaxX())<minSize){
	 cube = new Cube(	45.5+offset,// X dimention
			30,// Y dimention
			3//  Z dimention
			).toCSG()
			.toXMin()
			.toYMin()
			.toZMax()
			.movey(-7)

}
else{
	 cube = new Cube(	letters.getMaxX()+offset,// X dimention
			30,// Y dimention
			3//  Z dimention
			).toCSG()
			.toXMin()
			.toYMin()
			.toZMax()
			.movey(-7)

}


letters=	letters.movex(((cube.getMaxX()+cube.getMinX())/2)-((letters.getMaxX()+letters.getMinX())/2))
letters=  letters.movey(3)
title=   title.toXMax().movex(cube.getMaxX()-3)
title =  title.movey(-5)

cutout = cutout.toXMin().toYMin().movez(cube.getMinZ()).movex(((cube.getMinX()+cube.getMaxX())/2)-((cutout.getMinX()+cube.getMaxX())/2)-3.5).movey(((cube.getMinY()+cube.getMaxY())/2)-((cutout.getMinY()+cube.getMaxY())/2)+2.5)
cube = cube.difference(cutout);

if(Made.contains(name)){
	 fileName = ScriptingEngine.getWorkspace().getAbsolutePath()+"/nametags/"+name+index+".stl";
}
else{
	 fileName = ScriptingEngine.getWorkspace().getAbsolutePath()+"/nametags/"+name+".stl";
}
index++;
nameTag = CSG.unionAll([letters,title,cube]);
FileUtil.write(Paths.get(fileName),
		nameTag.toStlString());
}



return CSG.unionAll([letters,title,cube])
