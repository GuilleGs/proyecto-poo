package aplicacion.controllers.cli;

import aplicacion.models.Alumno;
import aplicacion.models.Apoderado;
import aplicacion.models.Curso;
import aplicacion.models.id.IDCurso;
import aplicacion.views.cli.AlumnoViewCLI;
import aplicacion.views.cli.menus.MenuCLI;
import utils.UtilsCLI;

import java.io.IOException;

/**
 * Clase controladora del menú de gestión de Alumnos de la interfaz de linea de comandos.
 *
 * @author Sebastián García, Guillermo González, Benjamín Navarrete
 * @version 2.0
 */
public class AlumnoControllerCLI {

    private final MenuCLI menuCLI;


    /**
     * Objeto controlador de Alumno en la interfaz de línea de comandos
     *
     * @param menuCLI Instancia de menú con los origenes de datos.
     */
    public AlumnoControllerCLI(MenuCLI menuCLI) {
        this.menuCLI = menuCLI;
    }

    /**
     * Solicita los datos necesarios al usuario para crear un objeto de tipo Alumno.
     *
     * @param nivel    Nivel del alumno a generar
     * @param paralelo Paralelo del curso al que pertenece el alumno
     * @return Alumno
     * @throws IOException Posibles errores de entrada/salida de datos
     */
    public Alumno obtenerDatosAlumno(int nivel, char paralelo) throws IOException {
        int telefonoAp;
        String rut, nombres, apPat, apMat, rutAp, nombresAp, apPatAp, apMatAp, emailAp;
        UtilsCLI.imprimirSolicitar("el RUT del alumno", "RUT");
        rut = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("los nombres del alumno", "texto");
        nombres = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el apellido paterno del alumno", "texto");
        apPat = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el apellido materno del alumno", "texto");
        apMat = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el RUT del apoderado", "RUT");
        rutAp = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("los nombres del apoderado", "texto");
        nombresAp = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el apellido paterno del apoderado", "texto");
        apPatAp = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el apellido materno del apoderado", "texto");
        apMatAp = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el email del apoderado", "texto");
        emailAp = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el teléfono del apoderado", "texto");
        telefonoAp = Integer.parseInt(this.menuCLI.getLector().readLine());
        return new Alumno(rut, nombres, apPat, apMat, nivel, paralelo, new Apoderado(rutAp, nombresAp, apPatAp, apMatAp,
                telefonoAp, emailAp));
    }

    /**
     * Solicita los datos necesarios al usuario para crear un objeto de tipo Alumno, generado desde la edición de un
     * alumno inicial.
     *
     * @param alumnoOriginal Alumno original (para actualizar datos)
     * @param nivel          Nivel del alumno a generar
     * @param paralelo       Paralelo del curso al que pertenece el alumno
     * @return Alumno
     * @throws IOException Posibles errores de entrada/salida de datos
     */
    public Alumno obtenerDatosAlumno(Alumno alumnoOriginal, int nivel, char paralelo) throws IOException {
        String nombres, apPat, apMat;
        UtilsCLI.imprimirSolicitar("los nombres del alumno", "texto");
        nombres = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el apellido paterno del alumno", "texto");
        apPat = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el apellido materno del alumno", "texto");
        apMat = this.menuCLI.getLector().readLine();
        return new Alumno(alumnoOriginal.getRut(), nombres, apPat, apMat, nivel, paralelo,
                alumnoOriginal.getApoderado());
    }

    public void agregarAlumno(CursoControllerCLI cursoController) throws IOException {
        IDCurso idCurso;
        Alumno alumno;

        idCurso = cursoController.obtenerIDCurso();
        alumno = obtenerDatosAlumno(idCurso.nivel, idCurso.paralelo);
        this.menuCLI.getAlumnoData().insert(alumno);
        UtilsCLI.imprimirPersona(alumno);
    }

    public void verAlumnos(CursoControllerCLI cursoController) throws IOException {
        IDCurso idCurso;
        idCurso = cursoController.obtenerIDCurso();
        AlumnoViewCLI.mostrarTablaAlumnos(this.menuCLI.getAlumnoData().getAll().get(),
                Curso.cursoToString(idCurso.nivel, idCurso.paralelo));
    }

    public void verAlumno() throws IOException {
        String rut;
        Alumno alumno;

        UtilsCLI.imprimirSolicitar("el RUT del alumno", "sin puntos, con guión");
        rut = this.menuCLI.getLector().readLine();
        alumno = this.menuCLI.getAlumnoData().get(rut);
        if (alumno != null)
            UtilsCLI.imprimirPersona(alumno);
        else
            UtilsCLI.mensajeErrorNoEncontrado("alumno", 0);
    }

    public void editarAlumno() throws IOException {
        String rut;
        Alumno alumno, alumnoEditado;

        UtilsCLI.imprimirSolicitar("el RUT del alumno", "sin puntos, con guión");
        rut = this.menuCLI.getLector().readLine();
        alumno = this.menuCLI.getAlumnoData().get(rut);
        if (alumno == null) {
            UtilsCLI.mensajeErrIngresado();
            return;
        }
        System.out.println("Editando alumno:\n  Nombre: " + alumno.getNombreCompleto() + "\n  RUT: " + rut);
        alumnoEditado = obtenerDatosAlumno(alumno, alumno.getNivel(), alumno.getParalelo());
        this.menuCLI.getAlumnoData().update(alumnoEditado, null);
    }

    public void eliminarAlumno() throws IOException {
        Alumno alumno;
        String rut;

        UtilsCLI.imprimirSolicitar("el RUT del alumno", "sin puntos, con guión");
        rut = this.menuCLI.getLector().readLine();
        alumno = this.menuCLI.getAlumnoData().get(rut);
        if (alumno == null) {
            UtilsCLI.mensajeErrIngresado();
            return;
        }
        System.out.println("Eliminando alumno:\n  Nombre: " + alumno.getNombreCompleto() + "\n  RUT: " + rut);
        this.menuCLI.getAlumnoData().delete(alumno);
    }

}
