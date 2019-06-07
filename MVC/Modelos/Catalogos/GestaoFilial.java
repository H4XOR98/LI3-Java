package MVC.Modelos.Catalogos;

import MVC.Modelos.Constantes;
import MVC.Modelos.ModelosBase.*;
import java.util.*;
import java.lang.*;
import java.util.stream.Collectors;

public class GestaoFilial{
    
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
    

    
    /*
    // Dado um mês válido, determinar o número total global de vendas realizadas e o número total de clientes distintos que as fizeram; 
    // Fazer o mesmo mas para cada uma das filiais.
    
    public List<String> q2 (int mes, int opcao){
        List <String> resultado = new ArrayList<>();
        int numTotalVendas = 0, numTotalClientes = 0;
        int numVendasFilial1 = 0, numVendasFilial2 = 0, numVendasFilial3 = 0;
        int numClienteFilial1 = 0, numClienteFilial2 = 0, numClienteFilial3 = 0;
        boolean clienteComprou, clienteComprouFilial1, clienteComprouFilial2, clienteComprouFilial3;
        for(String codCliente : this.gestaoFilial.keySet()){
            clienteComprou = false; clienteComprouFilial1 = false; clienteComprouFilial2 = false; clienteComprouFilial3 = false;
            Map <String,Matriz> produtos = this.gestaoFilial.get(codCliente);
            for (String codProduto : produtos.keySet()){
                Matriz gestaoMes = produtos.get(codProduto);
                Fatura[] gestaoFil = gestaoMes.get(mes);
                for(int j = 0; j<numFiliais ; j++){
                    Fatura fatura = gestaoFil[j];
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
        if (opcao == 1){
            resultado.add("Número total de vendas: "+ numTotalVendas + "\n");
            resultado.add("Número total de clientes que compraram no mês " + mes + ": " + numTotalClientes + "\n");
        }
        if (opcao == 2){
            resultado.add("Número de vendas na filial 1, no mês " + mes + ": " + numVendasFilial1 + "\n");
            resultado.add("Número de vendas na filial 2, no mês " + mes + ": "  +numVendasFilial2 + "\n");
            resultado.add("Número de vendas na filial 3, no mês " + mes + ": " + numVendasFilial3 + "\n");
            resultado.add("Número de clientes que compraram na filial 1, no mês " + mes + ": " + numClienteFilial1 + "\n");
            resultado.add("Número de clientes que compraram na filial 2, no mês " + mes + ": " + numClienteFilial2 + "\n");
            resultado.add("Número de clientes que compraram na filial 3, no mês " + mes + ": " + numClienteFilial3 + "\n");
        }
        return resultado;
    }
    // Dado um código de cliente, determinar, para cada mês, quantas compras fez, quantos produtos distintos comprou e quanto gastou no total.
             
    public List<String> q3 (String codCliente){
        List <String> result = new ArrayList<>(numMeses);
        Map <String, Matriz> produtos = this.gestaoFilial.get(codCliente);
        int numVendas = 0, numProdutos = 0;
        double faturado = 0.0;
        for (int i = 0; i < numMeses; i++){
            StringBuilder sb = new StringBuilder();
            numVendas = 0;
            faturado = 0.0;
            numProdutos = 0;
            for (String codProduto : produtos.keySet()){
               Matriz meses = produtos.get(codProduto);
                Fatura[] filiais = meses.get(i);
                for (int j = 0; j < numFiliais; j++){
                    Fatura fatura = filiais[(j)];
                    numVendas += fatura.getQuantidade();
                    faturado += fatura.getFaturado();
                }
                if (numVendas > 0) numProdutos++;
            }
            sb.append("Número de compras: " + numVendas + "\n");
            sb.append("Total gasto: " + faturado + "\n");
            sb.append("Número de produtos comprados: " + numProdutos + "\n");
            result.add(i, sb.toString());
        }
        return result;
    }
       
    // Dado o código de um produto existente, determinar, mês a mês, quantas vezes foi comprado, por quantos clientes diferentes e 
    // o total facturado.
    
    public List<String> q4(String codProd){
        int numClientes = 0;
        int numVendas = 0;
        double totalFaturado = 0;
        int mes = 0; 
        List<String> resultado = new ArrayList<>();
        resultado.add("--------------------- Produto: " + codProd + " ---------------------\n");
        for(int i = 0 ; i < numMeses ; i++){
            StringBuilder sb = new StringBuilder();
            numVendas = 0;
            totalFaturado = 0;
            sb.append("\t" + Meses.getMes(i) + " : \n");
            numClientes = 0;
            for(String cliente : this.gestaoFilial.keySet()){
                Map <String, Matriz> produtos = this.gestaoFilial.get(cliente);
                if(produtos.containsKey(codProd)){
                    Matriz meses = produtos.get(codProd);
                     Fatura[] filiais = meses.get(i);
                    for(int j = 0; j < numFiliais ; j++){
                        Fatura fatura = filiais[(j)];
                        numVendas += fatura.getQuantidade();
                        totalFaturado += fatura.getFaturado();
                        if(!fatura.faturaVazia()){
                            numClientes++;
                        }
                    }
                }
            }
            sb.append("\t   Número de Vendas: " + numVendas + ";\n");
            sb.append("\t   Total Faturado: " + totalFaturado + ";\n");
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
        if(!this.gestaoFilial.containsKey(codCliente)){
            throw new ClienteNaoExisteException(codCliente);
        }
        List<String> resultado = new ArrayList<>();
        Map <String,Fatura> resultadoMap = new TreeMap(comparaGestao);
        Map <String, Matriz> produtos = this.gestaoFilial.get(codCliente);
        for(String produto : produtos.keySet()){
           Matriz meses = produtos.get(produto);
            Fatura faturaAux = new Fatura();
            for(int i = 0; i < numMeses ; i++){
                Fatura[] filiais = meses.get(i);
                for(int j = 0; j < numFiliais ; j++){
                    Fatura fatura = filiais[(j)];
                    faturaAux.atualizaFatura(fatura.getQuantidadeN(), fatura.getPrecoN(), "N");
                    faturaAux.atualizaFatura(fatura.getQuantidadeP(), fatura.getPrecoP(), "P");
                }
            }
            resultadoMap.put((String)produto,(Fatura)faturaAux);
        }
        resultado.add("O cliente com o código " + codCliente + " comprou " + produtos.size() + " .\n\n");
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
        for(String codCliente : this.gestaoFilial.keySet()){
            Map<String,Matriz> produtos = this.gestaoFilial.get(codCliente);
            for(String codProduto : produtos.keySet()){
                Matriz meses = produtos.get(codProduto);
                for(int i = 0; i < numMeses ; i++){
                    Fatura[] filiais = meses.get(i);
                    for(int j = 0 ; j < numFiliais ; j++){
                        Fatura fatura = filiais[(j)];
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
    
   
    public List<String> q7 (){
        List<String> resultado = new ArrayList();
        Map<String,Integer> clientes;
        int faturado;
        StringBuilder sb;
        for(int i = 0 ; i < numFiliais; i++){
            clientes = new TreeMap<>();
            faturado = 0;
            for(String codCliente : this.gestaoFilial.keySet()){
                Map <String, Matriz> produtos = this.gestaoFilial.get(codCliente);
                for (String codProduto : produtos.keySet()){
                    Matriz meses = produtos.get(codProduto);
                    for(int j = 0 ; j < numMeses ; j++){
                        Fatura fatura = meses.getFatura(j,i);
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
            sb = new StringBuilder();    
            sb.append("Filial: " + (i+1) + "\n");
            for(String s : clientes.keySet()){
                sb.append("\t" + s + ";\n");
            }
            resultado.add(sb.toString());
        }
        resultado.clear();
        return resultado;
    }
   
    
    public List<String> q8 (int x){
        int totalComprado, i, j;
        boolean comprou = false;
        List<String> resultado = new ArrayList<String>();
        Map<String,Integer> clientesFaturados = new HashMap<String,Integer>();
        for(String codCliente : this.gestaoFilial.keySet()){
            Map <String, Matriz> produtos = this.gestaoFilial.get(codCliente);
            totalComprado = 0;
            for (String codProduto : produtos.keySet()){
                Matriz gestaoMes = produtos.get(codProduto);
                for(i = 0; i<12 ; i++){
                    Fatura[] gestaoFil = gestaoMes.get(i);
                    for(j = 0; j<numFiliais ; j++){
                        Fatura f = gestaoFil[(j)];
                        if(f.getQuantidadeP ()>0 || f.getQuantidadeN()>0) comprou = true;
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
    
    public List<String> q9 (String codProduto, int n){
        List <String> result = new ArrayList<>(n);
        Map <String, Integer> numVendasAux = new HashMap<>();
        Map <String, Double> faturadoAux = new HashMap<>();
        int numVendas = 0;
        double faturado = 0.0;
        for (String codCliente : this.gestaoFilial.keySet()){
            Map <String, Matriz> produtos = this.gestaoFilial.get(codCliente);
            if (produtos.containsKey(codProduto)){
               Matriz meses = produtos.get(codProduto);
                for (int i = 0; i < numMeses; i++){
                    Fatura[] filiais = meses.get(i);
                    for (int j = 0; j < numFiliais; j++){
                        Fatura fatura = filiais[(j)];
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
                    sb.append("Cliente: " + codCliente + " ->  gastou " + faturadoAux.get(codCliente) + "€\n");
                    result.add(i, sb.toString());
                    numVendasAux.remove(codCliente);
                    break;
                }
            }
        }
        return result;
    }
    
    // Consultas estatísticas
    
    // Número total de compras por mês
    
    public String numTotalVendasMes(){
        StringBuilder sb = new StringBuilder();
        int total = 0;
        for(int i = 0;  i < numMeses ; i++){
            total = 0;
            for(String codClientes : this.gestaoFilial.keySet()){
                Map<String,Matriz> produtos = this.gestaoFilial.get(codClientes);
                for(String codProdutos : produtos.keySet()){
                    Fatura[] filiais = produtos.get(codProdutos).get(i);
                    for(int j = 0; j < numFiliais ; j++){
                        Fatura fatura = filiais[(j)];
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
    
    // Número de distintos clientes que compraram em cada mês (não interessa quantas vezes o cliente comprou) filial a filial
    
    */
            
    
}