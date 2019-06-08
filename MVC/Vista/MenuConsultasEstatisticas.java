package MVC.Vista;

import MVC.IGestaoVendasVista;
/**
 * Escreva a descrição da classe MenuConsultasEstatisticas aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class MenuConsultasEstatisticas implements IGestaoVendasVista
{
    public void show() {
        System.out.println("\f|*********************************************************************************|");
        System.out.println("|************************** Sistema de Gestão de Vendas **************************|");
        System.out.println("|*********************************************************************************|\n\n");
        System.out.println("----------------------------- Consultas Estatísticas -----------------------------\n");
        System.out.println(" 1 - Número total de compras por mês.");
        System.out.println(" 2 - Faturação total por mês, filial a filial e global.");
        System.out.println(" 3 - Número de clientes que compraram em cada mês, filial a filial.");
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
