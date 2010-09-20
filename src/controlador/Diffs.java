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

    public int diffTextual(String texto1, String texto2) {
        String montaStringParaComparacao = "";
        int contadorDeSimilaridade = 0;
        String linha1SemEspaco = texto1.replaceAll(" ", ""); //necessario para calcucar a estatistica
        System.out.println("stringggggggggggggggggggggggggggggg  => " + linha1SemEspaco);
        for (int i = 0; i < texto1.length(); i++) {
            if (texto1.charAt(i) != ' ') {          //varre todo textoe monta uma string para verificar se esta no texto2
                montaStringParaComparacao += texto1.charAt(i);
            }else{
                contadorDeSimilaridade += this.procurarSimilaridade(montaStringParaComparacao, texto2);
                montaStringParaComparacao = ""; //zera variavel para proxima comparacao
            }
        }
        contadorDeSimilaridade += this.procurarSimilaridade(montaStringParaComparacao, texto2); //caso se a string chega no final for e nao compara a string montada
        return (100* contadorDeSimilaridade / linha1SemEspaco.length()); //calcula a estatistica
    }

    private int procurarSimilaridade(String stringMontada, String textoDesejado) {
        if (textoDesejado.contains(stringMontada)) {
            return stringMontada.length();
        }
        return 0;
    }
}
