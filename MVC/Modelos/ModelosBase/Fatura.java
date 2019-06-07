package MVC.Modelos.ModelosBase;


/**
 * Escreva a descrição da classe Fatura aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Fatura implements IFatura
{
    private int quantidadeP;
    private int quantidadeN;
    private double precoP;
    private double precoN;
    
    public Fatura(){
        this.quantidadeP = 0;
        this.quantidadeN = 0;
        this.precoP = 0.0;
        this.precoN = 0.0;
    }
        
    public Fatura(int quantidadeP, int quantidadeN, double precoP, double precoN){
        this.quantidadeP = quantidadeP;
        this.quantidadeN = quantidadeN; 
        this.precoP = precoP;
        this.precoN = precoN;
    }
    
    public Fatura(Fatura f){
        this.quantidadeP = f.getQuantidadeP();
        this.quantidadeN = f.getQuantidadeN();
        this.precoP = f.getPrecoP();
        this.precoN = f.getPrecoN();
    }
    
    public int getQuantidadeP(){
        return this.quantidadeP;
    }
    
    public int getQuantidadeN(){
        return this.quantidadeN;
    }
    
    public double getPrecoP(){
        return this.precoP;
    }
    
    public double getPrecoN(){
        return this.precoN;
    }
    
    
    public boolean equals(Object o){
        if(o==this) {
            return true;
        }
        if(o==null || o.getClass() != this.getClass()) {
            return false;
        }
        Fatura f = (Fatura)o;
        return (this.quantidadeP == f.getQuantidadeP() && this.quantidadeN == f.getQuantidadeN() && this.precoP == f.getPrecoP() && 
                this.precoN == f.getPrecoP());
    }
    
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\t   Tipo N:\n");
        sb.append("\t      -Quantidade: " + this.quantidadeN + ";\n");
        sb.append("\t      -Total Faturado: " + this.precoN + ";\n");
        sb.append("\t   Tipo P:\n");
        sb.append("\t      -Quantidade: " + this.quantidadeP + ";\n");
        sb.append("\t      -Total Faturado: " + this.precoP + ";");
        return sb.toString();
    }   
    
    public Fatura clone(){
        return new Fatura(this);
    }
    
    public boolean faturaVazia(){
        return quantidadeN == 0 && quantidadeP == 0;
    }
    
    public void atualizaFatura(int quantidade, double preco, String tipo){
        if(tipo.equals("N")){
            this.quantidadeN += quantidade;
            this.precoN += preco;
        }else{
            this.quantidadeP += quantidade;
            this.precoP += preco;
        }
    }
    
    public double getFaturado(){
        return (this.precoP * this.quantidadeP) +(this.precoN * this.quantidadeN);
    }
    
    public int getQuantidade(){
        return this.quantidadeP + this.quantidadeN;
    }
}
