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

        float similaridadeTotal = similaridade();
        addResultParent(diff);
        return diff;
    }

    //    TODO: @Override
    public void addResultParent(Diff pai) {

//        visualizarMatrixSimilaridade();
        incluirElementosComMaiorSimilaridade(pai);
        incluirElementosEsquerdos(pai);
        incluirElementosLadoDireito(pai);
    }

    /**
     * retorna o conjunto de elementos com maior similaridade
     * @param pai
     *   
     */
    private void incluirElementosComMaiorSimilaridade(Diff pai) {
        for (int i = 0; i < result.length; i++) {
            int indiceElementoEsquerdo = result[i][0];
            int indiceElementoDireito = result[i][1];

            float maiorSimilaridadeCorrente = originalMatrix[indiceElementoEsquerdo][indiceElementoDireito];

            /**
             * esta condição é necessária para que só aparece na tela os elementos
             * que tenha uma similaridade maior que zero. Se, por exemplo, tiver
             * um documento com a quantidade de elemento maior que o outro, entao
             * o algoritmo húngaro escolhe qualquer elemento. Isto pode gerar uma
             * inconsistencia nos dados.
             */
            if (maiorSimilaridadeCorrente > 0.5f) {
                Diff maiorSimilaridade = calculoSimilaridadeDosElementosCorrentes[indiceElementoEsquerdo][indiceElementoDireito];
                pai.addChildren(maiorSimilaridade);
            }
        }
    }

    private void incluirElementosLadoDireito(Diff pai) {
        /**
         * Monta o documento com todos os elementos do lado direito que não
         * aparece no lado esquerdo do outro documento.
         */
        for (int i = 0; i < lengthOfY(); i++) {
            boolean encontrouSimilaridade = false;
            int linha = 0; //varre a coluna com todos os elementos do documento direito
            while (linha < lengthOfX() && !encontrouSimilaridade) {
                encontrouSimilaridade = originalMatrix[linha][i] > 0;
                linha++;
            }
            if (!encontrouSimilaridade) {
                SimilarNode similarNode = (SimilarNode) y.get(i);
                Node nodeDireito = similarNode.getNode();
                /**
                 * criando um objeto Diff para que possa informar ao usuario
                 * todos os elementos do documento a esquerda da comparação
                 * que não tem similaridade alguma com os elementos a direita
                 * do documento
                 */
                Diff diffLadoDireito = new Diff(nodeDireito);
                diffLadoDireito = varreSubelementos(diffLadoDireito, nodeDireito);
                diffLadoDireito.addSideAttribute("right");
                diffLadoDireito.setSimilarity(0);
                pai.addChildren(diffLadoDireito);
            }
        }
    }

    private void incluirElementosEsquerdos(Diff pai) {
        //varre toda a matrix para encontrar todos os elementos que são difetentes,
        //ou seja, todos elementos que possui zero de similaridade e nao aparecem no outro documento.
        for (int i = 0; i < lengthOfX(); i++) {
            boolean encontrouSimilaridade = false;
            int coluna = 0; //varre a coluna com todos os elementos do documento direito
            while (coluna < lengthOfY() && !encontrouSimilaridade) {
                encontrouSimilaridade = originalMatrix[i][coluna++] > 0;
            }
            if (!encontrouSimilaridade) {
                /**
                 * criando um objeto Diff para que possa informar ao usuario
                 * todos os elementos do documento a esquerda da comparação
                 * que não tem similaridade alguma com os elementos a direita
                 * do documento
                 */
                Diff elementoEsquerdo = calculoSimilaridadeDosElementosCorrentes[i][0];
                elementoEsquerdo.addSideAttribute("left");
                elementoEsquerdo.setSimilarity(0);
                pai.addChildren(elementoEsquerdo);
            }
        }
    }

    private void visualizarMatrixSimilaridade() {
        for (int i = 0; i < originalMatrix.length; i++) {
            for (int j = 0; j < originalMatrix.length; j++) {
                System.out.print("A[ " + i + " ][" + j + "] = " + originalMatrix[i][j] + "           ");
            }
            System.out.println("");
        }
    }

    public Diff listaDeFilhos(Diff diff) {
        visualizarMatrixSimilaridade();

        //retorna o conjunto de elementos com maior similaridade
        for (int i = 0; i < result.length; i++) {
            int indiceElementoEsquerdo = result[i][0];
            int indiceElementoDireito = result[i][1];

            float maiorSimilaridadeCorrente = originalMatrix[indiceElementoEsquerdo][indiceElementoDireito];

            /**
             * esta condição é necessária para que só aparece na tela os elementos
             * que tenha uma similaridade maior que zero. Se, por exemplo, tiver
             * um documento com a quantidade de elemento maior que o outro, entao
             * o algoritmo húngaro escolhe qualquer elemento. Isto pode gerar uma
             * inconsistencia nos dados.
             */
            if (maiorSimilaridadeCorrente != 0.0) {
                SimilarNode elementoEsquerdo = (SimilarNode) x.get(indiceElementoEsquerdo);
                Diff maiorSimilaridade = new Diff(elementoEsquerdo.getNode());
                maiorSimilaridade.setSimilarity(maiorSimilaridadeCorrente);
                diff.addChildren(maiorSimilaridade); //nao é feito a referencia ao
                //documento diffXML da classe LcsXML
            }
        }
        //varre toda a matrix para encontrar todos os elementos que são difetentes,
        //ou seja, todos elementos que possui zero de similaridade.
        for (int i = 0; i < lengthOfX(); i++) {
            boolean encontrouSimilaridade = false;
            int coluna = 0; //varre a coluna com todos os elementos do documento direito
            while (coluna < lengthOfY() && !encontrouSimilaridade) {
                encontrouSimilaridade = originalMatrix[i][coluna++] > 0;
            }
            if (!encontrouSimilaridade) {
                SimilarNode similarNode = (SimilarNode) x.get(i);
                Node nodeEsquerdo = similarNode.getNode();
                /**
                 * criando um objeto Diff para que possa informar ao usuario
                 * todos os elementos do documento a esquerda da comparação
                 * que não tem similaridade alguma com os elementos a direita
                 * do documento
                 */
                Diff elementoEsquerdo = new Diff(nodeEsquerdo);
                elementoEsquerdo.addSideAttribute("left");
                elementoEsquerdo.setSimilarity(0);
                diff.addChildren(elementoEsquerdo);
            }
        }
        incluirElementosLadoDireito(diff);
        return diff;
    }

    public Diff test(Diff diff, Diff diffResultante) {
        diffResultante.addSideAttribute("left");
        diff.addChildren(diffResultante);
        incluirElementosLadoDireito(diff);
        return diff;
    }

    //ESTE MÉTODO ESTA DUPLICADO. TEM UM AQUI DENTRO DESTA CLASSE E OUTRO NA CLASSE SIMILARNODE.
    //REFATORIR ISSO PARA UMA UNICA CLASSE.
    /**
     * 
     * @param diff
     * @param sideNode
     * @return 
     */
    private Diff varreSubelementos(Diff diff, Node sideNode) {
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
                    valueNode.setAttributeNS(Diff.NAMESPACE, Diff.DIFF_PREFIX + "right", sideElementValue);
                    x.getDiffNode().appendChild(valueNode);

                } else if (filho.getNodeType() == Node.ELEMENT_NODE) {
                    novoDiff = new Diff(filho);
                    novoDiff.setSimilarity(0);
                    novoDiff.addSideAttribute("right");
                    novoDiff = inserirAtributosNosElementos(novoDiff, filho);

                    if (filho.hasChildNodes()) {
                        novoDiff = varreSubelementos(novoDiff, filho);
                    }
                    x.addChildren(novoDiff);
                }
            }
        }
        return x;
    }

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
