package aplicacion;

import aplicacion.models.*;
import aplicacion.models.dao.DAO;
import aplicacion.models.dao.PersonaDAO;
import aplicacion.models.dao.db.AlumnoDB;
import aplicacion.models.dao.db.ApoderadoDB;
import aplicacion.models.dao.db.CursoDB;
import aplicacion.models.dao.db.ProfesorDB;
import aplicacion.models.dao.df.AlumnoDF;
import aplicacion.models.dao.df.ApoderadoDF;
import aplicacion.models.dao.df.CursoDF;
import aplicacion.models.dao.df.ProfesorDF;
import aplicacion.models.id.IDCurso;
import aplicacion.views.cli.menus.MenuCLI;
import utils.UtilsCLI;
import utils.models.db.MySQLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Aplicación principal de la interfaz de consola de comandos (CLI).
 *
 * @author Sebastián García, Guillermo González, Benjamín Navarrete
 * @version 2.0
 */
public class CLI {

    private final MenuCLI menuCLI;

    /**
     * Genera la instancia de la aplicación CLI
     */
    public CLI() {
        PersonaDAO<Alumno> alumnoData;
        PersonaDAO<Apoderado> apoderadoData;
        PersonaDAO<Profesor> profesorData;
        DAO<Curso, IDCurso, ArrayList<Curso>> cursoData;

        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

        UtilsCLI.mensajeBienvenida();
        // Se intenta conexión con la base da datos y se verifica
        UtilsCLI.mensajeIntentandoConexionMySQL();

        if (MySQLConnection.isConnected()) {
            UtilsCLI.mensajeExitoConexionMySQL();
            alumnoData = new AlumnoDB();
            apoderadoData = new ApoderadoDB();
            profesorData = new ProfesorDB();
            cursoData = new CursoDB();
        } else {
            UtilsCLI.mensajeUtilizandoDatafile();
            alumnoData = new AlumnoDF();
            apoderadoData = new ApoderadoDF();
            profesorData = new ProfesorDF();
            cursoData = new CursoDF();
        }

        this.menuCLI = new MenuCLI(lector, alumnoData, apoderadoData, cursoData, profesorData);

    }

    /**
     * Método main. Contiene el programa principal, y la instancia del objeto CLI
     *
     * @param args Argumentos del main
     * @throws IOException Posibles errores de entrada/salida de datos
     */
    public static void main(String[] args) throws IOException {
        CLI cli = new CLI();
        cli.iniciarCLI();
        cli.finalizarCLI();
    }

    public void iniciarCLI() throws IOException{
        menuCLI.mostrarMenu("principal");
    }

    public void finalizarCLI() {
        UtilsCLI.mensajeDespedida();
    }

}
