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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Atributo other = (Atributo) obj;
        if ((this.chave == null) ? (other.chave != null) : !this.chave.equals(other.chave)) {
            return false;
        }
        if ((this.valor == null) ? (other.valor != null) : !this.valor.equals(other.valor)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.chave != null ? this.chave.hashCode() : 0);
        hash = 59 * hash + (this.valor != null ? this.valor.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Atributo{" + "chave=" + chave + ", valor=" + valor + '}';
    }
}
