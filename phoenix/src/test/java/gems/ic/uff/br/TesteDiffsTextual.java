package gems.ic.uff.br;

/**
 *
 * @author campello
 */
public class TesteDiffsTextual {

    public String xml = "<teste><nome>Fernando</nome><cpf>123</cpf><idade></idade><as></as><bs></bs><cidade></cidade></teste>";
    public String xml2 = "<teste><nome>Fernando</nome><cpf>123</cpf><idade></idade><cidade></cidade></teste>";

    //O método backtrack foi removido.
//    @Test
//    public void imprimiMaiorSequenciaComum() {
//
//        XML teste1 = new XML(xml);
//        XML teste2 = new XML(xml2);
//        LcsXML lcs = new LcsXML(teste1, teste2);
//        List<SimilarNode> l = lcs.backtrack();
//        for (SimilarNode similarNode : l) {
//            System.out.println("XXXXXXXXXXXXXXXX         " + similarNode.toString());
//        }
//        System.out.println("=============== FIM ==============================");
//    }
}
