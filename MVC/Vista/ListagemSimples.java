package MVC.Vista;

import MVC.IGestaoVendasVista;

/**
 * Escreva a descrição da classe ListagemString aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class ListagemSimples implements IGestaoVendasVista
{
    private String conteudo;
    
    public ListagemSimples() {
        this.conteudo = "n/a";
    }
    
    /**
     * Constructor for objects of class Menu
     */
    public ListagemSimples(String conteudo) {
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
        if(this.conteudo.isEmpty()){
            System.out.println("#### Não existem elementos a listar. ###");
        }else{
            System.out.println(conteudo);
        }
    }
    
    public void show(int n) {}
}
