package br.uff.ic.gems.phoenix.similarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import br.uff.ic.gems.phoenix.SettingsHelper;
import br.uff.ic.gems.phoenix.algorithm.HungarianAlgorithm;
import br.uff.ic.gems.phoenix.diff.DiffNode;
import br.uff.ic.gems.phoenix.diff.ElementDiffNode;
import br.uff.ic.gems.phoenix.diff.OneSideElementDiffNode;
import br.uff.ic.gems.phoenix.exception.ComparisonException;

public class HungarianSimilarity {
    
    private static final double NOT_CALCULATED = -1.0;
    
    private List<Node> leftNodes, rightNodes;
    private double similarity = NOT_CALCULATED;
    private ElementDiffNode[] resultNodes = null;
    private ElementSimilarityResult[][] tempResultMatrix;
    
    public HungarianSimilarity(List<Node> leftNodes, List<Node> rightNodes) {
        this.leftNodes = leftNodes;
        this.rightNodes = rightNodes;
    }
    
    public double calculateSimilarity() throws ComparisonException {
        
        if (similarity != NOT_CALCULATED) {
            return similarity;
        }
        
        int lnodesSize = leftNodes.size();
        int rnodesSize = rightNodes.size();
        
        if (lnodesSize == 0 || rnodesSize == 0) {
            
            if (lnodesSize > 0) {
                resultNodes = new ElementDiffNode[lnodesSize];
                for (int i = 0; i < lnodesSize; i++) {
                    resultNodes[i] = new OneSideElementDiffNode(leftNodes.get(i), DiffNode.SIDE_LEFT);
                }
            } else {
                resultNodes = new ElementDiffNode[rnodesSize];
                for (int i = 0; i < rnodesSize; i++) {
                    resultNodes[i] = new OneSideElementDiffNode(rightNodes.get(i), DiffNode.SIDE_RIGHT);
                }                
            }
            
            similarity = 0.0;            
            return similarity;
        }
        
        double[][] simMatrix = createSimilarityMatrix(leftNodes,rightNodes);
        double[][] normMatrix = normalizeMatrix(simMatrix);
        
        HungarianAlgorithm algorithm = new HungarianAlgorithm(normMatrix);
        int[] algResults = algorithm.execute();
        
        similarity = processHungarianResult(algResults, simMatrix);
        return similarity;
    }
    
    private double[][] createSimilarityMatrix(List<Node> nodes1, List<Node> nodes2) throws ComparisonException {
        
        double[][] matrix = new double[nodes1.size()][nodes2.size()];
        tempResultMatrix = new ElementSimilarityResult[nodes1.size()][nodes2.size()];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Element e1 = (Element) nodes1.get(i);
                Element e2 = (Element) nodes2.get(j);
                ElementSimilarity es = new ElementSimilarity(false);
                tempResultMatrix[i][j] = es.compare(e1, e2);
                // get the real similarity even if it does not reach threshold
                // this is to guarantee HungarianAlgorithm results
                matrix[i][j] = tempResultMatrix[i][j].getRealSimilarity();
            }
        }
        return matrix;
    }
    
    private static double[][] normalizeMatrix(double[][] orig) {
        
        double[][] matrix = new double[orig.length][orig[0].length];
        
        double maiorElementoMatrix = 0;

        for (int i = 0; i < orig.length; i++) {
            for (int j = 0; j < orig[i].length; j++) {
                if (orig[i][j] > maiorElementoMatrix) {
                    maiorElementoMatrix = orig[i][j];
                }
            }
        }

        for (int i = 0; i < orig.length; i++) {
            for (int j = 0; j < orig[i].length; j++) {
                matrix[i][j] = maiorElementoMatrix - orig[i][j];
            }
        }

        return matrix;
    }
    
    public double processHungarianResult(int[] result, double[][] matrix) {

        double similarityThreshold = SettingsHelper.getSimilarityThreshold();
    
        double similarity = 0;
        
        boolean[] rowsMatched = new boolean[matrix.length];
        boolean[] colsMatched = new boolean[matrix[0].length];
        Arrays.fill(rowsMatched, false);
        Arrays.fill(colsMatched, false);
        
        ArrayList<DiffNode> nodes = new ArrayList<DiffNode>();

        for (int i = 0; i < result.length; i++) {
            int row = i;
            int col = result[i];
            if (row != -1 && col != -1) {
                if (matrix[row][col] < similarityThreshold) {
                    matrix[row][col] = 0;
                }
                similarity += matrix[row][col];

                ElementDiffNode[] elemResults = tempResultMatrix[row][col].getResult();
                for (ElementDiffNode diffNode : elemResults) {
                    nodes.add(diffNode);
                }
                rowsMatched[row] = true;
                colsMatched[col] = true;
            }
        }
        
        // add any unmatched row (left side node) to result as one side subtree
        for (int i = 0; i < rowsMatched.length; i++) {
            if (!rowsMatched[i]) {
                nodes.add(new OneSideElementDiffNode(leftNodes.get(i), DiffNode.SIDE_LEFT));
            }
        }
        
        // add any unmatched col (right side node) to result as one side subtree
        for (int i = 0; i < colsMatched.length; i++) {
            if (!colsMatched[i]) {
                nodes.add(new OneSideElementDiffNode(rightNodes.get(i), DiffNode.SIDE_RIGHT));
            }
        }
        
        resultNodes = new ElementDiffNode[nodes.size()]; 
        nodes.toArray(resultNodes);
        
        int length = (matrix.length>matrix[0].length)?matrix.length:matrix[0].length; 
        return similarity / length;
    }

    public ElementDiffNode[] getResult() {
        
        return resultNodes;
    }
}
