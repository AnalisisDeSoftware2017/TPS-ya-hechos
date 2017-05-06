package testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Se buscan: - Lineas de codigo totales - Lineas de codigo comentadas -
 * Complejidad ciclomática - FanIn - FanOut - Logitud Halstead - Volumen -
 * Halstead.
 *
 *
 */
public class Analizador {

    public static final String TIPOS = "(void|int|char|Integer|String|boolean|double|float|long|short|type)";
    public static final String OPERADORES = "(\\+|-|\\*|/|if[\\s]*\\(|for[\\s]*\\(|while[\\s]*\\(|switch[\\s]*\\(|case|else|=|\\{|\\[|\\(|;[^\\)]*$|<|>|&&|\\|\\|)+";
    // Reg. Exp para metodos
    public static final String METODO = "[\\s]*(void|int|char|boolean|Integer|String|static|public|private)[*]*[\\s]+[*]*[^0-9*]+[\\w]*[\\s]*(\\([\\w\\s,*]*\\))[\\s]*\\{{0,1}[í\\s]*(//[\\s\\w\\d/*,;]*)*$";
    public static final String METODO_INICIO = "[\\s]*(void|int|char|boolean|Integer|String|static|public|private)[*]*[\\s|_]+[*]*";
    public static final String METODO_FIN = "[\\s]*(\\([\\w\\s,*]*\\))[\\s]*\\{{0,1}[í\\s]*(//[\\s\\w\\d/*,;]*)*";
    // Reg. Exp para operadores y operandos
    public static final String NUMERO = "[\\d]+";
    public static final String OPERANDO_NUEVO = "[\\w]{1,}";
    public static final String OPERANDO = "[\\w]{1,}";
    public static final String OPERANDO_INICIO = "[\\s]*(void|int|char)[*]*[\\s]+[*]*";
    public static final String OPERANDO_FIN = "[\\s]*(\\([\\w\\s,*]*\\))[\\s]*\\{{0,1}[í\\s]*(//[\\s\\w\\d/*,;]*)*";
    // Reg. Exp para complejidad ciclomática.
    public static final String NODO_CONDICION = "(if|while|for|case|&&|\\|\\|\\|)";

