package MVC;

import MVC.Controlador.GestaoVendasControlador;
import MVC.Modelos.SGV;
import MVC.Vista.MenuPrincipal;
import java.io.FileNotFoundException;
/**
 * Escreva a descrição da classe GereVendas aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class GestaoVendasApp{ 
    private static IGestaoVendasModelos criaDados(){
        IGestaoVendasModelos gestaoModelos = new SGV();
        try{
            gestaoModelos.lerFicheiros();
        }catch(FileNotFoundException e){
            System.out.println("Ficheiro não encontrado");
        }
        return gestaoModelos;
    }
    
    public static void main(String[] args) {
        IGestaoVendasModelos modelos = criaDados();
        if(modelos == null) { 
            System.out.println("ERRO INICIALIZACAO");
            System.exit(-1); 
        }
        IGestaoVendasVista vista = new MenuPrincipal();
        IGestaoVendasControlador controlador = new GestaoVendasControlador();
        controlador.setModelos(modelos);
        controlador.setVista(vista); 
        controlador.executa();
        System.exit(0);
    }  
}

