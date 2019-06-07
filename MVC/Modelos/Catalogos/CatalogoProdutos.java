package MVC.Modelos.Catalogos;

import java.util.*;
import java.io.Serializable;
import MVC.Modelos.ModelosBase.*;
import java.util.stream.Collectors;

public class CatalogoProdutos implements ICatalogoProdutos,Serializable{
   
    // Variáveis de Instância
    
    private Collection<Produto> produtos;
    private int numProdutosLidos;

    
    Comparator<Produto> compProdutoCodigo = ((a,b) -> a.getCodigoProduto().compareTo(b.getCodigoProduto()));
    
    
    // Construtores
    
    public CatalogoProdutos(){
        this.produtos = new TreeSet<>(compProdutoCodigo);
        //this.produtos = new HashSet<>();
        //this.produtos = new ArrayList<>();
        this.numProdutosLidos = 0;
    }
    
    public CatalogoProdutos (CatalogoProdutos catProdutos){
        this.produtos = catProdutos.getProdutos();
        this.numProdutosLidos = getNumProdutosLidos();
    }
    
    public CatalogoProdutos (Collection<Produto> produtos, int numProdutosLidos){
        this.produtos = new TreeSet<>(compProdutoCodigo);
        //this.produtos = new HashSet<>();
        //this.produtos = new ArrayList();
        for(Produto p : produtos){
            this.produtos.add(p.clone());
        }
        this.numProdutosLidos = numProdutosLidos;
    }
    
    // Gets
    
    public Collection<Produto> getProdutos(){
        Collection<Produto> aux = new TreeSet<>(compProdutoCodigo);
        //Collection<Produto> aux = new HashSet<>();
        //Collection<Produto> aux = new ArrayList<>();
        for(Produto p : this.produtos){
            aux.add(p.clone());
        }
        return aux;
    }
    
    public int getNumProdutosLidos(){
        return this.numProdutosLidos;
    }
    
    
    // Clone
    
    public CatalogoProdutos clone(){
        return new CatalogoProdutos(this);
    }

    

    // Equals
    
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if((o == null) || (o.getClass() != this.getClass())){
            return false;
        }
        
        CatalogoProdutos catProdutos = (CatalogoProdutos)o;
        
        return this.produtos.equals(catProdutos.getProdutos()) && this.numProdutosLidos == catProdutos.getNumProdutosLidos();
    }
    
    // toString
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("----------Catalogo de Produtos----------\n");
        for(Produto p : this.produtos){
            sb.append(p.toString());
        }
        sb.append("Número de produtos lidos: " + this.getNumProdutosLidos() + "\n");
        sb.append("Número de produtos válidos: " + this.produtos.size() + "\n");
        return sb.toString();
    }
    
    // Valida os produtos
    
    public CatalogoProdutos validaProdutos (Collection<String> produtos){
        CatalogoProdutos aux = new CatalogoProdutos();
        for(String s : produtos){
            if (Produto.validaCodigoProduto(s)){
                Produto p = new Produto(s);
                aux.adicionaProduto(p);
            }
        }
        aux.numProdutosLidos = produtos.size();
        return aux;
    }

    
    public boolean existeProduto(String codigoProduto){
        Produto p = new Produto(codigoProduto);
        return this.produtos.contains(p);
    }
    
    
    // Adiciona um produto 

    public void adicionaProduto(Produto produto){
        this.produtos.add(produto.clone());
    }   

    // Liberta o Catálogo de Produtos
    
    public void libertaCatalogoProdutos(){
        this.produtos.clear();
    }
    
    public List<String> toList(){
        return this.produtos.stream().map(Produto :: getCodigoProduto).collect(Collectors.toList());
    }
}