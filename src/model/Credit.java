package model;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class Credit {
    private long id;
    private Double capitale_Empr;
    private Integer nb_Mois;
    private Double taux_Mensuel;
    private String nom_Demandeur;
    private Double mensualite;

    @Override
    public String toString() {
        String creditStr = "\n=============================================================\n";
        creditStr+= "                       Credit N°"+getId()+"\n";
        creditStr+= "=> Nom du demandeur de credit : "+getNom_Demandeur()+"\n";
        creditStr+= "-------------------------------------------------------------\n";
        creditStr+= "=> Capital emprunte           : "+getCapitale_Empr()+"\n";
        creditStr+= "=> Nombre de mois             : "+getNb_Mois()+"\n";
        creditStr+= "=> Taux mensuel               : "+getTaux_Mensuel()+"\n";
        creditStr+= "-------------------------------------------------------------\n";
        creditStr+= "=> Mensualite                 : "+(getMensualite()==0.0 ? "Non calculee" : getMensualite()+" DH/Mois")+"\n";
        creditStr+= "=============================================================\n";
        return creditStr;
    }
}
