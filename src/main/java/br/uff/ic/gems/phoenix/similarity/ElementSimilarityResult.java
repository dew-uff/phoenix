package br.uff.ic.gems.phoenix.similarity;

import br.uff.ic.gems.phoenix.diff.ElementDiffNode;

public class ElementSimilarityResult {
    
    private double similarity = 0.0;
    private ElementDiffNode[] result = null;
    
    public ElementSimilarityResult(ElementDiffNode common, double similarity) {
        result = new ElementDiffNode[1];
        result[0] = common;
        this.similarity = similarity;
    }
    
    public ElementSimilarityResult(ElementDiffNode left, ElementDiffNode right, double similarity) {
        result = new ElementDiffNode[2];
        result[0] = left;
        result[1] = right;
        this.similarity = similarity;
    }

    public ElementDiffNode[] getResult() {
        return result;
    }
    
    public double getSimilarity() {
        if (result.length == 2) {
            return 0.0;
        } else {
            return similarity;
        }
    }
    
    public double getRealSimilarity() {
        return similarity;
    }
}
