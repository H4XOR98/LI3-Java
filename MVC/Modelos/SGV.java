package MVC.Modelos;

import java.util.InputMismatchException;
import java.lang.NumberFormatException;
import MVC.Modelos.*;
import MVC.Modelos.Catalogos.*;
import MVC.Modelos.ModelosBase.*;
import MVC.IGestaoVendasModelos;
import MVC.Modelos.Leitura;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import java.text.DecimalFormat;
import java.io.FileNotFoundException;

public class SGV implements IGestaoVendasModelos{
    
    // Variáveis de Instância
    
    private CatalogoClientes catClientes;
    private CatalogoProdutos catProdutos;
    private Faturacao faturacao;
    private GestaoFilial gestaoFilial;
    
    private static int numMeses = Constantes.numMeses;
    private static int numFiliais = Constantes.numFiliais;
    
    // Construtores
    
    public SGV(){
        this.catClientes = new CatalogoClientes();
        this.catProdutos = new CatalogoProdutos();
        this.faturacao = new Faturacao();
        this.gestaoFilial = new GestaoFilial();
    }
    
    // -------------------------------------------------------------- Leitura -------------------------------------------------------------- \\
    
    public String lerFicheiros() throws FileNotFoundException{
        
        StringBuilder sb = new StringBuilder();
        sb.append("Ficheiro de vendas: " + Constantes.ficheiroVendas + "\n");
        Leitura leitura;
        
        leitura = new Leitura(Constantes.ficheiroClientes);
        Collection<String> clientes = leitura.readFilesWithIO();
        this.catClientes.validaClientes(clientes);
        
        leitura = new Leitura(Constantes.ficheiroProdutos);
        Collection<String> produtos = leitura.readFilesWithIO();
        this.catProdutos.validaProdutos(produtos);
        
        leitura = new Leitura(Constantes.ficheiroVendas);
        Collection<String> vendasLidas = leitura.readFilesWithIO();
        
        for (String p : produtos){
            try{
                faturacao.insereProduto(p);
            }catch(ProdutoJaExisteException e){
                System.out.println(e.getMessage());
            }
        }
        
        sb.append("Total de clientes: " + clientes.size() + "\n");
        sb.append("Total de produtos: " + produtos.size() + "\n");
        
        
        clientes.clear();
        produtos.clear();
        
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
        sb.append("Vendas erradas: " + (vendasLidas.size() - vendas.size()) + "\n");
        vendasLidas.clear();
        this.faturacao.adicionaVendas(vendas);
        this.gestaoFilial.adicionaVendas(vendas);
        vendas.clear();
        return sb.toString();
    }
        
    // --------------------------------------------------------- Queries Interativas -------------------------------------------------------- \\
    
    Comparator<String> comparaProdutos = (a,b) -> a.compareTo(b);
    /*
     * Lista ordenada alfabeticamente com os códigos dos produtos nunca comprados e o seu respectivo total;
     */
    public List<String> q1 (){
        List<String> aux = new ArrayList<>();
        boolean repetido = false;
        Fatura fatura = null;
        for(String codProduto : faturacao.getProdutos()){
            repetido = false;
            for(int i = 0 ; i < numMeses && !repetido ; i++){
                for(int j = 0; j < numFiliais && !repetido ; j++){
                    fatura = this.faturacao.getFatura(codProduto,i,j);
                    if(!fatura.faturaVazia()){
                        repetido = true;
                    }
                }
            }
            if(fatura != null && !aux.contains(codProduto) && !repetido){
                aux.add(codProduto);
            }
        }
        aux.sort(comparaProdutos);
        return aux;
    }
    
    
    // Dado um mês válido, determinar o número total global de vendas realizadas e o número total de clientes distintos que as fizeram; 
    // Fazer o mesmo mas para cada uma das filiais.
    
