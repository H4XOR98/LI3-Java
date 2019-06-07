package MVC.Modelos.Catalogos;

import MVC.Modelos.ModelosBase.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
public class CatalogoClientes implements ICatalogoClientes, Serializable
{
    // variáveis de instância
    private Collection<Cliente> clientes;
    private int numeroClientesLidos;
    
    Comparator<Cliente> compClienteCodigo = ((cliente1,cliente2) -> cliente1.getCodigoCliente().compareTo(cliente2.getCodigoCliente()));
    
    public CatalogoClientes(){
        this.clientes = new TreeSet<>(compClienteCodigo);
        //this.clientes = new HashSet<>();
        //this.clientes = new ArrayList<>();
        this.numeroClientesLidos = 0;
    }
    
    public CatalogoClientes(Collection<Cliente> clientes, int numeroClientesLidos){
        this.clientes = new TreeSet<>(compClienteCodigo);
        //this.clientes = new HashSet<>();
        //this.clientes = new ArrayList();
        for(Cliente c : clientes){
            this.clientes.add(c.clone());
        }
        this.numeroClientesLidos = numeroClientesLidos;
    }
    
    public CatalogoClientes(CatalogoClientes catClientes){
        this.clientes = catClientes.getClientes();
        this.numeroClientesLidos = catClientes.getNumeroClientesLidos();
    }
    
    
    public Collection<Cliente> getClientes(){
        Collection<Cliente> aux = new TreeSet<>(compClienteCodigo);
        //Collection<Cliente> aux = new HashSet<>();
        //Collection<Cliente> aux = new ArrayList<>();
        for(Cliente c : this.clientes){
            aux.add(c.clone());
        }
        return aux;
    }
    
    public int getNumeroClientesLidos(){
        return this.numeroClientesLidos;
    }
    
    
    public void addCliente(Cliente cliente){
        this.clientes.add(cliente.clone());
    }   

    
    public void libertaCatalogoClientes(){
        this.clientes.clear();
    }
    
    public CatalogoClientes validaClientes(Collection<String> clientes){
        Collection<Cliente> aux = new TreeSet<>(compClienteCodigo);
        //Collection<Cliente> aux = new HashSet<>();
        //ArrayList<Cliente> aux = new ArrayList<>();
        for(String s : clientes){
            if(Cliente.validaCodigoCliente(s)){
                Cliente c = new Cliente(s);
                aux.add(c.clone());
            }
        }
        return new CatalogoClientes(aux,clientes.size());
    }
    
    
    
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if((o == null) || (o.getClass() != this.getClass())){
            return false;
        }
        
        CatalogoClientes catClientes = (CatalogoClientes)o;
        
        return this.clientes.equals(catClientes.getClientes()) && (this.numeroClientesLidos == catClientes.getNumeroClientesLidos());
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("----------Catalogo de Clientes----------\n");
        for(Cliente c : this.clientes){
            sb.append(c.toString());
        }
        sb.append("O numero de clientes lidos foi de : " + this.numeroClientesLidos + ".\n");
        sb.append("O numero de clientes validos é de : " + this.clientes.size() + ".\n");
        return sb.toString();
    }
    
    
    public List<String> toList(){
        return this.clientes.stream().map(Cliente :: getCodigoCliente).collect(Collectors.toList());
    }
    
    public CatalogoClientes clone(){
        return new CatalogoClientes(this);
    }
}
