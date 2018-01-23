package br.com.consultaiv2.model;

/**
 * Created by renan.boni on 19/01/2018.
 */

public class BilheteUnico {

    private String apelido;
    private double saldo;
    private boolean estudante;

    public BilheteUnico(){}

    public BilheteUnico(String apelido, double saldo, boolean estudante) {
        this.apelido = apelido;
        this.saldo = saldo;
        this.estudante = estudante;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean getEstudante() {
        return estudante;
    }

    public void setEstudante(boolean estudante) {
        this.estudante = estudante;
    }

    @Override
    public String toString() {
        return "BilheteUnico{" +
                "apelido='" + apelido + '\'' +
                ", saldo=" + saldo +
                ", estudante=" + estudante +
                '}';
    }
}
