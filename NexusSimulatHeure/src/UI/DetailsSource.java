package UI;

public class DetailsSource {
    private String frequence;
    private String heureDebut;
    private String nbElementsGeneres;
    private String circuit;
    
    public DetailsSource(Metier.Source.Source s)
    {
        //frequence = ((Double)s.getFrequence()).toString();
        heureDebut = s.getheureDebut().toString();
        circuit = s.getCircuit().getNom();
    }
    
    public String obtenirStringPourAffichage()
    {
        return String.format("$1 : $2, $3", circuit, heureDebut, frequence);
    }
}
