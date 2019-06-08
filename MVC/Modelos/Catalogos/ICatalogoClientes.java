package MVC.Modelos.Catalogos;

import java.util.*;
import MVC.Modelos.ModelosBase.*;
import MVC.Modelos.Catalogos.*;

public interface ICatalogoClientes
{
    public Collection<Cliente> getClientes();
    public int getNumeroClientesLidos();
    public void addCliente(Cliente cliente);
    public void libertaCatalogoClientes();
    public CatalogoClientes validaClientes(Collection<String> clientes);
    
    public boolean equals(Object o);
    public String toString();
    public CatalogoClientes clone();
}
