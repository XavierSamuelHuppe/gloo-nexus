package Metier.Circuit;

import Metier.Carte.Point;
import Metier.Profil.Passager;
import java.io.Serializable;
import java.util.List;

public interface ConteneurPassagers{
    public int obtenirNombrePassagers();
    public List<Passager> debarquer(Point point);
    public List<Passager> embarquer(Circuit circuit);
    public void octroyer(List<Passager> passagers);
}
