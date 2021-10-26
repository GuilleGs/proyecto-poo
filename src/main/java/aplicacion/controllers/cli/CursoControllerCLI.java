package aplicacion.controllers.cli;

import utils.models.df.DataFile;
import aplicacion.models.Alumno;
import aplicacion.models.Curso;
import aplicacion.models.id.IDCurso;
import aplicacion.models.Profesor;
import aplicacion.views.cli.CursoViewCLI;
import aplicacion.views.cli.menus.MenuCLI;
import utils.UtilsCLI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Clase controladora del menú de gestión de Cursos de la interfaz de linea de comandos.
 *
 * @author Sebastián García, Guillermo González, Benjamín Navarrete
 * @version 2.0
 */
public class CursoControllerCLI {
    private final MenuCLI menuCLI;

    /**
     * Objeto controlador de Curso en la interfaz de línea de comandos
     *
     * @param menuCLI Instancia de menú con los origenes de datos.
     */
    public CursoControllerCLI(MenuCLI menuCLI) {
        this.menuCLI = menuCLI;
    }

    /**
     * Genera el Datafile correspondiente a la generación de un reporte
     *
     * @param tipo Tipo de reporte
     * @param id   ID del reporte
     * @return Datafile con el reporte correspondiente.
     */
    private DataFile generarReporte(String tipo, String id) {
        return new DataFile("reportes/" + tipo + "-" + id);
    }

    /**
     * Solicita los datos necesarios al usuario para crear un objeto de tipo Curso
     *
     * @return Curso
     * @throws IOException Posibles errores de entrada/salida de datos
     */
    public Curso obtenerDatosCurso() throws IOException {
        int telefono;
        IDCurso idCurso;
        String rut, nombres, apPat, apMat, email, asignatura;

        idCurso = this.obtenerIDCurso();
        UtilsCLI.imprimirSolicitar("el RUT del profesor jefe", "RUT");
        rut = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("los nombres del profesor jefe", "texto");
        nombres = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el apellido paterno del profesor jefe", "texto");
        apPat = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el apellido materno del profesor jefe", "texto");
        apMat = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("la asignatura del profesor jefe", "texto");
        asignatura = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el email del profesor jefe", "texto");
        email = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el teléfono del profesor jefe", "texto");
        telefono = Integer.parseInt(this.menuCLI.getLector().readLine());
        return new Curso(idCurso.nivel, idCurso.paralelo, new Profesor(rut, nombres, apPat, apMat, asignatura, email,
                telefono));
    }

    /**
     * Solicita los datos necesarios al usuario para editar el profesor jefe de un curso definido.
     *
     * @param cursoOriginal Curso original a basar la información
     * @return Curso
     * @throws IOException Posibles errores de entrada/salida de datos
     */
    public Curso obtenerDatosCurso(Curso cursoOriginal) throws IOException {
        int telefono;
        String rut, nombres, apPat, apMat, email, asignatura;
        UtilsCLI.imprimirSolicitar("el RUT del profesor jefe", "RUT");
        rut = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("los nombres del profesor jefe", "texto");
        nombres = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el apellido paterno del profesor jefe", "texto");
        apPat = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el apellido materno del profesor jefe", "texto");
        apMat = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("la asignatura del profesor jefe", "texto");
        asignatura = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el email del profesor jefe", "texto");
        email = this.menuCLI.getLector().readLine();
        UtilsCLI.imprimirSolicitar("el teléfono del profesor jefe", "texto");
        telefono = Integer.parseInt(this.menuCLI.getLector().readLine());
        return new Curso(cursoOriginal.getNivel(), cursoOriginal.getParalelo(), new Profesor(rut, nombres, apPat, apMat,
                asignatura, email, telefono));
    }

    public IDCurso obtenerIDCurso() throws IOException {
        short nivel;
        char paralelo;

        UtilsCLI.imprimirSolicitar("el nivel del curso", "número de 1 a 12");
        nivel = Short.parseShort(this.menuCLI.getLector().readLine());
        while ((nivel < 1) || (nivel > 12)) {
            UtilsCLI.imprimirSolicitar("el nivel del curso", "número de 1 a 12");
            nivel = Short.parseShort(this.menuCLI.getLector().readLine());
        }

        UtilsCLI.imprimirSolicitar("el paralelo del curso", "caracter");
        paralelo = this.menuCLI.getLector().readLine().charAt(0);
        while (!Character.isAlphabetic(paralelo)) {
            UtilsCLI.imprimirSolicitar("el nivel del curso", "número de 1 a 12");
            paralelo = this.menuCLI.getLector().readLine().charAt(0);
        }

        return new IDCurso(nivel, paralelo);
    }

