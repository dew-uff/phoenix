package gems.ic.uff.br.modelo.similar;

import com.sun.org.apache.xpath.internal.NodeSet;
import gems.ic.uff.br.modelo.Diff;
import gems.ic.uff.br.modelo.DiffXML;
import gems.ic.uff.br.modelo.HungarianList;
import gems.ic.uff.br.modelo.LcsString;
import java.util.*;
import org.w3c.dom.*;

/**
 * Classe que encapsula a classe Node do DOM, adicionando um comportamento
 * de similaridade a ela.
 */
public class SimilarNode extends Similar<SimilarNode> {

    //elemento do lado esquerdo do documento
    private Node leftNode;
    
    //peso dos métodos de comparação
    public static float ATTRIBUTE_WEIGTH = 0.25f;
    public static float ELEMENT_VALUE_WEIGTH = 0.25f;
    public static float ELEMENT_NAME_WEIGTH = 0.25f;
    public static float ELEMENT_CHILDREN_WEIGTH = 0.25f;

    public SimilarNode(Node node) {
        this.leftNode = node;
    }

    @Override
    public Diff similar(SimilarNode y) {
        Node rightNode = y.getNode();

        Diff diff = new Diff(leftNode, rightNode);
        float similarity = 0;

        
        if (leftNode == null || rightNode == null) {
            
        } else {
            similarity += elementsNameSimilarity(rightNode);

            //Se não houver similaridade no nome, então não são o mesmo elemento.
            //Portanto, não é necessário continuar a calcular a similaridade.
            if (similarity != 0) {
                similarity += elementsValueSimilarity(rightNode, diff);
                similarity += elementsAttributesSimilarity(rightNode, diff);
                similarity += elementsChildrenSimilarity(rightNode, diff);

            } else {
                /**
                 * Se chegou nesta condição, então o elemento da esquerda é diferente
                 * do elemento da direita. Logo, esta informação deve ser exposta
                 * para usuário. Este método trata esta requisição.
                 */
                diff = varreTodosSubelementosParaMostrar(diff, leftNode, "left");
            }
        }
        diff.setSimilarity(similarity);
        return diff;
    }

    /**
     * Codição usada para verificar se os nomes dos elementos são iguais. Essa
     * condição é necessaria para que os outros métodos de similaridade possam
     * ser testado
     * @param rightNode nome do elemento do lado direito do documento
     * @return retorna o percentual de similaridade encontrado
     */
    protected float elementsNameSimilarity(Node rightNode) {
        float similarity = 0;

        if (leftNode.getNodeName().equals(rightNode.getNodeName())) {
            similarity = ELEMENT_NAME_WEIGTH;
        }

        return similarity;
    }

    /**
     * Compara a similaridade do conteúdo dos elementos. A comparação é feita
     * usando o algoritmo LCS.
     * @param rightNode conteudo do lado direito
     * @param diff diff corrente 
     * @return total de similaridade encontrado entre os conteúdos
     * @throws DOMException 
     */
    protected float elementsValueSimilarity(Node rightNode, Diff diff) throws DOMException {
        float similarity = 0;

        if (leftNode.hasChildNodes() || rightNode.hasChildNodes()) {
            String leftNodeValue = null;
            String rightNodeValue = null;

            if (leftNode.hasChildNodes()) {
                //O valor do elemento é um de seus filhos.
                leftNodeValue = leftNode.getFirstChild().getNodeValue();
            }

            if (rightNode.hasChildNodes()) {
                //O valor do elemento é um de seus filhos.
                rightNodeValue = rightNode.getFirstChild().getNodeValue();
            }

            if (leftNodeValue == null && rightNodeValue == null) {
                similarity = ELEMENT_VALUE_WEIGTH;
            } else {
                diff.setValue(leftNodeValue, rightNodeValue);

                if (leftNodeValue != null && rightNodeValue != null) {
                    similarity += new LcsString(leftNodeValue, rightNodeValue).similaridade() * ELEMENT_VALUE_WEIGTH;
                }
            }
        } else {
            similarity = ELEMENT_VALUE_WEIGTH;
        }

        return similarity;
    }

