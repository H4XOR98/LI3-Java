package MVC.Vista;

import MVC.IGestaoVendasVista;
/**
 * Escreva a descrição da classe Listagem aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Listagem implements IGestaoVendasVista
{
    private String titulo;
    private String conteudo;
    
    public Listagem() {
        this.titulo = "n/a";
        this.conteudo = "n/a";
    }
    
    /**
     * Constructor for objects of class Menu
     */
    public Listagem(String titulo,String conteudo) {
        this.titulo = titulo;
        this.conteudo = conteudo;
    }
    
    public int getNumPaginas(){
        return 0;
    }
    
    public int getPaginaAtual(){
        return 0;
    }
    
    /**
     * MÈtodo para apresentar o menu e ler uma opÁ„o.
     * 
     */
    public void show() {
        System.out.println("\f");
        System.out.println(titulo + "\n");
        if(this.conteudo.isEmpty()){
            System.out.println("#### Não existem elementos a listar. ###");
        }else{
            System.out.println(conteudo);
        }
    }
    
    public void show(int n) {}
}
