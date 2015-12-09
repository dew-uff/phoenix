package br.uff.ic.gems.phoenix.algorithm;

import br.uff.ic.gems.phoenix.SettingsHelper;
import br.uff.ic.gems.phoenix.exception.ComparisonException;
import br.uff.ic.gems.phoenix.similarity.LcsSimilarity;
import static java.lang.Math.abs;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

/*
 * DataTypeSimilarity.java
 *
 * Calcula similaridade entre numeros, booleanos e datas
 *
 * @author igor
 */
public class DataTypeSimilarity {

    private double similarity;
    
    private static final List<SimpleDateFormat> engDateFormats = new ArrayList<SimpleDateFormat>() {
        {
            //add(new SimpleDateFormat("y-M-d'T'H:m:s.SZ"));
            //add(new SimpleDateFormat("y-M-d'T'H:m:sZ"));
            add(new SimpleDateFormat("y-M-d'T'H:m:s.S"));
            add(new SimpleDateFormat("y-M-d'T'H:m:s"));
            add(new SimpleDateFormat("M/d/y h:m:s a"));
            add(new SimpleDateFormat("M/d/y H:m:s"));
            add(new SimpleDateFormat("M-d-y h:m:s a"));
            add(new SimpleDateFormat("M-d-y H:m:s"));
            add(new SimpleDateFormat("M-d-y h:m:s z"));
            add(new SimpleDateFormat("E M d, h:m a z"));
            add(new SimpleDateFormat("M/d/y"));
            add(new SimpleDateFormat("M-d-y"));
            add(new SimpleDateFormat("y-M-d"));
            add(new SimpleDateFormat("M.d.y"));
            add(new SimpleDateFormat("M d y"));
        }
    };
    
    private static final List<SimpleDateFormat> ptDateFormats = new ArrayList<SimpleDateFormat>() {
        {
            //add(new SimpleDateFormat("y-M-d'T'H:m:s.SZ"));
            //add(new SimpleDateFormat("y-M-d'T'H:m:sZ"));
            add(new SimpleDateFormat("y-M-d'T'H:m:s.S"));
            add(new SimpleDateFormat("y-M-d'T'H:m:s"));
            add(new SimpleDateFormat("d/M/y h:m:s a"));
            add(new SimpleDateFormat("d/M/y H:m:s"));
            add(new SimpleDateFormat("d-M-y h:m:s a"));
            add(new SimpleDateFormat("d-M-y H:m:s"));
            add(new SimpleDateFormat("d-M-y h:m:s z"));
            add(new SimpleDateFormat("d/M/y"));
            add(new SimpleDateFormat("d-M-y"));
            add(new SimpleDateFormat("d.M.y"));
            add(new SimpleDateFormat("d M y"));
        }
    };
    
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

    private List<SimpleDateFormat> getDateFormats() {
        return SettingsHelper.getDateFormat().equalsIgnoreCase("pt") ? ptDateFormats : engDateFormats;
    }
    
    /**
     * Função que verifica qual o tipo do conteúdo do nó Senão conseguir
     * coverter para Double, Boolean ou Date, então será tratado como String
     *
     * @param _rightNode conteúdo do lado direito
     * @param _leftNode conteúdo do lado esquerdo
     * @return similaridade entre o _rightNode e o _leftNode
     */
    public double verifyTypeAndCalculateSimilarity(String _rightNode, String _leftNode) throws ComparisonException {
        double DRightNode, DLeftNode;

        try {
            String auxRightNode = _rightNode.replace(",", ".");
            String auxLeftNode = _leftNode.replace(",", ".");
            DRightNode = Double.parseDouble(auxRightNode);
            DLeftNode = Double.parseDouble(auxLeftNode);

            similarity = NumberSimilarity(abs(DRightNode), abs(DLeftNode));
            return similarity;
        } catch (NumberFormatException e) {

            if ((_rightNode.equalsIgnoreCase("true") || _rightNode.equalsIgnoreCase("false"))
                    && (_leftNode.equalsIgnoreCase("true") || _leftNode.equalsIgnoreCase("false"))) {

                boolean BRightNode = Boolean.valueOf(_rightNode);
                boolean BLeftNode = Boolean.valueOf(_leftNode);
                
                if (BRightNode == BLeftNode) {
                    return 1;
                } else {
                    return 0;
                }
            } else { // tenta converter para data
                Date dateX = new Date();
                Date dateY = new Date();
                int indexDate1, indexDate2;
                indexDate1 = indexDate2 = 0;
                String auxRightNode = _rightNode.replaceAll("\"", "");
                String auxLeftNode = _leftNode.replaceAll("\"", "");

                for (SimpleDateFormat format : getDateFormats()) {
                    try {
                        format.setLenient(false);
                        dateX = format.parse(auxRightNode);
                        break;
                    } catch (ParseException f) {
                        indexDate1++;
                    }
                }

                for (SimpleDateFormat format : getDateFormats()) {
                    try {
                        format.setLenient(false);
                        dateY = format.parse(auxLeftNode);
                        if (indexDate1 < getDateFormats().size() && indexDate2 < getDateFormats().size()) {
                            similarity = NumberSimilarity(abs((double) dateX.getTime()), abs((double) dateY.getTime()));
                            //System.out.println("\ncase 3 data: " + similarity);
                            //System.out.println("(DATE) Similarity: "+ similarity +" -> R: "+ _rightNode + " - L: "+ _leftNode);
                            //System.out.println("DATE_DEBUG3: "+ dateX.toGMTString() +" ---- "+ dateY.toGMTString());
                            return similarity;
                        }
                    } catch (ParseException f) {
                        indexDate2++;
                    }
                }
            }
            // se nenhum tipo deu certo, sera tradado como string
            similarity = LcsSimilarity.calculateStringSimilarity(_leftNode, _rightNode);

            return similarity;
        }
    }

    /**
     * Calcula a similaridade entre dois números
     *
     * @param x conteúdo do rightNode
     * @param y conteúdo do leftNode
     * @return similaridade entre os dois números
     *
     */
    private double NumberSimilarity(double x, double y) {
        if (x + y != 0) { // nao é permitido numeros negativos, portanto se x+y = 0, tanto x quanto y sao 0
            if (x > y) {
                return (double) ((y - x) / x) + 1;
            } else {
                return (double) ((x - y) / y) + 1;
            }
        } else {
            return 1; // x e y sao 0, ou seja, sao iguais, similaridade 1
        }
    }

}
