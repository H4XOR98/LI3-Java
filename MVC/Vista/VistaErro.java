package MVC.Vista;


import MVC.IGestaoVendasVista;
/**
 * Escreva a descrição da classe VistaErro aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class VistaErro implements IGestaoVendasVista
{
    public void show(){
        System.out.println("\f##########################################");
        System.out.println("############# Opção Inválido #############");
        System.out.println("##########################################");
        System.out.println("\n\n\nIntroduza 'Enter' para prosseguir.");
    }
    
    public void show(int n){}
    
    public int getNumPaginas(){
        return 0;
    }
    
    public int getPaginaAtual(){
        return 0;
    }
}
