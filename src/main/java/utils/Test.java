package utils;

import utils.models.Criteria;
import utils.models.db.SQLSentences;

public class Test {

    public static void main(String[] args) {

        SQLSentences sqlSentences = new SQLSentences();
        Criteria[] criteria = new Criteria[2];
        criteria[0] = new Criteria("rut", "12345678-9", "=", true);
        criteria[1] = new Criteria("nombre", "Sebastian", "=", true);

        System.out.println(sqlSentences.insert("Alumnos", criteria));

    }

}