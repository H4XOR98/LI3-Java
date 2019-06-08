package MVC.Modelos;


/**
 * Enumeration class Meses - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum Meses
{
    Janeiro, Fevereiro, Março, Abril, Maio, Junho, Julho, Agosto, Setembro, Outubro, Novembro, Dezembro;
    
    public static Meses getMes(int valor){
        return values()[valor];
    }
}
