package br.uff.ic.gems.phoenix.algorithm;

import br.uff.ic.gems.phoenix.SettingsHelper;
import br.uff.ic.gems.phoenix.exception.ComparisonException;
import br.uff.ic.gems.phoenix.similarity.LcsSimilarity;
import static java.lang.Math.abs;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/*
 * DataTypeSimilarity.java
 *
 * Calcula similaridade entre numeros, booleanos e datas
 *
 * @author igor
 */
public class DataTypeSimilarity {

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
     * @throws br.uff.ic.gems.phoenix.exception.ComparisonException
     */
    public double verifyTypeAndCalculateSimilarity(String _rightNode, String _leftNode) throws ComparisonException {

        // Number
        try {
            double DRightNode = Double.parseDouble(_rightNode.replace(",", "."));
            double DLeftNode = Double.parseDouble(_leftNode.replace(",", "."));

            return NumberSimilarity(abs(DRightNode), abs(DLeftNode));
        } catch (NumberFormatException e) {
            // Not a Number
        }

        // Boolean
        String rightBool = _rightNode.trim();
        String leftBool = _leftNode.trim();
        if ((rightBool.equalsIgnoreCase("true") || rightBool.equalsIgnoreCase("false"))
                && (leftBool.equalsIgnoreCase("true") || leftBool.equalsIgnoreCase("false"))) {

            boolean BRightNode = Boolean.valueOf(rightBool);
            boolean BLeftNode = Boolean.valueOf(leftBool);

            return BRightNode == BLeftNode ? 1.0 : 0.0;
        }
        
        // Date
        Date dateRight = null;
        Date dateLeft;

        for (SimpleDateFormat format : getDateFormats()) {
            try {
                format.setLenient(false);
                dateRight = format.parse(_rightNode);
                break;
            } catch (ParseException f) {
                // Parse Fail
            }
        }

        if (dateRight != null) {
            for (SimpleDateFormat format : getDateFormats()) {
                try {
                    format.setLenient(false);
                    dateLeft = format.parse(_leftNode);
                    
                    return NumberSimilarity(abs((double) dateRight.getTime()), abs((double) dateLeft.getTime()));
                } catch (ParseException f) {
                    // Parse Fail
                }
            }
        }

        // String (Lcs)
        return LcsSimilarity.calculateStringSimilarity(_leftNode, _rightNode);
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
