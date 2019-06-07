package MVC.Modelos.Catalogos;

import MVC.Vista.*;
import java.util.List;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Collection;
import java.nio.charset.StandardCharsets;
import java.util.*;

import MVC.Controlador.*;

import MVC.Modelos.*;
import MVC.Modelos.Catalogos.*;
import MVC.Modelos.ModelosBase.*;
public class Main{
    
    // Ler do ficheiro
    
    public static Collection<String> readFilesWithIO(String filePath){
        Path p = Paths.get(filePath);
        List<String> l = null;
        try{
            l = Files.readAllLines(p,StandardCharsets.UTF_8);
        }catch(IOException e){
            e.printStackTrace();
        }
        return l;
    }

    // Main
    
    public static void main(String[] args){
        
        CatalogoClientes catClientes = new CatalogoClientes();
        Collection<String> clientes =  readFilesWithIO("./Clientes.txt");
        catClientes = catClientes.validaClientes(clientes);
        
        CatalogoProdutos catProdutos = new CatalogoProdutos();
        Collection<String> produtos =  readFilesWithIO("./Produtos.txt");
        catProdutos = catProdutos.validaProdutos(produtos);
        
         // ------ FATAURACAO------
        ///*
        /*Faturacao faturacao = new Faturacao();
        for (String p : produtos){
            try{
                faturacao.insereProduto(p);
            }catch(ProdutoJaExisteException e){
                System.out.println(e.getMessage());
            }
        }
        Collection<String> vendasLidas = readFilesWithIO("./Vendas_5M.txt");
        Collection<Venda> vendas = new ArrayList<>();
        if(vendasLidas != null){
            for(String v : vendasLidas){
                Venda vend = validaVenda(v,catClientes,catProdutos);
                if(vend != null){
                    vendas.add(vend.clone());
                }
            }
        }
        faturacao.adicionaVendas(vendas);
        ListagemLista listagem = new ListagemLista("",faturacao.q10());
        listagem.executa();
        faturacao.libertaFaturacao();*/
        
        
        GestaoFilial gestaoFilial = new GestaoFilial();
        Collection<String> vendasLidas = readFilesWithIO("./Vendas_5M.txt");
        Collection<Venda> vendas = new ArrayList<>();
        Venda venda = null;
        if(vendasLidas != null){
            for(String v : vendasLidas){
                venda = Venda.validaVenda(v);
                if(venda != null){
                    vendas.add(venda);
                }
            }
        }
        vendasLidas.clear();
        gestaoFilial.adicionaVendas(vendas);
        vendas.clear();
        
        //ListagemString listagem = new ListagemString(gestaoFilial.q9("UA1915",10),"");
        //ListagemLista listagem = new ListagemLista("",gestaoFilial.q9("UA1915",10));
        //listagem.show();
        
        gestaoFilial.clear();
    }
}