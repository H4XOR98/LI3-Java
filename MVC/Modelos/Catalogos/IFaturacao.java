package MVC.Modelos.Catalogos;

import java.util.*;
import MVC.Modelos.ModelosBase.*;
/**
 * Escreva a descrição da interface IFaturacao aqui.
 * 
 * @author (seu nome) 
 * @version (número da versão ou data)
 */

public interface IFaturacao
{
    public Map<String,Matriz> getFaturas();
    public void insereProduto(String produto) throws ProdutoJaExisteException;
    public void adicionaVendas(Collection<Venda> vendas);
    public String toString();
    //Queries
    //public List<String> q10 ();
}