    /**
     * Comparação de similaridade dos atributos do elemento corrente. A comparação
     * é feita agrupando todos os atributos pertencentes aos elementos, tanto da
     * esquerda quanto da direita, e colocado em uma classe Map, onde a chave é
     * o nome do elemento e o valor é uma lista do tipo SimilarString. Dentro 
     * dessa lista, a posição 0 indica o conteudo do lado esquerdo do documento
     * comparado, e a posição 1 indica o conteudo do lado direito.
     * @param rightNode Elemento do lado direito do documento
     * @param diff Diff corrente até o momento
     * @return Retorna a similaridade total encontrada entre os atributos.
     */
    protected float elementsAttributesSimilarity(Node rightNode, Diff diff) {
        float similarity = 0;

        if (!leftNode.hasAttributes() && !rightNode.hasAttributes()) {
            similarity = ATTRIBUTE_WEIGTH;
        } else {
            if (leftNode.hasAttributes() && rightNode.hasAttributes()) {
                NamedNodeMap attributesFromLeftNode = leftNode.getAttributes();
                NamedNodeMap attributesFromRightNode = rightNode.getAttributes();

                Map<String, List<SimilarString>> attributesMap = getAttributesFromNamedNodeMaps(attributesFromLeftNode, attributesFromRightNode);
                Iterator mapIterator = attributesMap.entrySet().iterator();

                while (mapIterator.hasNext()) {
                    Map.Entry<String, List<SimilarString>> entry = (Map.Entry) mapIterator.next();

                    String attributeName = entry.getKey();
                    SimilarString leftElementAttributeValue = entry.getValue().get(0);
                    SimilarString rightElementAttributeValue = entry.getValue().get(1);

                    diff.addAttribute(attributeName, leftElementAttributeValue.toString(), rightElementAttributeValue.toString());
                    //Alguns atributos vem com seu conteudo vazio. Logo, esta
                    //condição é necessária senão pode ocorrer erro de divisao(NaN).
                    if (leftElementAttributeValue.getString().isEmpty()
                            && rightElementAttributeValue.getString().isEmpty()) {
                        similarity += 1.0f;
                    } else {
                        similarity += new LcsString(leftElementAttributeValue, rightElementAttributeValue).similaridade();
                    }
                }

                similarity = similarity / attributesMap.keySet().size() * ATTRIBUTE_WEIGTH;
            } else {
            }
        }

        return similarity;
    }

    /**
     * Método de comparação dos subelementos. Este método agrupa todos os elementos
     * do lado esquerdo do documento, depois agrupa todos os elementos do lado
     * direito do documento e faz uma comparação entre eles.
     * @param rightNode Elemento do lado direito do documento
     * @param diff Diff corrente até o momento
     * @return 
     */
    protected float elementsChildrenSimilarity(Node rightNode, Diff diff) {

        float similarity = 0;

        if (!leftNode.hasChildNodes() && !rightNode.hasChildNodes()) {
            similarity = ELEMENT_CHILDREN_WEIGTH;
        } else {
            NodeSet leftSubElements = getElementNodes(leftNode.getChildNodes());
            NodeSet rightSubElements = getElementNodes(rightNode.getChildNodes());

            if ((leftSubElements.size() == 0 && rightSubElements.size() == 0)) {
                similarity = ELEMENT_CHILDREN_WEIGTH;
            } else if ((leftSubElements.size() != 0 && rightSubElements.size() != 0)) {
                HungarianList hungarianList = new HungarianList(leftSubElements, rightSubElements);
                diff = hungarianList.calcularSimilaridadeDosSubElementos(diff);
                similarity = hungarianList.similaridade() * ELEMENT_CHILDREN_WEIGTH;

            } else {
                if (leftSubElements.size() != 0) {
                    diff = varreTodosSubelementosParaMostrar(diff, leftNode, "left");

                } else {
                    diff = varreTodosSubelementosParaMostrar(diff, rightNode, "right");

                }

            }
        }

        diff.setSimilarity(similarity);
        return similarity;
    }

