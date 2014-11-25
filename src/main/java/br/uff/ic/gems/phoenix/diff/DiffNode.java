package br.uff.ic.gems.phoenix.diff;


public abstract class DiffNode {
    
    public static int SIDE_LEFT  = 0;
    public static int SIDE_RIGHT = 1;
    
    public static int TYPE_ONESIDEELEMENT   = 0;
    public static int TYPE_COMMONELEMENT    = 1;
    public static int TYPE_COMMONATTRIBUTE  = 2;
    public static int TYPE_ONESIDEATTRIBUTE = 3;
    
    public static final String DIFF_NAMESPACE = "ic.uff.br/xmldiff";
    public static final String DIFF_NAMESPACE_LEFT = "ic.uff.br/xmldiff/left";
    public static final String DIFF_NAMESPACE_RIGHT = "ic.uff.br/xmldiff/right";
    public static final String DIFF_LEFT = "diff:left";
    public static final String DIFF_RIGHT = "diff:right";
    public static final String DIFF_SIDE = "diff:side";
    public static final String DIFF_ELEMENT = "diff:element";
    public static final String DIFF_VALUE = "diff:value";
    public static final String DIFF_SIMILARITY = "diff:similarity";
    public static final String LEFT_PREFIX = "left:";
    public static final String RIGHT_PREFIX = "right:";
    
    protected double similarity = 0.0;
    
    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }
    
    public double getSimilarity() {
        return similarity;
    }
    
    public abstract int getType();
}