    /* Devuelve los metodos de un archivo java.
     * Incluye los datos que esta clase pretende analizar:
     * - Lineas de codigo totales
     * - Lineas de codigo comentadas
     * - Complejidad ciclomática
     * - FanIn
     * - FanOut
     * - Logitud Halstead
     * - Volumen Halstead.
    
     * 
     */
    public static LinkedList<Metodo> analizarMetodos(File file) {
        BufferedReader reader = null;
        LinkedList<Metodo> lista = new LinkedList<Metodo>();

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            int nLinea = 0;

            // Leo la primer linea.
            String linea = reader.readLine();

            /* Nivel es la capa en la que esta del codigo.
             * Ej.:
             * 
             * nivel->aca vale 0
             * while(condicion){
             *      nivel->aca vale 1
             *      if(condicion)
             *          nivel->aca vale 2
             *      else
             *          nivel->aca vale 2
             * }
             * nivel->aca vale 0 de nuevo.
             */
            int nivel = 0;

            // Instancio la clase donde voy a guardar los datos.
            Metodo metodo = new Metodo();

            // Variables de control.
            boolean inicio = true;
            boolean comentarioContinuado = false;

            while (linea != null) {
                // Incremento el nro linea.
                nLinea++;

                switch (nivel) {
                    case 0: // En este nivel busco metodos
                        metodo.setLineasCodigo(nLinea - metodo.getLinea());

                        metodo = Analizador.hayMetodo(linea);
                        if (metodo.getNombre() != null) {
                            metodo.setLinea(nLinea);
                            metodo.setCodigo(linea.trim());
                            lista.add(metodo);

                            nivel++; // Busco el codigo del metodo encontrado.
                            inicio = true;
                        }
                        break;
                    default: // En cualquier nivel != 0, busco completar
                        // datos del metodo encontrado

                        // Concateno un <BR>, que es nueva linea en HTML
                        metodo.setCodigo(metodo.getCodigo() + "<BR>");

                        // Separo para poder trabajar con la linea.
                        String lineaCompleta = linea;

                        // Comentario continuado es un boolean que me dice si la
                        // linea que estoy leyendo esta adentro de un comentario
                        // del tipo /* codigo */, ya que puede haberse abierto en
                        // una linea anterior.
                        comentarioContinuado = Analizador.verificarComentarioContinuado(linea, comentarioContinuado);

                        // Si en esta linea se inicia un /*, analizo lo que esta antes
                        // Ej.: int a = 2; /* a = 3 ---> int a = 2;
                        if (lineaCompleta.indexOf("/*") != -1) {
                            linea = lineaCompleta.substring(0, lineaCompleta.indexOf("/*"));
                            if(lineaCompleta.indexOf("*/") > lineaCompleta.indexOf("/*")){
                                comentarioContinuado = false;
                                metodo.incLineasComentario();
                            }
                        }

                        // Si hay comentario incremento lineasComentario
                        if (comentarioContinuado || Analizador.hayComentario(linea)) {
                            metodo.incLineasComentario();
                        }

                        // Si en esta linea se inicia un //, analizo lo que esta antes
                        // Ej.: int a = 2; // comentario ---> int a = 2;
                        if (lineaCompleta.indexOf("//") != -1) {
                            linea = linea.substring(0, linea.indexOf("//"));
                        }

                        // Si hay cierre de bloque bajo un nivel
                        if (!comentarioContinuado && Analizador.hayCierre(linea)) {
                            nivel--;
                        }

                        Analizador.agregarOperadores(linea, metodo);
                        Analizador.agregarNuevosOperandos(linea, metodo);
                        Analizador.agregarOperandos(linea, metodo);


                        // Agrego la tabulacion correspondiente
                        for (int j = 0; j < nivel && !inicio; j++) {
                            metodo.setCodigo(metodo.getCodigo() + "&nbsp;&nbsp;&nbsp;&nbsp;");
                        }

                        // Si hay apertura de bloque subo un nivel
                        if (!comentarioContinuado && Analizador.hayApertura(linea)) {
                            //if (!inicio) {
                                nivel++;
                            /*} else {
                                inicio = false;
                            }*/
                        }
                        if(inicio)
                            inicio = false;
                        // Concateno el codigo. Aca uso lineaCompleta.
                        metodo.setCodigo(metodo.getCodigo() + lineaCompleta.trim());

                        break;
                }
                // Leo la siguiente linea.
                linea = reader.readLine();
            }

            metodo.setLineasCodigo(nLinea - metodo.getLinea());

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Analizador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Calculo el FanIn, FanOut y complejidad ciclomática
        for (Metodo met : lista) {
            Analizador.calcularFanInOut(met, lista);
            Analizador.cacularComplejidadCiclomatica(met);
        }

        return lista;
    }

    /**
     * Castea un Object a Metodo
     */
    public static Metodo analizarMetodo(File file, Object selectedValue) {

        Metodo metodo = (Metodo) selectedValue;

        return metodo;
    }

    /**
     * Verifica si hay una declaracion de metodo en esa linea. Ej.: void main()
     */
    public static Metodo hayMetodo(String fila) {

        Pattern pattern = Pattern.compile(Analizador.METODO, Pattern.CASE_INSENSITIVE);
        fila = fila.replace("[]", "");
        fila = fila.replace('.', ' ');
        Matcher matcher = pattern.matcher(fila);
        String ret = null;

        if (matcher.find()) {
            ret = fila.trim()
                    .replaceAll(METODO_INICIO, "")
                    .replaceAll(METODO_FIN, "");
        }

        Metodo anMetodo = new Metodo();

        anMetodo.setNombre(ret);

        return anMetodo;
    }

