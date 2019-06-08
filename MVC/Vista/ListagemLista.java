package MVC.Vista;

import MVC.Input;
import java.util.List;
import java.util.ArrayList;
import MVC.IGestaoVendasVista;
/**
 * Escreva a descrição da classe Listagem aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class ListagemLista implements IGestaoVendasVista
{
    private String titulo;
    private List<String> lista;
    private int paginaAtual;
    private int numPaginas;
    
    
    private static final int elementosPorPagina = 3;
    
    public ListagemLista(){
        this.titulo = "n/a";
        this.lista = new ArrayList<String>();
        this.paginaAtual = 0;
        this.numPaginas = 0;
    }
    
    
    /**
     * Constructor for objects of class Menu
     */
    public ListagemLista(String titulo,List<String> lista) {
        this.titulo = titulo;
        this.lista = lista;
        this.paginaAtual = 0;
        this.numPaginas = (int) Math.ceil(((double)this.lista.size()) / ((double)this.elementosPorPagina));
    }
    
    
    public void setLista(List<String> lista) {
        this.lista = lista;
    }
    
    public int getPaginaAtual(){
        return this.paginaAtual;
    }
    
    
    public int getNumPaginas(){
        return this.numPaginas;
    }
    
    
    public void show() {
        System.out.println("\nOpção:");
    }
    
    
    
    /**
     * MÈtodo para apresentar o menu e ler uma opÁ„o.
     * 
     */
    public void show(int op) {
        System.out.println("\f" + titulo + "\n");
        if(this.lista.isEmpty()){
            System.out.println("Não existem elementos a listar.");
            System.out.println("\nPagina : " + (this.paginaAtual) + " de " + this.numPaginas + ".");
        }
        else{
            this.paginaAtual = op;
            int num = this.elementosPorPagina * this.paginaAtual;
            for(int i = num ; i < this.elementosPorPagina + num && i < this.lista.size() && this.paginaAtual < this.numPaginas ; i++){
                System.out.println(this.lista.get(i));
            }
            System.out.println("\nPagina : " + (this.paginaAtual + 1) + " de " + this.numPaginas + ".");
        }
        if(this.numPaginas == 0 || this.numPaginas == 1){
                System.out.println("************Navegar************");
                System.out.println("*         0 - Sair            *");
                System.out.println("*******************************");
            }else if(this.paginaAtual == 0){
                System.out.println("\nJá se encontra na primeira página!\n");
                System.out.println("************Navegar************");
                System.out.println("*         2 - Próximo         *");
                System.out.println("*         3 - Escolher página *");
                System.out.println("*         0 - Sair            *");
                System.out.println("*******************************");
            }else if(this.paginaAtual+1 == this.numPaginas){
                System.out.println("Já se encontra na última página!\n");
                System.out.println("************Navegar************");
                System.out.println("*         1 - Anterior        *");
                System.out.println("*         3 - Escolher página *");
                System.out.println("*         0 - Sair            *");
                System.out.println("*******************************");
            }else{
                System.out.println("************Navegar************");
                System.out.println("*         1 - Anterior        *");
                System.out.println("*         2 - Próximo         *");
                System.out.println("*         3 - Escolher página *");
                System.out.println("*         0 - Sair            *");
                System.out.println("*******************************");
            }
        show();
    }
}