    /**
     * Método que permite generar el reporte con la lista de un curso específico.
     *
     * @param nivel Nivel del curso a generar reporte
     * @param paralelo Letra identificadora de paralelo del curso
     * @return String con la ruta del archivo generado.
     */
    public String generarReporteListaCurso(short nivel, char paralelo) {
        DataFile reporteListaCurso = generarReporte("lista-curso", (nivel + "-" + paralelo));
        reporteListaCurso.insertLine(DataFile.listToCSV(Arrays.asList(UtilsCLI.headers.get("alumnos"))));
        Map<String, Alumno> alumnos = this.menuCLI.getCursoData().get(new IDCurso(nivel, paralelo)).getAlumnos();
        List<String> line = new ArrayList<>();
        for (Alumno alumno : alumnos.values()) {
            line.add(alumno.getRut());
            line.add(alumno.getApPaterno());
            line.add(alumno.getApMaterno());
            line.add(alumno.getNombres());
            line.add(alumno.getApoderado().getNombreCompleto());
            line.add(Integer.toString(alumno.getApoderado().getTelefono()));
            reporteListaCurso.insertLine(DataFile.listToCSV(line));
            line.clear();
        }
        return reporteListaCurso.getFilePath();

    }

    /**
     * Método que permite generar el reporte con la tabla de cursos.
     *
     * @return String con la ruta del archivo generado.
     */
    public String generarReporteTablaCursos() {
        DataFile reporteTablaCursos = generarReporte("tabla-cursos",
                Integer.toString((int) Math.floor(Math.random() * (9000) + 1000)));
        reporteTablaCursos.insertLine(DataFile.listToCSV(Arrays.asList(UtilsCLI.headers.get("cursos"))));
        List<Curso> cursos = this.menuCLI.getCursoData().getAll().get();
        List<String> line = new ArrayList<>();
        for (Curso curso : cursos) {
            line.add(curso.toShortStr());
            line.add(Short.toString(curso.getNivel()));
            line.add(Character.toString(curso.getParalelo()));
            line.add(curso.getProfesorJefe().getNombreCompleto());
            line.add(curso.getProfesorJefe().getEmail());
            line.add(Integer.toString(curso.getProfesorJefe().getTelefono()));
            reporteTablaCursos.insertLine(DataFile.listToCSV(line));
            line.clear();
        }
        return reporteTablaCursos.getFilePath();
    }

    public void agregarCurso() throws IOException {
        this.menuCLI.getCursoData().insert(this.obtenerDatosCurso());
        CursoViewCLI.mostrarTablaCursos(this.menuCLI.getCursoData().getAll().get());
    }

    public void mostrarCursos() {
        CursoViewCLI.mostrarTablaCursos(this.menuCLI.getCursoData().getAll().get());
    }

    public void asignarNuevoProfesorJefeACurso() throws IOException {
        IDCurso idCurso;
        Curso curso, cursoActualizado;
        idCurso = this.obtenerIDCurso();
        curso = this.menuCLI.getCursoData().get(idCurso);
        System.out.println("Cambiando profesor jefe del " + curso.cursoToString());
        cursoActualizado = this.obtenerDatosCurso(curso);
        this.menuCLI.getCursoData().update(cursoActualizado, null);
        this.menuCLI.getProfesorData().insert(cursoActualizado.getProfesorJefe());
        this.menuCLI.getProfesorData().delete(curso.getProfesorJefe());
    }

    public void eliminarCurso() throws IOException {
        IDCurso idCurso;
        Curso curso;

        idCurso = obtenerIDCurso();
        curso = this.menuCLI.getCursoData().get(idCurso);
        System.out.println("Eliminando curso " + curso.cursoToString());
        this.menuCLI.getCursoData().delete(curso);
    }

    public void verDatosCurso() throws IOException {
        IDCurso idCurso;
        Curso curso;

        idCurso = obtenerIDCurso();
        curso = this.menuCLI.getCursoData().get(idCurso);
        if (curso != null)
            UtilsCLI.imprimirCurso(curso);
        else
            UtilsCLI.mensajeErrorNoEncontrado("curso", 0);
    }

}
