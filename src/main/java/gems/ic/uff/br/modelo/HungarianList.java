package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.algorithm.Hungarian;
import gems.ic.uff.br.modelo.similar.Similar;
import gems.ic.uff.br.modelo.similar.SimilarNode;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HungarianList extends Hungarian<Similar> {

    private List<Similar> x;
    private List<Similar> y;
    
    /**
     * Atributos criados para manter o histórico de todos os elementos que tiveram
     * um casamento ideal do método húngaro. Serão importantes para consultar
     * todos os elementos que não forem selecionados pelo húngaro e devem ser
     * visualizados pelo usuário.
     */
    private List<Integer> indicesElementosEsquerdo = new ArrayList<Integer>();
    private List<Integer> indicesElementosDireito = new ArrayList<Integer>();

    public HungarianList(List<Similar> from, List<Similar> to) {
        this.x = from;
        this.y = to;
    }

    public HungarianList(NodeList left, NodeList right) {
        result = null;
        originalMatrix = null;
        this.x = new ArrayList<Similar>(left.getLength());
        this.y = new ArrayList<Similar>(right.getLength());

        for (int i = 0; i < left.getLength(); i++) {
            x.add(new SimilarNode(left.item(i)));
        }

        for (int i = 0; i < right.getLength(); i++) {
            y.add(new SimilarNode(right.item(i)));
        }
    }

    @Override
    protected int lengthOfX() {
        return x.size();
    }

    @Override
    protected int lengthOfY() {
        return y.size();
    }

    @Override
    protected Similar valueOfX(int index) {
        return x.get(index);
    }

    @Override
    protected Similar valueOfY(int index) {
        return y.get(index);
    }

    public Diff calcularSimilaridadeDosSubElementos(Diff diff) {
        
        similaridade();
        addResultParent(diff);
        return diff;
    }

    public void printResult() {
		for(int i=0; i < result.length; i++) {
			System.out.println("["+result[i][0]+","+result[i][1]+"]");
		}
	}

	/**
     * Método responsável para criar os resultado da comparação de todos os
     * subelementos do elemento comparado
     * @param pai 
     */
    public void addResultParent(Diff pai) {

//        visualizarMatrixSimilaridade();
        incluirElementosComMaiorSimilaridade(pai);
        incluirElementosLadoEsquerdos(pai);
        incluirElementosLadoDireito(pai);
    }

    /**
     * retorna o conjunto de elementos com maior similaridade
     *
     * @param pai
     *
     */
    private void incluirElementosComMaiorSimilaridade(Diff pai) {
        for (int i = 0; i < result.length; i++) {
            int indiceElementoEsquerdo = result[i][0];
            int indiceElementoDireito = result[i][1];
            
            // means the there are more columns than rows
            if (indiceElementoEsquerdo == -1 || indiceElementoDireito == -1) 
            	continue;

            double maiorSimilaridadeCorrente = originalMatrix[indiceElementoEsquerdo][indiceElementoDireito];

            /**
             * esta condição é necessária para que só aparece na tela os
             * elementos que tenha uma similaridade maior que zero. Se, por
             * exemplo, tiver um documento com a quantidade de elemento maior
             * que o outro, entao o algoritmo húngaro escolhe qualquer elemento.
             * Isto pode gerar uma inconsistencia nos dados.
             */
            if (maiorSimilaridadeCorrente > 0) {
                Diff maiorSimilaridade = calculaSimilaridadeDosElementosCorrentes[indiceElementoEsquerdo][indiceElementoDireito];
                indicesElementosEsquerdo.add(indiceElementoEsquerdo);
                indicesElementosDireito.add(indiceElementoDireito);
                pai.addChildren(maiorSimilaridade);
            }
        }
    }
    
    
    /**
     * criando um objeto Diff para que possa informar ao usuario todos os
     * elementos do documento a esquerda da comparação que não tem similaridade
     * alguma com os elementos a direita do documento
     */
    private void incluirElementosLadoEsquerdos(Diff pai) {
       
        for (int i = 0; i < lengthOfX(); i++) {
            if (!indicesElementosEsquerdo.contains(i)) {

                SimilarNode similarNode = (SimilarNode) x.get(i);
                Node nodeEsquerdo = similarNode.getNode();
                Diff diffLadoEsquerdo = new Diff(nodeEsquerdo);
                diffLadoEsquerdo = varreSubelementos(diffLadoEsquerdo, nodeEsquerdo, "left");
                diffLadoEsquerdo.addSideAttribute("left");
                diffLadoEsquerdo.setSimilarity(0);
                pai.addChildren(diffLadoEsquerdo);
            }
        }
    }

    /**
     * Monta o documento com todos os elementos do lado direito que não aparece
     * no lado esquerdo do outro documento.
     */
    private void incluirElementosLadoDireito(Diff pai) {
        /**
         * criando um objeto Diff para que possa informar ao usuario todos os
         * elementos do documento a esquerda da comparação que não tem
         * similaridade alguma com os elementos a direita do documento
         */
        for (int i = 0; i < lengthOfY(); i++) {
            if (!indicesElementosDireito.contains(i)) {

                SimilarNode similarNode = (SimilarNode) y.get(i);
                Node nodeDireito = similarNode.getNode();
                Diff diffLadoDireito = new Diff(nodeDireito);
                diffLadoDireito = varreSubelementos(diffLadoDireito, nodeDireito, "right");
                diffLadoDireito.addSideAttribute("right");
                diffLadoDireito.setSimilarity(0);
                pai.addChildren(diffLadoDireito);
            }
        }

    }

    /**
     * Método criado para visualizar o percentual de todos os elementos comparados.
     * Este método poderá ser apagado no futuro. É apenas um guia para ajudar no
     * desenvolvimento.
     */
    public void visualizarMatrixSimilaridade() {
        for (int i = 0; i < originalMatrix.length; i++) {
            for (int j = 0; j < originalMatrix[0].length; j++) {
                System.out.print("A[ " + i + " ][" + j + "] = " + originalMatrix[i][j] + "           ");
            }
            System.out.println("");
        }
        System.out.println("*** fim ***");
    }

    /**
     * Método usado para criar os elementos e subelementos que não foram considerados
     * similares a um dos lados do documento. 
     * @param diff  Diff contanto o elemento principal
     * @param sideNode Informa de que lado do documento o elemento percente."left" ou "right"
     * @return Retorna o objeto Diff com a construção de toda a cadeia de 
     * subelementos para que o usuário possa visualiza-los todos de uma cor. 
     */
    //ESTE MÉTODO ESTA DUPLICADO. TEM UM AQUI DENTRO DESTA CLASSE E OUTRO NA CLASSE SIMILARNODE.
    //NO TUTURO, REFATORAR ISSO PARA UMA UNICA CLASSE.
    private Diff varreSubelementos(Diff diff, Node sideNode, String side) {
        Diff x = diff;
        Diff novoDiff = null;
        if (sideNode.hasChildNodes()) {
            NodeList subElementos = sideNode.getChildNodes();
            for (int i = 0; i < subElementos.getLength(); i++) {
                Node filho = subElementos.item(i);
                String sideElementValue = filho.getNodeValue();
                if (filho.getNodeType() == Node.TEXT_NODE
                        && sideElementValue != null
                        && isElementValue(sideElementValue)) {

                    Element valueNode = (Element) DiffXML.createNode("value");

                    String qname = Diff.DIFF_LEFT;
                    if (side.equals("right")) {
                        qname = Diff.DIFF_RIGHT;
                    }
                    valueNode.setAttributeNS(Diff.DIFF_NAMESPACE, qname, sideElementValue);

                    x.getDiffNode().appendChild(valueNode);

                } else if (filho.getNodeType() == Node.ELEMENT_NODE) {
                    novoDiff = new Diff(filho);
                    novoDiff.setSimilarity(0);
                    novoDiff.addSideAttribute(side);
                    novoDiff = inserirAtributosNosElementos(novoDiff, filho);

                    if (filho.hasChildNodes()) {
                        novoDiff = varreSubelementos(novoDiff, filho, side);
                    }
                    x.addChildren(novoDiff);
                }
            }
        }
        return x;
    }

    /**
     * Método que reduz a quantidade de espaços desnecessários a comparaçao, 
     * caso os XMLs não estejam atrelados a um esquema. Isso acontece quando os
     * XMLs comparados possui muitos dos seus conteúdos com quebra de linha.
     *
     * @param valor 
     * @return
     */
    private boolean isElementValue(String valor) {
        return (valor.contains("\n")
                || valor.contains("      ")
                || valor.contains("&#10")) ? false : true;
    }

    private Diff inserirAtributosNosElementos(Diff diff, Node elemento) {
        NamedNodeMap attributes = elemento.getAttributes();
        if (elemento.hasAttributes()) {
            for (int i = 0; i < attributes.getLength(); i++) {
                diff.addAtribute(attributes.item(i));
            }
        }
        return diff;
    }
}
