package MVC.Controlador;



import java.util.List;
import MVC.Input;
import MVC.IGestaoVendasControlador;
import MVC.IGestaoVendasVista;
import MVC.IGestaoVendasModelos;
import MVC.Vista.VistaErro;
import MVC.Vista.MenuPrincipal;
import MVC.Vista.MenuConsultasEstatisticas;
import MVC.Vista.MenuConsultasInterativas;
import MVC.Vista.ListagemLista;
import MVC.Vista.ListagemSimples;
import MVC.Vista.MenuOpcoes;
import MVC.Vista.Listagem;
import MVC.Modelos.Catalogos.ClienteNaoExisteException;
/**
 * Escreva a descrição da classe GestaoVendasControlador aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class GestaoVendasControlador implements IGestaoVendasControlador
{
    private IGestaoVendasModelos modelos;
    private IGestaoVendasVista vista;
    
    public void setModelos(IGestaoVendasModelos modelos){
        this.modelos = modelos;
    }
    
    public void setVista(IGestaoVendasVista vista){
        this.vista = vista;
    }
    
    public void executa(){
        int opcao = -1;
        do{
            vista.show();
            opcao = Input.lerInt();
            switch(opcao){
                case 0 : break;
                case 1 : this.executaConsultasEstatisticas();
                         break;
                case 2 : this.executaConsultasInterativas();
                         break;
                default : setVista(new VistaErro());
                          vista.show();
                          Input.lerString();
                          opcao = -1;
                         break;
            }
            setVista(new MenuPrincipal());
        }while(opcao != 0);
    }
    
    
    private void executaConsultasEstatisticas(){
        int opcao = -1;
        setVista(new MenuConsultasEstatisticas());
        do{
            vista.show();
            opcao = Input.lerInt();
            switch(opcao){
                case 0 : break;
                case 1 : 
                         break;
                case 2 : 
                         break;
                case 3 : 
                         break;
                default : setVista(new VistaErro());
                          vista.show();
                          Input.lerString();
                          setVista(new MenuConsultasEstatisticas());
                          opcao = -1;
            }
        }while(opcao != 0);
    }
    
    private void executaConsultasInterativas(){
        int opcao = -1;
        setVista(new MenuConsultasInterativas());
        do{
            vista.show();
            opcao = Input.lerInt();
            switch(opcao){
                case 0 : setVista(new MenuPrincipal());
                        break;
                case 1 : this.q1();
                         break;
                case 2 : this.q2();
                         break;
                case 3 : this.q3();
                         break;
                case 4 : 
                         break;
                case 5 : 
                         break;
                case 6 : 
                         break;
                case 7 : 
                         break;
                case 8 : 
                         break;
                case 9 : 
                         break;
                case 10 : 
                         break;
                default : setVista(new VistaErro());
                          vista.show();
                          Input.lerString();
                          setVista(new MenuConsultasInterativas());
                          opcao = -1;
            }
        }while(opcao != 0);
    }
    
    
    
    private void q1() {
        String titulo = "------- Produtos nunca comprados -------";
        int opcao = 0;
        int pagina = 0;
        int paginaOP = 0;
        List<String> l = this.modelos.q1();
        setVista(new ListagemLista(titulo, l));
        do {
            vista.show(pagina);
            opcao = Input.lerInt();
            switch(opcao) {
                case 1 : pagina -= 1;
                         if(pagina < 0) pagina = 0;
                         vista.show(pagina);
                         break;
                case 2:
                        pagina += 1;
                        if(pagina >= vista.getNumPaginas()) pagina = vista.getNumPaginas() - 1;
                        vista.show(pagina);
                        break;
                case 3: setVista(new ListagemSimples("\n\nQual a página?"));
                        vista.show();
                        paginaOP = Input.lerInt();
                        if(paginaOP < 0 || paginaOP > vista.getNumPaginas()){
                            setVista(new VistaErro());
                            vista.show();
                            Input.lerString();
                        }else{
                             pagina = paginaOP-1;
                        }
                        setVista(new ListagemLista(titulo, l));
                        vista.show(pagina);
                        break;
                case 0: setVista(new ListagemSimples("\n\nTotal produtos nunca comprados: " + l.size() + " produtos.\n\n\nPressione 'Enter' para retroceder."));
                        vista.show();
                        Input.lerString();
                        setVista(new MenuConsultasInterativas());
                        break;
                default: setVista(new VistaErro());
                         vista.show();
                         Input.lerString();
                         setVista(new ListagemLista(titulo, l));
                    break;
            }
        }while(opcao != 0);
        l.clear();
    }
    
    
    private void q2() {
        String titulo = "------- Número total de vendas realizadas e de clientes distintos que as fizeram. -------";
        int opcao = 0;
        String s = "";
        int mes = 0;
        setVista(new MenuOpcoes());
        do {
            vista.show();
            opcao = Input.lerInt();
            if(opcao < 0 || opcao > 2){
                setVista(new VistaErro());
                vista.show();
                Input.lerString();
                setVista(new ListagemSimples("Pressione 'Enter' para retroceder."));
                vista.show();
                Input.lerString();
                setVista(new MenuOpcoes());
            }else if(opcao == 0){
                 setVista(new ListagemSimples("Pressione 'Enter' para retroceder."));
                 vista.show();
                 Input.lerString();
                 setVista(new MenuConsultasInterativas());
            }else{
                setVista(new ListagemSimples("Introduza o mês."));
                vista.show();
                mes = Input.lerInt();
                if(mes < 1 || mes > 12){
                    setVista(new VistaErro());
                    vista.show();
                    setVista(new ListagemSimples("Pressione 'Enter' para retroceder."));
                    vista.show();
                    Input.lerString();
                    setVista(new MenuOpcoes());
                }else{
                    s = this.modelos.q2(mes-1,opcao);
                    setVista(new Listagem(titulo,s));
                    vista.show();
                }
            }
        }while(opcao < 0 || opcao > 2 && mes < 1 || mes > 12);
    }
    
    private void q3(){
        String titulo = "------- Dados refentes às compras de um cliente -------";
        int opcao = 0;
        int pagina = 0;
        int paginaOP = 0;
        setVista(new Listagem(titulo,"Introduza um código de cliente."));
        vista.show();
        String codCli = Input.lerString();
        try{
            List<String> l = this.modelos.q3(codCli);
            setVista(new ListagemLista(titulo, l));
            do {
                vista.show(pagina);
                opcao = Input.lerInt();
                switch(opcao) {
                    case 1: pagina -= 1;
                            if(pagina < 0) pagina = 0;
                            vista.show(pagina);
                            break;
                    case 2: pagina += 1;
                            if(pagina >= vista.getNumPaginas()) pagina = vista.getNumPaginas() - 1;
                            vista.show(pagina);
                            break;
                    case 3: setVista(new ListagemSimples("\n\nQual a página?"));
                            vista.show();
                            paginaOP = Input.lerInt();
                            if(paginaOP < 0 || paginaOP > vista.getNumPaginas()){
                                setVista(new VistaErro());
                                vista.show();
                                Input.lerString();
                            }else{
                                pagina = paginaOP-1;
                            }
                            setVista(new ListagemLista(titulo, l));
                            vista.show(pagina);
                            break;
                    case 0: setVista(new ListagemSimples("\n\n\nPressione 'Enter' para retroceder."));
                            vista.show();
                            Input.lerString();
                            setVista(new MenuConsultasInterativas());
                            break;
                    default: setVista(new VistaErro());
                             vista.show();
                             Input.lerString();
                             setVista(new ListagemLista(titulo, l));
                             break;
                }
            }while(opcao != 0);
            l.clear();
        }catch(ClienteNaoExisteException e){
            setVista(new ListagemSimples("\fNão existe nenhum cliente com o código " + e.getMessage() + ".\n\nPara Prosseguir pressione 'Enter'."));
            vista.show();
            Input.lerString();
            setVista(new MenuConsultasInterativas());
        }
    }
}
