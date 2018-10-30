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
	static int pasoS = -1;
	public static Map<String, String> histPasos = new HashMap<String, String>();
	//------- INSERTAR NUESTRO C�DIGO DE ESCRIBIR LA PLANTILLA
	
	
	
	
	
	
	
	//------------------------------------------------------------
	public void writeDB2(Map<String, String> datos, String letraPaso, int pasoE, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
		//----------------Fichero de plantilla DB2--------------------------
	    FileReader ficheroDB2 = new FileReader("C:\\Cortex\\Plantillas\\JDB2.txt");
	    BufferedReader lectorDB2 = new BufferedReader(ficheroDB2);	
	    //----------------Variables------------------------------------------
	    String linea;
	    pasoS += 2;
	    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
	    histPasos.put(numeroPaso, "DB2");
	    int contadorLinea = 0;
	    
	    
	    //----------------M�todo---------------------------------------------
	    histPasos.put(numeroPaso, "DB2");
	    //--------------- Miramos si hay archivos para borrar antes de ejecutar:
	    for (int i = 1; datos.containsKey("Borrar" + String.valueOf(i)); i++) {
	    	if(!datos.get("Borrar" + String.valueOf(i)).equals("No")) {
	    		writeJBORRAF(datos, numeroPaso, i, letraPaso, writerCortex, pasoE);
	    	}
	    }
	    
	    //---------------- Escribimos la plantilla JDB2
	    while((linea = lectorDB2.readLine()) != null) {
	    	contadorLinea ++;
	    	switch (contadorLinea) {
	    	case 2:
	    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
	    		linea = linea.replace("NOMPROGR", datos.get("PGM"));
	    		if(!datos.containsKey("PARDB2")) {
	    			linea = linea.replace(datos.get("PGM") + ",", datos.get("PGM"));
	    		}
				break;
	    	case 3:
	    		if(!datos.containsKey("PARDB2")) {
	    			continue;
	    		}
	    		linea = linea.replace("&VAR1-&VAR2-..." , datos.get("PARDB2"));
	    		if (metodosAux.checkLiteralesPARDB2(datos.get("PARDB2"))) {
	    			writerCortex.write("****** LITERALES EN LOS PARAMETROS DEL PROGRAMA *****");
	    	    	writerCortex.newLine();
	    	    	TESTAvisos.LOGGER.log(Level.INFO, letraPaso + String.valueOf(pasoE) + " // Literales en el programa: "
	    	    			+ datos.get("PARDB2"));
	    		}
			default:
				break;
	    	}
	    	System.out.println("Escribimos: " + linea);
	    	writerCortex.write(linea);
	    	writerCortex.newLine();
	    }
	    lectorDB2.close();
	    
//--------------- Miramos si hay ficheros de entrada:
	    for (int i = 1; datos.containsKey("Entrada" + String.valueOf(i)); i++) {
	    	writeJFICHENT(datos, numeroPaso, i, letraPaso, writerCortex, pasoE);
	    }
//--------------- Miramos si hay ficheros de Salida:
	    for (int i = 1; datos.containsKey("Salida" + String.valueOf(i)); i++) {
	    	writeJFICHSAL(datos, numeroPaso, i, letraPaso, writerCortex, pasoE);
	    }
//--------------- Miramos si hay reportes para informar:
	    writeReports(datos, writerCortex, pasoE, letraPaso);
//--------------- Miramos si hay Comentarios:
	    writeComments(datos, writerCortex);
	}

	private void writeReports(Map<String, String> datos, BufferedWriter writerCortex, int pasoE, String letraPaso) throws IOException {
		// TODO Auto-generated method stub
		Map<String, String> infoRep = new HashMap<String, String>();
		for (int i = 1; datos.containsKey("Reporte" + String.valueOf(i)); i++) {
			infoRep = metodosAux.infoReportes(datos.get("Reporte" + String.valueOf(i)), pasoE, letraPaso);
			writerCortex.write("//*--REPORT-----------------------------------------------------------");
	    	writerCortex.newLine();
			writerCortex.write(infoRep.get("ReportKey"));
	    	writerCortex.newLine();
			infoRep.clear();
		}
	}

	public void writeComments(Map<String, String> datos, BufferedWriter writerCortex) throws IOException {
		for (int i = 1; datos.containsKey("Comentario" + String.valueOf(i)); i++) {
	    	writerCortex.write("//" + datos.get("Comentario" + String.valueOf(i)));
	    	writerCortex.newLine();
	    }
	}

	private void writeComments(Map<String, String> datos, BufferedWriter writerCortex, String tipo, int numFich) throws IOException {
		// TODO Auto-generated method stub
		for (int i = 1; datos.containsKey(tipo + String.valueOf(numFich) + String.valueOf(i)); i++) {
			System.out.println("Escribimos: " + "//" + datos.get(tipo + String.valueOf(numFich) + String.valueOf(i)));
	    	writerCortex.write("//" + datos.get(tipo + String.valueOf(numFich) + String.valueOf(i)));
	    	writerCortex.newLine();
	    }
	}
	
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
	    
	    //----------------M�todo---------------------------------------------
	    
	    writeComments(datos, writerCortex, "ComFichS", i);
	    
	    nombre = datos.get("Salida" + String.valueOf(i));
	    infoFich = metodosAux.infoFichero(pasoE, letraPaso, nombre);
	    for(int j = nombre.length(); j < 8; j++) {
			nombre += " ";
		}
	    if (!infoFich.containsKey("DUMMY")) {
		    while((linea = lectorJFICHSAL.readLine()) != null) {
		    	contadorLinea ++;
		    	switch (contadorLinea) {
		    	case 3:
		    		linea = linea.replace("DDNAME--", nombre);
		    		//REVISAR Z.
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
	    }else {
	    	writerCortex.write("//*--DUMMY-----------------------------------------------------------");
	    	writerCortex.newLine();
			writerCortex.write(infoFich.get("DUMMY"));
	    	writerCortex.newLine();	
	    }
	    infoFich.clear();
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
	    
	    //----------------M�todo---------------------------------------------
	    
	    writeComments(datos, writerCortex, "ComFichE", i);

	    Map<String, String> infoFich = new HashMap<String, String>();
	    nombre = datos.get("Entrada" + String.valueOf(i));
		for(int j = nombre.length(); j < 8; j++) {
			nombre += " ";
		}
	    infoFich = metodosAux.infoFichero(pasoE, letraPaso, nombre);
	    if (!infoFich.containsKey("DUMMY")) {
		    while((linea = lectorJFICHENT.readLine()) != null) {
		    	contadorLinea ++;
		    	if(i > 1 && contadorLinea == 1) {
		    		//No queremos que vuelva a escribir la primera l�nea de la plantilla
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
	    }else {
	    	writerCortex.write("//*--DUMMY-----------------------------------------------------------");
	    	writerCortex.newLine();
			writerCortex.write(infoFich.get("DUMMY"));
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
	    
	    //----------------M�todo---------------------------------------------
	    Map<String, String> infoFich = new HashMap<String, String>();
	    nombre = datos.get("Borrar" + String.valueOf(i));
	    infoFich = metodosAux.infoFichero(pasoE, letraPaso, nombre);
	    
	    while((linea = lectorJBORRAF.readLine()) != null) {
	    	contadorLinea ++;
	    	if(i > 1 && contadorLinea == 1) {
	    		//No queremos que vuelva a escribir la primera l�nea de la plantilla
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

	public void writeMAILTXT(Map<String, String> datos, String letraPaso, int paso, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
			//----------------Fichero de plantilla JJMAILTXT--------------------------
		    FileReader ficheroMAILTXT = new FileReader("C:\\Cortex\\Plantillas\\JMAILTXT.txt");
		    BufferedReader lectorMAILTXT = new BufferedReader(ficheroMAILTXT);	
		    //----------------Variables------------------------------------------
		    String linea;
		    pasoS += 2;
		    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
		    histPasos.put(numeroPaso, "MAILTXT");
		    int contadorLinea = 0;
		    
		    
		    //----------------M�todo---------------------------------------------
		    while((linea = lectorMAILTXT.readLine()) != null) {
		    	contadorLinea ++;
	//	    	ASDASD
		    	switch (contadorLinea) {
		    	case 2:
		    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
					break;
		    	case 4:
		    		linea = (datos.get("ASUNTO") == null) ? linea.trim() : linea.trim() + datos.get("ASUNTO");
		    		break;
		    	case 5:
		    		linea = (datos.get("ADREMI") == null) ? linea.trim() : linea.trim() + datos.get("ADREMI");
		    		break;
		    	case 6:
		    		linea = (datos.get("ADRDES") == null) ? linea.trim() : linea.trim() + datos.get("ADRDES");
		    		break;
		    	case 7:
		    		linea = (datos.get("ADRDE1") == null) ? linea.trim() : linea.trim() + datos.get("ADRDE1");
		    		break;
		    	case 8:
		    		linea = (datos.get("ADRDE2") == null) ? linea.trim() : linea.trim() + datos.get("ADRDE2");
		    		break;
		    	case 9:
		    		linea = (datos.get("ADRDE3") == null) ? linea.trim() : linea.trim() + datos.get("ADRDE3");
		    		break;
		    	case 10:
		    		linea = (datos.get("TIPMAIL") == null) ? linea.trim() : linea.trim() + datos.get("TIPMAIL");
		    		break;
		    	case 12:
		    		linea = (datos.get("UIDPETI") == null) ? linea.trim() : linea.trim() + datos.get("UIDPETI");
		    		break;
		    	case 13:
		    		linea = (datos.get("IDEANEX") == null) ? linea.trim() : linea.trim() + datos.get("IDEANEX");
		    		break;
		    	case 14:
		    		linea = (datos.get("DATAENVI") == null) ? linea.trim() : linea.trim() + datos.get("DATAENVI");
		    		break;
		    	case 15:
		    		linea = (datos.get("HORENVI") == null) ? linea.trim() : linea.trim() + datos.get("HORENVI");
		    		break;
		    	case 16:
		    		linea = (datos.get("DADA721") == null) ? linea.trim() : linea.trim() + datos.get("DADA721");
		    		break;
		    	case 17:
		    		linea = (datos.get("DADA722") == null) ? linea.trim() : linea.trim() + datos.get("DADA722");
		    		break;
		    	case 18:
		    		linea = (datos.get("DADA723") == null) ? linea.trim() : linea.trim() + datos.get("DADA723");
		    		break;
		    	case 19:
		    		linea = (datos.get("DADA724") == null) ? linea.trim() : linea.trim() + datos.get("DADA724");
		    		break;
		    	case 20:
		    		//Revisar nombre variable
		    		linea = (datos.get("DADA725") == null) ? linea.trim() : linea.trim() + datos.get("DADA725");
		    		break;
				default:
					break;
				}
		    	System.out.println("Escribimos: " + linea);
		    	writerCortex.write(linea);
		    	writerCortex.newLine();
		    }
		    lectorMAILTXT.close();		
		    writeComments(datos, writerCortex);
	}

	public void writeSORT(Map<String, String> datos, String letraPaso, int paso, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
		//----------------Fichero de plantilla SORT--------------------------
	    FileReader ficheroJSORT = new FileReader("C:\\Cortex\\Plantillas\\JSORT.txt");
	    BufferedReader lectorJSORT = new BufferedReader(ficheroJSORT);	
	    //----------------Variables------------------------------------------
	    String linea;
	    pasoS += 2;
	    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
	    histPasos.put(numeroPaso, "SORT");
	    int contadorLinea = 0;
	    int i = 1;
	    Map<String, String> infoFich = new HashMap<String, String>();
	    
	    //----------------M�todo---------------------------------------------
	    
	    infoFich = metodosAux.infoSort(paso, letraPaso);
	    //---------------- Escribimos la plantilla JSORT
	    while((linea = lectorJSORT.readLine()) != null) {
	    	contadorLinea ++;
	    	switch (contadorLinea) {
	    	case 2:
	    		linea = linea.replace("//---D1", "//" + letraPaso + numeroPaso + "D" + String.valueOf(i));
	    		i++;
	    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.&FAAMMDDV", infoFich.get("DSN"));
				break;
	    	case 4:
	    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
	    		break;
	    	case 5:
	    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.&FAAMMDDV", infoFich.get("SORTIN"));
	    		break;
	    	case 6:
	    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.&FAAMMDDV", infoFich.get("DSN"));
	    		break;
	    	case 8:
	    		if(infoFich.containsKey("MGMTCLAS")) {
	    			linea = linea.replace("//*", "// ");
	    			linea = linea.replace("EXLIXXXX", infoFich.get("MGMTCLAS"));
	    		}
	    		break;
	    	case 9:
	    		linea = linea.replace("(LONGREG,(KKK,KK))", infoFich.get("Definicion"));
	    		break;
	    	case 11:
	    		for (int j = 1; datos.containsKey("SORT" + j); j++) {
	    			if (datos.get("SORT" + j).startsWith("SORT")) {
	    				linea = linea.replace("SORT FIELDS=(X,XX,XX,X)", datos.get("SORT" + j));
	    			}else {
	    				linea = "   " + datos.get("SORT" + j); 
	    			}
	    			System.out.println("Escribimos: " + linea);
	    	    	writerCortex.write(linea);
	    	    	writerCortex.newLine();
	    	    	linea = "";
	    		}
	    		
	    		break;
			default:
				break;
			}
	    	if (!linea.equals("")) {
	    		System.out.println("Escribimos: " + linea);
	    		writerCortex.write(linea);
	    		writerCortex.newLine();
	    	}
	    }
	    lectorJSORT.close();
	    writeComments(datos, writerCortex);
	}

	public void writeJFTPSEND(Map<String, String> datos, String letraPaso, int pasoE, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
		//----------------Fichero de plantilla JFTPSEND--------------------------
	    FileReader ficheroJFTPSEND = new FileReader("C:\\Cortex\\Plantillas\\JFTPSEND.txt");
	    BufferedReader lectorJFTPSEND = new BufferedReader(ficheroJFTPSEND);	
	    //----------------Variables------------------------------------------
	    String linea;
	    pasoS += 2;
	    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
	    histPasos.put(numeroPaso, "FTPSEND");
	    int contadorLinea = 0, spaces = 0;
	    
	    //----------------M�todo---------------------------------------------
	    while((linea = lectorJFTPSEND.readLine()) != null) {
	    	contadorLinea ++;
	    	switch (contadorLinea) {
	    	case 2:
	    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
				break;
	    	case 3:
	    		//Calculamos cuantos espacios hay que a�adir detr�s para que no se muevan los comentarios de posici�n
	    		StringBuffer des = new StringBuffer("DES=" + datos.get("DES") + ",");
	    		spaces = 40 - des.length();
	    		for (int j = 0; j < spaces; j++) {
	    			des.append(" ");
	    		}
	    		linea = linea.replace("DES=destino,                            ", des);
				break;
	    	case 4:
	    	    StringBuffer host = new StringBuffer("HOST=Z." + metodosAux.infoFTP(pasoE, letraPaso, datos.get("FHOST")) + ",");
	    	    spaces = 40 - host.length();  		
	    		for (int j = 0; j < spaces; j++) {
	    			host.append(" ");
	    		}
	    		linea = linea.replace("HOST=,                                  ", host);
	    		break;
	    	case 5:
	    		if(datos.get("FDEST").contains("_")) {
	    			String aux = "'" + datos.get("FDEST") + "'";
	    			datos.replace("FDEST", aux);
	    		}
	    		if(datos.get("FDEST").contains("_&")) {
	    			String aux = datos.get("FDEST");
	    			aux = aux.replaceAll("_&", "-&");
	    			datos.replace("FDEST", aux);
	    			TESTAvisos.LOGGER.log(Level.INFO, letraPaso + String.valueOf(pasoE) + " // Revisar fichero -  contiene _& ");
	    			System.out.println("*****REVISAR FICHERO CON _&*****");
	    	    	writerCortex.write("*****REVISAR FICHERO CON _&*****");
	    	    	writerCortex.newLine();
	    		}
	    		StringBuffer fit = new StringBuffer("FIT=" + datos.get("FDEST"));
	    		if(datos.containsKey("MSG") || datos.containsKey("DIR")) {
	    			fit.append(",");
	    		}
	    		spaces = 40 - fit.length();  		
	    		for (int j = 0; j < spaces; j++) {
	    			fit.append(" ");
	    		}
	    		linea = linea.replace("FIT=nomfichred                          ", fit);
	    		break;
	    	case 6:
	    		if(datos.containsKey("DIR")) {
	    			linea = linea.replace("//*", "// "); 
	    			StringBuffer dir = new StringBuffer("DIR='" + datos.get("DIR") + "'");
		    		if(datos.containsKey("MSG")) {
		    			dir.append(",");
		    		}
		    		spaces = 40 - dir.length();  		
		    		for (int j = 0; j < spaces; j++) {
		    			dir.append(" ");
		    		}
		    		linea = linea.replace("DIR=XXX                                 ", dir);
	    		}
	    		break;
	    	case 7:
	    		if(datos.containsKey("MSG")) {
	    			linea = linea.replace("//*", "// ");
	    			if(!datos.containsKey("MSG2")) { 
		    			StringBuffer msg = new StringBuffer("MSG='" + datos.get("MSG").replace("-", ",") + "'");
			    		spaces = 40 - msg.length();  		
			    		for (int j = 0; j < spaces; j++) {
			    			msg.append(" ");
			    		}
			    		linea = linea.replace("MSG='UE----,UE----'                     ", msg);
	    			}else {
	    				StringBuffer msg = new StringBuffer("MSG='" + datos.get("MSG").replace("-", ",")
	    						+ datos.get("MSG2").trim().replace("-", ",") + "'");
	    				if (msg.length() > 68) {
	    					TESTAvisos.LOGGER.log(Level.INFO, letraPaso + String.valueOf(pasoE) + " // Variable MSG excede de la longitud permitida - " + msg);
	    	    			System.out.println("*****REVISAR LONGITUD MSG*****");
	    	    	    	writerCortex.write("*****REVISAR LONGITUD MSG*****");
	    	    	    	writerCortex.newLine();
	    				}
	    				linea = linea.replace("MSG='UE----,UE----'                     <== aviso usuario (opc.)", msg);
	    			}
	    		}
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
			    histPasos.put(numeroPaso, "FTPREB");
			    int contadorLinea = 0, spaces = 0;
			    Map<String, String> infoFtpReb = new HashMap<String, String>();
			    //----------------M�todo---------------------------------------------
			    
			    infoFtpReb = metodosAux.infoFtpReb(pasoE, letraPaso);
			    //----------------M�todo---------------------------------------------
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
			    		//Calculamos cuantos espacios hay que a�adir detr�s para que no se muevan los comentarios de posici�n
			    		StringBuffer orig = new StringBuffer("ORIG=" + datos.get("ORIG") + ",");
			    		spaces = 39 - orig.length();
			    		for (int j = 0; j < spaces; j++) {
			    			orig.append(" ");
			    		}
			    		linea = linea.replace("ORIG=SERVIDOR_ORIGEN,                  ", orig);
						break;
			    	case 5:
			    		if(datos.get("FORIG").contains("_")) {
			    			String aux = "'" + datos.get("FORIG") + "'";
			    			datos.replace("FORIG", aux);
			    		}
			    		if(datos.get("FORIG").contains("_&")) {
			    			String aux = datos.get("FORIG");
			    			aux = aux.replaceAll("_&", "-&");
			    			datos.replace("FORIG", aux);
			    			TESTAvisos.LOGGER.log(Level.INFO, letraPaso + String.valueOf(pasoE) + " // Revisar fichero -  contiene _& ");
			    			System.out.println("*****REVISAR FICHERO CON _&*****");
			    	    	writerCortex.write("*****REVISAR FICHERO CON _&*****");
			    	    	writerCortex.newLine();
			    		}
			    		if(datos.get("FORIG").contains("*")) {
			    			System.out.println("******** FICHERO CON ASTERISCOS - AVISAR APLICACI�N ******");
					    	writerCortex.write("******** FICHERO CON ASTERISCOS - AVISAR APLICACI�N ******");
					    	writerCortex.newLine();
					    	TESTAvisos.LOGGER.log(Level.INFO, letraPaso + String.valueOf(pasoE) + " // Fichero con * - Avisar Aplicacion ");
			    		}
			    	    StringBuffer forig = new StringBuffer("FIT=" + datos.get("FORIG").replace("*", "****"));
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
	
	public void writeFTPDEL(Map<String, String> datos, String letraPaso, int pasoE, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
			//----------------Fichero de plantilla JFTDEL--------------------------
		    FileReader ficheroJFTPDEL = new FileReader("C:\\Cortex\\Plantillas\\JFTPDEL.txt");
		    BufferedReader lectorJFTPDEL = new BufferedReader(ficheroJFTPDEL);	
		    //----------------Variables------------------------------------------
		    String linea;
		    pasoS += 2;
		    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
		    histPasos.put(numeroPaso, "FTEPDEL");
		    int contadorLinea = 0, spaces = 0;
		    //----------------M�todo---------------------------------------------
		    
		    while((linea = lectorJFTPDEL.readLine()) != null) {
		    	contadorLinea ++;
		    	switch (contadorLinea) {
		    	case 2:
	    			linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
	    			break;
		    	case 3:	
		    		StringBuffer orig = new StringBuffer("ORIG=" + datos.get("ORIG") + ",");
		    		spaces = 40 - orig.length();
		    		for (int j = 0; j < spaces; j++) {
		    			orig.append(" ");
		    		}
		    		linea = linea.replace("ORIG=SERVIDOR_ORIGEN,                   ", orig);
					break;
		    	case 4:
		    		if(datos.get("FITXER").contains("_")) {
		    			String aux = "'" + datos.get("FITXER") + "'";
		    			datos.replace("FITXER", aux);
		    		}
		    		if(datos.get("FITXER").contains("_&")) {
		    			String aux = datos.get("FITXER");
		    			aux = aux.replaceAll("_&", "-&");
		    			datos.replace("FITXER", aux);
		    			TESTAvisos.LOGGER.log(Level.INFO, letraPaso + String.valueOf(pasoE) + " // Revisar fichero -  contiene _& ");
		    			System.out.println("*****REVISAR FICHERO CON _&*****");
		    	    	writerCortex.write("*****REVISAR FICHERO CON _&*****");
		    	    	writerCortex.newLine();
		    		}
		    	    StringBuffer forig = new StringBuffer("FIT=" + datos.get("FITXER"));
		    	    if(datos.containsKey("DIR")) {
		    	    	forig.append(",");
		    	    }
		    	    spaces = 40 - forig.length();  		
		    		for (int j = 0; j < spaces; j++) {
		    			forig.append(" ");
		    		}
		    		linea = linea.replace("FIT=nomfichred                          ", forig);
		    		break;
		    	case 5:
		    		if(datos.containsKey("DIR")) {
		    			linea = linea.replace("//*", "// "); 
		    			StringBuffer dir = new StringBuffer("DIR='" + datos.get("DIR") + "'");
		    			spaces = 40 - dir.length();  		
			    		for (int j = 0; j < spaces; j++) {
			    			dir.append(" ");
			    		}
			    		linea = linea.replace("DIR=XXX                                 ", dir);
		    		}
		    		break;
				default:
					break;
				}
		    	System.out.println("Escribimos: " + linea);
		    	writerCortex.write(linea);
		    	writerCortex.newLine();
		    }
		    lectorJFTPDEL.close();		
		    writeComments(datos, writerCortex);	
	}

	public void writeJMAILMSG(Map<String, String> datos, String letraPaso, int pasoE, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
				//----------------Fichero de plantilla JMAILMSG--------------------------
			    FileReader ficheroJMAILMSG = new FileReader("C:\\Cortex\\Plantillas\\JMAILMSG.txt");
			    BufferedReader lectorJMAILMSG = new BufferedReader(ficheroJMAILMSG);	
			    //----------------Variables------------------------------------------
			    String linea;
			    pasoS += 2;
			    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
			    histPasos.put(numeroPaso, "JMAILMSG");
			    int contadorLinea = 0, spaces = 0;
			    //----------------M�todo---------------------------------------------    
			    
			    while((linea = lectorJMAILMSG.readLine()) != null) {
			    	contadorLinea ++;
			    	switch (contadorLinea) {
			    	case 2:
			    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
						break;
			    	case 3:
			    		//Calculamos cuantos espacios hay que a�adir detr�s para que no se muevan los comentarios de posici�n
			    		StringBuffer dsnName = new StringBuffer("DSNAME=Z." + metodosAux.infoDSN(pasoE, letraPaso, "ENTRA1") + ",");
			    		spaces = 42 - dsnName.length();
			    		for (int j = 0; j < spaces; j++) {
			    			dsnName.append(" ");
			    		}
			    		linea = linea.replace("DSNAME=,                                  ", dsnName);
						break;
			    	case 4:
			    		//Calculamos cuantos espacios hay que a�adir detr�s para que no se muevan los comentarios de posici�n
			    		StringBuffer fitTxt = new StringBuffer("FITTXT=" + datos.get("SORTIDA"));
			    		spaces = 42 - fitTxt.length();
			    		for (int j = 0; j < spaces; j++) {
			    			fitTxt.append(" ");
			    		}
			    		linea = linea.replace("FITTXT=                                   ", fitTxt);
						break;
					default:
						break;
					}
			    	System.out.println("Escribimos: " + linea);
			    	writerCortex.write(linea);
			    	writerCortex.newLine();
			    }
			    lectorJMAILMSG.close();		
			    writeComments(datos, writerCortex);
			}

	public void writeJFTPSAPP(Map<String, String> datos, String letraPaso, int pasoE, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
		//----------------Fichero de plantilla JFTPSAPP--------------------------
	    FileReader ficheroJFTPSAPP = new FileReader("C:\\Cortex\\Plantillas\\JFTPSAPP.txt");
	    BufferedReader lectorJFTPSAPP = new BufferedReader(ficheroJFTPSAPP);	
	    //----------------Variables------------------------------------------
	    String linea;
	    pasoS += 2;
	    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
	    histPasos.put(numeroPaso, "FTPSAPP");
	    int contadorLinea = 0, spaces = 0;
	    
	    while((linea = lectorJFTPSAPP.readLine()) != null) {
	    	contadorLinea ++;
	    	switch (contadorLinea) {
	    	case 2:
	    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
				break;
	    	case 3:
	    		//Calculamos cuantos espacios hay que a�adir detr�s para que no se muevan los comentarios de posici�n
	    		StringBuffer des = new StringBuffer("DES=" + datos.get("DES") + ",");
	    		spaces = 40 - des.length();
	    		for (int j = 0; j < spaces; j++) {
	    			des.append(" ");
	    		}
	    		linea = linea.replace("DES=destino,                            ", des);
				break;
	    	case 4:
	    	    StringBuffer sqlin = new StringBuffer("SQLIN='" + datos.get("SQLIN") + "',");
	    	    spaces = 40 - sqlin.length();  		
	    		for (int j = 0; j < spaces; j++) {
	    			sqlin.append(" ");
	    		}
	    		linea = linea.replace("SQLIN=,                                 ", sqlin);
	    		break;
	    	case 5:
	    		if(datos.get("FDEST").contains("_")) {
	    			String aux = "'" + datos.get("FDEST") + "'";
	    			datos.replace("FDEST", aux);
	    		}
	    		if(datos.get("FDEST").contains("_&")) {
	    			String aux = datos.get("FDEST");
	    			aux = aux.replaceAll("_&", "-&");
	    			datos.replace("FDEST", aux);
	    			TESTAvisos.LOGGER.log(Level.INFO, letraPaso + String.valueOf(pasoE) + " // Revisar fichero -  contiene _& ");
	    			System.out.println("*****REVISAR FICHERO CON _&*****");
	    	    	writerCortex.write("*****REVISAR FICHERO CON _&*****");
	    	    	writerCortex.newLine();
	    		}
	    		StringBuffer fit = new StringBuffer("FIT=" + datos.get("FDEST"));
	    		if(datos.containsKey("MSG") || datos.containsKey("DIR")) {
	    			fit.append(",");
	    		}
	    		spaces = 40 - fit.length();  		
	    		for (int j = 0; j < spaces; j++) {
	    			fit.append(" ");
	    		}
	    		linea = linea.replace("FIT=nomfichred                          ", fit);
	    		break;
	    	case 6:
	    		if(datos.containsKey("DIR")) {
	    			linea = linea.replace("//*", "// "); 
	    			StringBuffer dir = new StringBuffer("DIR='" + datos.get("DIR") + "'");
		    		if(datos.containsKey("MSG")) {
		    			dir.append(",");
		    		}
		    		spaces = 40 - dir.length();  		
		    		for (int j = 0; j < spaces; j++) {
		    			dir.append(" ");
		    		}
		    		linea = linea.replace("DIR=XXX                                 ", dir);
	    		}
	    		break;
	    	case 7:
	    		if(datos.containsKey("MSG")) {
	    			linea = linea.replace("//*", "// ");
	    			if(!datos.containsKey("MSG2")) { 
		    			StringBuffer msg = new StringBuffer("MSG='" + datos.get("MSG").replace("-", ",") + "'");
			    		spaces = 40 - msg.length();  		
			    		for (int j = 0; j < spaces; j++) {
			    			msg.append(" ");
			    		}
			    		linea = linea.replace("MSG='UE----,UE----'                     ", msg);
	    			}else {
	    				StringBuffer msg = new StringBuffer("MSG='" + datos.get("MSG").replace("-", ",")
	    						+ datos.get("MSG2").trim().replace("-", ",") + "'");
	    				if (msg.length() > 68) {
	    					TESTAvisos.LOGGER.log(Level.INFO, letraPaso + String.valueOf(pasoE) + " // Variable MSG excede de la longitud permitida - " + msg);
	    	    			System.out.println("*****REVISAR LONGITUD MSG*****");
	    	    	    	writerCortex.write("*****REVISAR LONGITUD MSG*****");
	    	    	    	writerCortex.newLine();
	    				}
	    				linea = linea.replace("MSG='UE----,UE----'                     <== aviso usuario (opc.)", msg);
	    			}
	    		}
	    		break;	
	    	default:
				break;	
	    	}
		    System.out.println("Escribimos: " + linea);
	    	writerCortex.write(linea);
	    	writerCortex.newLine();
	    }
	    lectorJFTPSAPP.close();
	}

	public void writeJMAILANX(Map<String, String> datos, String letraPaso, int pasoE, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
		//----------------Fichero de plantilla JMAILANX--------------------------
	    FileReader ficheroJMAILANX = new FileReader("C:\\Cortex\\Plantillas\\JMAILANX.txt");
	    BufferedReader lectorJMAILANX = new BufferedReader(ficheroJMAILANX);	
	    //----------------Variables------------------------------------------
	    String linea;
	    pasoS += 2;
	    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
	    histPasos.put(numeroPaso, "MAILANX");
	    int contadorLinea = 0;	    
	    
	    //----------------M�todo---------------------------------------------
	    while((linea = lectorJMAILANX.readLine()) != null) {
	    	contadorLinea ++;
	    	switch (contadorLinea) {
	    	case 2:
	    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
				break;
	    	case 3:
	    		StringBuffer dsname = new StringBuffer("DSNAME=Z." + metodosAux.infoDSN(pasoE, letraPaso, "ENTRA1") + ", ");
	    		linea = linea.replace("DSNAME=,               ", dsname);
	    		break;
	    	case 4:
	    		String aux = linea.replace("------.TXT      ", datos.get("SORTIDA") +" ");
	    		if (aux.length() > 72) {
	    			linea = linea.replace("FITTXT=------.TXT      <= debe ser idéntico al informado en IDEANEX", "FITTXT=" + datos.get("SORTIDA"));
				}else {
		    		linea = linea.replace("------.TXT      ", datos.get("SORTIDA")+" ");
				}
	    		break;
	    	case 6:
	    		linea = (datos.get("ASUNTO") == null) ? linea.trim() : linea.trim() + datos.get("ASUNTO");
	    		break;
	    	case 7:
	    		linea = (datos.get("ADREMI") == null) ? linea.trim() : linea.trim() + datos.get("ADREMI");
	    		break;
	    	case 8:
	    		linea = (datos.get("ADRDES") == null) ? linea.trim() : linea.trim() + datos.get("ADRDES");
	    		break;
	    	case 9:
	    		linea = (datos.get("ADRDE1") == null) ? linea.trim() : linea.trim() + datos.get("ADRDE1");
	    		break;
	    	case 10:
	    		linea = (datos.get("ADRDE2") == null) ? linea.trim() : linea.trim() + datos.get("ADRDE2");
	    		break;
	    	case 11:
	    		linea = (datos.get("ADRDE3") == null) ? linea.trim() : linea.trim() + datos.get("ADRDE3");
	    		break;
	    	case 12:
	    		linea = (datos.get("TIPMAIL") == null) ? linea.trim() : linea.replace("???", datos.get("TIPMAIL"));
	    		break;
	    	case 14:
	    		linea = (datos.get("UIDPETI") == null) ? linea.trim() : linea.trim() + datos.get("UIDPETI");
	    		break;
	    	case 15:
	    		linea = (datos.get("SORTIDA") == null) ? linea.trim() : linea.replace("------.TXT", datos.get("SORTIDA"));
	    		break;
	    	case 16:
	    		linea = (datos.get("DATENVI") == null) ? linea.trim() : linea.trim() + datos.get("DATENVI");
	    		break;
	    	case 17:
	    		linea = (datos.get("HORENVI") == null) ? linea.trim() : linea.trim() + datos.get("HORENVI");
	    		break;
	    	case 18:
	    		linea = (datos.get("DADA721") == null) ? linea.trim() : linea.trim() + datos.get("DADA721");
	    		break;
	    	case 19:
	    		linea = (datos.get("DADA722") == null) ? linea.trim() : linea.trim() + datos.get("DADA722");
	    		break;
	    	case 20:
	    		linea = (datos.get("DADA723") == null) ? linea.trim() : linea.trim() + datos.get("DADA723");
	    		break;
	    	case 21:
	    		linea = (datos.get("DADA724") == null) ? linea.trim() : linea.trim() + datos.get("DADA724");
	    		break;
	    	case 22:
	    		linea = (datos.get("DADA725") == null) ? linea.trim() : linea.trim() + datos.get("DADA725");
	    		break;
	    	default:
				break;
			}
	    	System.out.println("Escribimos: " + linea);
	    	writerCortex.write(linea);
	    	writerCortex.newLine();
	    }
	    lectorJMAILANX.close();		
	    writeComments(datos, writerCortex);
	}
	
	public void writeJFIVACIO(Map<String, String> datos, String letraPaso, int pasoE, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
		//----------------Fichero de plantilla JFIVACIO--------------------------
	    FileReader ficheroJFIVACIO = new FileReader("C:\\Cortex\\Plantillas\\JFIVACIO.txt");
	    BufferedReader lectorJFIVACIO = new BufferedReader(ficheroJFIVACIO);	
	    //----------------Variables------------------------------------------
	    String linea;
	    pasoS += 2;
	    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
	    histPasos.put(numeroPaso, "FIVACIO");
	    int contadorLinea = 0;
	    //----------------M�todo---------------------------------------------
	    			    
	    //----------------M�todo---------------------------------------------
	    while((linea = lectorJFIVACIO.readLine()) != null) {
	    	contadorLinea ++;
	    	switch (contadorLinea) {
	    	case 2:			    		
	    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);			    		
	    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.&FAAMMDDV", metodosAux.infoDSN(pasoE, letraPaso, "ENTRADA"));
				break;			    	
			}
	    	System.out.println("Escribimos: " + linea);
	    	writerCortex.write(linea);
	    	writerCortex.newLine();
	    }
	    lectorJFIVACIO.close();		
	    writeComments(datos, writerCortex);
	}

	public void writeJOPCREC(Map<String, String> datos, String letraPaso, int pasoE, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
		//----------------Fichero de plantilla JMAILANX--------------------------
	    FileReader ficheroJOPCREC = new FileReader("C:\\Cortex\\Plantillas\\JOPCREC.txt");
	    BufferedReader lectorJOPCREC = new BufferedReader(ficheroJOPCREC);	
	    //----------------Variables------------------------------------------
	    String linea;
	    pasoS += 2;
	    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
	    histPasos.put(numeroPaso, "OPCREC");
	    int contadorLinea = 0;	    
	    
	    //----------------M�todo---------------------------------------------
	    while((linea = lectorJOPCREC.readLine()) != null) {
	    	contadorLinea ++;
	    	switch (contadorLinea) {
	    	case 2:
	    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
				break;
	    	case 4:
	    		linea = linea.replace("'APL.XXXXXXXX.NOMMEM'", "'Z." + datos.get("SRSTAT") + "'");
	    		break;
	    	default:
				break;
			}
	    	System.out.println("Escribimos: " + linea);
	    	writerCortex.write(linea);
	    	writerCortex.newLine();
	    }
	    lectorJOPCREC.close();		
	    writeComments(datos, writerCortex);
	}
	
	public void writeJFUSION(Map<String, String> datos, String letraPaso, int pasoE, BufferedWriter writerCortex) throws IOException {
		// TODO Auto-generated method stub
		//----------------Fichero de plantilla JMAILMSG--------------------------
	    FileReader ficheroJFUSION = new FileReader("C:\\Cortex\\Plantillas\\JFUSION.txt");
	    BufferedReader lectorJFUSION = new BufferedReader(ficheroJFUSION);	
	    //----------------Variables------------------------------------------
	    String linea;
	    pasoS += 2;
	    String numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
	    histPasos.put(numeroPaso, "JFUSION");
	    int contadorLinea = 0;
	    //----------------M�todo---------------------------------------------    
	    
	    metodosAux.infoJFUSION(datos, pasoE, letraPaso);
	    
	    
	    while((linea = lectorJFUSION.readLine()) != null) {
	    	contadorLinea ++;
	    	switch (contadorLinea) {
	    	case 3:
	    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
	    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.&FAAMMDDV", datos.get("DSN"));
				break;
	    	case 5:
	    		linea = linea.replace("//---", "//" + letraPaso + numeroPaso);
	    		break;
	    	case 7:
	    		linea = linea.replace("&APLIC", datos.get("APL"));
	    		break;
	    	case 8:
	    		linea = linea.replace("&NOMQDRE", datos.get("QUADRE"));
	    		break;
	    	case 10:
	    		for(int i = 1; datos.containsKey("DSN" + i); i++) {
	    			String lineaEditada = linea;
	    			lineaEditada = lineaEditada.replace("TSFUS01.DD----1", "TSFUS01." + datos.get("FICH" + i));
	    			if (i < 10) {
	    				lineaEditada = lineaEditada.replace("&DUMY01", "&DUMY0" + i);
	    			}else {
	    				lineaEditada = lineaEditada.replace("&DUMY01", "&DUMY" + i);
	    			} 
	    			lineaEditada = lineaEditada.replace("APL.XXXXXXXX.NOMMEM.&GENE1", "Z." + datos.get("DSN" + i));
	    			
	    			System.out.println("Escribimos: " + lineaEditada);
	    	    	writerCortex.write(lineaEditada);
	    	    	writerCortex.newLine();
	    		}
	    		linea = "";
	    		break;
	    	case 12:
	    		linea = "";
	    		for(int i = 1; datos.containsKey("FICHA" + i); i++){
	    			linea = datos.get("FICHA" + i);
	    			
	    			System.out.println("Escribimos: " + linea);
	    	    	writerCortex.write(linea);
	    	    	writerCortex.newLine();	
	    		}
	    		linea = "";
	    		break;
	    	case 13:
	    		linea = "";
	    		break;
	    	case 14:
	    		StringBuffer nameFich = new StringBuffer(datos.get("SALIDA"));
	    		for (int i = nameFich.length(); i < 9; i++) {
	    			nameFich.append(" ");
	    		}
	    		linea = linea.replace("//DDSAL--  ", "//" + nameFich);
	    		linea = linea.replace("APL.XXXXXXXX.NOMMEM.&FAAMMDDV", datos.get("DSN"));
	    		break;
	    	case 16:
	    		if (datos.containsKey("MGMTCLAS")) {
	    			linea = linea.replace("//*", "// ");
	    			linea = linea.replace("EXLIXXXX", datos.get("MGMTCLAS"));
	    		}
	    		break;
	    	case 17:
	    		linea = linea.replace("(LONGREG,(KKK,KK))", datos.get("Definicion"));
	    		break;
	    	case 19:
	    		linea = linea.replace("//---IF-", "//" + letraPaso + numeroPaso + "IF1");
	    		break;
	    	case 21:
	    		pasoS += 2;
	    		numeroPaso = (pasoS < 10) ? "0" + String.valueOf(pasoS) : String.valueOf(pasoS) ;
	    		for(int i = 1; datos.containsKey("DSN" + i); i++) {
	    			String lineaEditada = linea;
	    			lineaEditada = lineaEditada.replace("//---D-", "//" + letraPaso + numeroPaso + "D" + i);
	    			lineaEditada = lineaEditada.replace("APL.XXXXXXXX.NOMMEM.&GENE1", "Z." + datos.get("DSN" + i));
	    			
	    			System.out.println("Escribimos: " + lineaEditada);
	    	    	writerCortex.write(lineaEditada);
	    	    	writerCortex.newLine();
	    		}
	    		linea = "";
	    		break;
	    	case 22:
	    		numeroPaso = (pasoS - 2 < 10) ? "0" + String.valueOf(pasoS - 2) : String.valueOf(pasoS - 2) ;
	    		linea = linea.replace("//E---IF-", "//E" + letraPaso + numeroPaso + "IF1");
	    		break;
			default:
				break;
			}
	    	if(!linea.equals("")) {
		    	System.out.println("Escribimos: " + linea);
		    	writerCortex.write(linea);
		    	writerCortex.newLine();
	    	}
	    }
	    lectorJFUSION.close();		
	    writeComments(datos, writerCortex);
	}


}
