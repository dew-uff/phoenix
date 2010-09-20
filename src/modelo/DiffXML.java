package modelo;

public class DiffXML {

    public static double calcularPercentual(String texto1, String texto2) {
        int qtdCaracteresIguais = 0;

        if (texto1.equals(texto2)) {
            return 1;
        } else {
            return 0;
        }
    }
}
