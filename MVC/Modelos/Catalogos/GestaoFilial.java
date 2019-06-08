package MVC.Modelos.Catalogos;

import MVC.Modelos.Constantes;
import MVC.Modelos.ModelosBase.*;
import java.util.*;
import java.lang.*;
import java.util.stream.Collectors;
import java.io.Serializable;
import MVC.Modelos.Meses;

public class GestaoFilial implements Serializable, IGestaoFilial{
    
    // Variáveis de Instância
    
    private Map <String, Map <String, Matriz>> gestaoFilial;
    
    final static int numMeses = Constantes.numMeses;
    final static int numFiliais = Constantes.numFiliais;
    
    // Construtores
    
    public GestaoFilial(){
        this.gestaoFilial = new HashMap<>();
    }
    
    public GestaoFilial (GestaoFilial gf){
        this.gestaoFilial = gf.getGestaoFilial();
    }

    public GestaoFilial (Map<String,Map<String,Matriz>> gestor){
        this.gestaoFilial = new HashMap<>();
        for (String codCliente : gestor.keySet()){
            Map <String, Matriz> produtos = gestor.get(codCliente); 
            Map <String, Matriz> produtosAux = new HashMap<>();     
            for (String codProduto : produtos.keySet()){
                Matriz matriz = produtos.get(codProduto);
                Matriz matrizAux = new Matriz();
                for (int i = 0; i < numMeses; i++){
                    for (int j = 0; j < numFiliais; j++){
                        Fatura fatura = matriz.getFatura(i,j);
                        matrizAux.setFatura(fatura, i, j);
                    }
                }
            }
            this.gestaoFilial.put(codCliente, produtosAux);
        }    
    }

    // Gets
    
    public Map <String, Map <String, Matriz>> getGestaoFilial(){
        Map <String, Map <String, Matriz>> aux = new HashMap<>();
        for (String codCliente : this.gestaoFilial.keySet()){
            Map <String, Matriz> produtos = this.gestaoFilial.get(codCliente);
            Map <String, Matriz> produtosAux = new HashMap<>(); 
            for (String codProduto : produtos.keySet()){
                Matriz matriz = produtos.get(codProduto);
                Matriz matrizAux = new Matriz();
                for (int i = 0; i < numFiliais; i++){
                    for (int j = 0; j < numMeses; j++){
                        Fatura fatura = matriz.getFatura(i,j);
                        matrizAux.setFatura(fatura, i, j);
                    }
                }
            }
            aux.put(codCliente, produtosAux);
        }  
        return aux;
    }
    
    // Clone 
    
    public GestaoFilial clone(){
        return new GestaoFilial(this);
    }
    
    // Equals

    public boolean equals (Object o){
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        GestaoFilial gf = (GestaoFilial)o;
        return this.gestaoFilial.equals(gf.getGestaoFilial());
    }
    
    // toString
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (String codCliente : this.gestaoFilial.keySet()){
            sb.append("Cliente: " + codCliente + "\n");
            Map <String, Matriz> produtos = this.gestaoFilial.get(codCliente);
            for (String codProduto : produtos.keySet()){
                sb.append("\tProduto: " + codProduto + "\n");;
                Matriz matriz = produtos.get(codProduto);
                for (int i = 0; i < numMeses; i++){
                    sb.append("\t Mês " + Meses.getMes(i) + ":\n");
                    for (int j = 0; j < numFiliais; j++){
                        Fatura fatura = matriz.getFatura(i,j);
                        sb.append("\t  Filial " + (j+1) + ":\n");
                        sb.append(fatura.toString()+"\n\n\n");
                    }
                }
            }
        }
        return sb.toString();
    }
  
    // Criar a matriz Filial - Mês
    
    public List <List <Fatura>> criaMatriz(){
        List <List <Fatura>> meses = new ArrayList<>(numMeses);
        for (int i = 0; i < numMeses; i++){
            List <Fatura> filiais = new ArrayList<>(numFiliais);
            for (int j = 0; j < numFiliais; j++){
                filiais.add(j, new Fatura());
            }
            meses.add(i, filiais);
        }
        return meses;
    }
    
    // Adiciona uma venda
    
    public void adicionaVenda (Venda venda){
        Map <String, Matriz> produtos;
        Matriz matriz;
        if (this.gestaoFilial.containsKey(venda.getCodCli())){
            produtos = this.gestaoFilial.get(venda.getCodCli());
            if (produtos.containsKey(venda.getCodProd())){
                matriz = produtos.get(venda.getCodProd());
                Fatura fatura = matriz.getFatura(venda.getMes() - 1, venda.getFilial() - 1);
                fatura.atualizaFatura(venda.getQuantidade(), venda.getPreco(), venda.getTipo());
            }
            else {
                matriz = new Matriz();
                Fatura fatura = matriz.getFatura(venda.getMes() - 1, venda.getFilial() - 1);
                fatura.atualizaFatura(venda.getQuantidade(), venda.getPreco(), venda.getTipo());
                produtos.put(venda.getCodProd(), matriz);
            }
        }
        else {
            produtos = new HashMap<>();
            matriz = new Matriz();
            Fatura fatura = matriz.getFatura(venda.getMes() - 1, venda.getFilial() - 1);
            fatura.atualizaFatura(venda.getQuantidade(), venda.getPreco(), venda.getTipo());
            produtos.put(venda.getCodProd(), matriz);
            this.gestaoFilial.put(venda.getCodCli(), produtos);
        }
    }
            
    // Adiciona as vendas
    
    public void adicionaVendas (Collection<Venda> vendas){
        for (Venda v : vendas){
            adicionaVenda(v);
        }
    }
    
    public void clear(){
        for (String codCliente : this.gestaoFilial.keySet()){
            Map <String,Matriz> produtos = this.gestaoFilial.get(codCliente);
            for (String codProduto : produtos.keySet()){
                Matriz meses = produtos.get(codProduto);
                meses.clear();
            }
            produtos.clear();
        }
        this.gestaoFilial.clear();
    }
    
    
    public Set<String> getClientes(){
        return this.gestaoFilial.keySet();
    }

    public Set<String> getProdutos(String codCliente){
        return this.gestaoFilial.get(codCliente).keySet();
    }

    public Fatura getFatura (String codCliente, String codProduto, int mes, int filial){
        return this.gestaoFilial.get(codCliente).get(codProduto).getFatura(mes, filial);
    }

    public boolean clienteExiste (String codCliente){
        return this.gestaoFilial.containsKey(codCliente);
    }

    public boolean produtoExiste (String codCliente, String codProduto){
        return this.gestaoFilial.get(codCliente).containsKey(codProduto);
    }
    
    public boolean produtoExiste(String codProduto){
        for(String codCli : this.gestaoFilial.keySet()){
            Map <String, Matriz> produtos = this.gestaoFilial.get(codCli);
            for(String codProd : produtos.keySet()){
                if(codProd.equals(codProduto)){
                    return true;
                }
            }
        }
        return false;
    }
}