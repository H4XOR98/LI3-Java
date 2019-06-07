package MVC.Vista;


import MVC.Input;
import java.util.*;
/**
 * Escreva a descrição da classe Teste aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Teste
{
    
    public static void main (String[] args){
        int opcao = 0;
        int pagina = 0;
        int paginaOP = 0;
        String[] ar ={"1","2","3","4","5","6","7","8","9"};
        ListagemLista listagem = new ListagemLista("CU",Arrays.asList(ar));

        do {
            listagem.show(pagina);
            opcao = Input.lerInt();

            switch(opcao) {
                case 1 :
                        pagina -= 1;
                        if(pagina < 0) pagina = 0;
                        listagem.show(pagina);
                        break;
                case 2:
                        pagina += 1;
                        if(pagina >= listagem.getNumPaginas()) pagina = listagem.getNumPaginas() - 1;
                        listagem.show(pagina);
                        break;
                case 3: System.out.println("Qual a página?");
                        paginaOP = Input.lerInt();
                        if(paginaOP < 0 || paginaOP > listagem.getNumPaginas()){
                            System.out.println("ERRO");
                        }else{
                            pagina = paginaOP-1;
                        }
                        //MOstrar LISTA vista = listagem
                        listagem.show(pagina);
                        break;
                case 0:
                        break;
                default: System.out.println("ERRO");
                    break;
            }
        }
        while(opcao != 0);
    }
}
