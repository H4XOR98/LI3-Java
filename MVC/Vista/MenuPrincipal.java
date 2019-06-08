package MVC.Vista;

import MVC.IGestaoVendasVista;

public class MenuPrincipal implements IGestaoVendasVista {
    /** Apresentar o menu */
    public void show() {
        System.out.println("|\f|***************************************************************************|");
        System.out.println("|*********************** Sistema de Gestão de Vendas ***********************|");
        System.out.println("|***************************************************************************|\n\n");
        System.out.println("------------------------------ Menu Principal ------------------------------\n");
        System.out.println("1 - Consultas Estatísticas");
        System.out.println("2 - Consultas Interativas");
        System.out.println("0 - Sair");
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
