package gems.ic.uff.br.modelo.algorithm;

import gems.ic.uff.br.modelo.similar.Similar;

public abstract class Hungarian<VALUE extends Similar> extends AbstractAlgorithm {

    private float[][] originalMatrix;
    protected int[][] result;

    private void calculateHungarian() {
        if (result == null) {
            originalMatrix = createSimilarityMatrix();

            float[][] matrixNormalizada = normalizarMatrix(copiarMatrix(originalMatrix));

            HungarianAlgorithm algorithm = new HungarianAlgorithm();
            result = algorithm.computeAssignments(matrixNormalizada);
        }
    }

    private float[][] createSimilarityMatrix() {
        int length = this.lengthOfX() > this.lengthOfY() ? this.lengthOfX() : this.lengthOfY();
        float[][] matrix;

        matrix = new float[length][length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i >= lengthOfX() || j >= lengthOfY()) {
                    matrix[i][j] = 0;
                } else {
                    Similar valueX = valueOfX(i);
                    Similar valueY = valueOfY(j);

                    float similarity = valueX.similar(valueY).getSimilarity();
                    matrix[i][j] = (similarity > similarThreshold) ? similarity : 0;
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

    public float[][] resultWithSimilarity() {
        calculateHungarian();
        float[][] array = new float[result.length][3];

        for (int i = 0; i < result.length; i++) {
            array[i][0] = result[i][0];
            array[i][1] = result[i][1];
            array[i][2] = originalMatrix[result[i][0]][result[i][1]];
        }
        
        return array;
    }
}
