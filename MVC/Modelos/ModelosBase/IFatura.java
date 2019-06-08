package MVC.Modelos.ModelosBase;


/**
 * Escreva a descrição da interface IFatura aqui.
 * 
 * @author (seu nome) 
 * @version (número da versão ou data)
 */

public interface IFatura
{
    public int getQuantidadeP();
    public int getQuantidadeN();
    public double getPrecoP();
    public double getPrecoN();
    
    public boolean equals(Object o);
    public String toString();
    public Fatura clone();
    
    public boolean faturaVazia();
    public void atualizaFatura(int quantidade, double faturado, String tipo);
    public double getFaturado();
    public int getQuantidade();
}
