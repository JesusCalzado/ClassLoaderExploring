package com.calzado.util;
 

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

 
public class Example {
 
    
	static Logger logRoot = Logger.getRootLogger();
    static Logger logApp = Logger.getLogger("loggerForMyApplication");
    
 
    public static void main(String[] args) throws IOException,RuntimeException,URISyntaxException {

        URLClassLoader exampleClassLoader = Optional
                            .ofNullable((URLClassLoader)Example.class.getClassLoader())                            
                            .orElseThrow(() -> new RuntimeException("class loader de la clase Example es nulo"));
                            
        ClassLoader granFatherExampleClassLoader = exampleClassLoader.getParent().getParent();
        ClassLoader granFatherSystemClassLoader = ClassLoader.getSystemClassLoader().getParent().getParent();                    
        logApp.info("Class loader de la clase Example:"+exampleClassLoader.toString()+"\n");
        logApp.info("Padre del class loader de clase example:"+exampleClassLoader.getParent()+"\n");                                   
        logApp.info("Abuelo del class loader de la clase Example:"+((granFatherExampleClassLoader != null) ? granFatherExampleClassLoader.toString(): "es nulo")+"\n");
        logApp.info("System Class Loader(si no tiene un class loader propio debe ser el mismo de Example):"+ClassLoader.getSystemClassLoader().toString()+"\n");         
        logApp.info("Padre del system class Laoder:"+ClassLoader.getSystemClassLoader().getParent()+"\n");
        logApp.info("###Abuelo del System Class Loader:"+((granFatherSystemClassLoader != null) ? granFatherSystemClassLoader.toString(): "es nulo")+"\n");        
        logApp.info("Dominio de protección de clase Logger: "+Logger.class.getProtectionDomain().toString());
        logApp.info("Fuente de la clase Logger(ruta desde donde se carga el class): "+Logger.class.getProtectionDomain().getCodeSource());
        String recursoABuscar = "logging.properties";
        logApp.info("Buscar el recurso "+recursoABuscar);
        URL url = exampleClassLoader.getResource(recursoABuscar);
        logApp.info(((url != null) ? "Se encontro el recurso!!!:\n"+
                                     "Ruta(URL): "+url.getPath()+"\n"+
                                     "URI:: "+url.toURI().getPath()+"\n":
                                     "No se encontro el recurso: "+recursoABuscar));
        
        logApp.info("Listar las URL del class loader de Example");                            
        Consumer<URL> printURL = x -> logApp.info("Path File:"+x.getFile()+"\n"); 

        Arrays.stream(exampleClassLoader.getURLs()).forEach(printURL);      

    }
}