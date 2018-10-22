import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TESTmainApp {
	
	//--------------------- DATO A INTRODUCIR ------------------------------
	public static String programa = "Prueba";
	//----------------------------------------------------------------------
	public static Map<String, String> datos = new HashMap<String, String>();
	static String letraPaso = programa.substring(5,6);
	static int paso = -2;
	static ArrayList<String> fichero = new ArrayList<String>();
	static ArrayList<String> pasos = new ArrayList<String>();
	static int lineNumber = 0;
	static TESTLectorPasos lectorPasos = new TESTLectorPasos();
	static TESTWriterPasos writerPasos = new TESTWriterPasos();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String extension = ".txt";
		String linea, tipoPaso;
		boolean seguir = true;
//-------------------------------------Ficheros-------------------------------------------------		
	    FileReader ficheroPCL = new FileReader("C:\\Cortex\\Tester\\" + programa + extension);
	    BufferedReader lectorPCL = new BufferedReader(ficheroPCL);
	    
	    FileWriter ficheroCortex = new FileWriter("C:\\Cortex\\Tester\\Resultado\\" + programa.substring(0,6) + ".txt");
	    BufferedWriter writerCortex = new BufferedWriter(ficheroCortex);
//----------------------------------------------------------------------------------------------
	  //------------- Pasamos todo el fichero a un arraylist	
	    while((linea = lectorPCL.readLine())!=null) {
	    	fichero.add(linea);	 
	    }
	    lectorPCL.close();
		// ------------ Aislamos el paso
	    while (seguir) {
		    tipoPaso = aislamientoDePaso();
		    //Verificación aislamiento
		    System.out.println("------- El paso es:  -------------------");
		    for (int i = 0; i < pasos.size(); i++) {
		    	System.out.println(pasos.get(i));
		    }
		    System.out.println("----------------------------------------");
		    		    		    
		    // ----- INSERTAR AQUI VUESTRO METODO ---------3
		    // NOMBRE PLANTILLA : "XXXX"
		    datos = lectorPasos.leerPaso(pasos);
		    System.out.println("------- Datos sacados del Paso:  -------");
		    datos.forEach((k,v) -> System.out.println(k + "-" + v));
		    System.out.println("----------------------------------------");
			//writerPasos.writeSORT(datos, letraPaso, paso, writerCortex);
		    // --------------------------------------------
		    if (lineNumber + 1 == fichero.size()) {
				seguir = false;
			}
	    }
	    writerCortex.close();
	}

// -----------------------------------IGNORAR--------------------------
	private static String aislamientoDePaso() {
		// Si se acaba hacer un booleano de fin fichero
				int inicio = 0, fin = 0, index = 0;
				String tipoPaso = "";
				
				for(int i = lineNumber; i < fichero.size(); i++) {
			    	String numeroPaso = (paso < 10) ? "0" + String.valueOf(paso) : String.valueOf(paso) ;
					String numeroPasoSiguiente = (paso + 2 < 10) ? "0" + String.valueOf(paso+2) : String.valueOf(paso+2);
			    	//Buscamos que la linea empiece por I+paso
			    	if(fichero.get(i).startsWith(letraPaso + String.valueOf(numeroPaso))) {
			    		inicio = i;
			    	}
			    	if(fichero.get(i).startsWith(letraPaso + String.valueOf(numeroPasoSiguiente))) {
			    		fin = i;
			    		i = fichero.size() + 1;
			    	}
			    	if(i == 0) {
			    		inicio = 0;
			    		tipoPaso = "Inicio";
			    	}
			    	if(i + 1 == fichero.size()) {
			    		fin = i;
			    		i = fichero.size() + 1;
			    	}
			    }
				pasos.clear();
				
				for(int i = inicio; i < fin; i++) {
					String linea = fichero.get(i);
					if (linea.length() >= 71) {
						linea = linea.substring(0, 71);
					}
					for (int j = i + 1; j < fichero.size() && fichero.get(j).startsWith(" "); j++) {
						if(fichero.get(j).endsWith("X")) {
							linea = linea + fichero.get(j).substring(0, fichero.get(j).length()-1).trim();
						}else {
							linea = linea + fichero.get(j).trim();
						}
						i = j;
					}
					pasos.add(linea);
				}
				
				index = fichero.get(inicio).indexOf("PATTERN");
				if (index != -1) {
					for(int i = index; i < fichero.get(inicio).trim().length(); i++) {
						if(fichero.get(inicio).charAt(i) == ',' || i + 1 == fichero.get(inicio).trim().length()) {
							tipoPaso = fichero.get(inicio).substring(index + 8, i);
							i = 80;
						}				
					}
				}else {
					if (fichero.get(inicio).contains(" SORT ")) {
						tipoPaso = "SORT";
					}else {
						
					}
				}
				
				lineNumber = fin;
				return tipoPaso;
			}
}
