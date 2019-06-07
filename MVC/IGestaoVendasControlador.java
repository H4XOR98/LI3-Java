package MVC;


/**
 * Escreva a descrição da interface IGereVendasControlador aqui.
 * 
 * @author (seu nome) 
 * @version (número da versão ou data)
 */

public interface IGestaoVendasControlador
{
    public void setModelos(IGestaoVendasModelos modelos);
    public void setVista(IGestaoVendasVista vista);
    public void executa();
}
