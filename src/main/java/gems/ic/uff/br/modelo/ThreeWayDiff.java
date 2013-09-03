package gems.ic.uff.br.modelo;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xpath.internal.NodeSet;
import gems.ic.uff.br.modelo.similar.SimilarNode;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.w3c.dom.*;

public class ThreeWayDiff {

    public static final String NAMESPACE = "ic.uff.br/xmldiff";
    public static final String DIFF_PREFIX = "diff:";
    public static final String DIFF_SIDE = "diff:side";
    public static final String SON1_PREFIX = "left:";
    public static final String SON2_PREFIX = "right:";
    public static final String ANCESTRAL_PREFIX = "center:";
    public static final String DIFF_RELATION = "diff:relation";
    public static final String DIFF_SON1 = "son1";
    public static final String DIFF_SON2 = "son2";
    public static final String DIFF_ANCESTRAL = "ancestral";
    public static final String DIFF_ANCESTRAL_SON1 = "ancestralSon1";
    public static final String DIFF_ANCESTRAL_SON2 = "ancestralSon2";
    public static final String DIFF_SON1_SON2 = "son1Son2";
    private float similaritySon1;
    private float similaritySon2;
    private Node diffNode;
    private SimilarNode son1Node;
    private SimilarNode son2Node;
    private SimilarNode ancestralNode;
    private boolean bConflito;

    //Esses construtores são POGs!
    public ThreeWayDiff(float similarity) {
        this.similaritySon1 = similarity;
        bConflito =false;
    }

    public ThreeWayDiff(Node node) {
        this.diffNode = DiffXML.getInstance().getDocument().createElement(node.getNodeName());
        this.ancestralNode = new SimilarNode(node);
        bConflito =false;
    }

    public ThreeWayDiff(Node ancestral,Node son1Node, Node son2Node) {
        this.diffNode = DiffXML.getInstance().getDocument().createElement(ancestral.getNodeName());
        this.son1Node = new SimilarNode(son1Node);
        this.son2Node = new SimilarNode(son2Node);
        this.ancestralNode = new SimilarNode(ancestral);
        bConflito =false;
    }

    public ThreeWayDiff(SimilarNode ancestral,SimilarNode son1, SimilarNode son2) {
        
        this.diffNode = DiffXML.getInstance().getDocument().createElement(ancestral.getNode().getNodeName());
        this.son1Node = son1;
        this.son2Node = son2;
        this.ancestralNode = ancestral;
        bConflito =false;
    }
    
    public void setConflito(boolean conflito)
    {
        bConflito = conflito;
    }
    
    public boolean getConflito()
    {
        return bConflito;
    }
    
    private boolean isElementValue(String valor) {
        return (valor.contains("\n")
                || valor.contains("      ")
                || valor.contains("&#10")) ? false : true;
    }

    
    /**
     * Cria os elementos para informar ao usuário o conteudo do lado esquerdo
     * e  direito
     * @param leftElementValue Conteudo do lado esquerdo
     * @param rightElementValue Conteudo do lado direito
     */
    public void setValue(String ancestralElementValue, String son1ElementValue,String son2ElementValue) 
    {
        if (ancestralElementValue != null || son1ElementValue != null || son2ElementValue != null) 
        {
            if (ancestralElementValue != null && son1ElementValue != null && son2ElementValue != null) 
            {
                boolean bS1 = ancestralElementValue.equals(son1ElementValue);
                boolean bS2 = ancestralElementValue.equals(son2ElementValue);
                if (bS1 &&bS2 ) 
                {
                    this.diffNode.setTextContent(ancestralElementValue);
                }
                else if(bS1)
                {
                    Element valueNode = (Element) DiffXML.createNode("value");
                    valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "left", ancestralElementValue);
                    valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", son2ElementValue);
                    this.diffNode.appendChild(valueNode);
                }    
                else if(bS2)
                {
                    Element valueNode = (Element) DiffXML.createNode("value");
                    valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "left", ancestralElementValue);
                    valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", son1ElementValue);
                    this.diffNode.appendChild(valueNode);
                }
                else 
                {
                    if (isElementValue(ancestralElementValue)) 
                    {

                        Element valueNode = (Element) DiffXML.createNode("value");
                        valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "center", ancestralElementValue);
                        
                        
                        if (isElementValue(son1ElementValue)) 
                        {
                            valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "left", son1ElementValue);
                        }
                        
                        if (isElementValue(son2ElementValue)) 
                        {
                            valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", son2ElementValue);
                        }
                        this.diffNode.appendChild(valueNode);

                    } 
                    else if (isElementValue(son1ElementValue)) 
                    {
                        Element valueNode = (Element) DiffXML.createNode("value");
                        valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "left", son1ElementValue);
                        
