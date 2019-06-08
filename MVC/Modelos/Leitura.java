package MVC.Modelos;

import java.util.Collection;
import java.util.List;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Leitura{
     
    // Variáveis de Instância
    
    private String fileName;
    
    // Construtores
    
    public Leitura(){
        this.fileName = "";
    }
    
    public Leitura (String fileName){
        this.fileName = fileName;
    }
    
    public Leitura (Leitura leitura){
        this.fileName = leitura.getFileName();
    }
    
    // Gets
    
    public String getFileName(){
        return this.fileName;
    }
    
    // Clone
    
    public Leitura clone(){
        return new Leitura(this);
    }
    
    // Equals
    
    public boolean equals (Object o){
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Leitura l = (Leitura)o;
        return this.fileName.equals(l.getFileName());
    }
    
    // Ler do ficheiro
    
    public Collection<String> readFilesWithIO() throws FileNotFoundException{
        Path p;
        if ((!this.fileName.equals("./Vendas_3M.txt")) && (!this.fileName.equals("./Vendas_5M.txt")) && 
            (!this.fileName.equals("./Clientes.txt")) && (!this.fileName.equals("./Produtos.txt"))){
            File file = new File(Constantes.ficheiroVendas);
            if (!file.exists()) throw new FileNotFoundException(Constantes.ficheiroVendas);
            p = Paths.get(Constantes.ficheiroVendas);
        }
        else{
            File file = new File(this.fileName);
            if (!file.exists()) throw new FileNotFoundException(this.fileName);
            p = Paths.get(this.fileName);
        }
        List<String> l = null;
        try{
            l = Files.readAllLines(p,StandardCharsets.UTF_8);
        }catch(IOException e){
            e.printStackTrace();
        }
        return l;
    }
}