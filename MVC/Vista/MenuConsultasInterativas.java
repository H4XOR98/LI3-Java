package MVC.Vista;

import MVC.IGestaoVendasVista;
/**
 * Escreva a descrição da classe MenuConsultasInterativas aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class MenuConsultasInterativas implements IGestaoVendasVista 
{
    public void show() {
        System.out.println("\f|*********************************************************************************|");
        System.out.println("|************************** Sistema de Gestão de Vendas **************************|");
        System.out.println("|*********************************************************************************|\n\n");
        System.out.println("------------------------------ Consultas Interativas ------------------------------\n");
        System.out.println(" 1 - Produtos nunca comprados.");
        System.out.println(" 2 - Número total de vendas realizadas e de clientes distintos que as fizeram.");
        System.out.println(" 3 - Dados refentes às compras de um cliente.");
        System.out.println(" 4 - Dados refentes às vendas de um produto.");
        System.out.println(" 5 - TOP de produtos que um cliente comprou.");
        System.out.println(" 6 - TOP de produtos ano e número de clientes que os compraram.");
        System.out.println(" 7 - TOP 3 maiores compradores filial a filial");
        System.out.println(" 8 - TOP n maiores compradores de produtos distintos.");
        System.out.println(" 9 - TOP n clientes que compraram um determinado produto.");
        System.out.println("10 - Faturação total de cada produto, mês a mês, filial a filial.");
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
