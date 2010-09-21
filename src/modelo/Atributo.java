package modelo;

public class Atributo {

    private String chave;
    private String valor;

    public Atributo(String texto) {
        String textoModificado = texto.trim();
        textoModificado = textoModificado.replaceAll("'", "");
        textoModificado = textoModificado.replaceAll("\"", "");
        String[] split = textoModificado.split("=");
        
        
        this.chave = split[0];
        this.valor = split[1];
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
