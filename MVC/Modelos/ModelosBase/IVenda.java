package MVC.Modelos.ModelosBase;

import MVC.Modelos.ModelosBase.*;

public interface IVenda
{
    public String getCodProd();
    public String getCodCli();
    public int getQuantidade();
    public double getPreco();
    public int getMes();
    public String getTipo();
    public int getFilial();
    
    public double faturado();
    
    //public String toString();
    //public IVenda clone();
    //public boolean equals();
    //public int hashcode();
}