    public String q2 (int mes, int opcao){
        int numTotalVendas = 0, numTotalClientes = 0;
        int numVendasFilial1 = 0, numVendasFilial2 = 0, numVendasFilial3 = 0;
        int numClienteFilial1 = 0, numClienteFilial2 = 0, numClienteFilial3 = 0;
        boolean clienteComprou, clienteComprouFilial1, clienteComprouFilial2, clienteComprouFilial3;
        for(String codCliente : this.gestaoFilial.getClientes()){
            clienteComprou = false; clienteComprouFilial1 = false; clienteComprouFilial2 = false; clienteComprouFilial3 = false;
            for (String codProduto : this.gestaoFilial.getProdutos(codCliente)){
                for(int j = 0; j<numFiliais ; j++){
                    Fatura fatura = this.gestaoFilial.getFatura(codCliente, codProduto, mes, j);
                    if (j == 0) {numVendasFilial1 += fatura.getQuantidade(); if (fatura.getQuantidade()>0) clienteComprouFilial1 = true;}
                    if (j == 1) {numVendasFilial2 += fatura.getQuantidade(); if (fatura.getQuantidade()>0) clienteComprouFilial2 = true;}
                    if (j == 2) {numVendasFilial3 += fatura.getQuantidade(); if (fatura.getQuantidade()>0) clienteComprouFilial3 = true;}
                    numTotalVendas += fatura.getQuantidade();
                    if(fatura.getQuantidade()>0) clienteComprou = true;
                }
            }
            if (clienteComprou == true) numTotalClientes += 1;
            if (clienteComprouFilial1 == true) numClienteFilial1 += 1;
            if (clienteComprouFilial2 == true) numClienteFilial2 += 1;
            if (clienteComprouFilial3 == true) numClienteFilial3 += 1;
        }
        StringBuilder sb = new StringBuilder();
        if (opcao == 1){
            sb.append("Número total de vendas: "+ numTotalVendas + "\n");
            sb.append("Número total de clientes que compraram no mês " + mes + ": " + numTotalClientes + "\n");
        }
        if (opcao == 2){
            sb.append("Número de vendas na filial 1, no mês " + mes + ": " + numVendasFilial1 + "\n");
            sb.append("Número de vendas na filial 2, no mês " + mes + ": "  +numVendasFilial2 + "\n");
            sb.append("Número de vendas na filial 3, no mês " + mes + ": " + numVendasFilial3 + "\n");
            sb.append("Número de clientes que compraram na filial 1, no mês " + mes + ": " + numClienteFilial1 + "\n");
            sb.append("Número de clientes que compraram na filial 2, no mês " + mes + ": " + numClienteFilial2 + "\n");
            sb.append("Número de clientes que compraram na filial 3, no mês " + mes + ": " + numClienteFilial3 + "\n");
        }
        return sb.toString();
    }
    
    // Dado um código de cliente, determinar, para cada mês, quantas compras fez, quantos produtos distintos comprou e quanto gastou no total.
             
    public List<String> q3 (String codCliente) throws ClienteNaoExisteException{
        List <String> result = new ArrayList<>(numMeses);
        int numVendas = 0, numProdutos = 0;
        double faturado = 0.0;
        DecimalFormat formatter = new DecimalFormat("#0.00"); 
        if (!this.gestaoFilial.clienteExiste(codCliente)){
            throw new ClienteNaoExisteException(codCliente);
        }
        for (int i = 0; i < numMeses; i++){
            StringBuilder sb = new StringBuilder();
            sb.append(Meses.getMes(i) + ":\n");
            numVendas = 0;
            faturado = 0.0;
            numProdutos = 0;
            for (String codProduto : this.gestaoFilial.getProdutos(codCliente)){
                for (int j = 0; j < numFiliais; j++){
                    Fatura fatura = this.gestaoFilial.getFatura(codCliente, codProduto, i, j);
                    numVendas += fatura.getQuantidade();
                    faturado += fatura.getFaturado();
                }
                if (numVendas > 0) numProdutos++;
            }
            sb.append("Número de compras: " + numVendas + "\n");
            sb.append("Total gasto: " + formatter.format(faturado) + "\n");
            sb.append("Número de produtos comprados: " + numProdutos + "\n");
            result.add(i, sb.toString());
        }
        return result;
    }
       
    // Dado o código de um produto existente, determinar, mês a mês, quantas vezes foi comprado, por quantos clientes diferentes e 
    // o total facturado.
    
