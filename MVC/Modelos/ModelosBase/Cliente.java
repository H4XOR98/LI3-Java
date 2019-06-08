package MVC.Modelos.ModelosBase;

import java.io.Serializable;

public class Cliente implements ICliente, Serializable
{
    //Variaveis de instancia
    private String codigoCliente;
    
    
    
    
    /**
     * Construtores da classe Cliente.
     * Declaracao dos construtores por omissao (vazio),
     * parametrizado e de copia.
     */
    
   
    /**
     * Construtor por omissao de Cliente.
     */
    public Cliente(){
        this.codigoCliente = "n/a";
    }
    
    /**
     * Construtor parametrizado de Cliente.
     * Aceita como parametros um codigo.
     */
    public Cliente(String codigoCliente){
        this.codigoCliente = codigoCliente;
    }
    
    /**
     * Construtor de copia de Cliente.
     * Aceita como parametro outro Cliente e utiliza os metodos
     * de acesso ao valor da variavel de instancia.
     */
    public Cliente(Cliente cliente){
        this.codigoCliente = cliente.getCodigoCliente();
    }
    
    
     /**
      * metodos de instancia
      */
    
    /**
      * Devolve o codigo de Cliente.
      * 
      * @return codigo de Cliente.
      */
    public String getCodigoCliente(){
        return this.codigoCliente;
    }
    
    /**
     * Metodo que determina se um Cliente e valido.
     * @return booleano que e verdadeiro se o codigo satisfizer as condicoes.
     */
    public static boolean validaCodigoCliente(String codCliente){
        return codCliente.length() == 5 
                   && (codCliente.matches("[A-Z]{1}[1-4]{1}[0-9]{3}") 
                   ||  codCliente.matches("[A-Z]{1}[5]{1}[0]{3}"));
    }
    
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if((o == null) || (o.getClass() != this.getClass())){
            return false;
        }
        Cliente c = (Cliente)o;
        return this.codigoCliente.equals(c.getCodigoCliente());
    }
    
    
    /**
     * Metodo que devolve a representacao em String do Cliente.
     * @return String com o codigo de Cliente.
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo: " + this.codigoCliente + ";\n");
        return sb.toString();
    }
    
    public Cliente clone(){
        return new Cliente(this);
    }
}