                        if(isElementValue(son2ElementValue))
                        {
                           valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", son2ElementValue);
                        }

                        this.diffNode.appendChild(valueNode);
                    }
                    else if (isElementValue(son2ElementValue)) 
                    {
                        Element valueNode = (Element) DiffXML.createNode("value");
                        valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", son2ElementValue);

                        this.diffNode.appendChild(valueNode);
                    }
                }
            } 
            else 
            {
                if (ancestralElementValue != null) 
                {
                    if(son1ElementValue== null && son2ElementValue== null)
                        this.diffNode.setTextContent(ancestralElementValue);
                    else 
                    {
                        Element valueNode = (Element) DiffXML.createNode("value");
                        if(isElementValue(ancestralElementValue))
                            valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "left", ancestralElementValue);
                        
                        if (son1ElementValue!= null && isElementValue(son1ElementValue)) 
                        {
                            valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", son1ElementValue);
                        }
                        
                        if (son2ElementValue!= null && isElementValue(son2ElementValue)) 
                        {
                            valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", son2ElementValue);
                        }
                        this.diffNode.appendChild(valueNode);
                    }
                } 
                else if(son1ElementValue != null )
                {
                    if(ancestralElementValue== null && son2ElementValue== null)
                        this.diffNode.setTextContent(son1ElementValue);
                    else 
                    {
                        
                        Element valueNode = (Element) DiffXML.createNode("value");
                        if (ancestralElementValue!= null && isElementValue(ancestralElementValue)) 
                        {
                             if(isElementValue(son1ElementValue))
                                valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", son1ElementValue);
                            valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "left", ancestralElementValue);
                        }
                        
                        if (son2ElementValue!= null && isElementValue(son2ElementValue)) 
                        {
                            if(isElementValue(son1ElementValue))
                                valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "left", son1ElementValue);
                            valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", son2ElementValue);
                        }
                        this.diffNode.appendChild(valueNode);
                    }
                }
                else if(son2ElementValue != null )
                {
                    if(ancestralElementValue== null && son1ElementValue== null)
                       this.diffNode.setTextContent(son2ElementValue);
                    else
                    {
                        Element valueNode = (Element) DiffXML.createNode("value");
                        if(isElementValue(son2ElementValue))
                                valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", son2ElementValue);
                        
                        if (ancestralElementValue!= null && isElementValue(ancestralElementValue)) 
                        {         
                            valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "left", ancestralElementValue);
                        }
                        
                        if (son1ElementValue!= null && isElementValue(son1ElementValue)) 
                        {
                            valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "left", son1ElementValue);
                        }
                        this.diffNode.appendChild(valueNode);   
                    }
                }
            }
        }
    }

    /**
     * Este método é usado para indicar os valores dos atributos, tanto esquerdo
     * quanto direito, do elemento comparado.
     *
     * @param attributeName Nome do atributo
     * @param leftElementAttributeValue Valor do atributo correspondente ao lado
     * esquerdo do documento
     * @param rightElementAttributeValue Valor do atributo correspondente ao
     * lado direito do documento
     */
    public void addAttribute(String attributeName, String leftElementAttributeValue, String rightElementAttributeValue) {
        
        /** Esta primeira condição é necessária para verificar se os elementos 
         * roots dos documentos possui namedspace ou não.Se possuir, deve ser 
         * criado uma nova namespace e adicionado ao diff resultante. No futuro, criar
         * esta verificação a parte para que não possa ser perguntado a todos 
         * os seus filhos.
         */
        if (attributeName.contains("xmlns") ) {
            if (leftElementAttributeValue.equals(rightElementAttributeValue)) {
                ((Element) this.diffNode).setAttributeNS(XML.ENDERECO_NAMESPACE, attributeName, leftElementAttributeValue);
            } else {
                if (!leftElementAttributeValue.isEmpty()) {
                    ((Element) this.diffNode).setAttributeNS(XML.ENDERECO_NAMESPACE, attributeName, leftElementAttributeValue);
                }
                if (!rightElementAttributeValue.isEmpty()) {
                    ((Element) this.diffNode).setAttributeNS(XML.ENDERECO_NAMESPACE, attributeName, rightElementAttributeValue);
                }
            }
        } else {

            // *** fim ***    
            if (leftElementAttributeValue.isEmpty() || rightElementAttributeValue.isEmpty()) {
                if (leftElementAttributeValue.isEmpty()) {
                    ((Element) this.diffNode).setAttributeNS(NAMESPACE, SON1_PREFIX + attributeName, leftElementAttributeValue);
                } else {
                    ((Element) this.diffNode).setAttributeNS(NAMESPACE, SON2_PREFIX + attributeName, rightElementAttributeValue);
                }
            } else {
                if (leftElementAttributeValue.equals(rightElementAttributeValue)) {
                    ((Element) this.diffNode).setAttribute(attributeName, leftElementAttributeValue);
                } else {
                    ((Element) this.diffNode).setAttributeNS(NAMESPACE, SON1_PREFIX + attributeName, leftElementAttributeValue);
                    ((Element) this.diffNode).setAttributeNS(NAMESPACE, SON2_PREFIX + attributeName, rightElementAttributeValue);
                }
            }
        }
    }
    
    public void addSimilarityAttribute(float similaritySon1, float similaritySon2 ) {
        
         String similar = " son1: "+ Float.toString(similaritySon1);
         similar += " son2: "+ Float.toString(similaritySon2);
        ((Element) this.diffNode).setAttributeNS(NAMESPACE, DIFF_PREFIX + "similarity",similar );
    }

    public void addSideAttribute(String nodeSide) {
        ((Element) this.diffNode).setAttributeNS(NAMESPACE, DIFF_SIDE, nodeSide);
    }
    
    public void addDiffRelation(String diffRelation)
    {
        ((Element) this.diffNode).setAttributeNS(NAMESPACE, DIFF_RELATION, diffRelation);
        
    }

    /**
     * Método criado para inserir todos os atributos em um elemento. A diferença
     * deste método com os demais é que este só será usado para indicar os
     * atributos que pertence a um determinado elemento sendo que este é...
     *
     * @param atributo
     */
    public void addAtribute(Node atributo) {
        ((Element) this.diffNode).setAttributeNS(NAMESPACE, atributo.getNodeName(), atributo.getNodeValue());
    }

    public void addChildren(ThreeWayDiff anotherDiff) {
        this.diffNode.appendChild(anotherDiff.getDiffNode());
    }
    
    public void addChildren(Diff anotherDiff) 
    {
        this.diffNode.appendChild(anotherDiff.getDiffNode());
    }
            
    public float getSimilaritySon1() {
        return similaritySon1;
    }
    
    
    public float getSimilaritySon2() {
        return similaritySon2;
    }

    public Node getDiffNode() {
        return diffNode;
    }

    public ThreeWayDiff varreTodosSubelementosParaMostrar(Node nodeCorrente) {
        if (nodeCorrente.hasChildNodes()) {
            NodeList subElementos = nodeCorrente.getChildNodes();
            for (int i = 0; i < subElementos.getLength(); i++) {
                Node filho = subElementos.item(i);
                if (filho.getNodeType() == Node.TEXT_NODE) {
                    this.diffNode.setTextContent(filho.getNodeValue());
                } else if (filho.getNodeType() == Node.ELEMENT_NODE) {
                    if (filho.hasChildNodes()) {
//                        novoDiff = varreTodosSubelementosParaMostrar(novoDiff, filho);
                    }
                    this.addChildren(new ThreeWayDiff(nodeCorrente.getChildNodes().item(i)));
                }
            }
            return this;
        }
        return this;
    }
 
    private boolean compareNode(Node node1, Node node2)
    {
        boolean bEqual = true;
        
        if(node1 == null && node2 != null )
            return false;
        
        if(node1 != null && node2 == null)
            return false;
        
        boolean bName = true;
        if(node1.getNodeType() == Node.TEXT_NODE && node2.getNodeType() != Node.TEXT_NODE)
            bName =false;
        if(node1.getNodeType() != Node.TEXT_NODE && node2.getNodeType() == Node.TEXT_NODE) 
             bName =false;
        if(bName)
        {
            if( ! (node1.getNodeName().equals(node2.getNodeName())))
                bEqual =false;
        }
        
        String value1 = node1.getNodeValue();
        String value2 = node2.getNodeValue();
        
        if(value1!=null && value2 !=null)
        {
            if( !(node1.getNodeValue().equals(node2.getNodeValue())))
                bEqual =false;
        }
        else
        {
            if(value1 == null && value2 !=null)
                  bEqual =false;
            if(value1 != null && value2 ==null)
                 bEqual =false;
        }
        
         return bEqual;
    }
    
    private boolean  contemLista(List<Node> elementos, Node node)
    {
        for (int i = 0; i < elementos.size(); i++) 
        {
            
             Node lisNode = elementos.get(i);
             if( node.isSameNode(lisNode))
                 return true;
            
        }
        
        return false;
    }
    
    private void ImprimirElementos(String str, NodeList elementos)
    {
        System.out.println(str); 
        for (int i = 0; i < elementos.getLength(); i++) 
         {
             Node filho1 = elementos.item(i);
             System.out.println(filho1.getNodeName());
         }
    }
    
     private void ImprimirLista(String str, List<Node> elementos)
    {
        System.out.println(str); 
        for (int i = 0; i < elementos.size(); i++) 
         {
             Node filho1 = elementos.get(i);
             System.out.println(filho1.getNodeName());
         }
    }
    
    public void mergeTree(Node diffNode1, Node diffNode2, Node ancestral,boolean bKeepAncestral,ThreeWayDiff diff)
    {
       NodeList subElementos1 = diffNode1.getChildNodes();
       //ImprimirElementos("ELEMENTOS 1",subElementos1);
       NodeList subElementos2 = diffNode2.getChildNodes();
       //ImprimirElementos("ELEMENTOS 2",subElementos2);
       List<Node> elementosInseridos = new ArrayList<Node>();
       //boolean bConflito = false;
       
       mergeSubElementos2( subElementos2, subElementos1, elementosInseridos, ancestral, diff, bKeepAncestral);
       //ImprimirLista("ELEMENTOS INSERIDOS ",elementosInseridos);
       mergeSubElementos1( subElementos1, elementosInseridos, ancestral, diff, bKeepAncestral);
      
       
    }
    
    
    private void mergeSubElementos2(NodeList subElementos2,NodeList subElementos1,List<Node> elementosInseridos,Node ancestral,ThreeWayDiff diff,boolean bKeepAncestral)
    {
        for (int i = 0; i < subElementos2.getLength(); i++) 
       {
                Node filho2 = subElementos2.item(i);
                preencherOrigemFilho2(filho2);
                
                
                //System.out.println("1"+filho1.getNodeName());
                boolean bFind = false;
                for (int j = 0; j < subElementos1.getLength(); j++)
                {
                    Node filho1 = subElementos1.item(j);
                    preencherOrigemFilho1(filho1);
                    //System.out.println("2"+filho2.getNodeName());
                    if(contemLista(elementosInseridos,filho1))
                        continue;
                    if( compareNode(filho1,filho2))
                    {
                        Document doc = ancestral.getOwnerDocument();
                        //DocumentFragment fragment = doc.createDocumentFragment();
                        //fragment.appendChild(filho1);
                        if(bKeepAncestral)
                            preencherSimilaridade(filho2,filho1);
                        boolean bInsertFilho1 =false;
                        if(filho1.getNodeName().contains("diff:value") && filho2.getNodeName().contains("diff:value"))
                        {
                                Node noEsquerdo1 = filho1.getAttributes().getNamedItem("diff:left");
                                Node noDireito1 = filho1.getAttributes().getNamedItem("diff:right");
                                Node noEsquerdo2 = filho2.getAttributes().getNamedItem("diff:left");
                                Node noDireito2 = filho2.getAttributes().getNamedItem("diff:right");
                                
                                if(noDireito1 != null && noDireito2 != null)
                                {
                                    if(! noDireito1.getNodeValue().equals(noDireito2.getNodeValue()))
                                       diff.setConflito(true);
                                    //bConflito =true;
                                }
                                
                                if(noEsquerdo1 != null)
                                {
                                    //PAI F1 e PAI F2
                                    if(compareNode(noEsquerdo1, noEsquerdo2))
                                    {
                                        filho1.getAttributes().removeNamedItem("diff:left");
                                    }
                                    else
                                    {
                                        //PAI F1 e FILHO F2
                                        if(compareNode(noEsquerdo1, noDireito2))
                                            filho1.getAttributes().removeNamedItem("diff:left");
                                    }
                                }
                                if(noDireito1 != null)
                                {
                                    //FILHO F1 e PAI F2
                                    if(compareNode(noDireito1, noEsquerdo2))
                                    {
                                       filho1.getAttributes().removeNamedItem("diff:right");
                                    }
                                    else
                                    {
                                        //FILHO F1 e FILHO F2
                                        if(compareNode(noDireito1, noDireito2))
                                            filho1.getAttributes().removeNamedItem("diff:right");
                                    }
                                }
                                
                                 noEsquerdo1 = filho1.getAttributes().getNamedItem("diff:left");
                                 noDireito1 = filho1.getAttributes().getNamedItem("diff:right");
                                 
                                 if(noEsquerdo1!= null )
                                 {   
                                     if(!bKeepAncestral)
                                     {
                                         filho1.getAttributes().removeNamedItem("diff:left");
                                     }  
                                     else
                                     {
                                       bInsertFilho1 =true;
                                     }
                                 }
                                 
                                 if(noDireito1 != null)
                                      bInsertFilho1 =true;
                                     
                        }
                        
                        
                        //MERGE
                        if(!bKeepAncestral)
                        {
                                if(filho2.getNodeName().contains("diff:value"))
                                {
                                        Node noDireito = filho2.getAttributes().getNamedItem("diff:left");
                                        if(noDireito != null)
                                        {
                                            filho2.getAttributes().removeNamedItem("diff:left");
                                        }
                                }
                                else
                                {

                                    if(filho2.hasAttributes())
                                    {
                                        Node nodeSide = filho2.getAttributes().getNamedItem("diff:side");
                                        if (nodeSide != null && nodeSide.getNodeValue().equals("left")) 
                                        {
                                            continue;
                                        }
                                    }
                                }
                        }
                        
                        //preencherOrigem(filho2,"son1Son2");
                        
                        Node fragment = doc.importNode(filho2,false);
                        //fragment.setUserData("1", DIFF_SON2, null);
                        
                        fragment = ancestral.appendChild(fragment);
                        if(bInsertFilho1)
                        {
                            //preencherOrigem(filho1,"son1");
                            
                            Node fragment1 = doc.importNode(filho1,false);
                            fragment1 = ancestral.appendChild(fragment1);
                        }
                        bFind = true;
                        mergeTree(filho1, filho2, fragment,bKeepAncestral, diff); 
                        elementosInseridos.add(filho1);
                        break;
                    }
                }
                
               if(!bFind)
                {
                        Document doc = ancestral.getOwnerDocument();
                       
                        //MERGE
                        if(!bKeepAncestral)
                        {
                                if(filho2.getNodeName().contains("diff:value"))
                                {
                                        Node noDireito = filho2.getAttributes().getNamedItem("diff:left");
                                        if(noDireito != null)
                                        {
                                            filho2.getAttributes().removeNamedItem("diff:left");
                                        }
                                }
                                else
                                {

                                    if(filho2.hasAttributes())
                                    {
                                        Node nodeSide = filho2.getAttributes().getNamedItem("diff:side");
                                        if (nodeSide != null && nodeSide.getNodeValue().equals("left")) 
                                        {
                                            continue;
                                        }
                                    }
                                }
                        }
                        
                        
                        //preencherOrigem(filho2,"son2");
                        Node fragment = doc.importNode(filho2,true);
                       
                        fragment = ancestral.appendChild(fragment);
                }
       }
    }
    
    private void mergeSubElementos1(NodeList subElementos1,List<Node> elementosInseridos,Node ancestral,ThreeWayDiff diff,boolean bKeepAncestral)
    {
        
         for (int i = 0; i < subElementos1.getLength(); i++) 
       {
                 //
                Node filho1 = subElementos1.item(i);
                if(contemLista(elementosInseridos,filho1))
                    continue;
                
                 if(!bKeepAncestral)
                {
                    
                
                        // DIFERENTE DO PAI
                        if(filho1.getNodeName().contains("diff:value"))
                        {
                                Node noEsquerdo = filho1.getAttributes().getNamedItem("diff:left");
                                boolean hasSameFather = false;
                                if(noEsquerdo != null)
                                {
                                    NodeList lista  = ancestral.getChildNodes();
                                    for (int j = 0; j < lista.getLength(); j++)
                                    {
                                        if(compareNode(lista.item(j),noEsquerdo))
                                        {
                                            ancestral.removeChild(lista.item(j));
                                            hasSameFather = true;
                                            break;
                                        }    
                                    }
                                    
                                    filho1.getAttributes().removeNamedItem("diff:left");
                                }
                                
                                Node noDireito = filho1.getAttributes().getNamedItem("diff:right");
                                
                                //DIferente do PAI E DIferente de FILHO2
                                if(noDireito != null)
                                {
                                    NodeList lista  = ancestral.getChildNodes();
                                    boolean bEqual = false; 
                                    for (int j = 0; j < lista.getLength(); j++)
                                    {
                                        if(compareNode(lista.item(j),noDireito))
                                        {
                                            bEqual = true;
                                            break;
                                        }    
                                    }
                                    if(bEqual)
                                        continue;
                                    else
                                    {
                                        //NAO PAI DE F1 ==  F2
                                        if(!hasSameFather)
                                          diff.setConflito(true);
                                        //bConflito =true;
                                    }
                                }
                        }
                        else
                        {
                            
                            if(filho1.hasAttributes())
                            {
                                Node nodeSide = filho1.getAttributes().getNamedItem("diff:side");
                                if (nodeSide != null && nodeSide.getNodeValue().equals("left")) 
                                {
                                    continue;
                                }
                            }
                           
                        }
                }
                
                
                 Document doc = ancestral.getOwnerDocument();
                 NodeList listAncestral = ancestral.getChildNodes();
                 Boolean bEqual =false; 
                 if(filho1.getNodeName().contains("diff:value") )
                 {
                    Node noEsquerdo = filho1.getAttributes().getNamedItem("diff:left");
                    Node noDireito = filho1.getAttributes().getNamedItem("diff:right");
                   
                    if (noEsquerdo != null) 
                    {
                      
                       for (int j = 0; j < listAncestral.getLength(); j++)
                       {
                           if(compareNode(listAncestral.item(j),noEsquerdo))
                               bEqual =true;
                       }
                    }
                    if(bEqual)
                        filho1.getAttributes().removeNamedItem("diff:left");
                    
                    bEqual =false;
                    if(noDireito!= null)
                    {
                       for (int j = 0; j < listAncestral.getLength(); j++)
                       {
                           if(compareNode(listAncestral.item(j),noDireito))
                               bEqual =true;
                       }
                    }
                    
                    if(bEqual)
                       filho1.getAttributes().removeNamedItem("diff:right");
                 }
                 else
                 {
                     if(ancestral.getNodeName().contains("diff:value"))
                     {
                            Node noEsquerdo = ancestral.getAttributes().getNamedItem("diff:left");
                            Node noDireito = ancestral.getAttributes().getNamedItem("diff:right");

                            if (noEsquerdo != null) 
                            {
                                    if(compareNode(noEsquerdo,filho1))
                                        bEqual =true;
                                
                            }
                            if(bEqual)
                                continue;

                            bEqual =false;
                            if(noDireito!= null)
                            {
                                if(compareNode(noDireito,filho1))
                                        bEqual =true;
                            }

                            if(bEqual)
                              continue;
                     }
                 }
                 //DocumentFragment fragment = doc.createDocumentFragment();
                 //fragment.appendChild(filho2);
                 bEqual = false;
                 for (int j = 0; j < listAncestral.getLength(); j++)
                 {
                      Node nodeFilho = listAncestral.item(j);
                     if(nodeFilho.getNodeName().contains("diff:value"))
                     {
                            Node noEsquerdo = nodeFilho.getAttributes().getNamedItem("diff:left");
                            Node noDireito = nodeFilho.getAttributes().getNamedItem("diff:right");

                            if (noEsquerdo != null) 
                            {
                                    if(compareNode(noEsquerdo,filho1))
                                        bEqual =true;
                                
                            }
                            if(bEqual)
                                continue;

                            bEqual =false;
                            if(noDireito!= null)
                            {
                                if(compareNode(noDireito,filho1))
                                        bEqual =true;
                            }

                            if(bEqual)
                              continue;
                     }
                     
                     //***#####
                     //if(compareNode(nodeFilho,filho1))
                       // bEqual =true;
                 }
                 
                 if(bEqual)
                     continue;
              
                 Node fragment = doc.importNode(filho1,true);
                 ancestral.appendChild(fragment);
                
       }
    }
    
    public void removeAttributes(Node root)
    {
        if(root.hasAttributes())
        {
             NamedNodeMap attrs = root.getAttributes();
             String[] names = new String[attrs.getLength()];
             for (int i = 0; i < names.length; i++) 
             {
                names[i] = attrs.item(i).getNodeName();
             }
            for (int i = 0; i < names.length; i++) 
            {
                if(names[i].contains("diff:side") || names[i].contains("similarity"))
                  attrs.removeNamedItem(names[i]);
                 //attrs.removeNamedItemNS(NAMESPACE,names[i]);
                 //
            }
        }
        
       NodeList list = root.getChildNodes();
       for(int i = 0; i < list.getLength(); i++)
           removeAttributes(list.item(i));
    }
    
     public void copyTree(Node root,Node newRoot)
    {
        
        
       NodeList list = root.getChildNodes();
       for(int i = 0; i < list.getLength(); i++)
       {
          String name  =list.item(i).getNodeName();
          if(name == null || list.item(i).getNodeType() == Node.TEXT_NODE)
              name ="a";
         
          Node nofilho=  DiffXML.getInstance().getDocument().createElement(name);
          nofilho.setNodeValue(list.item(i).getNodeValue());
          newRoot.appendChild(nofilho);
          copyTree(list.item(i),nofilho);
       }
    }
     
    void preencherOrigemFilho1(Node node)
    {
        //if(node.getNodeType()!= Node.TEXT_NODE)
        //((Element) node).setAttributeNS(NAMESPACE, DIFF_PREFIX+"relation" , value);
        
        if(node.getNodeName().contains("diff:value"))
        {
                Node noEsquerdo = node.getAttributes().getNamedItem("diff:left");
                if(noEsquerdo != null)
                {
                        //node.getAttributes().getNamedItem("diff:left").setUserData("Color", "YELLOW", null);
                        System.out.println("USer Data Filho1 Esquerdo");
                }
                    Node noDireito = node.getAttributes().getNamedItem("diff:right");
                if(noDireito != null)
                {
                        //node.getAttributes().getNamedItem("diff:right").setUserData("Color", "YELLOW", null);
                        System.out.println("USer Data Filho1 Direito");
                }
        }
        else
        {

            if(node.hasAttributes())
            {
                
                
                String relation ="";
                Node nodeSimilaridade = node.getAttributes().getNamedItem("diff:similarity");
                if(nodeSimilaridade != null )
                {
                    if(nodeSimilaridade.getNodeValue().contains("1.0"))
                    {
                        relation="ancestralSon1";
                    }
                }
                else
                {
                    Node nodeSide = node.getAttributes().getNamedItem("diff:side");
                    if (nodeSide != null && nodeSide.getNodeValue().equals("right")) 
                    {
                        //node.setUserData("Color", "YELLOW", null);
                        relation = "son1";
                        System.out.println("USer Data Atribute Direito");
                    }

                    if (nodeSide != null && nodeSide.getNodeValue().equals("left")) 
                    {
                        //node.setUserData("Color", "YELLOW", null);
                        relation = "ancestral";
                        System.out.println("USer Data Atribute Esquerdo");
                    }
                }
                ((Element) node).setAttributeNS(NAMESPACE, DIFF_PREFIX+"relation" , relation);
            }
        }
    }
    
    void preencherOrigemFilho2(Node node)
    {
        //if(node.getNodeType()!= Node.TEXT_NODE)
        //((Element) node).setAttributeNS(NAMESPACE, DIFF_PREFIX+"relation" , value);
            if(node.getNodeName().contains("diff:value"))
            {
                    Node noEsquerdo = node.getAttributes().getNamedItem("diff:left");
                    if(noEsquerdo != null)
                    {
                         //node.getAttributes().getNamedItem("diff:left").setUserData("Color", "YELLOW", null);
                         //noEsquerdo.setTextContent("ancestral");
                         System.out.println("USer Data Esquerdo");
                    }
                     Node noDireito = node.getAttributes().getNamedItem("diff:right");
                    if(noDireito != null)
                    {
                         //node.getAttributes().getNamedItem("diff:right").setUserData("Color", "YELLOW", null);
                         System.out.println("USer Data Direito");
                    }
            }
            else
            {

                if(node.hasAttributes())
                {
                    String relation ="";
                    Node nodeSimilaridade = node.getAttributes().getNamedItem("diff:similarity");
                    if(nodeSimilaridade != null )
                    {
                       if(nodeSimilaridade.getNodeValue().contains("1.0"))
                       {
                           relation="ancestralSon2";
                       }
                    }
                    else
                    {
                        Node nodeSide = node.getAttributes().getNamedItem("diff:side"); 
                        if (nodeSide != null && nodeSide.getNodeValue().equals("right")) 
                        {
                        //node.setUserData("Color", "YELLOW", null);

                        relation = "son2";
                        System.out.println("USer Data Atribute Direito");
                        }

                        if (nodeSide != null && nodeSide.getNodeValue().equals("left")) 
                        {
                        //node.setUserData("Color", "YELLOW", null);
                        relation = "ancestral";
                        System.out.println("USer Data Atribute Esquerdo");
                        }
                    }
                    
                     //((Element) node).setAttributeNS(NAMESPACE, DIFF_PREFIX+"relation" , relation);
                }
            }
    }
     
    void preencherSimilaridade(Node node2 , Node node1)
    {
        
          if(node1.hasAttributes())
          {
               Node nodeSimilaridade1 = node1.getAttributes().getNamedItem("diff:similarity");
               if(nodeSimilaridade1!= null)
               {
                   String simFinal = " ";
                   String simValue1 = nodeSimilaridade1.getNodeValue();
                   simFinal = " son1: "+simValue1;
                   if(node2.hasAttributes())
                   {
                        Node nodeSimilaridade2 = node2.getAttributes().getNamedItem("diff:similarity");
                        if(nodeSimilaridade2 != null)
                        {
                           String simValue2 = nodeSimilaridade2.getNodeValue();
                           simFinal+= " son2: "+simValue2;
                        }
                   }
                   //((Element) node2).setAttributeNS(NAMESPACE, DIFF_PREFIX + "similarity", simFinal);
               }
              
          }
    }
    
}