    public List<String> q4 (String codProduto) throws ProdutoNaoExisteException{
        int numClientes = 0;
        int numVendas = 0;
        double totalFaturado = 0;
        int mes = 0; 
        DecimalFormat formatter = new DecimalFormat("#0.00"); 
        if(!this.gestaoFilial.produtoExiste(codProduto)){
            throw new ProdutoNaoExisteException(codProduto);
        }
        List<String> resultado = new ArrayList<>();
        resultado.add("--------------------- Produto: " + codProduto + " ---------------------\n");
        for(int i = 0 ; i < numMeses ; i++){
            StringBuilder sb = new StringBuilder();
            numVendas = 0;
            totalFaturado = 0;
            sb.append("\t" + Meses.getMes(i) + " : \n");
            numClientes = 0;
            for(String codCliente : this.gestaoFilial.getClientes()){
                if(this.gestaoFilial.produtoExiste(codCliente, codProduto)){
                    for(int j = 0; j < numFiliais ; j++){
                        Fatura fatura = this.gestaoFilial.getFatura(codCliente, codProduto, i, j);
                        numVendas += fatura.getQuantidade();
                        totalFaturado += fatura.getFaturado();
                        if(!fatura.faturaVazia()){
                            numClientes++;
                        }
                    }
                }
            }
            sb.append("\t   Número de Vendas: " + numVendas + ";\n");
            sb.append("\t   Total Faturado: " + formatter.format(totalFaturado) + ";\n");
            sb.append("\t   Número de clientes diferentes " + numClientes + ";\n\n");
            resultado.add(sb.toString());
        }
        return resultado;
    }
        
    // Dado o código de um cliente determinar a lista de códigos de produtos que mais comprou (e quantos), ordenada por ordem decrescente
    // de quantidade e, para quantidades iguais, por ordem alfabética dos códigos.
    // Comparador mal definido
    
    Comparator<String> comparaGestao = (a,b) -> a.compareTo(b);
                                     
    public List<String> q5 (String codCliente) throws ClienteNaoExisteException{
        if(!this.gestaoFilial.clienteExiste(codCliente)){
            throw new ClienteNaoExisteException(codCliente);
        }
        List<String> resultado = new ArrayList<>();
        Map <String,Fatura> resultadoMap = new TreeMap(comparaGestao);
        for(String codProduto : this.gestaoFilial.getProdutos(codCliente)){
            Fatura faturaAux = new Fatura();
            for(int i = 0; i < numMeses ; i++){
                for(int j = 0; j < numFiliais ; j++){
                    Fatura fatura = this.gestaoFilial.getFatura(codCliente, codProduto, i, j);
                    faturaAux.atualizaFatura(fatura.getQuantidadeN(), fatura.getPrecoN(), "N");
                    faturaAux.atualizaFatura(fatura.getQuantidadeP(), fatura.getPrecoP(), "P");
                }
            }
            resultadoMap.put((String)codProduto,(Fatura)faturaAux);
        }
        for(String s : resultadoMap.keySet()){
            resultado.add(s);
        }
        resultadoMap.clear();
        return resultado;
    }
    
    // Determinar o conjunto dos X produtos mais vendidos em todo o ano (em número de unidades vendidas) indicando o número total de 
    // distintos clientes que o compraram (X é um inteiro dado pelo utilizador).
    // O x não pode ser menor que 0
    
    public List<String> q6 (int x) throws NumeroInvalidoException{
        if(x < 0){
            throw new NumeroInvalidoException("x é menor 0");
        }
        int quantidade = 0;
        int numClientes = 0;
        List<String> resultado = new ArrayList<>();
        Map<String,Integer> maisVendidos = new TreeMap<>();
        Map<String,Integer> clientesDiferentes = new HashMap<>();
        for(String codCliente : this.gestaoFilial.getClientes()){
            for(String codProduto : this.gestaoFilial.getProdutos(codCliente)){
                for(int i = 0; i < numMeses ; i++){
                    for(int j = 0 ; j < numFiliais ; j++){
                        Fatura fatura = this.gestaoFilial.getFatura(codCliente, codProduto, i, j);
                        if(maisVendidos.containsKey(codProduto)){
                            quantidade = maisVendidos.get(codProduto);
                        }else{
                            quantidade = 0;
                        }
                        quantidade += fatura.getQuantidade();
                        maisVendidos.put(codProduto,quantidade);
                    }
                }
                if(clientesDiferentes.containsKey(codProduto)){
                    numClientes = clientesDiferentes.get(codProduto);
                }else{
                    numClientes = 0;
                }
                numClientes++;
                clientesDiferentes.put(codProduto,numClientes);
            }
        }
        
        maisVendidos = maisVendidos.entrySet().stream()
                                   .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                   .limit(x)
                                   .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
                                   
        maisVendidos.forEach((k,v) -> resultado.add("O produto com o código " + k + " foi comprado por " + clientesDiferentes.get(k) + " clientes diferentes.\n"));                                                    
        maisVendidos.clear();
        clientesDiferentes.clear();
        return resultado;
    }
    