    /**
     * Retornar os nós de um NodeList que são do tipo elemento.
     *
     * @param nodeList
     * @return
     * @see Node
     */
    public static NodeSet getElementNodes(NodeList nodeList) {
        NodeSet elementNodes = new NodeSet();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);

            if (item.getNodeType() == Node.ELEMENT_NODE) {
                elementNodes.addNode(item);
            }
        }

        return elementNodes;
    }

    
    /**
     *  Método que extrai todos os atributos pertencentes a ambos os lados do 
     * documento.
     * @param ladoEsquerdo - atributos que estão no lado esquerdo do documento
     * @param ladoDireito - atributos que estão no lado direito do documento
     * @return
     *  Retorna uma biblioteca com todos os atributos de ambos os lados dos documentos
     * com seus respectivos conteúdos.
     */
    protected Map<String, List<SimilarString>> getAttributesFromNamedNodeMaps(NamedNodeMap ladoEsquerdo, NamedNodeMap ladoDireito) {

        Map<String, List<SimilarString>> attributesMap = new TreeMap<String, List<SimilarString>>();

        for (int i = 0; i < ladoEsquerdo.getLength(); i++) {
            Node item = ladoEsquerdo.item(i);

            List<SimilarString> similarList = new ArrayList<SimilarString>(2);
            similarList.add(new SimilarString(item.getNodeValue()));
            similarList.add(new SimilarString(""));

            attributesMap.put(item.getNodeName(), similarList);
        }
        for (int i = 0; i < ladoDireito.getLength(); i++) {
            Node item = ladoDireito.item(i);

            List<SimilarString> similarList = attributesMap.get(item.getNodeName());
            if (similarList != null) {
                similarList.remove(1);
                similarList.add(new SimilarString(item.getNodeValue()));
            } else {
                similarList = new ArrayList<SimilarString>(2);
                similarList.add(new SimilarString(""));
                similarList.add(new SimilarString(item.getNodeValue()));

                attributesMap.put(item.getNodeName(), similarList);
            }
        }

        return attributesMap;
    }

    public Node getNode() {
        return leftNode;
    }

    @Override
    public String toString() {
        return leftNode.getNodeName();
    }

    private boolean isElementValue(String valor) {
        return (valor.contains("\n") && valor.contains("        ")) ? false : true;
    }

    /**
     *  Cria um objeto do tipo Diff com todos os elementos e subelmentos que não
     * aparece no outro lado do documento.
     * @param diff - diff corrente até o momento
     * @param nodeCorrente - elemento que deve ser criado
     * @param nodeSide - informa de qual lado do documento o nodeCorrente pertence
     * @return 
     *  Retorna um novo objeto Diff com todos os subelementos que pertence aquele
     * elemento.
     */
    private Diff varreTodosSubelementosParaMostrar(Diff diff, Node nodeCorrente, String nodeSide) {
        Diff x = diff;
        Diff novoDiff = null;
        if (nodeCorrente.hasChildNodes()) {
            NodeList subElementos = nodeCorrente.getChildNodes();
            for (int i = 0; i < subElementos.getLength(); i++) {
                Node filho = subElementos.item(i);
                String sideElementValue = filho.getNodeValue();
                if (filho.getNodeType() == Node.TEXT_NODE
                        && sideElementValue != null
                        && isElementValue(sideElementValue)) {

                    Element valueNode = (Element) DiffXML.createNode("value");
                    valueNode.setAttributeNS(Diff.NAMESPACE, Diff.DIFF_PREFIX + nodeSide, sideElementValue);
                    x.getDiffNode().appendChild(valueNode);
                } else if (filho.getNodeType() == Node.ELEMENT_NODE) {
                    novoDiff = new Diff(filho);
                    novoDiff.setSimilarity(0);
                    novoDiff.addSideAttribute(nodeSide);
                    novoDiff = inserirAtributosNosElementos(novoDiff, filho);

                    if (filho.hasChildNodes()) {
                        novoDiff = varreTodosSubelementosParaMostrar(novoDiff, filho, nodeSide);
                    }
                    x.addChildren(novoDiff);
                }
            }
        }
        return x;
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
