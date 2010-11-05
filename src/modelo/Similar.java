package modelo;

public abstract class Similar<T extends Similar> {

    public abstract double similar(T y);
}