    // Determinar, para cada filial, a lista dos três maiores compradores em termos de dinheiro facturado.
    
    public String q7 (){
        StringBuilder sb = new StringBuilder();
        Map<String,Integer> clientes;
        int faturado;
        for(int i = 0 ; i < numFiliais; i++){
            clientes = new TreeMap<>();
            faturado = 0;
            for(String codCliente : this.gestaoFilial.getClientes()){
                for (String codProduto : this.gestaoFilial.getProdutos(codCliente)){
                    for(int j = 0 ; j < numMeses ; j++){
                        Fatura fatura = this.gestaoFilial.getFatura(codCliente, codProduto, j, i);
                        if(clientes.containsKey(codCliente)){
                            faturado = clientes.get(codCliente);
                        }else{
                            faturado = 0;
                        }
                        faturado += fatura.getFaturado();
                    }
                }
                clientes.put(codCliente,faturado);
            }
            
            clientes = clientes.entrySet().stream()
                                   .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                   .limit(3)
                                   .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
            sb.append("Filial: " + (i+1) + "\n");
            for(String s : clientes.keySet()){
                sb.append("\t" + s + ";\n");
            }
        }
        return sb.toString();
    }
   
    // Determinar os códigos dos X clientes (sendo X dado pelo utilizador) que compraram mais produtos diferentes (não interessa 
    // a quantidade nem o valor), indicando quantos, sendo o critério de ordenação a ordem decrescente do número de produtos.
    
    public List<String> q8 (int x) throws NumeroInvalidoException{
        if(x < 0){
            throw new NumeroInvalidoException("x é menor 0");
        }
        int totalComprado, i, j;
        boolean comprou = false;
        List<String> resultado = new ArrayList<String>();
        Map<String,Integer> clientesFaturados = new HashMap<String,Integer>();
        for(String codCliente : this.gestaoFilial.getClientes()){
            totalComprado = 0;
            for (String codProduto : this.gestaoFilial.getProdutos(codCliente)){
                for(i = 0; i<12 ; i++){
                    for(j = 0; j<numFiliais ; j++){
                        Fatura fatura = this.gestaoFilial.getFatura(codCliente, codProduto, i, j);
                        if(fatura.getQuantidadeP ()>0 || fatura.getQuantidadeN()>0) comprou = true;
                    }
                }
                if(comprou == true) totalComprado += 1;
                comprou = false;
            }
            clientesFaturados.put(codCliente,totalComprado);
        }
        for(i=0;i<x;i++){
            int max = Collections.max(clientesFaturados.values());
            Iterator it = clientesFaturados.entrySet().iterator();
            j = 0;
            while (it.hasNext() && j!=1) {
                Map.Entry cf = (Map.Entry)it.next();
                if((Integer)cf.getValue() == max) {
                    resultado.add(""+cf.getKey()); 
                    clientesFaturados.remove(cf.getKey()); 
                    j = 1;
                }
            }
        }
        clientesFaturados.clear();
        return resultado;
    }

    // Dado o código de um produto que deve existir, determinar o conjunto dos X clientes que mais o compraram e, para cada um, 
    // qual o valor gasto.
    
