import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class TESTWriterPasos {

	TESTMetodosAux metodosAux = new TESTMetodosAux();
	int pasoS = -1;
	//------- INSERTAR NUESTRO CÓDIGO DE ESCRIBIR LA PLANTILLA
	
	
	
	
	
	
	
	//------------------------------------------------------------

	public void writeJFICHSAL(Map<String, String> datos, String numeroPaso, int i, String letraPaso,
			BufferedWriter writerCortex, int pasoE) throws IOException {
		// TODO Auto-generated method stub
		//----------------Fichero de plantilla JFICHENT--------------------------
	    FileReader ficheroJFICHSAL = new FileReader("C:\\Cortex\\Plantillas\\JFICHSAL.txt");
	    BufferedReader lectorJFICHSAL = new BufferedReader(ficheroJFICHSAL);	
	    //----------------Variables------------------------------------------
	    Map<String, String> infoFich = new HashMap<String, String>();
	    String linea, nombre;
	    int contadorLinea = 0;
	    
	    //----------------Método---------------------------------------------
	    nombre = datos.get("Salida" + String.valueOf(i));
	    infoFich = metodosAux.infoFichero(pasoE, letraPaso, nombre);	    for(int j = nombre.length(); j < 8; j++) {
			nombre += " ";
		}
	    while((linea = lectorJFICHSAL.readLine()) != null) {
	    	contadorLinea ++;
	    	switch (contadorLinea) {
	    	case 3:
	    		linea = linea.replace("DDNAME--", nombre);
	    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.&FAAMMDDV", infoFich.get("DSN"));
	    		break;
	    	case 5:
	    		if(infoFich.containsKey("MGMTCLAS")) {
	    			linea = linea.replace("EXLIXXXX", infoFich.get("MGMTCLAS"));
	    		}else {
	    			linea = linea.replace("// ", "//*");
	    		}
	    		break;
	    	case 6:
	    		linea = linea.replace("(LONGREG,(KKK,KK))", infoFich.get("Definicion"));
	    		break;
	    	case 9:
	    		linea = linea.replace("DDNAME--", nombre);
	    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.XP", infoFich.get("DSN"));
	    		break;
	    	case 11:
	    		linea = linea.replace("(LONGREG,(KKK,KK))", infoFich.get("Definicion"));
	    		break;
	    	case 14:
	    		linea = linea.replace("DDNAME--", nombre);
	    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.&FAAMMDDV", infoFich.get("DSN"));
	    		break;
	    	case 16:
	    		if(infoFich.containsKey("MGMTCLAS")) {
	    			linea = linea.replace("EXLIXXXX", infoFich.get("MGMTCLAS"));
	    		}else {
	    			linea = linea.replace("// ", "//*");
	    		}
	    		break;
	    	case 17:
	    		linea = linea.replace("(LONGREG,(KKK,KK))", infoFich.get("Definicion"));
	    		break;
	    	default:
				break;
	    	}
	    	
	    	if(infoFich.get("DISP").equals("NEW") && contadorLinea > 6) {
	    		//No escribimos el resto de ficheros (mod, temp)
	    		linea = "";
	    	}
	    	if(infoFich.get("DISP").equals("MOD") && contadorLinea < 12) {
	    		//No escribimos el resto de ficheros (new, temp)
	    		linea = "";
	    	}
	    	if(infoFich.get("DISP").equals("TEMP") && (contadorLinea < 7 || contadorLinea > 11)) {
	    		//No escribimos el resto de ficheros (new, mod)
	    		linea = "";
	    	}   
	    		    	
	    	if (!linea.equals("")) {
		    	System.out.println("Escribimos: " + linea);
		    	writerCortex.write(linea);
		    	writerCortex.newLine();
	    	}
	    }
	    lectorJFICHSAL.close();	 
	}
	
	public void writeJFICHENT(Map<String, String> datos, String numeroPaso, int i, String letraPaso, BufferedWriter writerCortex, int pasoE) throws IOException {
		// TODO Auto-generated method stub
		//----------------Fichero de plantilla JFICHENT--------------------------
	    FileReader ficheroJFICHENT = new FileReader("C:\\Cortex\\Plantillas\\JFICHENT.txt");
	    BufferedReader lectorJFICHENT = new BufferedReader(ficheroJFICHENT);	
	    //----------------Variables------------------------------------------
	    String linea, nombre;
	    int contadorLinea = 0;
	    
	    //----------------Método---------------------------------------------
	    
	    Map<String, String> infoFich = new HashMap<String, String>();
	    nombre = datos.get("Entrada" + String.valueOf(i));
		for(int j = nombre.length(); j < 8; j++) {
			nombre += " ";
		}
	    infoFich = metodosAux.infoFichero(pasoE, letraPaso, nombre);
	    
	    while((linea = lectorJFICHENT.readLine()) != null) {
	    	contadorLinea ++;
	    	if(i > 1 && contadorLinea == 1) {
	    		//No queremos que vuelva a escribir la primera línea de la plantilla
	    		continue;
	    	}
	    	switch (contadorLinea) {
	    	case 2:
	    		linea = linea.replace("DDNAME--", nombre);
	    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.&FAAMMDDV", "Z." + infoFich.get("DSN"));
	    		break;
	    	default:
				break;
	    	}
	    	System.out.println("Escribimos: " + linea);
	    	writerCortex.write(linea);
	    	writerCortex.newLine();
	    }
	    lectorJFICHENT.close();	 
	}
	
	public void writeJBORRAF(Map<String, String> datos, String numeroPaso, int i, String letraPaso, BufferedWriter writerCortex, int pasoE) throws IOException {
		// TODO Auto-generated method stub
		//----------------Fichero de plantilla JBORRAF--------------------------
	    FileReader ficheroJBORRAF = new FileReader("C:\\Cortex\\Plantillas\\JBORRAF.txt");
	    BufferedReader lectorJBORRAF = new BufferedReader(ficheroJBORRAF);	
	    //----------------Variables------------------------------------------
	    String linea, nombre;
	    int contadorLinea = 0;
	    Map<String, String> infoFich = new HashMap<String, String>();
	    
	    //----------------Método---------------------------------------------
	    nombre = datos.get("Borrar" + String.valueOf(i));
	    infoFich = metodosAux.infoFichero(pasoE, letraPaso, nombre);
	    
	    while((linea = lectorJBORRAF.readLine()) != null) {
	    	contadorLinea ++;
	    	if(i > 1 && contadorLinea == 1) {
	    		//No queremos que vuelva a escribir la primera línea de la plantilla
	    		continue;
	    	}
	    	switch (contadorLinea) {
	    	case 2:
	    		if(i < 10) {
	    			linea = linea.replace("//---D-", "//" + letraPaso + numeroPaso + "D" + String.valueOf(i));
	    		}else {
	    			linea = linea.replace("//---D- ", "//" + letraPaso + numeroPaso + "D" + String.valueOf(i));
	    		}
	    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.&FAAMMDDV", infoFich.get("DSN"));
	    		break;
	    	default:
				break;
	    	}
	    	System.out.println("Escribimos: " + linea);
	    	writerCortex.write(linea);
	    	writerCortex.newLine();
	    }
	    lectorJBORRAF.close();	 
	}

	public void writeComments(Map<String, String> datos, BufferedWriter writerCortex) throws IOException {
		for (int i = 1; datos.containsKey("Comentario" + String.valueOf(i)); i++) {
	    	writerCortex.write("//" + datos.get("Comentario" + String.valueOf(i)));
	    	writerCortex.newLine();
	    }
	}

	public void writeJFTPSEND(Map<String, String> datos, String letraPaso, int pasoE, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
		//----------------Fichero de plantilla JJMAILTXT--------------------------
	    FileReader ficheroJFTPSEND = new FileReader("C:\\Cortex\\Plantillas\\JFTPSEND.txt");
	    BufferedReader lectorJFTPSEND = new BufferedReader(ficheroJFTPSEND);	
	    //----------------Variables------------------------------------------
	    String linea;
	    pasoS += 2;
	    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
	    int contadorLinea = 0;
	    Map<String, String> infoFTP = new HashMap<String, String>();
	    //----------------Método---------------------------------------------
	    
	    while((linea = lectorJFTPSEND.readLine()) != null) {
	    	contadorLinea ++;
	    	switch (contadorLinea) {
	    	case 2:
	    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
				break;
	    	case 3:
	    		linea = linea.replace("DES=destino,", "DES=" + datos.get("DES") + ",");
				break;
	    	case 4:
	    		//CAMBIAR!!!!
	    		linea = linea.replace("HOST=,", "HOST=" + datos.get("HOST") + ",");
	    		break;
	    	case 5:
	    		linea = linea.replace("FIT=nomfichred", "HOST=" + datos.get("HOST") + ",");
	    		break;
			default:
				break;
			}
	    	System.out.println("Escribimos: " + linea);
	    	writerCortex.write(linea);
	    	writerCortex.newLine();
	    }
	    lectorJFTPSEND.close();		
	    writeComments(datos, writerCortex);
	}

	public void writeJFTPREB(Map<String, String> datos, String letraPaso, int pasoE, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
				//----------------Fichero de plantilla JFTPREB--------------------------
			    FileReader ficheroJFTPREB = new FileReader("C:\\Cortex\\Plantillas\\JFTPREB.txt");
			    BufferedReader lectorJFTPREB = new BufferedReader(ficheroJFTPREB);	
			    //----------------Variables------------------------------------------
			    String linea;
			    pasoS += 2;
			    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
			    int contadorLinea = 0, spaces = 0;
			    Map<String, String> infoFtpReb = new HashMap<String, String>();
			    //----------------Método---------------------------------------------
			    
			    infoFtpReb = metodosAux.infoFtpReb(pasoE, letraPaso);
			    //----------------Método---------------------------------------------
			    while((linea = lectorJFTPREB.readLine()) != null) {
			    	contadorLinea ++;
			    	switch (contadorLinea) {
			    	case 2:
			    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
			    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.&FAAMMDDV", infoFtpReb.get("DSN"));
						break;
			    	case 3:
			    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
			    		break;
			    	case 4:
			    		//Calculamos cuantos espacios hay que añadir detrás para que no se muevan los comentarios de posición
			    		StringBuffer orig = new StringBuffer("ORIG=" + datos.get("ORIG") + ",");
			    		spaces = 39 - orig.length();
			    		for (int j = 0; j < spaces; j++) {
			    			orig.append(" ");
			    		}
			    		linea = linea.replace("ORIG=SERVIDOR_ORIGEN,                  ", orig);
						break;
			    	case 5:
			    	    StringBuffer forig = new StringBuffer("FIT=" + datos.get("FORIG").replace("*", ".TXT"));
			    	    if(datos.containsKey("DIR")) {
			    	    	forig.append(",");
			    	    }
			    	    spaces = 39 - forig.length();  		
			    		for (int j = 0; j < spaces; j++) {
			    			forig.append(" ");
			    		}
			    		linea = linea.replace("FIT=NOMFICHRED.TXT                     ", forig);
			    		break;
			    	case 6:
			    		if(datos.containsKey("DIR")) {
			    			linea = linea.replace("//*", "// "); 
			    			StringBuffer dir = new StringBuffer("DIR='" + datos.get("DIR") + "'");
			    			spaces = 38 - dir.length();  		
				    		for (int j = 0; j < spaces; j++) {
				    			dir.append(" ");
				    		}
				    		linea = linea.replace("DIR=XXX                               ", dir);
			    		}
			    		break;
			    	case 7:
			    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.&FAAMMDDV", infoFtpReb.get("DSN"));
			    		break;
			    	case 9:
			    		if(infoFtpReb.containsKey("MGMTCLAS")) {
			    			linea = linea.replace("//*", "// ");
			    			linea = linea.replace("EXLIXXXX", infoFtpReb.get("MGMTCLAS"));
			    		}
			    		break;
			    	case 10:
			    		linea = linea.replace("(LONGREG,(KKK,KK))", infoFtpReb.get("Definicion"));
			    	case 11:
			    		linea = linea.replace("LONGREG", infoFtpReb.get("LRECL"));
					default:
						break;
					}
			    	System.out.println("Escribimos: " + linea);
			    	writerCortex.write(linea);
			    	writerCortex.newLine();
			    }
			    lectorJFTPREB.close();		
			    writeComments(datos, writerCortex);
			}
}
