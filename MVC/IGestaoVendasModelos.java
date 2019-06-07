package MVC;

import java.util.List;
import MVC.Modelos.Catalogos.ClienteNaoExisteException;
import MVC.Modelos.Catalogos.NumeroInvalidoException;
import MVC.Modelos.Catalogos.ProdutoNaoExisteException;
import java.io.FileNotFoundException;
/**
 * Escreva a descrição da interface IGereVendasModelos aqui.
 * 
 * @author (seu nome) 
 * @version (número da versão ou data)
 */

public interface IGestaoVendasModelos
{
    public List<String> q1();
    public String q2(int mes, int opcao);
    public List<String> q3(String codCliente) throws ClienteNaoExisteException;
    public List<String> q4(String codProd) throws ProdutoNaoExisteException;
    public List<String> q5 (String codCliente) throws ClienteNaoExisteException;
    public List<String> q6 (int x) throws NumeroInvalidoException;
    public String q7 ();
    public List<String> q8 (int x) throws NumeroInvalidoException;
    public List<String> q9 (String codProduto, int n) throws NumeroInvalidoException, ProdutoNaoExisteException;
    public List<String> q10();
    public String numTotalVendasMes();
    public String vendasGlobalFilial();
    public String lerFicheiros() throws FileNotFoundException;
}