    public List<String> q9 (String codProduto, int n) throws NumeroInvalidoException, ProdutoNaoExisteException{
        if(n < 0){
            throw new NumeroInvalidoException("x é menor 0");
        }
        if(!this.gestaoFilial.produtoExiste(codProduto)){
            throw new ProdutoNaoExisteException(codProduto);
        }
        DecimalFormat formatter = new DecimalFormat("#0.00"); 
        List <String> result = new ArrayList<>(n);
        Map <String, Integer> numVendasAux = new HashMap<>();
        Map <String, Double> faturadoAux = new HashMap<>();
        int numVendas = 0;
        double faturado = 0.0;
        for (String codCliente : this.gestaoFilial.getClientes()){
            if (this.gestaoFilial.produtoExiste(codCliente, codProduto)){
                for (int i = 0; i < numMeses; i++){
                    for (int j = 0; j < numFiliais; j++){
                        Fatura fatura = this.gestaoFilial.getFatura(codCliente, codProduto, i, j);
                        if(fatura != null){
                            numVendas += fatura.getQuantidade();
                            faturado += fatura.getFaturado();
                        }
                    }
                }
            }
            numVendasAux.put(codCliente, numVendas);
            faturadoAux.put(codCliente, faturado);
            numVendas = 0;
            faturado = 0;
        }
        for (int i = 0; i < n; i++){
            StringBuilder sb = new StringBuilder();
            int max = Collections.max(numVendasAux.values());
            for (String codCliente : numVendasAux.keySet()){
                if (numVendasAux.get(codCliente) == max && numVendasAux.get(codCliente) != 0){
                    sb.append("Cliente: " + codCliente + " ->  gastou " + formatter.format(faturadoAux.get(codCliente)) + "€\n");
                    result.add(i, sb.toString());
                    numVendasAux.remove(codCliente);
                    break;
                }
            }
        }
        numVendasAux.clear();
        faturadoAux.clear();
        return result;
    }
    
    // Determinar mês a mês, e para cada mês filial a filial, a facturação total com cada produto.

     public List<String> q10(){
        List<String> aux = new ArrayList<>();
        DecimalFormat formatter = new DecimalFormat("#0.00"); 
        for(String codProduto : this.faturacao.getProdutos()){
            StringBuilder sb = new StringBuilder();
            sb.append("--------------------- Produto: " + codProduto + " ---------------------\n");
            for(int i = 0 ; i < numMeses ; i++){
                sb.append("\t" + Meses.getMes(i) + " : \n");
                sb.append("\t  Filial:\n");
                for(int j = 0; j < numFiliais; j++){
                    Fatura fatura = this.faturacao.getFatura(codProduto, i, j);
                    double total = fatura.getFaturado();
                    sb.append("\t   " + (j+1) + " - " + formatter.format(total) + "€;");
                }
                sb.append("\n\n");
            }
            aux.add(sb.toString());
        }
        return aux;
    }
    
    // --------------------------------------------------------- Queries Estatísticas ------------------------------------------------------- \\
    
    // Número total de compras por mês

    public String numTotalVendasMes(){
        StringBuilder sb = new StringBuilder();
        int total = 0;
        for(int i = 0;  i < numMeses ; i++){
            total = 0;
            for(String codCliente : this.gestaoFilial.getClientes()){
                for(String codProduto : this.gestaoFilial.getProdutos(codCliente)){
                    for(int j = 0; j < numFiliais ; j++){
                        Fatura fatura = this.gestaoFilial.getFatura(codCliente, codProduto, i, j);
                        if (fatura!=null){
                            total += fatura.getQuantidade();
                        }
                    }
                }
            }
            sb.append("Numero de vendas em " + Meses.getMes(i) + " foram " + total + ".\n");
        }
        return sb.toString();
    }
    
    // Facturação total por mês (valor total das compras/vendas) para cada filial e o valor total global.

    public String vendasGlobalFilial(){
        StringBuilder sb = new StringBuilder();
        int numVendasMes, numVendasFilial, numVendasGlobal = 0;
        for (int i = 0; i < numMeses; i++){
            sb.append(Meses.getMes(i) + ":\n");
            numVendasMes = 0;
            for (int j = 0; j < numFiliais; j++){
                numVendasFilial = 0;
                for (String codProduto : this.faturacao.getProdutos()){
                    Fatura fatura = this.faturacao.getFatura(codProduto, i, j);
                    numVendasFilial += fatura.getQuantidade();
                }
                numVendasMes += numVendasFilial;
                sb.append("Filial " + (j+1) + ": " + numVendasFilial + " vendas.\n"); 
            }
            numVendasGlobal += numVendasMes;
            sb.append("" + numVendasMes + " vendas no mês.\n\n\n");
        }
        sb.append("----------- Vendas Globais -----------\n           " + numVendasGlobal + " vendas\n--------------------------------------\n");
        return sb.toString();
    }    
    
    
}
