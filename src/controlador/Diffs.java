/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author campello
 */
public class Diffs {

    public Diffs() {
    }

    public int diffTextual(String linha1, String linha2) {
        String comparador = "";
        int contadorDeSimilaridade = 0;
        String linha1SemEspaco = linha1.replaceAll(" ", "");
        System.out.println("stringggggggggggggggggggggggggggggg     " + linha1SemEspaco);
//        String linha2SemEspaco = linha2.trim();
        for (int i = 0; i < linha1.length(); i++) {
            if (linha1.charAt(i) == ' ') {
                contadorDeSimilaridade += this.procurarSimilaridade(comparador, linha2);
                comparador = "";
            }else{
                comparador += linha1.charAt(i);
            }
        }
        contadorDeSimilaridade += this.procurarSimilaridade(comparador, linha2); //caso se a string chega no final e nao compara
        return (100* contadorDeSimilaridade / linha1SemEspaco.length());
    }

    private int procurarSimilaridade(String valorDesejado, String stringComparada) {
        if (stringComparada.contains(valorDesejado)) {
            return valorDesejado.length();
        }
        return 0;
    }
}
