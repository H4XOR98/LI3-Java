package MVC;


/**
 * Escreva a descrição da interface IGestaoVendasVista aqui.
 * 
 * @author (seu nome) 
 * @version (número da versão ou data)
 */

public interface IGestaoVendasVista
{
    public void show();
    public void show(int opcao);
    public int getPaginaAtual();
    public int getNumPaginas();
}
