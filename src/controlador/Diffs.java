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

    /*
    public int diffTextual(String texto1, String texto2) {
    int tamanhoCaracterTexto1 = texto1.length();
    int tamanhoCaracterTexto2 = texto2.length();

    System.out.println("TOTAL DE CARACTER DO TEXTO 1 = " + tamanhoCaracterTexto1);
    System.out.println("TOTAL DE CARACTER DO TEXTO 2 = " + tamanhoCaracterTexto2);


    if (texto1.length() <= texto2.length()) {
    return varreString(texto1, texto2);
    } else {
    return varreString(texto2, texto1);
    }
    }

    private int varreString(String texto1, String texto2) {

    Scanner lerCadaLinhaTexto1 = new Scanner(texto1);
    Scanner lerCadaLinhaTexto2 = new Scanner(texto2);
    for (int i = 0; i < texto1.length(); i++) {
    String linhaCorrenteTexto1 = lerCadaLinhaTexto1.nextLine();
    String linhaCorrenteTexto2 = lerCadaLinhaTexto2.nextLine();
    if (texto1.charAt(i) == texto2.charAt(i)) {
    //                contadorDeSimilaridade++;
    }
    }
    //        System.out.println("TESTEEEEEEEEEEEEE   " + (contadorDeSimilaridade / texto1.length()) * 100);
    //        return (int) ((contadorDeSimilaridade / texto1.length()) * 100);
    return 0;

    }

    public int diffTextual2(String linha1, String linha2) {
    Scanner linhaString1 = new Scanner(linha1);
    Scanner linhaString2 = new Scanner(linha2);
    int contadorSimilaridade = 0;
    int tamanho = 0;
    boolean linha1Maior = false;
    boolean linha2Maior = false;
    if (linha1.length() < linha2.length()) {
    tamanho = linha1.length();
    linha2Maior = false;
    } else if (linha1.length() > linha2.length()) {
    tamanho = linha2.length();
    linha1Maior = true;
    } else {
    tamanho = linha1.length();
    }
    for (int i = 0; i < tamanho; i++) {
    if (linha1.charAt(i) == linha2.charAt(i)) {
    contadorSimilaridade++;
    }
    }
    //        if(linha1Maior){
    //            return  100 - (linha1.length() / contadorSimilaridade;
    //        }
    return contadorSimilaridade;
    }
     */
    public int diffTextual3(String linha1, String linha2) {
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
