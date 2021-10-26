package utils.models.db;

import aplicacion.models.Alumno;
import utils.models.Criteria;

/**
 * Enum con las sentencias SQL utilizadas
 *
 * @author Sebastián García, Guillermo González, Benjamín Navarrete
 * @version 2.0
 */
public class SQLSentences {

    public static <T> Criteria[] getCriteria(T obj) {
        Criteria[] criteria = null;
        if (obj instanceof Alumno) {
            criteria = new Criteria[7];
            criteria[0] = new Criteria("nivel", (Integer.toString(((Alumno) obj).getNivel())), false);
            keys[1] = "paralelo";
            keys[2] = "rut";
            keys[3] = "nombres";
            keys[4] = "apellido_paterno";
            keys[5] = "apellido_materno";
            keys[6] = "rut_apoderado";
        }
        switch (tipo) {
            case "Alumnos":

            default:
                keys = null;
        }
        return criteria;
    }

    private static String criteriaToWhere(Criteria[] criteria) {
        boolean first = true;
        String txt = "";
        for (Criteria crit: criteria) {
            if (!first)
                txt = txt.concat(" AND ");
            txt = txt.concat(crit.get());
            first = false;
        }
        return txt;
    }

    private static String criteriaToIntoValues(Criteria[] criteria) {
        boolean first = true;
        String txt1 = "", txt2 = "";
        for (Criteria crit: criteria) {
            if (!first) {
                txt1 = txt1.concat(", ");
                txt2 = txt2.concat(", ");
            }
            txt1 = txt1.concat(crit.getKey());
            txt2 = txt2.concat(crit.getValue());
            first = false;
        }
        return String.format("(%s) VALUES (%s)", txt1, txt2);
    }

    private static String criteriaToSet(Criteria[] criteria) {
        boolean first = true;
        String txt = "";
        for (Criteria crit: criteria) {
            if (!first)
                txt = txt.concat(", ");
            txt = txt.concat(String.format("%s = %s", crit.getKey(), crit.getValue()));
            first = false;
        }
        return txt;
    }

    public static String select(String tipo) {
        return String.format("SELECT * FROM %s", tipo);
    }

    public static String select(String tipo, Criteria[] criteria) {
        if (criteria == null) return select(tipo);
        return String.format("SELECT * FROM %s WHERE %s", tipo, criteriaToWhere(criteria));
    }

    public static String insert(String tipo, Criteria[] criteria) {
        return String.format("INSERT INTO %s %s", tipo, criteriaToIntoValues(criteria));
    }

    public static String update(String tipo, Criteria[] criteria) {
        return String.format("UPDATE %s SET %s", tipo, criteriaToSet(criteria));
    }
}