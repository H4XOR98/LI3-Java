package MVC.Vista;

import MVC.IGestaoVendasVista;

/**
 * Escreva a descrição da classe MenuOpcoes aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class MenuOpcoes implements IGestaoVendasVista
{
    public void show() {
        System.out.println("\f|*********************************************************************************|");
        System.out.println("|************************** Sistema de Gestão de Vendas **************************|");
        System.out.println("|*********************************************************************************|\n\n");
        System.out.println("----------------------------- Consultas Estatísticas -----------------------------\n");
        System.out.println(" 1 - Filial a Filial.");
        System.out.println(" 2 - Global.");
        System.out.println(" 0 - Sair");
        System.out.println("\nInsira Opção:");
    }
    
    public void show(int n){ }
    
    public int getNumPaginas(){
        return 0;
    }
    
    public int getPaginaAtual(){
        return 0;
    }
}
