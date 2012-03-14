package gems.ic.uff.br.modelo.algorithm;

import gems.ic.uff.br.modelo.Diff;
import gems.ic.uff.br.modelo.similar.Similar;

public abstract class Hungarian<VALUE extends Similar> extends AbstractAlgorithm {

    protected float[][] originalMatrix;
    protected int[][] result;
    protected Diff[][] calculoSimilaridadeDosElementosCorrentes;

    private void calculateHungarian() {
        if (result == null) {
            originalMatrix = createSimilarityMatrix();
            //Matriz normalizada com todos os valores encontrados para encontrar o conjunto de elementos
            // com maior similaridade.
            float[][] matrixNormalizada = normalizarMatrix(copiarMatrix(originalMatrix));

            HungarianAlgorithm algorithm = new HungarianAlgorithm();
            result = algorithm.computeAssignments(matrixNormalizada);
        }
    }

    private float[][] createSimilarityMatrix() {
        int length = this.lengthOfX() > this.lengthOfY() ? this.lengthOfX() : this.lengthOfY();
        float[][] matrix;

        matrix = new float[length][length];
        calculoSimilaridadeDosElementosCorrentes = new Diff[length][length];
        for (int i = 0; i < matrix.length; i++) {
            Diff diffCorrente = null;
            for (int j = 0; j < matrix[i].length; j++) {
                if (i >= lengthOfX() || j >= lengthOfY()) {
                    matrix[i][j] = 0;
                } else {
                    Similar valueX = valueOfX(i);
                    Similar valueY = valueOfY(j);

                    diffCorrente = valueX.similar(valueY);
                    float similarity = diffCorrente.getSimilarity();
                    matrix[i][j] = (similarity > similarThreshold) ? similarity : 0;
                    calculoSimilaridadeDosElementosCorrentes[i][j] = diffCorrente;
                }
            }
        }
        return matrix;
    }

    private float[][] normalizarMatrix(float[][] matrix) {
        float maiorElementoMatrix = 0;

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

    private float[][] copiarMatrix(float[][] originalMatrix) {
        float[][] matrix = new float[originalMatrix.length][originalMatrix.length];

        for (int i = 0; i < originalMatrix.length; i++) {
            for (int j = 0; j < originalMatrix.length; j++) {
                matrix[i][j] = originalMatrix[i][j];
            }
        }

        return matrix;
    }

    @Override
    public float similaridade() {
        calculateHungarian();
        float similarity = 0;

        for (int i = 0; i < result.length; i++) {
            similarity += originalMatrix[result[i][0]][result[i][1]];
        }

        return similarity / result.length;
    }

    //METODO OBSOLOTE. NAO IREI APAGAR AGORA POIS EXISTEM TESTES QUE AINDA O USAM
    public float[][] resultWithSimilarity() {
        calculateHungarian();
        float[][] matrix = new float[result.length][3];

        for (int i = 0; i < result.length; i++) {
            matrix[i][0] = result[i][0];
            matrix[i][1] = result[i][1];
            matrix[i][2] = originalMatrix[result[i][0]][result[i][1]];
        }

        return matrix;
    }
}
