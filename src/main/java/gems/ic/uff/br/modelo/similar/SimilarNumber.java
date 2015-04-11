package gems.ic.uff.br.modelo.similar;

import static java.lang.Math.abs;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import gems.ic.uff.br.modelo.LcsString;

/*
 * SimilarNumber.java
 *
 * Calcula similaridade entre numeros, booleanos e datas
 *
 * @author igor
 */

public class SimilarNumber {
	
	private float similarity;
	private List<SimpleDateFormat> dateFormats = new ArrayList<SimpleDateFormat>() {
        {
        	// padroes en
            add(new SimpleDateFormat("M/dd/yyyy")); //0
            add(new SimpleDateFormat("M/dd/yy")); //1
            add(new SimpleDateFormat("M-dd-yyyy"));
            add(new SimpleDateFormat("yyyy-M-dd"));
            add(new SimpleDateFormat("M-dd-yy")); 
            add(new SimpleDateFormat("MMM-dd-yy")); 
            add(new SimpleDateFormat("M.dd.yyyy")); 
            add(new SimpleDateFormat("M.dd.yy")); 
            add(new SimpleDateFormat("M d yyyy")); 
            add(new SimpleDateFormat("M/dd/yyyy hh:mm:ss a")); 
            add(new SimpleDateFormat("M/dd/yy hh:mm:ss a")); 
            add(new SimpleDateFormat("M-d-yyyy hh:mm:ss a")); 
            add(new SimpleDateFormat("MMM-d-yy hh:mm:ss z")); 
            add(new SimpleDateFormat("EEE MMM d, hh:mm a z")); //13

            //padroes pt
            add(new SimpleDateFormat("dd/M/yyyy")); //14
            add(new SimpleDateFormat("dd/M/yy"));
            add(new SimpleDateFormat("dd-M-yyyy")); 
            add(new SimpleDateFormat("dd-M-yy")); 
            add(new SimpleDateFormat("dd.M.yyyy")); 
            add(new SimpleDateFormat("dd.M.yy")); 
            add(new SimpleDateFormat("d M yyyy")); 
            add(new SimpleDateFormat("d/MM/yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("d/MM/yy hh:mm:ss a"));
            add(new SimpleDateFormat("d-M-yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("d-MMM-yy hh:mm:ss z")); //24

        }
    };
    
	/**
     * Função que verifica qual o tipo do conteúdo do nó
     * Senão conseguir coverter para Double, Boolean ou Date, então será tratado como String
     * 
     * @param _rightNode conteúdo do lado direito
     * @param _leftNode conteúdo do lado esquerdo
     * @return similaridade entre o _rightNode e o _leftNode
     */
    float verifyTypeAndCalculateSimilarity(String _rightNode, String _leftNode){
    	double DRightNode,DLeftNode;
    	
    	try{
    		String auxRightNode = _rightNode.replace(",", ".");
    		String auxLeftNode = _leftNode.replace(",", ".");
    		DRightNode = Double.parseDouble(auxRightNode);
    		DLeftNode = Double.parseDouble(auxLeftNode);
	    	
	    	//System.out.println("X: " + Dx);
	    	//System.out.println("Y: " + Dy);
	    	
	    	similarity = NumberSimilarity(abs(DRightNode),abs(DLeftNode));
	    	//System.out.println("R: " +DRightNode+ " - L: " + DLeftNode);
	    	//System.out.println("1: " + similarity);
	    	return similarity;
    	}
    	catch(NumberFormatException e){
    		//System.err.println("Exceção 1 [ao converter para DOUBLE]: " + e);
    		
    		if (_rightNode.equalsIgnoreCase("true") || _rightNode.equalsIgnoreCase("false") &&
    				_leftNode.equalsIgnoreCase("true") || _leftNode.equalsIgnoreCase("false")) {
                
    			boolean BRightNode = Boolean.valueOf(_rightNode);
                boolean BLeftNode = Boolean.valueOf(_leftNode);

               // System.out.println("X: " + Bx);
               // System.out.println("Y: " + By);
                
                if(BRightNode == BLeftNode)
                	return 1;
                else
                	return 0;
            }
    		else{ // tenta converter para data
    			Date dateX = new Date();
    	        Date dateY = new Date();
    	        int indexDate1, indexDate2;
    	        indexDate1 = indexDate2 = 0;
    	        String auxRightNode = _rightNode.replaceAll("\"", "");
        		String auxLeftNode = _leftNode.replaceAll("\"", "");
    	        
    	        for (SimpleDateFormat format : dateFormats) {
    	            try {
    	            	format.setLenient(false);
    	            	dateX = format.parse(auxRightNode);
    	                //System.out.println("data: " + dateX);
    	                break;
    	            } catch (ParseException f) {
    	                indexDate1++;
    	                //System.err.println(indexDate1 + ": " + f);
    	            }
    	        }
    	        
    	        // se as datas foram convertidas em linguas diferentes (EN / PT), sinal que as duas devem estar na mesma
    	        // lingua, porem nao gerou excessao pois o dia do mes era inferior a 12, por isso a que nao gerou excessao sera tratada
    	        // no formato da que gerou.
    	        for (SimpleDateFormat format : dateFormats) {
    	            try {
    	            	format.setLenient(false);
    	            	dateY = format.parse(auxLeftNode);
    	                if (indexDate1 < 14 && indexDate2 > 13 && indexDate2 < 25) { // converte a primeira data no formato da segunda
    	                	dateX = dateFormats.get(indexDate2).parse(auxRightNode); 
    	                    //System.out.println("data2: " + dateY);
    	                	similarity = NumberSimilarity(abs((double)dateX.getTime()), abs((double)dateY.getTime()));
    	                	//System.out.println("case 1 data: " + similarity);
    	                	return similarity;
    	                }
    	                else if(indexDate1 > 13 && indexDate1 < 25 && indexDate2 < 14){ // converte a segunda data no formato da primeira
    	                	dateY = dateFormats.get(indexDate1).parse(auxLeftNode);
    	                	similarity = NumberSimilarity(abs((double)dateX.getTime()), abs((double)dateY.getTime()));
    	                	//System.out.println("case 2 data: " + similarity);
    	                	return similarity;
    	                }
    	                else if (indexDate1 < 25 && indexDate2 < 25){             
    	                	similarity = NumberSimilarity(abs((double)dateX.getTime()), abs((double)dateY.getTime()));
    	                	//System.out.println("case 3 data: " + similarity);
    	                	return similarity;
    	                }
    	            } catch (ParseException f) {
    	                indexDate2++;
    	                //System.err.println(indexDate2 + ": " + f);
    	            }
    	        }
    		}
    		// se nenhum tipo deu certo, sera tradado como string
    		LcsString cmp = new LcsString(_leftNode, _rightNode);
			return cmp.similaridade();
    	}
    }
    
    /**
     * Calcula a similaridade entre dois números
     * @param x conteúdo do rightNode
     * @param y conteúdo do leftNode
     * @return similaridade entre os dois números
     * */
    float NumberSimilarity(double x,double y){
    	if (x+y != 0){ // nao é permitido numeros negativos, portanto se x+y = 0, tanto x quanto y sao 0
	        if(x>y)
	        	return (float)((y-x)/x)+1;
	        else 
	        	return (float)((x-y)/y)+1;
        }
    	else return 1; // x e y sao 0, ou seja, sao iguais, similaridade 1
    }

}