    /**
     * Verifica si el string pasado es un tipo de dato valido. Ej.: int, char,
     * FILE, etc...
     */
    public static boolean esTipoDato(String str) {

        Pattern pattern = Pattern.compile(Analizador.TIPOS, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
            return true;
        else
            return false;

    }

    public static void agregarOperadores(String linea, Metodo metodo) {

        Pattern pattern = Pattern.compile(Analizador.OPERADORES, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(linea);
        while (matcher.find()) {
            metodo.agregarOperador(matcher.group());
        }
    }

    public static void agregarNuevosOperandos(String linea, Metodo metodo) {

        Pattern patternTipos = Pattern.compile(Analizador.TIPOS, Pattern.CASE_INSENSITIVE);
        Matcher matcherTipos = patternTipos.matcher(linea);

        Pattern patternOperando = Pattern.compile(Analizador.OPERANDO_NUEVO, Pattern.CASE_INSENSITIVE);
        Matcher matcherOperando = null;

        if (matcherTipos.find()) {

            linea = linea.substring(matcherTipos.end());
            matcherOperando = patternOperando.matcher(linea);
            while (matcherOperando.find()) {
                metodo.agregarOperando(matcherOperando.group());
//                System.out.println(matcherOperando.group());
            }

        }

    }

    public static void agregarOperandos(String linea, Metodo metodo) {

        for (String palabra1 : linea.split(" ")) {
            for (String palabra : palabra1.split("=")) {

                if (metodo.esOperando(palabra)) {
                    metodo.agregarOperando(palabra);
                } else {
                    Pattern pattern = Pattern.compile(Analizador.NUMERO);
                    Matcher matcher = pattern.matcher(palabra);
                    while (matcher.find()) {
                        metodo.agregarOperando(matcher.group());
                    }
                }

            }
        }

    }

    /**
     * Verifica si hay una apertura de bloque ({) Ej.: {
     */
    private static boolean hayApertura(String linea) {
        return linea != null ? linea.indexOf("{") != -1 ? true : false : false;
    }

    /**
     * Verifica si hay un cierre de bloque (}) Ej.: }
     */
    private static boolean hayCierre(String linea) {
        return linea != null ? linea.indexOf("}") != -1 ? true : false : false;
    }

    /**
     * Verifica si en una linea existe un comentario inLine Ej.: codigo
     * //comentario
     */
    private static boolean hayComentario(String linea) {
        if (linea != null && linea.indexOf("//") != -1
                && linea.substring(linea.indexOf("//")).length() > 2) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Verifica si la linea es comentario o no. Ej.: /* comentario bla bla
     */
    private static boolean verificarComentarioContinuado(String linea, boolean hayContinuo) {
        if (hayContinuo && (linea != null && linea.indexOf("*/") != -1)) {
            return false;
        } else if (!hayContinuo && (linea != null && linea.indexOf("/*") == -1)) {
            return false;
        } else {
            return true;
        }

    }

    private static void calcularFanInOut(Metodo metodo, LinkedList<Metodo> lista) {
        for (Metodo metodoAjeno : lista) {
            if(!metodoAjeno.getNombre().equals(metodo.getNombre())){
                Pattern patternIn = Pattern.compile(metodo.getNombre().trim() + "[\\s]*\\(");
                Matcher matcherIn = patternIn.matcher(metodoAjeno.getCodigo());
                while (matcherIn.find()) {
                    metodo.incFanIn();
                }
            }
        }
        
        Pattern patternOut = Pattern.compile("[\\w\\s]*\\(");
        Matcher matcherOut = patternOut.matcher(metodo.getCodigo());
        while (matcherOut.find()) {
            if(matcherOut.group().indexOf("if(") == -1
                    && matcherOut.group().indexOf("for(") == -1
                    && matcherOut.group().indexOf("while(") == -1
                    && matcherOut.group().indexOf("if (") == -1
                    && matcherOut.group().indexOf("for (") == -1
                    && matcherOut.group().indexOf("while (") == -1)
                metodo.incFanOut();
        }
        
        // Decremento 1 porque el matcher cuenta al mismo metodo como una llamada.
        metodo.setFanOut(metodo.getFanOut()-1);
        
        
        
    }
    
    private static void cacularComplejidadCiclomatica(Metodo metodo){
        
        Pattern pattern = Pattern.compile(Analizador.NODO_CONDICION, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(metodo.getCodigo());
        while (matcher.find()) {
            metodo.incComlejidadCiclomatica();
        }
        
        metodo.incComlejidadCiclomatica();
    }
}
