package Metier.Circuit;

import Metier.Passager;

public interface ConteneurPassagers {
    public int obtenirNombrePassagers();
    public void ajouterPassager(Passager passager);
    public void retirerPassager(Passager passager);
}
