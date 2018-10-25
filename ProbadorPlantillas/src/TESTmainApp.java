import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class TESTmainApp {
	
	//--------------------- DATO A INTRODUCIR ------------------------------
	public static String programa = "Prueba";
	//----------------------------------------------------------------------
	//--------------------- Variables Programa -----------------------------
		//private final static Logger LOGGER = Logger.getLogger("mainApp");
		public static Map<String, String> datos = new HashMap<String, String>();
		static String letraPaso = programa.substring(5,6);
		static int pasoE = 0;
		static int pasoS = 1;
		static ArrayList<String> fichero = new ArrayList<String>();
		static ArrayList<String> pasos = new ArrayList<String>();
		static int lineNumber = 0;
		static int auxTot = 0;
		static int auxDecimal = 0;
		static int auxUnidad = 0;
		static TESTLectorPasos lectorPasos = new TESTLectorPasos();
		static TESTWriterPasos writerPasos = new TESTWriterPasos();
		static TESTAvisos  avisos = new TESTAvisos();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		Handler consoleHandler = new ConsoleHandler();
//		Handler fileHandler = new FileHandler("C:\\Cortex\\incidencias.log", false);
//		SimpleFormatter simpleFormatter = new SimpleFormatter();
//		fileHandler.setFormatter(simpleFormatter);
//		LOGGER.addHandler(consoleHandler);
//        LOGGER.addHandler(fileHandler);
//        consoleHandler.setLevel(Level.ALL);
//        fileHandler.setLevel(Level.ALL);
//		
		String linea, tipoPaso;
		boolean seguir = true, escribir = false;

//-------------------------------------Ficheros-------------------------------------------------		
	    FileReader ficheroPCL = new FileReader("C:\\Cortex\\Tester\\" + programa + ".txt");
	    BufferedReader lectorPCL = new BufferedReader(ficheroPCL);
	    
	    FileWriter ficheroCortex = new FileWriter("C:\\Cortex\\Tester\\Resultado\\" + programa.substring(0,6) + ".txt");
	    BufferedWriter writerCortex = new BufferedWriter(ficheroCortex);
//----------------------------------------------------------------------------------------------
	  //------------- Pasamos todo el paso a un arraylist	
	    while((linea = lectorPCL.readLine())!=null) {
	    	fichero.add(linea);	 
	    }
	    lectorPCL.close();
		// ------------ Aislamos el paso
	    while (seguir) {
		    tipoPaso = aislamientoDePaso();
		    //Verificaci�n aislamiento
		    System.out.println("------- El paso es:  -------------------");
		    for (int i = 0; i < pasos.size(); i++) {
		    	System.out.println(pasos.get(i));
		    }
		    System.out.println("----------------------------------------");
    		    
		    datos = lectorPasos.leerPaso(pasos);
		    System.out.println("------- Datos sacados del Paso:  -------");
		    System.out.println("Paso para el switch: " + tipoPaso);		
		    datos.forEach((k,v) -> System.out.println(k + "-" + v));
		    System.out.println("----------------------------------------");
		    
		    // ----- INSERTAR AQUI VUESTRO METODO ---------3
		    // NOMBRE PLANTILLA : "XXXX"
			//writerPasos.writeSORT(datos, letraPaso, pasoE, writerCortex);
		      writerPasos.writeJFTPSEND(datos, letraPaso, pasoE, writerCortex);
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
//			    	String numeroPaso = (pasoE < 10) ? "0" + String.valueOf(pasoE) : String.valueOf(pasoE) ;
//					String numeroPasoSiguiente = (pasoE + pasoAPaso < 10) ? "0" + String.valueOf(pasoE+pasoAPaso) : String.valueOf(pasoE+pasoAPaso);
			    	//Buscamos que la linea empiece por I+paso
//			    	if(fichero.get(i).startsWith(letraPaso + String.valueOf(numeroPaso))) {
//			    		inicio = i;
//			    	}
//			    	if(fichero.get(i).startsWith(letraPaso + String.valueOf(numeroPasoSiguiente))) {
//		    		fin = i;
//		    		i = fichero.size() + 1;
//		    	}
			    	if(fichero.get(i).matches("[" + letraPaso + "][" + auxDecimal + "-9][" + auxUnidad + "-9] (.*)")
			    			|| fichero.get(i).matches("[" + letraPaso + "][" + auxDecimal + 1 + "-9][0-9] (.*)")) {
			    		if (inicio == 0) {
			    			inicio= i;
			    			pasoE = Integer.parseInt(fichero.get(i).substring(1,3));
				    		auxDecimal = pasoE / 10;
				    		auxUnidad  = pasoE - auxDecimal * 10 + 1;
			    		}else {
			    			fin = i;
			    			i = fichero.size() + 1;
			    		}
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
					}
					if (fichero.get(inicio).contains("PGM=SOF07013")) {
							tipoPaso = "JBORRARF";
					}
					if (fichero.get(inicio).contains("PGM=IDCAMS")) {
						tipoPaso = "IDCAMS";
					}
				}
				
				lineNumber = fin;
				return tipoPaso;
			}
}