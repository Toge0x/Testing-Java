package ppss;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FicheroTexto {

    public FileReader getFileReader(String nombreFichero) throws FileNotFoundException {
        return new FileReader(nombreFichero);
    }

    // SUT NO TESTABLE - Refactorizada a getFileReader
    public int contarCaracteres(String nombreFichero) throws FicheroException {
        int contador = 0;
        FileReader fichero = null;
        try {
            //fichero = new FileReader(nombreFichero);        // dependencia externa, nunca cambiará de tipo NO TESTABLE
            fichero = getFileReader(nombreFichero);
            int i=0;
            while (i != -1) {
                i = fichero.read();         // dependencia externa
                contador++;
            }
            contador--;
        } catch (FileNotFoundException e1) {
            throw new FicheroException(nombreFichero +
                    " (No existe el archivo o el directorio)");
        } catch (IOException e2) {
            throw new FicheroException(nombreFichero +
                    " (Error al leer el archivo)");
        }
        try {
            fichero.close();            // dependencia externa
        } catch (IOException e) {
            throw new FicheroException(nombreFichero +
                    " (Error al cerrar el archivo)");
        }
        return contador;
    }
}