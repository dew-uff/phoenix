package gems.ic.uff.br.modelo.algorithm;

import gems.ic.uff.br.modelo.Diff;
import gems.ic.uff.br.modelo.similar.Similar;

public abstract class Hungarian<VALUE extends Similar> extends AbstractAlgorithm {

    protected double[][] originalMatrix;
    protected int[][] result;
    protected Diff[][] calculaSimilaridadeDosElementosCorrentes;
    protected double similaridadeCalculada = -1;

    private void calculateHungarian() {
        if (result == null) {
            originalMatrix = createSimilarityMatrix();
            //Matriz normalizada com todos os valores encontrados para encontrar
            //o conjunto de elementos
            // com maior similaridade.
            double[][] matrixNormalizada = normalizarMatrix(copiarMatrix(originalMatrix));

            HungarianAlgorithm algorithm = new HungarianAlgorithm(matrixNormalizada);
            int[] results = algorithm.execute();
            result = new int[results.length][];
            for(int i=0; i < results.length; i++) {
            	result[i] = new int[]{i,results[i]}; 
            }
        }
    }

    private double[][] createSimilarityMatrix() {
        double[][] matrix;

        matrix = new double[lengthOfX()][lengthOfY()];
        calculaSimilaridadeDosElementosCorrentes = new Diff[lengthOfX()][lengthOfY()];
        for (int i = 0; i < matrix.length; i++) {
            Diff diffCorrente = null;
            for (int j = 0; j < matrix[i].length; j++) {
                Similar valueX = valueOfX(i);
                Similar valueY = valueOfY(j);

                diffCorrente = valueX.similar(valueY);
                double similarity = diffCorrente.getSimilarity();
                matrix[i][j] = (similarity >= similarThreshold) ? similarity : 0;
                calculaSimilaridadeDosElementosCorrentes[i][j] = diffCorrente;
            }
        }
        return matrix;
    }

    private double[][] normalizarMatrix(double[][] matrix) {
        double maiorElementoMatrix = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > maiorElementoMatrix) {
                    maiorElementoMatrix = matrix[i][j];
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = maiorElementoMatrix - matrix[i][j];
            }
        }

        return matrix;
    }

    private double[][] copiarMatrix(double[][] originalMatrix) {
        double[][] matrix = new double[originalMatrix.length][originalMatrix[0].length];

        for (int i = 0; i < originalMatrix.length; i++) {
            for (int j = 0; j < originalMatrix[0].length; j++) {
                matrix[i][j] = originalMatrix[i][j];
            }
        }

        return matrix;
    }

    @Override
    public float similaridade() {
        if (similaridadeCalculada == -1) {
        	calculateHungarian();
            double similarity = 0;

            for (int i = 0; i < result.length; i++) {
            	int row = result[i][0];
            	int col = result[i][1];
            	if (row != -1 && col != -1) {
            		similarity += originalMatrix[row][col];
            	}
            }
            int length = lengthOfX()>lengthOfY()?lengthOfX():lengthOfY(); 
            similaridadeCalculada = similarity / length;
        }

        return (float) similaridadeCalculada;
    }

    //METODO OBSOLOTE. NAO IREI APAGAR AGORA POIS EXISTEM TESTES QUE AINDA O USAM
    public double[][] resultWithSimilarity() {
        calculateHungarian();
        double[][] matrix = new double[result.length][3];

        for (int i = 0; i < result.length; i++) {
            matrix[i][0] = result[i][0];
            matrix[i][1] = result[i][1];
            matrix[i][2] = originalMatrix[result[i][0]][result[i][1]];
        }

        return matrix;
    }
}
