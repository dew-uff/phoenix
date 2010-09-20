package modelo;

public class Tag {

    private String nome;

    public Tag(String texto) {
        this.nome = pegarNome(texto);
    }

    public boolean isVazia() {
        return true;
    }

    private String pegarNome(String texto) {
        String textoModificado = texto.replace("<", "");
        textoModificado = textoModificado.replace(">", "");
        textoModificado = textoModificado.replace("/", "");

        return textoModificado;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
