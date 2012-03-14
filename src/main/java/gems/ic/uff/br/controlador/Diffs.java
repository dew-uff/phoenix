package gems.ic.uff.br.controlador;

/**
 *
 * @author campello
 */
public class Diffs {

    public static int diffTextual(String texto1, String texto2) {
        String stringParaComparacao = "";
        int contadorDeSimilaridade = 0;
        String linha1SemEspaco = texto1.replaceAll(" ", ""); //necessario para calcucar a estatistica
        
        //System.out.println("stringggggggggggggggggggggggggggggg  => " + linha1SemEspaco);
        
        for (int i = 0; i < texto1.length(); i++) {
            if (texto1.charAt(i) != ' ') {          //varre todo textoe monta uma string para verificar se esta no texto2
                stringParaComparacao += texto1.charAt(i);
            }else{
                contadorDeSimilaridade += procurarSimilaridade(stringParaComparacao, texto2);
                stringParaComparacao = ""; //zera variavel para proxima comparacao
            }
        }
        
        contadorDeSimilaridade += procurarSimilaridade(stringParaComparacao, texto2); //caso se a string chega no final for e nao compara a string montada
        return (100 * contadorDeSimilaridade / linha1SemEspaco.length()); //calcula a estatistica
    }

    private static int procurarSimilaridade(String stringMontada, String textoDesejado) {
        if (textoDesejado.contains(stringMontada)) {
            return stringMontada.length();
        }
        return 0;
    }
}
